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

import static org.junit.Assert.*;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.ws.jpa.diagnostics.orm.ano.EntityMappingsScannerResults;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.AnnotationElementType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.AnnotationInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ClassInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ClassInformationType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.FieldInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.FieldsType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.InterfacesType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.MethodInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.MethodsType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ModifierType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ModifiersType;
import com.ibm.ws.jpa.diagnostics.puparser.PersistenceParseResult;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_Persistence;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnit;
import com.ibm.ws.jpa.diagnostics.puscanner.PersistenceUnitScannerResults;

public class TestWARScanner {
    private static final String testAssetName = "testapp-jee7-simple-webapp.war";
    private static File cDir;
    private static File testDir;
    private static File testAssetDir;
    private static File testAsset;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        cDir = new File(System.getProperty("user.dir"));
        testDir = new File(cDir, "../../test");
        testAssetDir = new File(testDir, "jee7-simple-application/testapp-jee7-simple-webapp/build/libs/");
        testAsset = new File(testAssetDir, testAssetName);
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
    public void testWarScanner() throws Exception {
        assertTrue(testAsset.exists());

        WARScanner warScanner = new WARScanner();
        WARScannerResult scanResult = warScanner.scanWarFile(testAsset, null, null);
        assertNotNull(scanResult);
        
        assertEquals(testAssetName, scanResult.getWarFileName());
        assertEquals(testAsset.toPath(), scanResult.getWarFilePath());
        
        // Verify scan of war file's persistence.xml document
        PersistentArchiveScanResult asr = scanResult.getWebInfClassesPersistentArchiveScanResult();
        assertNotNull(asr);
        
        PersistenceParseResult ppr = asr.getPpr();
        assertNotNull(ppr);
        
        // Default hash alg is MD5, so war's persistence.xml hash should be cb92ae8a5f2ccd6fb11af6eb33b6b72d
        BigInteger hashBI = ppr.getHash();
        assertNotNull(hashBI);
        String hashStr = hashBI.toString(16);
        assertEquals("cb92ae8a5f2ccd6fb11af6eb33b6b72d", hashStr);
       
        // Fetch persistence.xml representation
        PUP_Persistence pupp = ppr.getPersistence();
        assertNotNull(pupp);
        assertEquals("2.1", pupp.getVersion());
        
        // Fetch list of persistence-units (should be 1)
        List<PUP_PersistenceUnit> puppList = pupp.pup_getPersistenceUnit();
        assertNotNull(puppList);
        assertEquals(1, puppList.size());
        
        // Test single persistence unit entry's information, including the default values for entries
        // not defined in the persistence unit.
        PUP_PersistenceUnit puppu = puppList.get(0);
        assertNotNull(puppu);
        validateSimpleWarPersistenceUnit(puppu);
        
        // Test Scanner Results
        List<PersistenceUnitScannerResults> puScanResultsList = asr.getPuScanResultsList();
        assertNotNull(puScanResultsList);
        assertEquals(1, puScanResultsList.size());
        PersistenceUnitScannerResults pusr = puScanResultsList.get(0);
        assertNotNull(pusr);
        validatePersistenceUnitScannerResults(pusr);
    }
    
    public static void validateSimpleWarPersistenceUnit(PUP_PersistenceUnit puppu) {
        // Test defined entries
        assertEquals("Test-Web-PU", puppu.getName());        
        assertEquals("jdbc/JTA_DS", puppu.getJtaDataSource());
        
        // Test undefined (default) entries
        assertNull(puppu.getProvider());
        assertNull(puppu.getNonJtaDataSource());
        assertNull(puppu.getDescription());
        assertNotNull(puppu.getJarFile());
        assertEquals(0, puppu.getJarFile().size());
        assertNotNull(puppu.getMappingFile());
        assertEquals(0, puppu.getMappingFile().size());
        assertNotNull(puppu.getClazz());
        assertEquals(0, puppu.getClazz().size());
        assertNotNull(puppu.pup_getProperties());
        assertEquals(0, puppu.pup_getProperties().size());
        assertNull(puppu.pup_getSharedCacheMode());
        assertNull(puppu.pup_getTransactionType()); 
    }
    
