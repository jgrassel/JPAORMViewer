/*******************************************************************************
 * Copyright (c) 2011, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.ibm.ws.jpa.diagnostics.orm.ano;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ClassInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ClassInformationType;

public final class EntityMappingsScanner {
	public static EntityMappingsScannerResults scanTargetArchive(URL targetArchive, ClassLoader scannerCL) throws ClassScannerException {
		if (targetArchive == null || scannerCL == null) {
			throw new ClassScannerException("EntityMappingsScanner.scanTargetArchive cannot accept null arguments.");
		}

		EntityMappingsScanner ems = new EntityMappingsScanner(targetArchive, scannerCL);
		ClassInformationType cit = ems.scanTargetArchive();
		return new EntityMappingsScannerResults(cit, targetArchive);
	}

	private final URL targetArchive;
	private final ClassLoader scannerCL;

	private EntityMappingsScanner(URL targetArchive, ClassLoader scannerCL) {
		this.targetArchive = targetArchive;
		this.scannerCL = scannerCL;
	}

	private ClassInformationType scanTargetArchive() throws ClassScannerException {
		final HashSet<ClassInfoType> citSet = new HashSet<ClassInfoType>();

		/*
		 * The JPA Specification's PersistenceUnitInfo contract for getJarFileURLs() and getPersistenceUnitRoot() 
		 * makes the following mandate:
		 * 
		 * A URL will either be a file: URL referring to a jar file or referring to a directory
		 * that contains an exploded jar file, or some other URL from which an InputStream in jar 
		 * format can be obtained.
		 */
		final String urlProtocol = targetArchive.getProtocol();
		if ("file".equalsIgnoreCase(urlProtocol)) {
			// Protocol is "file", which either addresses a jar file or an exploded jar file
			// TODO: Implement file protocol handling
		} else {
			// InputStream will be in jar format.
			citSet.addAll(processJarFormatInputStreamURL(targetArchive));
		}

		//        // Scan the classes found in the referenced archive
		//        final Set<ClassInfoType> scannedClasses = new HashSet<ClassInfoType>();       
		//        for (final String className : classNames) {
		//            try {                
		//                final Class<?> targetClass = scannerCL.loadClass(className);
		//                scannedClasses.add(AsmClassAnalyzer.analyzeClass(targetClass));             
		//            } catch (ClassNotFoundException e) {
		//                // TODO: Properly log
		//                e.printStackTrace();
		//            }
		//        }

		ClassInformationType cit = new ClassInformationType();
		List<ClassInfoType> citList = cit.getClassInfo();
		citList.addAll(citSet);

		return cit;
	}

	private Set<ClassInfoType> processJarFormatInputStreamURL(URL jarURL) throws ClassScannerException {
		final HashSet<ClassInfoType> citSet = new HashSet<ClassInfoType>();

		try (JarInputStream jis  = new JarInputStream(jarURL.openStream(), false)) {
			JarEntry jarEntry = null; 
			while ((jarEntry = jis.getNextJarEntry()) != null ) {
				String name = jarEntry.getName();
				if (name != null && name.endsWith(".class")) {
					if (!name.contains("$")) {
						// Only regular, not inner, classes are candidates.
						name = name.substring(0, name.length() - 6).replace("/", ".");

						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						byte[] buffer = new byte[4096];
						int bytesRead = 0;
						while ((bytesRead = jis.read(buffer, 0, 4096)) > -1) {
							if (bytesRead > 0) {
								baos.write(buffer, 0, bytesRead);
							}
						}

						byte[] classByteCode = baos.toByteArray();
						citSet.add(AsmClassAnalyzer.analyzeClass(name, classByteCode));
					}
				}
			} 
		} catch (Throwable t) {
			throw new ClassScannerException(t);
		}

		return citSet;
	}
}
