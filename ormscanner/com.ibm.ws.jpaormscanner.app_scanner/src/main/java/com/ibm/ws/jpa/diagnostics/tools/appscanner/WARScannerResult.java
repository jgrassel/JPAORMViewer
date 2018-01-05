package com.ibm.ws.jpa.diagnostics.tools.appscanner;

import java.nio.file.Path;

/**
 * Results from the WARScanner.
 * 
 */
public class WARScannerResult {
    private final Path warFilePath;   
    private final String warFileName;
    
    // private PersistentArchiveScanResult webLibClassesPersistentArchiveScanResult = null; // Will be a list
    private PersistentArchiveScanResult webInfClassesPersistentArchiveScanResult = null;

    public WARScannerResult(Path warFilePath) {
        this.warFilePath = warFilePath;
        this.warFileName = warFilePath.getFileName().toString();
    }

    public final Path getWarFilePath() {
        return warFilePath;
    }
    
    public final String getWarFileName() {
        return warFileName;
    }

    final void setWebInfClassesPersistentArchiveScanResult(PersistentArchiveScanResult webInfClassesPersistentArchiveScanResult) {
        this.webInfClassesPersistentArchiveScanResult = webInfClassesPersistentArchiveScanResult;
    }

    public final PersistentArchiveScanResult getWebInfClassesPersistentArchiveScanResult() {
        return webInfClassesPersistentArchiveScanResult;
    }

    
}
