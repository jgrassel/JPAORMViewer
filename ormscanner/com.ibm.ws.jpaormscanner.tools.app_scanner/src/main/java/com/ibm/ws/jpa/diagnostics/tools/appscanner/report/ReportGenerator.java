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

package com.ibm.ws.jpa.diagnostics.tools.appscanner.report;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.ibm.ws.jpa.diagnostics.orm.ano.EntityMappingsScannerResults;
import com.ibm.ws.jpa.diagnostics.orm.xml.EntityMappingsDefinition;
import com.ibm.ws.jpa.diagnostics.orm.xml.entitymapping.IEntityMappings;
import com.ibm.ws.jpa.diagnostics.puparser.PersistenceParseResult;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_Persistence;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnit;
import com.ibm.ws.jpa.diagnostics.puscanner.PersistenceUnitScannerResults;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.LibraryJarReference;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.PersistentArchiveScanResult;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.WARScannerResult;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10.PersistenceParseResultType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10.PersistenceUnitType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10.PersistentArchiveScanResultType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10.RelativePathLibraryJarPathMapEntryType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10.RelativePathLibraryJarPathMapType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10.ClassScanResultType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10.ORMScanResultType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10.PersistenceUnitScanResultType;
import com.ibm.ws.jpa.diagnostics.utils.encapsulation.EncapsulatedData;
import com.ibm.ws.jpa.diagnostics.utils.encapsulation.EncapsulatedDataGroup;

public final class ReportGenerator {
    
    public final static void generateWARScanReport(final WARScannerResult warScanResult, final OutputStream out) throws Exception {
        final PersistentArchiveScanResult pasr = warScanResult.getWebInfClassesPersistentArchiveScanResult();
        final PersistenceParseResult ppr = pasr.getPpr();
        final PUP_Persistence pupp = ppr.getPersistence();
        final Map<String, URL> relPathLibJarPathMap = pasr.getRelativePathLibraryJarPathMap();
        
        final List<PersistenceUnitScannerResults> puScanResultsList = Collections.unmodifiableList(pasr.getPuScanResultsList());
        
        final PersistentArchiveScanResultType pasrt = new PersistentArchiveScanResultType();
        final RelativePathLibraryJarPathMapType rpljpmt = new RelativePathLibraryJarPathMapType();
        final PersistenceParseResultType pprt = new PersistenceParseResultType();
        
//        pasrt.setPersistenceUnitRootPath(pasr.getPuRootPath().toString());
//        pasrt.setPersistenceUnitRootURL(pasr.getPersistenceUnitRootURL().toExternalForm());        
//        pasrt.setRelativePathLibraryJarPathMap(rpljpmt);
        pasrt.setPersistence(pprt);
        
        pprt.setVersion(pupp.getVersion());
        final List<PersistenceUnitType> puDocList = pprt.getPersistenceUnitDocument();
        
        final List<RelativePathLibraryJarPathMapEntryType> rpljpmetList = rpljpmt.getJarFile();
        final Map<String, LibraryJarReference> jfrm = pasr.getJarFileReferenceMap();
        for (Map.Entry<String, LibraryJarReference> entry : jfrm.entrySet()) {
            RelativePathLibraryJarPathMapEntryType rpljpmet = new RelativePathLibraryJarPathMapEntryType();
            rpljpmet.setRelativePath(entry.getKey());
            rpljpmet.setURL(entry.getValue().getJarPath().toUri().toString());
        }
        
        EncapsulatedDataGroup edg = EncapsulatedDataGroup.createEncapsulatedDataGroup("jpa-report", "jpa-report");
        
        for (PUP_PersistenceUnit puppu : pupp.pup_getPersistenceUnit()) {
            final Map<String, String> retTuple = processPersistenceUnit(puppu, edg);                 
            final String puName = retTuple.get(KEY_PU_NAME);
            final String puPathRoot = retTuple.get(KEY_PU_REP_PATH);
            
            final PersistenceUnitType put = new PersistenceUnitType();
            put.setName(puName);
            put.setScanLoc(puPathRoot);
            puDocList.add(put);
            
            final PersistenceUnitScanResultType pusrt = new PersistenceUnitScanResultType();
            pusrt.setName(puName);
            
            for (PersistenceUnitScannerResults pusr : puScanResultsList) {
                if (pusr.getPersistenceUnitName().equals(puName)) {
                    processPersistenceScanResult(pusrt, pusr, puPathRoot, relPathLibJarPathMap, edg);
                    break;
                }
            }
            
            writeJPAPUScannerResults(pusrt, puPathRoot, edg);
        }            
        
        writeJPAScannerResults(pasrt, edg);   
        
        edg.writeToString(out);
    }
    
    private static final void writeJPAScannerResults(final PersistentArchiveScanResultType pasrt, 
            final EncapsulatedDataGroup edg) throws Exception {
        JAXBContext pusrv10 = JAXBContext.newInstance("com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10");
        final Marshaller marshaller = pusrv10.createMarshaller();  
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(pasrt, baos);
        
        EncapsulatedData ed = EncapsulatedData.createEncapsulatedData("JPAScannerResult.xml", 
                "JPAScannerResult.xml", baos.toByteArray());
        edg.putDataItem(ed);
    }
    
