package com.ibm.ws.jpaormscanner.tools.app_reader.appdata;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10.PersistentArchiveScanResultType;
import com.ibm.ws.jpa.diagnostics.tools.common.PersistenceArchiveScanResultTools;
import com.ibm.ws.jpa.diagnostics.utils.encapsulation.EncapsulatedData;
import com.ibm.ws.jpa.diagnostics.utils.encapsulation.EncapsulatedDataGroup;

public class WarModuleData {
    public static WarModuleData loadWarModuleData(ZipFile zf) throws Exception {
        final WarModuleData wmd = new WarModuleData();
        wmd.load(zf);
        return wmd;
    }
    
    public static WarModuleData loadWarModuleData(InputStream is) throws Exception {
        final WarModuleData wmd = new WarModuleData();
        wmd.load(is);
        return wmd;
    }
    
    private PersistentArchiveScanResultType pasrt = null;
    
    private WarModuleData() {
        
    }
    
    public PersistentArchiveScanResultType getPasrt() {
        return pasrt;
    }
    
    private void load(InputStream is) throws Exception {
        EncapsulatedDataGroup edg = EncapsulatedDataGroup.createEncapsulatedDataGroup(is);
        EncapsulatedData ed = edg.getDataItem("JPAScannerResult.xml");
        pasrt = PersistenceArchiveScanResultTools.get().load(PersistentArchiveScanResultType.class, ed.getDataAsInputStream());
    }
    
    private void load(ZipFile zf) throws Exception {
        final ZipEntry jpaScannerResultZE = zf.getEntry("JPAScannerResult.xml");
        if (jpaScannerResultZE == null) {
            throw new IllegalArgumentException();
        }
        
        pasrt = PersistenceArchiveScanResultTools.get().load(PersistentArchiveScanResultType.class, zf.getInputStream(jpaScannerResultZE));
    }
    

}