    public static void validatePersistenceUnitScannerResults(PersistenceUnitScannerResults puScanResult) throws Exception {
        assertEquals("Test-Web-PU", puScanResult.getPersistenceUnitName());
        assertNotNull(puScanResult.getpUnit());
        assertNotNull(puScanResult.getEntityMappingsDefinitionsList());
        assertNotNull(puScanResult.getClassScannerResults());
        
        assertEquals(0, puScanResult.getEntityMappingsDefinitionsList().size());
        
        List<EntityMappingsScannerResults> scanResultsList = puScanResult.getClassScannerResults();
        assertEquals(1, scanResultsList.size());
        
        EntityMappingsScannerResults scanResult = scanResultsList.get(0);
        assertNotNull(scanResult);
        
        assertNotNull(scanResult.getTargetArchive());
        
        ClassInformationType cit = scanResult.getCit();
        assertNotNull(cit);
        
        List<ClassInfoType> citList = cit.getClassInfo();
        assertNotNull(citList);
        assertEquals(2, citList.size());
        
        final String[] expectedClassNames = { 
                "com.ibm.jpascanner.testapp.jee7.simple.webapp.TestEntity", 
                "com.ibm.jpascanner.testapp.jee7.simple.webapp.TestServlet" 
        };
        
        final boolean[] passedExpectedClasses = { false, false };
        
        for (ClassInfoType citEntry : citList) {
            String clsName = citEntry.getClassName();
            assertNotNull(clsName);
            
            if (expectedClassNames[0].equals(clsName)) {
                // Testing com.ibm.jpascanner.testapp.jee7.simple.webapp.TestEntity               
                passedExpectedClasses[0] = verifyTestEntity(citEntry);
            } else if (expectedClassNames[1].equals(clsName)) {
                // Testing com.ibm.jpascanner.testapp.jee7.simple.webapp.TestServlet
                passedExpectedClasses[1] = true;
                
                List<AnnotationInfoType> attList = citEntry.getAnnotations().getAnnotation();
                assertNotNull(attList);
                assertEquals(1, attList.size());
            } else {
                fail("Found unexpected Class Entry " + clsName);
            }
            
        }
        
        int idx = 0;
        for (boolean b : passedExpectedClasses) {
            assertTrue("Test " + expectedClassNames[idx++], b);
        }
    }
    
