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
        final HashSet<String> classNames = new HashSet<String>();
        
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
            classNames.addAll(processJarFormatInputStreamURL(targetArchive));
        }
        
        // Scan the classes found in the referenced archive
        final Set<ClassInfoType> scannedClasses = new HashSet<ClassInfoType>();       
        for (final String className : classNames) {
            try {                
                final Class<?> targetClass = scannerCL.loadClass(className);
                scannedClasses.add(ClassAnalyzer.analyzeClass(targetClass));             
            } catch (ClassNotFoundException e) {
                // TODO: Properly log
                e.printStackTrace();
            }
        }
        
        ClassInformationType cit = new ClassInformationType();
        List<ClassInfoType> citList = cit.getClassInfo();
        citList.addAll(scannedClasses);
        
        return cit;
    }
    
    private Set<String> processJarFormatInputStreamURL(URL jarURL) throws ClassScannerException {
        final HashSet<String> classFiles = new HashSet<String>();
        
        try (JarInputStream jis  = new JarInputStream(jarURL.openStream(), false)) {
            JarEntry jarEntry = null; 
            while ((jarEntry = jis.getNextJarEntry()) != null ) {
                String name = jarEntry.getName();
                if (name != null && name.endsWith(".class")) {
                    if (!name.contains("$")) {
                        // Only regular, not inner, classes are candidates.
                        name = name.substring(0, name.length() - 6).replace("/", ".");
                        classFiles.add(name);
                    }
                }
            } 
        } catch (Throwable t) {
            throw new ClassScannerException(t);
        }

        return classFiles;
    }
}
