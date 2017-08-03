/*******************************************************************************
 * Copyright (c) 2011, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.ibm.ws.jpa.diagnostics.puscanner;

import java.util.Collections;
import java.util.List;

import javax.persistence.spi.PersistenceUnitInfo;

import com.ibm.ws.jpa.diagnostics.orm.ano.EntityMappingsScannerResults;
import com.ibm.ws.jpa.diagnostics.orm.xml.EntityMappingsDefinition;

public final class PersistenceUnitScannerResults {
    final private String applicationName;
    final private String moduleName;
    final private PersistenceUnitInfo pUnit;
    
    final private List<EntityMappingsDefinition> entityMappingsDefinitionsList;
    final private List<EntityMappingsScannerResults> classScannerResults;
       
    public PersistenceUnitScannerResults(String applicationName, String moduleName, PersistenceUnitInfo pUnit,
            List<EntityMappingsDefinition> entityMappingsDefinitionsList, List<EntityMappingsScannerResults> classScannerResults) {
        this.applicationName = applicationName;
        this.moduleName = moduleName;
        this.pUnit = pUnit;
                
        this.entityMappingsDefinitionsList = entityMappingsDefinitionsList;
        this.classScannerResults = classScannerResults;
    }

    public final String getApplicationName() {
        return applicationName;
    }

    public final String getModuleName() {
        return moduleName;
    }
    
    public final String getPersistenceUnitName() {
        return pUnit.getPersistenceUnitName();
    }

    public final PersistenceUnitInfo getpUnit() {
        return pUnit;
    }

    public final List<EntityMappingsDefinition> getEntityMappingsDefinitionsList() {
        return Collections.unmodifiableList(entityMappingsDefinitionsList);
    }

    public final List<EntityMappingsScannerResults> getClassScannerResults() {
        return Collections.unmodifiableList(classScannerResults);
    }

    @Override
    public String toString() {
        return "PersistenceUnitScannerResults [applicationName=" + applicationName + ", moduleName=" + moduleName
                + ", pUnit=" + pUnit.getPersistenceUnitName() 
                + ", entityMappingsDefinitionsList (size)=" + entityMappingsDefinitionsList.size()
                + ", classScannerResults (size)=" + classScannerResults.size() 
                + "]";
    }

    
}
