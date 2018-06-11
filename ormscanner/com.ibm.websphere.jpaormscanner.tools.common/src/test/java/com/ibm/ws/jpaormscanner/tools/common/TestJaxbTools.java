package com.ibm.ws.jpaormscanner.tools.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10.PersistenceParseResultType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10.PersistenceUnitType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10.PersistentArchiveScanResultType;
import com.ibm.ws.jpa.diagnostics.tools.common.PersistenceArchiveScanResultTools;

public class TestJaxbTools {
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
    public void testPersistenceArchiveScanResultTools() throws Exception {
        final File testFile = new File(resDir, "PersistenceArchiveScanResult.xml");        
        final PersistenceArchiveScanResultTools tool = PersistenceArchiveScanResultTools.get();
        
        try (final FileInputStream fis = new FileInputStream(testFile)) {
            PersistentArchiveScanResultType pasrt = tool.load(PersistentArchiveScanResultType.class, fis);
            assertNotNull(pasrt);
            
            PersistenceParseResultType pprt  = pasrt.getPersistence();
            assertNotNull(pprt);
            
            assertEquals("2.1", pprt.getVersion());
            
            List<PersistenceUnitType> putList = pprt.getPersistenceUnitDocument();
            assertNotNull(putList);
            assertEquals(1, putList.size());
            
            PersistenceUnitType put = putList.get(0);
            assertNotNull(put);
            assertEquals("Test-Web-PU", put.getName());
            assertEquals("a1de4dced19ce57b311eaacd11384d98/", put.getScanLoc());
        }
        
    }
}