    private static final void writeJPAPUScannerResults(final PersistenceUnitScanResultType pusrt, 
            final String puPathRoot, final EncapsulatedDataGroup edg) throws Exception {
        JAXBContext pusrv10 = JAXBContext.newInstance("com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10");
        final Marshaller marshaller = pusrv10.createMarshaller();  
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(pusrt, baos);
        
        EncapsulatedData ed = EncapsulatedData.createEncapsulatedData(puPathRoot + "PersistenceUnitScanResult.xml", 
                puPathRoot + "PersistenceUnitScanResult.xml", baos.toByteArray());
        edg.putDataItem(ed);
    }
    
    public static final String KEY_PU_NAME = "Persistence Unit Name";
    public static final String KEY_PU_REP_PATH = "Persistence Unit Report Path Root";
    
    private static final Map<String, String> processPersistenceUnit(final PUP_PersistenceUnit puppu, final EncapsulatedDataGroup edg) throws Exception {
        final JAXBContext jaxbCtx = JAXBContext.newInstance(puppu.getClass());
        final Marshaller marshaller = jaxbCtx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();        
        final MessageDigest md = MessageDigest.getInstance("MD5");   
        final DigestOutputStream dos = new DigestOutputStream(baos, md);
               
        marshaller.marshal(puppu, dos);
        
        BigInteger digestBigInt = new BigInteger(1, md.digest());
        final String hashStr = digestBigInt.toString(16);
        
        final String puName = puppu.getName();
        final String pathRoot = hashStr + "/";
        final String puXML = pathRoot + "persistenceUnit.xml";
        
        EncapsulatedData ed = EncapsulatedData.createEncapsulatedData(puXML, puXML, baos.toByteArray());      
        edg.putDataItem(ed);
        
        HashMap<String, String> retTuples = new HashMap<String, String>();
        retTuples.put(KEY_PU_NAME, puName);
        retTuples.put(KEY_PU_REP_PATH, pathRoot);
        
        return Collections.unmodifiableMap(retTuples);
    }
    
    private static void processPersistenceScanResult(final PersistenceUnitScanResultType pusrt, 
            final PersistenceUnitScannerResults pusr, final String pathRoot, 
            final Map<String, URL> relPathLibJarPathMap, final EncapsulatedDataGroup edg) throws Exception {
//        final List<ClassScanResultType> csrtList = new ArrayList<ClassScanResultType>();
        final String emPathRoot = pathRoot + "ORM/";
        final String citPathRoot = pathRoot + "/classes/";
        
        final List<ORMScanResultType> ormScanResultList = pusrt.getOrmScanResult();
        
        final List<EntityMappingsDefinition> emdList = pusr.getEntityMappingsDefinitionsList();
        for (final EntityMappingsDefinition emd : emdList) {
            IEntityMappings ieMappings = emd.getEntityMappings();
            
            final JAXBContext jaxbCtx = JAXBContext.newInstance(ieMappings.getClass());
            final Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();        
            final MessageDigest md = MessageDigest.getInstance("MD5");   
            final DigestOutputStream dos = new DigestOutputStream(baos, md);
                   
            marshaller.marshal(ieMappings, dos);
            
            BigInteger digestBigInt = new BigInteger(1, md.digest());
            final String hashStr = digestBigInt.toString(16);
            
            
            ORMScanResultType ormScanResult = new ORMScanResultType();
            ormScanResultList.add(ormScanResult);
            ormScanResult.setScanHash(hashStr);
            ormScanResult.setURL(emd.getSource().toString());
            ormScanResult.setOrmHash(emd.getHash().toString(16));
            ormScanResult.setOrmFilePath("ORM/" + hashStr + ".xml");
            
            String name = emPathRoot + hashStr + ".xml";
            EncapsulatedData ed = EncapsulatedData.createEncapsulatedData(name, name, baos.toByteArray());
            edg.putDataItem(ed);
        }
        
        final List<ClassScanResultType> classScanResultList = pusrt.getClassScanResult();
        
        final List<EntityMappingsScannerResults> emsrList = pusr.getClassScannerResults();
        for (final EntityMappingsScannerResults emsr : emsrList) {
            final ClassScanResultType csrt = new ClassScanResultType();
            classScanResultList.add(csrt);
                        
            final Map<String, Object> retVal = emsr.produceXMLWithHash();            
            final byte[] citAsXML = (byte[]) retVal.get(EntityMappingsScannerResults.KEY_CITXML);
            final String hash = (String) retVal.get(EntityMappingsScannerResults.KEY_MD5HASH);
            
            final String name = citPathRoot + hash + ".xml";           
            csrt.setClassScanPath("classes/" + hash + ".xml");
            
            final URL targetArchiveURL = emsr.getTargetArchive();
            String jarFileLoc = null;
            if (!relPathLibJarPathMap.isEmpty()) {
                for (Map.Entry<String, URL> entry : relPathLibJarPathMap.entrySet()) {
                    if (entry.getValue().equals(targetArchiveURL)) {
                        jarFileLoc = entry.getKey();
                        break;
                    }
                }
            }
            if (jarFileLoc == null) {
                csrt.setLocation("PersistenceUnitRoot");
            } else {
                csrt.setLocation("JarFile");
                csrt.setLocation(jarFileLoc);
            }            
            
            EncapsulatedData ed = EncapsulatedData.createEncapsulatedData(name, name, citAsXML);
            edg.putDataItem(ed);
        }
        
    }
}
