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

package com.ibm.ws.jpaormscanner.tools.was_reader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.ClassInfoType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.ClassInformationType;
import com.ibm.ws.jpa.diagnostics.puparser.PersistenceParseResult;
import com.ibm.ws.jpa.diagnostics.puparser.PersistenceUnitParser;
import com.ibm.ws.jpa.diagnostics.utils.encapsulation.EncapsulatedData;
import com.ibm.ws.jpa.diagnostics.utils.encapsulation.EncapsulatedDataGroup;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;

@SuppressWarnings("restriction")
public class ORMLogData {
    public static ORMLogData createORMLogData(File file) throws Exception {
        ORMLogData logData =  new ORMLogData(file);
        logData.open();
        return logData;
    }
    
    public static ORMLogData createORMLogData(String data) throws Exception {
        ORMLogData logData =  new ORMLogData(data);
        logData.open();
        return logData;
    }
    
    private String data;
    private File file;
    private EncapsulatedDataGroup edg;
    
    private String persistenceUnitName;
    private String persistenceXml;
    private PersistenceParseResult puParseResult;
    
    private List<ScannedClassArchiveInfo> sciList = new ArrayList<ScannedClassArchiveInfo>();
    private List<EntityMappingsInfo> emiList = new ArrayList<EntityMappingsInfo>();
    private Map<TreeItem<String>, TabData> tabDataMap = new HashMap<TreeItem<String>, TabData>();
    
    private Tab associatedTab;
    
    private ORMLogData(File file) {
        this.file = file;
    }
    
    private ORMLogData(String data) {
        this.data = data;
    }
    
    private void open() throws Exception {
        if (file != null) {
            System.out.println("Opening file " + file.getAbsolutePath());
            try (FileInputStream fis = new FileInputStream(file)) {
                edg = EncapsulatedDataGroup.createEncapsulatedDataGroup(fis);
            }
        } else {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes())) {
                edg = EncapsulatedDataGroup.createEncapsulatedDataGroup(bais);
            }
        }
               
        // Process Persistence Unit Info
        persistenceUnitName = edg.getProperties().get("Persistence Unit Name");       
        loadPersistenceXml();
        puParseResult = PersistenceUnitParser.parsePersistenceUnit(persistenceXml.getBytes());
        
        // Process scanned classes
        processScannedClasses();
        
        // Process entity mappings
        processEntityMappings();
    }
    
    public File getFile() {
        return file;
    }
    
    public void associateTab(Tab tab) {
        this.associatedTab = tab;
    }
    
    public Tab getAssociatedTab() {
        return this.associatedTab;
    }
    
    // Persistence Unit Access
    
    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }
    
    public String getPersistenceXml() {
        return persistenceXml;
    }
    
    public ListView<String> getPropertiesListView() {
        ListView<String> propertiesListView = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList ( edg.getProperties().keySet());
        propertiesListView.setItems(items);
        return propertiesListView;
    }
    private void loadPersistenceXml() throws Exception {
        EncapsulatedData ed = edg.getDataItemByName("persistence.xml");
        if (ed != null) {
            persistenceXml = new String(ed.getData());      
        } else {
            persistenceXml = "<< The persistence.xml file is unavailable >>\n";
        }
         
    }
    
    // Scanned Classes Access
    public List<TreeItem<String>> getScannedClassesTreeItems() {
        ArrayList<TreeItem<String>> list = new ArrayList<TreeItem<String>>();
        
        for (ScannedClassArchiveInfo sci : sciList) {
            list.add(sci.treeItem);
        }
        
        return list;
    }
    
    private void processScannedClasses() throws Exception {
        final JAXBContext jaxbCtx = JAXBContext.newInstance("com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10");
        final Unmarshaller um = jaxbCtx.createUnmarshaller();
        
        final EncapsulatedDataGroup classesGroup = edg.getDataSubGroup("ClassScanner");
        final Set<String> dataItemsNameSet = classesGroup.getDataItemsNames();
        for (String name : dataItemsNameSet) {
            final EncapsulatedData ed = classesGroup.getDataItem(name);
            final ClassInformationType cit = (ClassInformationType) um.unmarshal(ed.getDataAsInputStream());
            final ScannedClassArchiveInfo scai = new ScannedClassArchiveInfo(ed, cit);
            sciList.add(scai);
            
            List<ClassInfoType> citList = cit.getClassInfo();
            for (ClassInfoType citItem : citList) {
                ScannedClassInfo sci = new ScannedClassInfo(scai, citItem);
                scai.classesMap.put(citItem.getClassName(), sci);
            }
        }
    }
    
    // Entity Mappings
    public List<TreeItem<String>> getEntityMappingsTreeItems() {
        ArrayList<TreeItem<String>> list = new ArrayList<TreeItem<String>>();
        
        for (EntityMappingsInfo emi : emiList) {
            list.add(emi.treeItem);
        }
        return list;
    }
    
    private void processEntityMappings() throws Exception {
        final EncapsulatedDataGroup entityMappingsGroup = edg.getDataSubGroup("EntityMappings");
        final Set<String> dataItemsNameSet = entityMappingsGroup.getDataItemsNames();
        for (String name : dataItemsNameSet) {
            final EncapsulatedData ed = entityMappingsGroup.getDataItem(name);
            String ormData = new String(ed.getData());
            
            EntityMappingsInfo emi = new EntityMappingsInfo(ed.getName(), ormData);
            emiList.add(emi);
        }
    }
    
    // Tab Data Management
    public String getTabData(TreeItem<String> item) {
        return tabDataMap.get(item).getData();
    }
    
    private interface TabData {
        public String getData();
    }
    
    private class ScannedClassArchiveInfo implements TabData {
        private EncapsulatedData ed;
        private ClassInformationType cit;
        private TreeItem<String> treeItem;
        private HashMap<String, ScannedClassInfo> classesMap = new HashMap<String, ScannedClassInfo>();
        
        private ScannedClassArchiveInfo(EncapsulatedData ed, ClassInformationType cit) {
            this.ed = ed;
            this.cit = cit;
            treeItem = new TreeItem<String>(ed.getName());
            tabDataMap.put(treeItem, this);
        }
        
        public String getData() {
            return cit.toString();
        }
    }
    
    private class ScannedClassInfo implements TabData {
        private ScannedClassArchiveInfo scai;
        private ClassInfoType cit;
        private TreeItem<String> treeItem;
        
        public ScannedClassInfo(ScannedClassArchiveInfo scai, ClassInfoType cit) {
            this.scai = scai;
            this.cit = cit;          
            treeItem = new TreeItem<String>(cit.getClassName());
            tabDataMap.put(treeItem, this);
            scai.treeItem.getChildren().add(treeItem);
        }
        
        public String getData() {
            return ClassInfoPresentor.process(cit);
        }
    }
    
    private class EntityMappingsInfo implements TabData {
        private String name;
        private String ormData;
        private TreeItem<String> treeItem;
        
        public EntityMappingsInfo(String name, String ormData) {
            this.name = name;
            this.ormData = ormData;
            treeItem = new TreeItem<String>(name);
            tabDataMap.put(treeItem, this);
        }
        
        public String getData() {
            return ormData;
        }
    }
}