    /**
     * Verifies expected ClassInfoType for type "com.ibm.jpascanner.testapp.jee7.simple.webapp.TestEntity"
     */
    private static boolean verifyTestEntity(ClassInfoType citEntry) throws Exception {
        // Verify that the class is Public, and that there are no other modifiers
        ModifiersType modType = citEntry.getModifiers();
        assertNotNull(modType);
        List<ModifierType> modList = modType.getModifier();
        assertNotNull(modList);
        assertEquals(1, modList.size());
        assertTrue(modList.contains(ModifierType.PUBLIC));    
        
        // Verify that the class has no declared interfaces
        InterfacesType ift = citEntry.getInterfaces();
        assertNull(ift);              

        // Verify class annotation @Entity
        List<AnnotationInfoType> attList = citEntry.getAnnotations().getAnnotation();
        assertNotNull(attList);
        assertEquals(1, attList.size());
        
        AnnotationInfoType ait = attList.get(0);
        assertNotNull(ait);
        assertEquals("Entity", ait.getName());
        assertEquals("javax.persistence.Entity", ait.getType());
        
        List<AnnotationElementType> aetList = ait.getElement();
        assertNotNull(aetList);
        assertEquals(0, aetList.size());
        
        // Verify Class Fields
        FieldsType fit = citEntry.getFields();
        assertNotNull(fit);
        
        List<FieldInfoType> fitList = fit.getField();
        assertNotNull(fitList);
        assertEquals(4, fitList.size());
        
        final String[] expectedFieldNames = { "id", "intData", "strData", "version" };
        final boolean[] expectedFieldNamesPassed = { false, false, false, false };
        
        final ModifierType[] expectedFieldModifiersArr = { ModifierType.PRIVATE };
        
        for (FieldInfoType fitEntry : fitList) {
            if (expectedFieldNames[0].equals(fitEntry.getName())) {
                // Testing "id"               
                assertEquals("long", fitEntry.getType());
                testExpectedModifiers(expectedFieldModifiersArr, fitEntry.getModifiers());
                
                // Verify field annotation @Id
                List<AnnotationInfoType> fattList = fitEntry.getAnnotations().getAnnotation();
                assertNotNull(fattList);
                assertEquals(1, fattList.size());
                
                AnnotationInfoType fait = fattList.get(0);
                assertNotNull(fait);
                assertEquals("Id", fait.getName());
                assertEquals("javax.persistence.Id", fait.getType());
                
                expectedFieldNamesPassed[0] = true;
            } else if (expectedFieldNames[1].equals(fitEntry.getName())) {
                // Testing "intData"
                assertEquals("int", fitEntry.getType());
                testExpectedModifiers(expectedFieldModifiersArr, fitEntry.getModifiers());
                
                // Verify field annotation @Basic
                List<AnnotationInfoType> fattList = fitEntry.getAnnotations().getAnnotation();
                assertNotNull(fattList);
                assertEquals(1, fattList.size());
                
                AnnotationInfoType fait = fattList.get(0);
                assertNotNull(fait);
                assertEquals("Basic", fait.getName());
                assertEquals("javax.persistence.Basic", fait.getType());
                
                expectedFieldNamesPassed[1] = true;
            } else if (expectedFieldNames[2].equals(fitEntry.getName())) {
                // Testing "strData"
                assertEquals("java.lang.String", fitEntry.getType());
                testExpectedModifiers(expectedFieldModifiersArr, fitEntry.getModifiers());
                
                // Verify field annotation @Basic
                List<AnnotationInfoType> fattList = fitEntry.getAnnotations().getAnnotation();
                assertNotNull(fattList);
                assertEquals(1, fattList.size());
                
                AnnotationInfoType fait = fattList.get(0);
                assertNotNull(fait);
                assertEquals("Basic", fait.getName());
                assertEquals("javax.persistence.Basic", fait.getType());
                
                expectedFieldNamesPassed[2] = true;
            } else if (expectedFieldNames[3].equals(fitEntry.getName())) {
                // Testing "version"
                assertEquals("long", fitEntry.getType());
                testExpectedModifiers(expectedFieldModifiersArr, fitEntry.getModifiers());
                
                // Verify field annotation @Version
                List<AnnotationInfoType> fattList = fitEntry.getAnnotations().getAnnotation();
                assertNotNull(fattList);
                assertEquals(1, fattList.size());
                
                AnnotationInfoType fait = fattList.get(0);
                assertNotNull(fait);
                assertEquals("Version", fait.getName());
                assertEquals("javax.persistence.Version", fait.getType());
                
                expectedFieldNamesPassed[3] = true;
            } else {
                fail("Found unexpected Field Entry " + fitEntry.getName());
            }
        }
        
        int fidx = 0;
        for (boolean b : expectedFieldNamesPassed) {
            assertTrue("Test " + expectedFieldNamesPassed[fidx++], b);
        }
        
        // Verify Class Methods
        MethodsType mit = citEntry.getMethods();
        assertNotNull(mit);
        
        List<MethodInfoType> mitList = mit.getMethod();
        assertNotNull(mitList);
        assertEquals(10, mitList.size());
        
        final String[] expectedMethodNames = { "<init>", "getId", "setId", "getIntData", "setIntData", "getStrData", 
                "setStrData", "getVersion", "setVersion", "toString" };
        final boolean[] expectedMethodNamesPassed = { false, false, false, false, false, false, false, false, false, false };
        
        final ModifierType[] expectedMethodModifiersArr = { ModifierType.PUBLIC };
        
        for (MethodInfoType mitEntry : mitList) {
            if (expectedMethodNames[0].equals(mitEntry.getMethodName())) {
                // Testing "init" (ctor)
                testExpectedModifiers(expectedMethodModifiersArr, mitEntry.getModifiers());
                
                expectedMethodNamesPassed[0] = true;
            } else if (expectedMethodNames[1].equals(mitEntry.getMethodName())) {
                // Testing "getId"
                testExpectedModifiers(expectedMethodModifiersArr, mitEntry.getModifiers());
                
                expectedMethodNamesPassed[1] = true;
            } else if (expectedMethodNames[2].equals(mitEntry.getMethodName())) {
                // Testing "setId"
                testExpectedModifiers(expectedMethodModifiersArr, mitEntry.getModifiers());
                
                expectedMethodNamesPassed[2] = true;
            } else if (expectedMethodNames[3].equals(mitEntry.getMethodName())) {
                // Testing "getIntData"
                testExpectedModifiers(expectedMethodModifiersArr, mitEntry.getModifiers());
                
                expectedMethodNamesPassed[3] = true;
            } else if (expectedMethodNames[4].equals(mitEntry.getMethodName())) {
                // Testing "setIntData"
                testExpectedModifiers(expectedMethodModifiersArr, mitEntry.getModifiers());
                
                expectedMethodNamesPassed[4] = true;
            } else if (expectedMethodNames[5].equals(mitEntry.getMethodName())) {
                // Testing "getStrData"
                testExpectedModifiers(expectedMethodModifiersArr, mitEntry.getModifiers());
                
                expectedMethodNamesPassed[5] = true;
            } else if (expectedMethodNames[6].equals(mitEntry.getMethodName())) {
                // Testing "setStrData"
                testExpectedModifiers(expectedMethodModifiersArr, mitEntry.getModifiers());
                
                expectedMethodNamesPassed[6] = true;
            } else if (expectedMethodNames[7].equals(mitEntry.getMethodName())) {
                // Testing "getVersion"
                testExpectedModifiers(expectedMethodModifiersArr, mitEntry.getModifiers());
                
                expectedMethodNamesPassed[7] = true;
            } else if (expectedMethodNames[8].equals(mitEntry.getMethodName())) {
                // Testing "setVersion"
                testExpectedModifiers(expectedMethodModifiersArr, mitEntry.getModifiers());
                
                expectedMethodNamesPassed[8] = true;
            } else if (expectedMethodNames[9].equals(mitEntry.getMethodName())) {
                // Testing "toString"
                testExpectedModifiers(expectedMethodModifiersArr, mitEntry.getModifiers());
                
                expectedMethodNamesPassed[9] = true;
            } else {
                fail("Unexpected method " + mitEntry.getMethodName() + " encountered.");
            }
        }
        
        int midx = 0;
        for (boolean b : expectedMethodNamesPassed) {
            assertTrue("Test " + expectedMethodNames[midx++], b);
        }
        
        return true;
    }

    private static void testExpectedModifiers(ModifierType[] expected, ModifiersType modType) throws Exception {
        assertNotNull(expected);
        assertNotNull(modType);
        
        List<ModifierType> modList = modType.getModifier();
        assertNotNull(modList);
        assertEquals(expected.length, modList.size());
        
        boolean[] found = new boolean[expected.length];
        Arrays.fill(found, false);
        
        for (ModifierType mt : modList) {
            for (int index = 0; index < expected.length; index++) {
                if (expected[index].equals(mt)) {
                    found[index] = true;
                }
            }
        }
        
        for (int index = 0; index < found.length; index++) {
            assertTrue("Did not find Modifier " + expected[index] + " in Modifiers list.", found[index]);
        }
    }
}
