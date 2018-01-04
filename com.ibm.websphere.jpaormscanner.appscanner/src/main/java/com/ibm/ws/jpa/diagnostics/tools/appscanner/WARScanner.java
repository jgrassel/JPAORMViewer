package com.ibm.ws.jpa.diagnostics.tools.appscanner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipException;

import com.ibm.ws.jpa.diagnostics.puparser.PersistenceUnitParserException;
import com.ibm.ws.jpa.diagnostics.puscanner.PersistenceUnitScannerException;
import com.ibm.ws.jpa.diagnostics.utils.nio2streamhandler.Nio2FileSystemRegistryToken;
import com.ibm.ws.jpa.diagnostics.utils.nio2streamhandler.Nio2StreamService;

public class WARScanner extends AbstractScanner {
    public WARScannerResult scanWarFile(File warFile, ClassLoader parent, final List<LibraryJarReference> earLibPathList) throws ZipException, IOException, ApplicationParserException, PersistenceUnitParserException, PersistenceUnitScannerException {
        return scanWarFile(warFile.toPath(), parent, earLibPathList);
    }

    public WARScannerResult scanWarFile(Path warFilePath, ClassLoader parent, final List<LibraryJarReference> earLibPathList) throws ZipException, IOException, ApplicationParserException, PersistenceUnitParserException, PersistenceUnitScannerException {
        // Assets which require cleanup before exit
        final Workspace workSpace = createWorkspace(warFilePath.getFileName().toString(), warFilePath);        
        final List<LibraryJarReference> warLibLibraryJarRefList = new ArrayList<LibraryJarReference>();
        
        try {
            final Path tempWorkspace = workSpace.getWorkspaceRoot();
            final WARScannerResult result = new WARScannerResult(warFilePath);
            
            final Path webInfPath = tempWorkspace.resolve("WEB-INF");
            final Path webInfClassesPath = webInfPath.resolve("classes");
            final Path webInfLibPath = webInfPath.resolve("lib");

            final Set<Path> libPathSet = new HashSet<Path>(); // Contents of WEB-INF/lib
            final Set<Path> classesPathSet = new HashSet<Path>(); // Contents of WEB-INF/classes
            final Set<Path> webInfSet = new HashSet<Path>(); // Contents of WEB-INF that doesn't fall into the previous two categories

            scanDirectory(webInfLibPath, new HashSet<Path>(), libPathSet); // Find everything in WEB-INF/lib
            scanDirectory(webInfClassesPath, new HashSet<Path>(), classesPathSet); // Find everything in WEB-INF/classes

            final Set<Path> excludeSet = new HashSet<Path>();
            excludeSet.add(webInfClassesPath);
            excludeSet.add(webInfLibPath);
            scanDirectory(webInfPath, excludeSet, webInfSet); // Find everything else in WEB-INF other than lib and classes
            
            // Generate LibraryJarReference entries for everything in WEB-INF/lib            
            for (Path path : libPathSet) {
                try (FileSystem zfs = FileSystems.newFileSystem(path, null)) {
                    Nio2FileSystemRegistryToken token = Nio2StreamService.registerFileSystem(zfs);
                    LibraryJarReference ljr = new LibraryJarReference("lib", path.getFileName().toString(), path, token);
                }               
            }

            // Generate the ClassLoader to handle the war file
            final List<Path> warLibCLPathElements = new ArrayList<Path>();
            warLibCLPathElements.addAll(libPathSet);
            warLibCLPathElements.add(webInfClassesPath);
            final ApplicationClassLoader warLibClassLoader = (parent == null) 
                    ? new ApplicationClassLoader(warLibCLPathElements) 
                    : new ApplicationClassLoader(parent, warLibCLPathElements);

            /*
             * Scan for persistence units
             * 
             * Example 4:
             * app.ear
             *     war1.war
             *         WEB-INF/lib/warEntities.jar
             *         WEB-INF/lib/warPUnit.jar (with META-INF/persistence.xml )
             * 
             * persistence.xml contains:
             *     <jar-file>warEntities.jar</jar-file>
             *     
             * Example 5:
             * app.ear
             *     war2.war
             *         WEB-INF/lib/warEntities.jar
             *         WEB-INF/classes/META-INF/persistence.xml
             *
             * persistence.xml contains:
             *     <jar-file>lib/warEntities.jar</jar-file>
             *
             * Example 6:
             * app.ear
             *     lib/earEntities.jar
             *     war2.war
             *         WEB-INF/classes/META-INF/persistence.xml
             * 
             * persistence.xml contains:
             *     <jar-file>../../lib/earEntities.jar</jar-file>
             * 
             * Example 7:
             * app.ear
             *     lib/earEntities.jar
             *     war1.war
             *         WEB-INF/lib/warPUnit.jar (with META-INF/persistence.xml )
             * 
             * persistence.xml contains:
             *     <jar-file>../../../lib/earEntities.jar</jar-file>
             *
             */
            // Scan WEB-INF/lib jars for persistence units (and process <jar-file> examples 4 and 7)
            if (!libPathSet.isEmpty()) {
                final Map<String, URL> relativePathLibraryJarPathMap = new HashMap<String, URL>();
                
                // Example 4 <jar-file> mapping
                for (final LibraryJarReference ljr : warLibLibraryJarRefList) {
                    final String libJarName = ljr.getLibJarName();
                    final URL assetURL = ljr.getToken().url("/", true);
                    relativePathLibraryJarPathMap.put(libJarName, assetURL);
                }

                // Example 7 <jar-file> mapping
//                if (earLibPathList != null && earLibPathList.size() > 0) {
//                    for (final Path earLibPath : earLibPathList) {
//                        final String earLibJarName = earLibPath.getFileName().toString();
//                        final String earLibDirName = earLibPath.getParent().getFileName().toString();
//                        final String relativePathtoEarLibJar = "../../../" + earLibDirName + "/" + earLibJarName;
//                        relativePathLibraryJarPathMap.put(relativePathtoEarLibJar, earLibPath);
//                    }
//                }

                for (final Path path : libPathSet) {
                    // TODO Collect scanner result
                    scanPersistentArchive(path, warLibClassLoader, relativePathLibraryJarPathMap);
                }
            }

            // Scan WEB-INF/classes directory for persistence units
            final Path pXmlPath = webInfClassesPath.resolve("META-INF/persistence.xml");
            if (Files.exists(pXmlPath)) {
                final Map<String, URL> relativePathLibraryJarPathMap = new HashMap<String, URL>();
                final Map<String, LibraryJarReference> jarFileReferenceMap = new HashMap<String, LibraryJarReference>();
                
                // Example 5 <jar-file> mapping
                for (final LibraryJarReference ljr : warLibLibraryJarRefList) {
                    final String libJarName = ljr.getLibDirName() + "/" + ljr.getLibJarName();
                    final URL assetURL = ljr.getToken().url("/", true);
                    relativePathLibraryJarPathMap.put(libJarName, assetURL);
                    jarFileReferenceMap.put(libJarName, ljr);
                }

                // Example 6 <jar-file> mapping
//                if (earLibPathList != null && earLibPathList.size() > 0) {
//                    for (final Path earLibPath : earLibPathList) {
//                        final String earLibJarName = earLibPath.getFileName().toString();
//                        final String earLibDirName = earLibPath.getParent().getFileName().toString();
//                        final String relativePathtoEarLibJar = "../../" + earLibDirName + "/" + earLibJarName;
//                        relativePathLibraryJarPathMap.put(relativePathtoEarLibJar, earLibPath);
//                    }
//                }

                PersistentArchiveScanResult scanResult = scanPersistentArchive(webInfClassesPath, warLibClassLoader, 
                        relativePathLibraryJarPathMap);
                scanResult.setJarFileReferenceMap(jarFileReferenceMap);
                result.setWebInfClassesPersistentArchiveScanResult(scanResult);
                
                /*
                 * Future: Web Application info such as web.xml, web bindings and extensions.  Stuff currently
                 * out of scope.
                 */
            }

            return result;
        } finally {
            // Clean up warLibPathList - each entry has a zipFS ref which must be closed.
            for (final LibraryJarReference ljr : warLibLibraryJarRefList) {
                final Nio2FileSystemRegistryToken token = ljr.getToken();
                final FileSystem fs = token.getFileSystem();
                
                Nio2StreamService.deregisterFileSystem(token);
                try {
                    fs.close();
                } catch (Throwable t) {}
            }
            
            // Clean up workspace
            destroyWorkspace(workSpace);
        }        
    }



}
