package com.ibm.ws.jpa.diagnostics.tools;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.ws.jpa.diagnostics.tools.appscanner.PersistentArchiveScanResult;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.WARScanner;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.WARScannerResult;

public class TestWARScanner {
    private static File cDir;
    private static File resDir;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        cDir = new File(System.getProperty("user.dir"));
        resDir = new File(cDir, "../../test-jee-applications/jee7-simple-application/testapp-jee7-simple-webapp/build/libs");
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
    public void test() throws Exception{
        final File testFile = new File(resDir, "testapp-jee7-simple-webapp.war");
        final WARScanner warScanner = new WARScanner();
        final WARScannerResult result = warScanner.scanWarFile(testFile, null, null);
        assertNotNull(result);
        
        assertEquals(testFile.toPath(), result.getWarFilePath());
        assertEquals("testapp-jee7-simple-webapp.war", result.getWarFileName());
        
        final PersistentArchiveScanResult webInfClassesPersistentArchiveScanResult = result.getWebInfClassesPersistentArchiveScanResult();
        assertNotNull(webInfClassesPersistentArchiveScanResult);
        
    }

}
