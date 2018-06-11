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

import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.ws.jpa.diagnostics.puparser.PersistenceParseResult;
import com.ibm.ws.jpa.diagnostics.puscanner.PersistenceUnitScannerResults;

/**
 * Retains the results of the persistence scan of an archive.
 *
 */
public class PersistentArchiveScanResult {
    // Scan Environment
    private final URL persistenceUnitRootURL;
    private final Path puRootPath;
    private final Map<String, URL> relativePathLibraryJarPathMap;
    
    private final Map<String, LibraryJarReference> jarFileReferenceMap = new HashMap<String, LibraryJarReference>();
    
    // Scan Results
    private final PersistenceParseResult ppr;
    private final List<PersistenceUnitScannerResults> puScanResultsList;
    
    public PersistentArchiveScanResult(Path puRootPath, URL persistenceUnitRootURL, Map<String, URL> relativePathLibraryJarPathMap, 
            PersistenceParseResult ppr, List<PersistenceUnitScannerResults> puScanResultsList) {
        // Save information about the environment for the scanning
        this.puRootPath = puRootPath;       
        this.persistenceUnitRootURL = persistenceUnitRootURL;
        this.relativePathLibraryJarPathMap = relativePathLibraryJarPathMap;
        
        // Save scan results
        this.ppr = ppr;
        this.puScanResultsList = puScanResultsList;
    }
    
    public void setJarFileReferenceMap(Map<String, LibraryJarReference> jarFileReferenceMap) {
        this.jarFileReferenceMap.clear();
        this.jarFileReferenceMap.putAll(jarFileReferenceMap);
    }

    public final URL getPersistenceUnitRootURL() {
        return persistenceUnitRootURL;
    }

    public final Path getPuRootPath() {
        return puRootPath;
    }

    public final Map<String, URL> getRelativePathLibraryJarPathMap() {
        return relativePathLibraryJarPathMap;
    }

    public final Map<String, LibraryJarReference> getJarFileReferenceMap() {
        return jarFileReferenceMap;
    }

    public final PersistenceParseResult getPpr() {
        return ppr;
    }

    public final List<PersistenceUnitScannerResults> getPuScanResultsList() {
        return puScanResultsList;
    }
    
    

}
