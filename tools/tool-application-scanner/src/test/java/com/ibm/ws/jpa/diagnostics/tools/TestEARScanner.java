package com.ibm.ws.jpa.diagnostics.tools;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.ws.jpa.diagnostics.tools.appscanner.EARScanner;

public class TestEARScanner {
    private static File cDir;
    private static File resDir;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        cDir = new File(System.getProperty("user.dir"));
        resDir = new File(cDir, "../../test-jee-applications/jee7-simple-application/testapp-jee7-simple-app/target");
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
    public void test() throws Exception {
        final File testFile = new File(resDir, "TestApp_JEE7_Simple.ear");
        
        EARScanner ears = new EARScanner();
        ears.scanEarFile(testFile);
        
//        fail("Not yet implemented");
    }

}
