//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.09 at 03:58:13 PM CST 
//


package com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PersistentArchiveScanResult_QNAME = new QName("PersistentArchiveScanResult_1.0", "PersistentArchiveScanResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PersistentArchiveScanResultType }
     * 
     */
    public PersistentArchiveScanResultType createPersistentArchiveScanResultType() {
        return new PersistentArchiveScanResultType();
    }

    /**
     * Create an instance of {@link RelativePathLibraryJarPathMapEntryType }
     * 
     */
    public RelativePathLibraryJarPathMapEntryType createRelativePathLibraryJarPathMapEntryType() {
        return new RelativePathLibraryJarPathMapEntryType();
    }

    /**
     * Create an instance of {@link PersistenceParseResultType }
     * 
     */
    public PersistenceParseResultType createPersistenceParseResultType() {
        return new PersistenceParseResultType();
    }

    /**
     * Create an instance of {@link RelativePathLibraryJarPathMapType }
     * 
     */
    public RelativePathLibraryJarPathMapType createRelativePathLibraryJarPathMapType() {
        return new RelativePathLibraryJarPathMapType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersistentArchiveScanResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "PersistentArchiveScanResult_1.0", name = "PersistentArchiveScanResult")
    public JAXBElement<PersistentArchiveScanResultType> createPersistentArchiveScanResult(PersistentArchiveScanResultType value) {
        return new JAXBElement<PersistentArchiveScanResultType>(_PersistentArchiveScanResult_QNAME, PersistentArchiveScanResultType.class, null, value);
    }

}
