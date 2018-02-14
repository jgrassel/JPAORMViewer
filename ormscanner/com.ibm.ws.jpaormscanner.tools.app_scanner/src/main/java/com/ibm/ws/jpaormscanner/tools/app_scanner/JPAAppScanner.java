/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.ibm.ws.jpaormscanner.tools.app_scanner;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.ibm.ws.jpa.diagnostics.tools.appscanner.WARScanner;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.WARScannerResult;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.ReportGenerator;

public class JPAAppScanner {

    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            usage();          
        }
        
        // Get the path to the application archive from the argument.
        final Path appFilePath = Paths.get(args[0]);
        if (!Files.exists(appFilePath)) {
            System.err.println("The file \"" + appFilePath + "\" does not exist.");
            System.exit(1);
        }
        
        // Get the path for the report file
        final Path reportFilePath = Paths.get(args[1]);
        
        final JPAAppScanner jpaAppScanner = new JPAAppScanner(appFilePath, reportFilePath);
        try {
            jpaAppScanner.scan();
        } catch (Throwable t) {
            System.err.println("An unrecoverable error has occurred.");
            t.printStackTrace();
            System.exit(1);
        }        
    }
    
    private static void usage() {
        System.err.println("Usage: <path to EAR, JAR, or WAR file> <path to generated report file>");
        System.exit(1);
    }
    
    private final Path archivePath;
    private final Path reportFilePath;
    
    private JPAAppScanner(Path archivePath, Path reportFilePath) {
        this.archivePath = archivePath;
        this.reportFilePath = reportFilePath;
    }
    
    private enum ArchiveType {
        EAR,
        JAR,
        WAR,
        UNKNOWN;
    }
    
    private void scan() throws Exception {
        System.out.println("Starting scan of \"" + archivePath + "\" ...");
        
        final ArchiveType archiveType = identifyArchiveType();
        System.out.println("Determined that the archive is of type: " + archiveType);
        if (archiveType == ArchiveType.UNKNOWN) {
            System.err.println("The scanner is not able to work with archives it cannot recognise.");
            System.exit(1);
        }
        
        if (archiveType == ArchiveType.WAR) {
            System.out.println("Analyzing stand-alone WAR application ...");
            final WARScanner warScanner = new WARScanner();
            final long startTimeMilli = System.currentTimeMillis();
            final WARScannerResult scanResult = warScanner.scanWarFile(archivePath, null, null);
            final long finishTimeMilli = System.currentTimeMillis();
            System.out.println("Analysis of standalone WAR application completed (" + 
                    (finishTimeMilli - startTimeMilli) + 
                    " ms).");
            System.out.println();
            
            try (final OutputStream os = Files.newOutputStream(reportFilePath)) {
                ReportGenerator.generateWARScanReport(scanResult, os);
            }
            
            
        }
    }
    
    private ArchiveType identifyArchiveType() throws Exception {
        // Identify the type of archive we are scanning.  If it's a EAR, JAR, or WAR.
        ArchiveType archiveType = ArchiveType.UNKNOWN;
        
        // Easiest thing to do is to check the file extension.
        final String fileName = archivePath.getFileName().toString();
        if (fileName.length() > 4) {
            final String extension = fileName.substring(fileName.length() - 4);
            if (extension.equalsIgnoreCase(".ear")) {
                archiveType = ArchiveType.EAR;
            } else if (extension.equalsIgnoreCase(".war")) {
                archiveType = ArchiveType.WAR;
            } else if (extension.equalsIgnoreCase(".jar")) {
                archiveType = ArchiveType.JAR;
            }
        }
        
        if (archiveType == ArchiveType.UNKNOWN) {
            // Was not able to identify the archive type by looking at its file extension.  We'll have
            // to look inside.
        
            if (Files.isRegularFile(archivePath)) {
                // Is a regular file, which means it should be in a jar (zip) file format.
                try (final ZipFile zip = new ZipFile(archivePath.toFile())) {
                    final Enumeration<? extends ZipEntry> entryEnum = zip.entries();
                    while (entryEnum.hasMoreElements()) {
                        final ZipEntry zipEntry = entryEnum.nextElement();
                        final String name = zipEntry.getName();
                        
                        if ("META-INF/application.xml".equals(name)) {
                            // This is an EAR file.
                            archiveType = ArchiveType.EAR;
                            break;
                        } if (name.startsWith("WEB-INF/")) {
                            // This is an WAR file.
                            archiveType = ArchiveType.WAR;
                            break;
                        }
                    }
                }
                
                if (archiveType == null) {
                    // Processed through the zip archive, and didn't find anything to give us a clue on what
                    // the archive is, so default to jar
                    archiveType = ArchiveType.JAR;
                }
            } else if (Files.isDirectory(archivePath)) {
                // Possibly an exploded archive
                // TODO: Implement analyzing an exploded archive to determine its application type.
            } else {
                // Whatever this Path points to, we can't work with.
                System.err.println("The file \"" + archivePath + "\" isn't a file this tool can consume.");
                System.exit(1);
            }
        }  
        
        return archiveType;
    }

}
