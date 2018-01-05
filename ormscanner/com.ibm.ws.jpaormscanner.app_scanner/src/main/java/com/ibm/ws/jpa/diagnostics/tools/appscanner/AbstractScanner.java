package com.ibm.ws.jpa.diagnostics.tools.appscanner;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.persistence.spi.PersistenceUnitInfo;

import com.ibm.ws.jpa.diagnostics.puparser.PersistenceParseResult;
import com.ibm.ws.jpa.diagnostics.puparser.PersistenceUnitParser;
import com.ibm.ws.jpa.diagnostics.puparser.PersistenceUnitParserException;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_Persistence;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnit;
import com.ibm.ws.jpa.diagnostics.puscanner.PersistenceUnitScanner;
import com.ibm.ws.jpa.diagnostics.puscanner.PersistenceUnitScannerException;
import com.ibm.ws.jpa.diagnostics.puscanner.PersistenceUnitScannerResults;

public abstract class AbstractScanner {
    /**
     * Creates a fresh workspace in the default temporary directory in the default file system.  
     * 
     * @param workspaceName - prefix to assign to the temporary directoroy
     * @return - A Path referencing the new temporary directory
     * @throws IOException - Thrown when an error occurs attempting to create the temporary directory
     */
    protected final Workspace createWorkspace(String workspaceName) throws IOException {
        Objects.nonNull(workspaceName);
        
        final Path tempWorkspace = Files.createTempDirectory(workspaceName + "_");
        Workspace workSpace = new Workspace(FileSystems.getDefault(), tempWorkspace);
        return workSpace;
    }
    
    /**
     * Creates a pre-populated workspace in the default temporary directory in the default file system.  
     * 
     * @param workspaceName - prefix to assign to the temporary directory
     * @return - A Path referencing the new temporary directory
     * @throws IOException - Thrown when an error occurs attempting to create the temporary directory
     */
    protected final Workspace createWorkspace(String workspaceName, Path initContents) throws IOException {
        Objects.nonNull(workspaceName);
        Objects.nonNull(initContents);
        
        final Path tempWorkspace = Files.createTempDirectory(workspaceName + "_");
        Workspace workSpace = new Workspace(FileSystems.getDefault(), tempWorkspace);
        
        if (Files.isDirectory(initContents)) {
            // Target is a directory, so copy its contents into the workspace temp dir
            Files.copy(initContents, workSpace.getWorkspaceRoot());
        } else {
            // Target is a file, assume is jar-format.  At least until greater intelligence is needed for this impl.           
            explodeArchiveFile(initContents, workSpace);
        }
        
        return workSpace;
    }
    
    /**
     * Deletes the entire contents of the workspace.
     * @param workspacePath - A Path referencing the temporary directory
     * @throws IOException - THrown when an error occurs attempting to delete the temporary directory's contents.
     */
    protected final void destroyWorkspace(Workspace workSpace) throws IOException {
        Objects.nonNull(workSpace);        
        workSpace.destroyWorkspace();
    }
    
    private final void explodeArchiveFile(final Path archiveFile, final Workspace workSpace) throws IOException {
        final Path tempWorkspace = workSpace.getWorkspaceRoot();
        
        try (final ZipFile zip = new ZipFile(archiveFile.toFile())) {
            final Enumeration<? extends ZipEntry> entryEnum = zip.entries();
            while (entryEnum.hasMoreElements()) {
                final ZipEntry zipEntry = entryEnum.nextElement();
                final String name = zipEntry.getName();
                
                if (zipEntry.isDirectory()) {
                    Path newDirPath = tempWorkspace.resolve(name);
                    if (!Files.exists(newDirPath)) {
                        Files.createDirectory(newDirPath);
                    }
                } else {
                    Path newFilePath = tempWorkspace.resolve(name);
                    
                    // Make sure the parent directory exists, MANIFEST.MF can appear ahead of the entry for META-INF dir
                    if (newFilePath.getParent() != null && !Files.exists(newFilePath.getParent())) {
                        Files.createDirectory(newFilePath.getParent());
                    }
                    
                    newFilePath = Files.createFile(newFilePath);
                    
                    OutputStream os = Files.newOutputStream(newFilePath);
                    try (InputStream is = zip.getInputStream(zipEntry)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead = -1;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                    }
                }               
            }
        }
    }
    
