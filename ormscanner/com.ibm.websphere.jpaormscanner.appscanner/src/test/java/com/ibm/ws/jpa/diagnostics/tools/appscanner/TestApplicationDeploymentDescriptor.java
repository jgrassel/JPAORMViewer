package com.ibm.ws.jpa.diagnostics.tools.appscanner;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd.*;

public class TestApplicationDeploymentDescriptor {
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
    public void test() throws Exception {
        final File testFile = new File(resDir, "simple-application.xml");
        
        final ApplicationDeploymentDescriptor add = ApplicationDeploymentDescriptor.readApplicationDocument(testFile);
        assertNotNull(add);
        
        final List<Module> moduleList = add.getModuleList();
        assertNotNull(moduleList);
        assertEquals(2, moduleList.size());
        
        final String[] moduleIds = { "EjbModule_1", "WebModule_1" };
        final boolean[] foundModules = { false, false };
        
        for (final Module module : moduleList) {
            final String id = module.getModuleId();
            assertNotNull(id);
            int idx = -1;
            for (int index = 0; index < moduleIds.length; index++) {
                if (moduleIds[index].equalsIgnoreCase(id)) {
                    idx = index;
                    foundModules[idx] = true;
                    break;
                }
            }
            assertFalse(-1 == idx);
            
            if ("EjbModule_1".equalsIgnoreCase(id)) {
                // EJB Module
                assertEquals(ModuleType.ejb, module.getModuleType());
                EJBModule ejbModule = (EJBModule) module;
                assertEquals("SampleEJB.jar", ejbModule.getEjbUri());
            } else if ("WebModule_1".equalsIgnoreCase(id)) {
                // Web Module
                assertEquals(ModuleType.web, module.getModuleType());
                WebModule webModule = (WebModule) module;
                assertEquals("SampleWeb", webModule.getContextRoot());
                assertEquals("SampleWebApp.war", webModule.getWebUri());
            }
        }
        for (boolean b : foundModules) {
            assertTrue(b);
        }
        
        assertEquals("nondefaultlib/", add.getLibraryDirectory());
    }

}
