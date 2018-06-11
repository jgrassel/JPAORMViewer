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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ApplicationClassLoader extends ClassLoader {
    private final List<Path> jarFileList;
    private URLClassLoader urlCL;

    public ApplicationClassLoader(List<Path> jarFileList) throws MalformedURLException {
        this.jarFileList = new ArrayList<Path>();
        this.jarFileList.addAll(jarFileList);
        
        initializeClassLoader();
    }

    public ApplicationClassLoader(ClassLoader parent, List<Path> jarFileList) throws MalformedURLException {
        super(parent);
        this.jarFileList = new ArrayList<Path>();
        this.jarFileList.addAll(jarFileList);
        
        initializeClassLoader();
    }
    
    private void initializeClassLoader() throws MalformedURLException {
        URL[] urls = new URL[jarFileList.size()];
        int index = 0;
        for (Path path : jarFileList) {
            urls[index++] = path.toUri().toURL();
        }
        
        urlCL = new URLClassLoader(urls);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            return urlCL.loadClass(name);
        } catch (ClassNotFoundException e) {
            return super.loadClass(name);
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            return urlCL.loadClass(name);
        } catch (ClassNotFoundException e) {
            return super.loadClass(name, resolve);
        }
    }

    @Override
    protected Object getClassLoadingLock(String className) {
        // TODO
        return super.getClassLoadingLock(className);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // TODO
        return super.findClass(name);
    }

    @Override
    public URL getResource(String name) {
        URL retVal = urlCL.getResource(name);
        if (retVal == null) {
            retVal = super.getResource(name);
        }
        
        return retVal;
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        Enumeration<URL> retVal = urlCL.getResources(name);
        if (retVal == null) {
            retVal = super.getResources(name);
        }
        
        return retVal;
    }

    @Override
    protected URL findResource(String name) {
        // TODO
        return super.findResource(name);
    }

    @Override
    protected Enumeration<URL> findResources(String name) throws IOException {
        // TODO
        return super.findResources(name);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        InputStream retVal = urlCL.getResourceAsStream(name);
        if (retVal == null) {
            retVal = super.getResourceAsStream(name);
        }
        
        return retVal;
    }

    @Override
    protected Package definePackage(String name, String specTitle, String specVersion, String specVendor,
            String implTitle, String implVersion, String implVendor, URL sealBase) throws IllegalArgumentException {
        // TODO
        return super.definePackage(name, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor, sealBase);
    }

    @Override
    protected Package getPackage(String name) {
        // TODO
        return super.getPackage(name);
    }

    @Override
    protected Package[] getPackages() {
        // TODO
        return super.getPackages();
    }

    @Override
    protected String findLibrary(String libname) {
        // TODO
        return super.findLibrary(libname);
    }

    @Override
    public void setDefaultAssertionStatus(boolean enabled) {
        urlCL.setDefaultAssertionStatus(enabled);
    }

    @Override
    public void setPackageAssertionStatus(String packageName, boolean enabled) {
        urlCL.setPackageAssertionStatus(packageName, enabled);
    }

    @Override
    public void setClassAssertionStatus(String className, boolean enabled) {
        urlCL.setClassAssertionStatus(className, enabled);
    }

    @Override
    public void clearAssertionStatus() {
        urlCL.clearAssertionStatus();
    }
}