    protected final void scanDirectory(final Path dirPath, final Set<Path> excludesSet, final Set<Path> resultsPathSet) throws IOException {
        if (dirPath == null || resultsPathSet == null || excludesSet == null) {
            throw new NullPointerException("ScanDirectory cannot accept null arguments.");
        }
         
        if (Files.exists(dirPath)) {
            Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (excludesSet.contains(dir)) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (excludesSet.contains(file)) {
                        return FileVisitResult.CONTINUE;
                    }
                    
                    FileVisitResult sFVR = super.visitFile(file, attrs);                       
                    if (FileVisitResult.CONTINUE == sFVR) {
                        resultsPathSet.add(file);
                    }
                    return sFVR;
                }
            });
        }
    }
    
    protected PersistentArchiveScanResult scanPersistentArchive(final Path puRootPath, final ClassLoader classLoader,  
            final Map<String, URL> relativePathLibraryJarPathMap) 
        throws IOException, PersistenceUnitParserException, PersistenceUnitScannerException {    
        final PersistenceParseResult ppr = readPersistenceXmlFile(puRootPath);
        if (ppr == null) {
            // No persistence.xml was found in the target path
            return null;
        }
        
        /*
         * Found a persistence.xml file.  Process each persistence Unit
         */
        final URL persistenceUnitRootURL = puRootPath.toUri().toURL();
        final PUP_Persistence persistence = ppr.getPersistence();
        final String persistenceVersion = persistence.getVersion();
        final List<PUP_PersistenceUnit> puList = persistence.pup_getPersistenceUnit();
        final List<PersistenceUnitScannerResults> puScanResultsList = new ArrayList<PersistenceUnitScannerResults>();
        
        for (PUP_PersistenceUnit pu : puList) {
            final PersistenceUnitInfo pUnit = new ScannerPersistenceUnitInfo(pu, persistenceUnitRootURL, classLoader, 
                    puRootPath, relativePathLibraryJarPathMap, persistenceVersion);
            
            PersistenceUnitScannerResults puScanResults = PersistenceUnitScanner.scanPersistenceUnit(pUnit);
            puScanResultsList.add(puScanResults);
        }
        
        PersistentArchiveScanResult retVal = new PersistentArchiveScanResult(puRootPath, persistenceUnitRootURL, 
                relativePathLibraryJarPathMap, ppr, puScanResultsList);
        return retVal;
    }
    
    /**
     * Reads the persistence.xml at the specified persistence unit root.
     * 
     * @param archivePath - the potential persistence unit root.  Scanner will look for "META-INF/persistence.xml" 
     *                      within the archivePath.  (Note: archivePath must not specifically target persistence.xml file)
     * @return PersistenceParseResult containing the parsed persistence.xml file if the file exists.  Returns null
     *         if there is no META-INF/persistence.xml file or if the argument was null.
     * 
     * @throws IOException - An error occurred reading from the filesystem
     * @throws PersistenceUnitParserException - An error occurred during parsing
     */
    private PersistenceParseResult readPersistenceXmlFile(final Path archivePath) throws IOException, PersistenceUnitParserException {
        if (archivePath == null || !Files.exists(archivePath)) {
            return null;
        }
        
        if (Files.isDirectory(archivePath)) {
            // Exploded archive file, so navigate the regular file system
            final Path pXmlPath = archivePath.resolve("META-INF/persistence.xml");
            if (!Files.exists(pXmlPath)) {
                // No persistence.xml to scan in this archive.
                return null;
            }
            try (InputStream is = Files.newInputStream(pXmlPath)) {
                PersistenceParseResult ppr = PersistenceUnitParser.parsePersistenceUnit(is);
                return ppr;
            }
        } else {
            // Path addresses a file, so must be a jar/zip format file.
            try (FileSystem zfs = FileSystems.newFileSystem(archivePath.toUri(), new HashMap<String,String>())) {
                Path pXml = zfs.getPath("META-INF/persistence.xml");
                if (Files.exists(pXml)) {
                    try (InputStream is = Files.newInputStream(pXml)) {
                        PersistenceParseResult ppr = PersistenceUnitParser.parsePersistenceUnit(is);
                        return ppr;
                    }
                }
                
            }
        }        
        
        return null;
    }
    
    protected final class Workspace {
        private FileSystem fs;
        private Path workspaceRoot;
        private boolean destroyed = false;
        
        private Workspace(FileSystem fs, Path workspaceRoot) {
            this.fs = fs;
            this.workspaceRoot = workspaceRoot;
        }
        
        public Path getWorkspaceRoot() {
            if (destroyed) {
                throw new IllegalStateException("Workspace has been destroyed.");
            }
            return workspaceRoot;
        }
        
        private final void destroyWorkspace() throws IOException {
            if (destroyed) {
                return;
            }
            destroyed = true;
            
            Files.walkFileTree(workspaceRoot, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}
