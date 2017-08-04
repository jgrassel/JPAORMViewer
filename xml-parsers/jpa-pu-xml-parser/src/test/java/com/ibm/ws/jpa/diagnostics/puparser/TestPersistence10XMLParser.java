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

package com.ibm.ws.jpa.diagnostics.puparser;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_Persistence;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnit;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnitTransactionType;

public class TestPersistence10XMLParser {
    private static File cDir;
    private static File resDir;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        cDir = new File(System.getProperty("user.dir"));
        resDir = new File(cDir, "src/test/resources");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testJPA10() throws Exception {
        File testFile = new File(resDir, "persistence-1.0_01.xml");
        PersistenceParseResult pd = PersistenceUnitParser.parsePersistenceUnit(testFile);
        assertNotNull(pd);
        
        PUP_Persistence pxml = pd.getPersistence();
        assertNotNull(pxml);
        assertEquals("1.0", pxml.getVersion());
        
        List<PUP_PersistenceUnit> puList = pxml.pup_getPersistenceUnit();
        assertNotNull(puList);
        assertTrue(puList.size() == 1);
        
        PUP_PersistenceUnit pu = puList.get(0);
        assertNotNull(pu);
        
        assertEquals("testPU-001", pu.getName());
        assertEquals(PUP_PersistenceUnitTransactionType.JTA, pu.pup_getTransactionType());
        assertEquals("Test Persistence Unit", pu.getDescription());
        assertEquals("a.fictituos.provider", pu.getProvider());
        assertEquals("datasource/jta", pu.getJtaDataSource());
        assertEquals("datasource/non-jta", pu.getNonJtaDataSource());
        
        // Test mapping files
        List<String> mapFileList = pu.getMappingFile();
        assertNotNull(mapFileList);
        assertEquals(2, mapFileList.size());
        String[] mapNames = { "a.mapping.file", "another.mapping.file"};
        boolean[] foundMaps = { false, false };
        for (int index = 0; index < mapNames.length; index++) {
            if (mapNames[index].equals(mapFileList.get(index))) {
                foundMaps[index] = true;
            }
        }
        for (boolean b : foundMaps) {
            assertTrue(b);
        }
        
        // Test jar files
        List<String> jarFileList = pu.getJarFile();
        assertNotNull(jarFileList);
        assertEquals(2, jarFileList.size());
        String[] jarNames = { "a.jar.file", "another.jar.file"};
        boolean[] foundJars = { false, false };
        for (int index = 0; index < jarNames.length; index++) {
            if (jarNames[index].equals(jarFileList.get(index))) {
                foundJars[index] = true;
            }
        }
        for (boolean b : foundJars) {
            assertTrue(b);
        }
        
        // Test classes
        List<String> classList = pu.getClazz();
        assertNotNull(classList);
        assertEquals(2, classList.size());
        String[] classNames = { "test.entity.OneEntity", "test.entity.TwoEntity"};
        boolean[] foundClasses = { false, false };
        for (int index = 0; index < classNames.length; index++) {
            if (classNames[index].equals(classList.get(index))) {
                foundClasses[index] = true;
            }
        }
        for (boolean b : foundClasses) {
            assertTrue(b);
        }
        
        assertFalse(pu.isExcludeUnlistedClasses());
        
        Map<String, String> properties = pu.pup_getProperties();
        assertNotNull(properties);
        assertEquals(1, properties.size());
        assertTrue(properties.containsKey("a.property"));
        assertEquals(properties.get("a.property"), "a.value");
    }

}
