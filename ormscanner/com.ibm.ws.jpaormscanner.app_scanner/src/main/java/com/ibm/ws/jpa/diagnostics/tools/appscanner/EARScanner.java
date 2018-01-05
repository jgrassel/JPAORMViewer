package com.ibm.ws.jpa.diagnostics.tools.appscanner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import com.ibm.ws.jpa.diagnostics.puparser.PersistenceUnitParserException;
import com.ibm.ws.jpa.diagnostics.puscanner.PersistenceUnitScannerException;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd.ApplicationDeploymentDescriptor;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd.EJBModule;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd.Module;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd.ModuleType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd.WebModule;

public class EARScanner extends AbstractScanner {
    
    public void scanEarFile(File earFile) throws ZipException, IOException, ApplicationParserException, PersistenceUnitParserException, PersistenceUnitScannerException {       
        final Workspace workSpace = createWorkspace(earFile.getName(), earFile.toPath());
        final Path tempWorkspace = workSpace.getWorkspaceRoot();
               
        try {
            // Process application.xml, if any.
            final ApplicationDeploymentDescriptor appDD = scanApplicationDeploymentDescriptor(tempWorkspace);
            
            // Discover library jars, if any
            final Set<Path> earLibraryJarFilesSet = new HashSet<Path>();
            discoverLibraryJarFiles(appDD, tempWorkspace, earLibraryJarFilesSet);
            
            // Discover application module files
            final Set<Path> ejbModuleFilesSet = new HashSet<Path>();
            final Set<Path> webModuleFilesSet = new HashSet<Path>();
            discoverApplicationModuleFiles(appDD, tempWorkspace, ejbModuleFilesSet, webModuleFilesSet);
            
            // Generate a ClassLoader that mimics the Application ClassLoader -- that is, it loads library jars
            // and EJB jar contents
            final List<Path> appCLPathElements = new ArrayList<Path>();
            appCLPathElements.addAll(earLibraryJarFilesSet);
            appCLPathElements.addAll(ejbModuleFilesSet);
            final ApplicationClassLoader appClassLoader = new ApplicationClassLoader(appCLPathElements);
            
            // Scan the Library jars for persistence units
            for (final Path libJarPath : earLibraryJarFilesSet) {
                final URL libJarURL = libJarPath.toUri().toURL();
                final String pXmlURLStr = libJarURL.toString() + "!/META-INF/persistence.xml";
                final URL pXMLURL = new URL(pXmlURLStr);
                
                try (final ZipFile libZip = new ZipFile(libJarPath.toString())) {
                    final ZipEntry pXmlZipEntry = libZip.getEntry("META-INF/persistence.xml");
                    URL puRootURL = libJarPath.toUri().toURL();
//                    if (puRootURL.getProtocol().equals("file")) {
//                        // Change this to a jar-file URL                       
//                        puRootURL = new URL("jar:" + puRootURL.toString() + "!/");
//                    }
                    
//                    if (pXmlZipEntry != null) {
//                       try (InputStream pXmlIS = libZip.getInputStream(pXmlZipEntry)) {
//                           ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                           byte[] buffer = new byte[1024];
//                           int bytesRead = -1;
//                           while ((bytesRead = pXmlIS.read(buffer)) != -1) {
//                               baos.write(buffer, 0, bytesRead);
//                           }
//                           
//                           final byte[] puByteData = baos.toByteArray();
//                           PersistenceDefinition pd = PersistenceUnitParser.parsePersistenceUnit(pXMLURL, puByteData);
//                           System.out.println(pd);
//                           
//                           // Process each persistence Unit
//                           final PUP_Persistence persistence = pd.getPersistence();
//                           final String persistenceVersion = persistence.getVersion();
//                           final List<PUP_PersistenceUnit> puList = persistence.pup_getPersistenceUnit();
//                           
//                           for (PUP_PersistenceUnit pu : puList) {
//                               final PersistenceUnitInfo pUnit = new ScannerPersistenceUnitInfo(pu, puRootURL, appClassLoader, libJarPath, earLibraryJarFilesSet, persistenceVersion);
//                               System.out.println(pUnit);
//                               
//                               PersistenceUnitScannerResults puScanResults = PersistenceUnitScanner.scanPersistenceUnit("appName", "moduleName", pUnit);
//                               System.out.println(puScanResults);
//                               System.out.println();
//                           }
//                           
//                       }
//                    }
                }
            }
            
        } finally {
            // Clean up workspace
            destroyWorkspace(workSpace);
        }  
    }
    

