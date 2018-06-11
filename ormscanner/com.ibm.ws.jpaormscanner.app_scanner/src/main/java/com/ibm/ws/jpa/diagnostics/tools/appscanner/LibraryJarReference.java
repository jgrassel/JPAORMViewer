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
import java.util.Objects;

import com.ibm.ws.jpa.diagnostics.utils.nio2streamhandler.Nio2FileSystemRegistryToken;

public class LibraryJarReference {
    private String libDirName;
    private String libJarName;
    
    private Path jarPath;
    
    private Nio2FileSystemRegistryToken token;

    public LibraryJarReference(String libDirName, String libJarName, Path jarPath, Nio2FileSystemRegistryToken token) {
        super();
        
        Objects.nonNull(libDirName);
        Objects.nonNull(libJarName);
        Objects.nonNull(jarPath);
        Objects.nonNull(token);
        
        this.libDirName = libDirName;
        this.libJarName = libJarName;
        this.jarPath = jarPath;
        this.token = token;
    }

    public final String getLibDirName() {
        return libDirName;
    }

    public final String getLibJarName() {
        return libJarName;
    }

    public final Path getJarPath() {
        return jarPath;
    }

    public final Nio2FileSystemRegistryToken getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "LibraryJarReference [libDirName=" + libDirName + ", libJarName=" + libJarName + ", jarPath=" + jarPath
                + ", token=" + token + "]";
    }
    
    

}
