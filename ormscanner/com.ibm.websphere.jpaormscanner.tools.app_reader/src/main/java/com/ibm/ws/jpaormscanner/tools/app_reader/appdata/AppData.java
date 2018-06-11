package com.ibm.ws.jpaormscanner.tools.app_reader.appdata;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10.PersistentArchiveScanResultType;

public class AppData {
    public final static AppData loadAppData(Path pathToAppDataReportFile) throws Exception {
        final AppData retVal = new AppData();
        retVal.readReportFile(pathToAppDataReportFile);
        return retVal;
    }
    
    private AppData() {
        
    }


    private void readReportFile(Path file) throws Exception {
        if (!Files.exists(file)) {
            throw new FileNotFoundException("The file \"" + file.toString() + "\" does not exist.");
        }
        
        try (InputStream is = Files.newInputStream(file)) {
            WarModuleData wmd = WarModuleData.loadWarModuleData(is);
        }
       
        
//        try (final ZipFile zf = new ZipFile(file.toFile())) {
//            WarModuleData wmd = WarModuleData.loadWarModuleData(zf);
//        }        
    }
    
    
    private PersistentArchiveScanResultType loadJPAScannerResult (final ZipFile zf, final ZipEntry jpaScannerResultZE) throws Exception {
        final JAXBContext pusrv10 = JAXBContext.newInstance("com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10");
        final Unmarshaller unmarshaller = pusrv10.createUnmarshaller();
        
        final PersistentArchiveScanResultType pasrt = (PersistentArchiveScanResultType) unmarshaller.
                unmarshal(zf.getInputStream(jpaScannerResultZE));
        return pasrt;
    }
}
