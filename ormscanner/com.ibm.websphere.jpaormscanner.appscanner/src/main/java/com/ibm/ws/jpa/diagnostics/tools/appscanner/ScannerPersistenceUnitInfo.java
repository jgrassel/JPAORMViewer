package com.ibm.ws.jpa.diagnostics.tools.appscanner;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnit;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnitCachingType;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnitTransactionType;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnitValidationModeType;

public class ScannerPersistenceUnitInfo implements PersistenceUnitInfo {
    private final PUP_PersistenceUnit pu;
    private final ClassLoader classLoader;
    private final Path targetArchive;
    private final Set<Path> libraryArchiveSet = new HashSet<Path>();
    private final String pXmlVersion;
    
    private final ArrayList<URL> jarUrlList = new ArrayList<URL>();
//    private final HashMap<URL, Path> jarUrlPathMap = new HashMap<URL, Path>();
    private final URL persistenceUnitRoot;
    
    public ScannerPersistenceUnitInfo(PUP_PersistenceUnit pu, URL persistenceUnitRoot, ClassLoader classLoader, 
            Path targetArchive, final Map<String, URL> relativePathLibraryJarPathMap, String pXmlVersion) 
        throws MalformedURLException {
        if (pu == null || persistenceUnitRoot == null || classLoader == null || targetArchive == null || libraryArchiveSet == null || pXmlVersion == null) {
            throw new NullPointerException("Constructor cannot accept any null value arguments.");
        }
        this.pu = pu;
        this.classLoader = classLoader;
        this.targetArchive = targetArchive;
        this.libraryArchiveSet.addAll(libraryArchiveSet);
        this.pXmlVersion = pXmlVersion;
        
        this.persistenceUnitRoot = persistenceUnitRoot;
        
        // Pre-Process Jar Files
        final List<String> jarFileNameList = pu.getJarFile();       
        for (String jarFileName : jarFileNameList) {
            boolean found = false;
            for (Map.Entry<String, URL> entry: relativePathLibraryJarPathMap.entrySet()) {
                if (entry.getKey().equals(jarFileName)) {
                    URL jarUrl = entry.getValue();
                    jarUrlList.add(jarUrl);
//                    jarUrlPathMap.put(jarUrl, entry.getValue());
                    found = true;
                }
            }
            
            if (!found) {
                System.err.println("Could not locate referenced jar file: " + jarFileName); // TODO: Report this better
            }            
        }
    }


    @Override
    public void addTransformer(ClassTransformer arg0) {
        // Doesn't need to do anything in the context of a tool
    }

    @Override
    public boolean excludeUnlistedClasses() {
        Boolean retVal = pu.isExcludeUnlistedClasses();
        return (retVal == null) ? false : retVal;
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public List<URL> getJarFileUrls() {
        return Collections.unmodifiableList(jarUrlList);
    }

    @Override
    public DataSource getJtaDataSource() {
        return null; // This method won't have a sensible return value in the context of tooling.
    }

    @Override
    public List<String> getManagedClassNames() {
        return Collections.unmodifiableList(pu.getClazz());
    }

    @Override
    public List<String> getMappingFileNames() {
        return Collections.unmodifiableList(pu.getMappingFile());
    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return classLoader;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return null; // This method won't have a sensible return value in the context of tooling.
    }

    @Override
    public String getPersistenceProviderClassName() {
        return pu.getProvider();
    }

    @Override
    public String getPersistenceUnitName() {
        return pu.getName();
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return persistenceUnitRoot;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return pXmlVersion;
    }

    @Override
    public Properties getProperties() {
        Map<String, String> puProps = pu.pup_getProperties();
        Properties p = new Properties();
        Set<Entry<String, String>> entrySet = puProps.entrySet();
        for (Entry<String, String> entry : entrySet) {
            p.setProperty(entry.getKey(), entry.getValue());
        }
        
        return p;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        final PUP_PersistenceUnitCachingType sharedCacheMode = pu.pup_getSharedCacheMode();
        switch (sharedCacheMode) {
            case ALL: 
                return SharedCacheMode.ALL;
            case NONE:
                return SharedCacheMode.NONE;
            case ENABLE_SELECTIVE:
                return SharedCacheMode.ENABLE_SELECTIVE;
            case DISABLE_SELECTIVE:
                return SharedCacheMode.DISABLE_SELECTIVE;
            case UNSPECIFIED:
                return SharedCacheMode.UNSPECIFIED;
        }
        
        return null;
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        final PUP_PersistenceUnitTransactionType tranType = pu.pup_getTransactionType();
        if (tranType == null) {
            return null; // Default depends on the runtime env, JTA in appserver, RL in JSE
        }
        
        switch (tranType) {
            case JTA:
                return PersistenceUnitTransactionType.JTA;
            case RESOURCE_LOCAL:
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
        }
        
        return null;
    }

    @Override
    public ValidationMode getValidationMode() {
        final PUP_PersistenceUnitValidationModeType valMode = pu.pup_getValidationMode();
        switch (valMode) {
            case AUTO:
                return ValidationMode.AUTO;
            case CALLBACK:
                return ValidationMode.CALLBACK;
            case NONE:
                return ValidationMode.NONE;
        }
        
        return null;
    }


    @Override
    public String toString() {
        ValidationMode vm = null;
        SharedCacheMode scm = null;
        try { 
            scm = getSharedCacheMode();
            vm = getValidationMode();
        } catch (Throwable t) {}
        
        return "ScannerPersistenceUnitInfo [excludeUnlistedClasses()=" + excludeUnlistedClasses()
                + ", getClassLoader()=" + getClassLoader() + ", getJarFileUrls()=" + getJarFileUrls()
                + ", getJtaDataSource()=" + getJtaDataSource() + ", getManagedClassNames()=" + getManagedClassNames()
                + ", getMappingFileNames()=" + getMappingFileNames() + ", getNewTempClassLoader()="
                + getNewTempClassLoader() + ", getNonJtaDataSource()=" + getNonJtaDataSource()
                + ", getPersistenceProviderClassName()=" + getPersistenceProviderClassName()
                + ", getPersistenceUnitName()=" + getPersistenceUnitName() + ", getPersistenceUnitRootUrl()="
                + getPersistenceUnitRootUrl() + ", getPersistenceXMLSchemaVersion()=" + getPersistenceXMLSchemaVersion()
                + ", getProperties()=" + getProperties() + 
                ", getSharedCacheMode()=" + scm
                + ", getTransactionType()=" + getTransactionType() + 
                
                ", getValidationMode()=" + vm
                + "]";
    }

}
