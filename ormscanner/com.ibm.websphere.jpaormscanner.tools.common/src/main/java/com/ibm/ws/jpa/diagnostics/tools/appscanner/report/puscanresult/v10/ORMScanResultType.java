//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.15 at 02:23:12 PM CST 
//


package com.ibm.ws.jpa.diagnostics.tools.appscanner.report.puscanresult.v10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ORMScanResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ORMScanResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ormFilePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="URL" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="ormHash" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="scanHash" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ORMScanResultType", propOrder = {
    "ormFilePath",
    "url",
    "ormHash",
    "scanHash"
})
public class ORMScanResultType {

    @XmlElement(required = true)
    protected String ormFilePath;
    @XmlElement(name = "URL", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String url;
    @XmlElement(required = true)
    protected String ormHash;
    @XmlElement(required = true)
    protected String scanHash;

    /**
     * Gets the value of the ormFilePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrmFilePath() {
        return ormFilePath;
    }

    /**
     * Sets the value of the ormFilePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrmFilePath(String value) {
        this.ormFilePath = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURL() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURL(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the ormHash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrmHash() {
        return ormHash;
    }

    /**
     * Sets the value of the ormHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrmHash(String value) {
        this.ormHash = value;
    }

    /**
     * Gets the value of the scanHash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScanHash() {
        return scanHash;
    }

    /**
     * Sets the value of the scanHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScanHash(String value) {
        this.scanHash = value;
    }

}