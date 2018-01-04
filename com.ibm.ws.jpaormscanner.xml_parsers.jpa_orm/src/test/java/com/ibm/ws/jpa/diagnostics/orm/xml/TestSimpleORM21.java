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

package com.ibm.ws.jpa.diagnostics.orm.xml;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.ws.jpa.diagnostics.orm.xml.EntityMappingsDefinition;
import com.ibm.ws.jpa.diagnostics.orm.xml.EntityMappingsFactory;
import com.ibm.ws.jpa.diagnostics.orm.xml.entitymapping.IEntity;
import com.ibm.ws.jpa.diagnostics.orm.xml.entitymapping.IEntityMappings;

public class TestSimpleORM21 {
    private static File cDir;
    private static File resDir;
    private static File emptyORM21File;
    private static File simpleORM21File;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        cDir = new File(System.getProperty("user.dir"));
        resDir = new File(cDir, "src/test/resources");
        emptyORM21File = new File(resDir, "empty-orm-2.1.xml");
        simpleORM21File = new File(resDir, "simple-orm-2.1.xml");
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
    public void testEmptyORM21() throws Exception {        
        EntityMappingsDefinition emd = EntityMappingsFactory.parseEntityMappings(emptyORM21File);
        assertNotNull(emd);
        assertEquals("2.1", emd.getVersion());
    }

    
    @Test
    public void testSimpleORM21() throws Exception {        
        EntityMappingsDefinition emd = EntityMappingsFactory.parseEntityMappings(simpleORM21File);
        assertNotNull(emd);
        assertEquals("2.1", emd.getVersion());
        
        IEntityMappings eMappings = emd.getEntityMappings();
        assertNotNull(eMappings);
        
        List<IEntity> entityList = eMappings.getEntityList();
        assertNotNull(entityList);
        assertEquals(1, entityList.size());
    }

}
