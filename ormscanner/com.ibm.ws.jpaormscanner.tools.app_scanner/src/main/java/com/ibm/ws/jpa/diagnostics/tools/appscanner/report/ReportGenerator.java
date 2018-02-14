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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.ibm.ws.jpa.diagnostics.orm.ano.EntityMappingsScannerResults;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ClassInformationType;
import com.ibm.ws.jpa.diagnostics.puparser.PersistenceParseResult;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_Persistence;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnit;
import com.ibm.ws.jpa.diagnostics.puscanner.PersistenceUnitScannerResults;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.LibraryJarReference;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.PersistentArchiveScanResult;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.WARScannerResult;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10.PersistenceParseResultType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10.PersistentArchiveScanResultType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10.RelativePathLibraryJarPathMapEntryType;
import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10.RelativePathLibraryJarPathMapType;

public final class ReportGenerator {
    
    public final static void generateWARScanReport(final WARScannerResult warScanResult, final OutputStream out) throws Exception {
        final PersistentArchiveScanResult pasr = warScanResult.getWebInfClassesPersistentArchiveScanResult();
        final PersistenceParseResult ppr = pasr.getPpr();
        final PUP_Persistence pupp = ppr.getPersistence();
        
        final List<PersistenceUnitScannerResults> puScanResultsList = Collections.unmodifiableList(pasr.getPuScanResultsList());
        
        final PersistentArchiveScanResultType pasrt = new PersistentArchiveScanResultType();
        final RelativePathLibraryJarPathMapType rpljpmt = new RelativePathLibraryJarPathMapType();
        final PersistenceParseResultType pprt = new PersistenceParseResultType();
        
        pasrt.setPersistenceUnitRootPath(pasr.getPuRootPath().toString());
        pasrt.setPersistenceUnitRootURL(pasr.getPersistenceUnitRootURL().toExternalForm());        
        pasrt.setRelativePathLibraryJarPathMap(rpljpmt);
        pasrt.setPersistence(pprt);
        
        pprt.setVersion(pupp.getVersion());
        
        final List<RelativePathLibraryJarPathMapEntryType> rpljpmetList = rpljpmt.getJarFile();
        final Map<String, LibraryJarReference> jfrm = pasr.getJarFileReferenceMap();
        for (Map.Entry<String, LibraryJarReference> entry : jfrm.entrySet()) {
            RelativePathLibraryJarPathMapEntryType rpljpmet = new RelativePathLibraryJarPathMapEntryType();
            rpljpmet.setRelativePath(entry.getKey());
            rpljpmet.setURL(entry.getValue().getJarPath().toUri().toString());
        }
        
        
        
        try (final ZipOutputStream zos = new ZipOutputStream(out)) {
            for (PUP_PersistenceUnit puppu : pupp.pup_getPersistenceUnit()) {
                Map<String, String> retTuple = processPersistenceUnit(puppu, zos); 
                
                final String puName = retTuple.get(KEY_PU_NAME);
                final String puPathRoot = retTuple.get(KEY_PU_REP_PATH);
                
                for (PersistenceUnitScannerResults pusr : puScanResultsList) {
                    if (pusr.getPersistenceUnitName().equals(puName)) {
                        processPersistenceScanResult(pusr, puPathRoot, zos);
                        break;
                    }
                }
            }            
            
            writeJPAScannerResults(pasrt, zos);             
        }
    }
    
    private static final void writeJPAScannerResults(final PersistentArchiveScanResultType pasrt, final ZipOutputStream zos) throws Exception {
        JAXBContext pusrv10 = JAXBContext.newInstance("com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10");
        final Marshaller marshaller = pusrv10.createMarshaller();  
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(pasrt, baos);
        
        ZipEntry ze = new ZipEntry("JPAScannerResult.xml"); // TODO: Better to have this be a constant ref
        ze.setSize(baos.size());
        
        zos.putNextEntry(ze);
        zos.write(baos.toByteArray(), 0, baos.size());
        zos.closeEntry();        
    }
    
    public static final String KEY_PU_NAME = "Persistence Unit Name";
    public static final String KEY_PU_REP_PATH = "Persistence Unit Report Path Root";
    
    private static final Map<String, String> processPersistenceUnit(final PUP_PersistenceUnit puppu, final ZipOutputStream zos) throws Exception {
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
        
        final ZipEntry ze = new ZipEntry(puXML);
        ze.setSize(baos.size());
        ze.setComment("Persistence Unit Name: " + puppu.getName());
        
        zos.putNextEntry(ze);
        zos.write(baos.toByteArray(), 0, baos.size());
        zos.closeEntry();
        
        HashMap<String, String> retTuples = new HashMap<String, String>();
        retTuples.put(KEY_PU_NAME, puName);
        retTuples.put(KEY_PU_REP_PATH, pathRoot);
        
        return Collections.unmodifiableMap(retTuples);
    }
    
    private static void processPersistenceScanResult(final PersistenceUnitScannerResults pusr, final String pathRoot, final ZipOutputStream zos) throws Exception {
        final String citPathRoot = pathRoot + "/classes/";
        
        final List<EntityMappingsScannerResults> emsrList = pusr.getClassScannerResults();
        for (final EntityMappingsScannerResults emsr : emsrList) {
            final ClassInformationType cit = emsr.getCit();
            
            final Map<String, Object> retVal = emsr.produceXMLWithHash();            
            final byte[] citAsXML = (byte[]) retVal.get(EntityMappingsScannerResults.KEY_CITXML);
            final String hash = (String) retVal.get(EntityMappingsScannerResults.KEY_MD5HASH);
            
            final ZipEntry ze = new ZipEntry(citPathRoot + hash + ".xml");
            ze.setSize(citAsXML.length);
            
            zos.putNextEntry(ze);
            zos.write(citAsXML, 0, citAsXML.length);
            zos.closeEntry();
        }
    }
}
