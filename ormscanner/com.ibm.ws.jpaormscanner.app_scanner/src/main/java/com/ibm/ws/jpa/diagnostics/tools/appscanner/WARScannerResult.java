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
