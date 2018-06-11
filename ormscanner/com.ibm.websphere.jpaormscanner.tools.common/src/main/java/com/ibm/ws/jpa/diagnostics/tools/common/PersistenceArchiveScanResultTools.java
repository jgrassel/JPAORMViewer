package com.ibm.ws.jpa.diagnostics.tools.common;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.ibm.ws.jpa.diagnostics.tools.appscanner.report.pascanresult.v10.PersistentArchiveScanResultType;

public final class PersistenceArchiveScanResultTools {
    private static PersistenceArchiveScanResultTools singleton = null;
    
    public final static synchronized PersistenceArchiveScanResultTools get() throws JAXBException {
        if (singleton == null) {
            singleton = new PersistenceArchiveScanResultTools();
        }
        
        return singleton;
    }
    
    private final JAXBContext persistenceArchiveScanResultJAXBContext;
    
    private PersistenceArchiveScanResultTools() throws JAXBException {
        persistenceArchiveScanResultJAXBContext = JAXBContext.newInstance(Constants.PKG_PERSISTENCE_ARCHIVE_SCAN_RESULT);
    }
    
    public final void save(final PersistentArchiveScanResultType pasrt, final OutputStream os) throws JAXBException {
        final Marshaller marshaller = persistenceArchiveScanResultJAXBContext.createMarshaller();  
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(pasrt, os);
    }
    
    public final <T> T load(Class<T> type, InputStream is) throws JAXBException {
        if (type.equals(PersistentArchiveScanResultType.class)) {
            final Unmarshaller unmarshaller = persistenceArchiveScanResultJAXBContext.createUnmarshaller();
            final T pasrt = (T) unmarshaller.unmarshal(is);
            return pasrt;
        }
        
        throw new IllegalArgumentException("The type \"" + type + " is not supported.");        
    }
}