    private ApplicationDeploymentDescriptor scanApplicationDeploymentDescriptor(final Path tempWorkspace) throws IOException, ApplicationParserException {
        final Path appXmlFile = Paths.get(tempWorkspace.toString(), "META-INF/application.xml");
        if (!Files.exists(appXmlFile)) {
            return null;
        }
        
        try (final InputStream is = Files.newInputStream(appXmlFile)) {
            return ApplicationDeploymentDescriptor.readApplicationDocument(is);
        }       
    }
    
    private void discoverLibraryJarFiles(final ApplicationDeploymentDescriptor appDD, final Path tempWorkspace, Set<Path> earLibraryJarFilesSet) throws IOException {
        final String earLibDir = (appDD == null) ? "lib/" : appDD.getLibraryDirectory();
        final Path earLibDirPath = Paths.get(tempWorkspace.toString(), earLibDir);
        if (Files.exists(earLibDirPath)) {
            Files.walkFileTree(earLibDirPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    // Only navigate one directory level, skip any further sub directories, if any.
                    if (FileVisitResult.CONTINUE == super.preVisitDirectory(dir, attrs) && earLibDirPath.equals(dir)) {
                        return FileVisitResult.CONTINUE;
                    }
                    return FileVisitResult.SKIP_SUBTREE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    FileVisitResult sFVR = super.visitFile(file, attrs);                       
                    if (FileVisitResult.CONTINUE == sFVR) {
                        earLibraryJarFilesSet.add(file);
                    }
                    return sFVR;
                }

            });
        }
    }
    
    private void discoverApplicationModuleFiles(final ApplicationDeploymentDescriptor appDD, final Path tempWorkspace,
            final Set<Path> ejbModuleFilesSet, final Set<Path> webModuleFilesSet) throws IOException {
        if (appDD != null) {
            final List<Module> declaredModuleList = appDD.getModuleList();
            for (Module m  : declaredModuleList) {
                if (m.getModuleType().equals(ModuleType.ejb)) {
                    EJBModule ejbModule = (EJBModule) m;
                    Path ejbPath = Paths.get(tempWorkspace.toString(), ejbModule.getEjbUri());
                    if (Files.exists(ejbPath)) {
                        ejbModuleFilesSet.add(ejbPath);
                    } else {
                        // TODO: Ut oh, application.xml declared an EJB module that doesn't exist!
                    }
                    
                } else if (m.getModuleType().equals(ModuleType.web)) {
                    WebModule webModule = (WebModule) m;
                    Path webPath = Paths.get(tempWorkspace.toString(), webModule.getWebUri());
                    if (Files.exists(webPath)) {
                        webModuleFilesSet.add(webPath);
                    } else {
                        // TODO: Ut oh, application.xml declared an WAR module that doesn't exist!
                    }
                }
            }
        } else {
            Files.walkFileTree(tempWorkspace, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    // Only navigate one directory level, skip any further sub directories, if any.
                    if (FileVisitResult.CONTINUE == super.preVisitDirectory(dir, attrs) && tempWorkspace.equals(dir)) {
                        return FileVisitResult.CONTINUE;
                    }
                    return FileVisitResult.SKIP_SUBTREE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    final FileVisitResult sFVR = super.visitFile(file, attrs);                       
                    if (FileVisitResult.CONTINUE == sFVR) {
                        final String fileName = file.getFileName().toString().toLowerCase();
                        if (fileName.endsWith(".jar")) {
                            ejbModuleFilesSet.add(file);
                        } else if (fileName.endsWith(".war")) {
                            webModuleFilesSet.add(file);
                        } else {
                            // TODO: Skip file with unrecognized extension
                        }
                    }
                    return sFVR;
                }
            });
        }
    }
    
    
}
