//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.08 at 03:24:59 PM CST 
//


package com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParameterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParameterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="annotations" type="{ClassInfo_1.0}AnnotationsType" minOccurs="0"/>
 *         &lt;element name="properties" type="{ClassInfo_1.0}PropertiesType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="isPrimitive" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isArray" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="arrayDimensions" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="isBridge" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isSynthetic" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isVarArgs" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isDefault" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterType", propOrder = {
    "annotations",
    "properties"
})
public class ParameterType {

    protected AnnotationsType annotations;
    protected PropertiesType properties;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "type", required = true)
    protected String type;
    @XmlAttribute(name = "isPrimitive", required = true)
    protected boolean isPrimitive;
    @XmlAttribute(name = "isArray")
    protected Boolean isArray;
    @XmlAttribute(name = "arrayDimensions")
    protected BigInteger arrayDimensions;
    @XmlAttribute(name = "isBridge")
    protected Boolean isBridge;
    @XmlAttribute(name = "isSynthetic")
    protected Boolean isSynthetic;
    @XmlAttribute(name = "isVarArgs")
    protected Boolean isVarArgs;
    @XmlAttribute(name = "isDefault")
    protected Boolean isDefault;

    /**
     * Gets the value of the annotations property.
     * 
     * @return
     *     possible object is
     *     {@link AnnotationsType }
     *     
     */
    public AnnotationsType getAnnotations() {
        return annotations;
    }

    /**
     * Sets the value of the annotations property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnotationsType }
     *     
     */
    public void setAnnotations(AnnotationsType value) {
        this.annotations = value;
    }

    /**
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link PropertiesType }
     *     
     */
    public PropertiesType getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertiesType }
     *     
     */
    public void setProperties(PropertiesType value) {
        this.properties = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the isPrimitive property.
     * 
     */
    public boolean isIsPrimitive() {
        return isPrimitive;
    }

    /**
     * Sets the value of the isPrimitive property.
     * 
     */
    public void setIsPrimitive(boolean value) {
        this.isPrimitive = value;
    }

    /**
     * Gets the value of the isArray property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsArray() {
        return isArray;
    }

    /**
     * Sets the value of the isArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsArray(Boolean value) {
        this.isArray = value;
    }

    /**
     * Gets the value of the arrayDimensions property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getArrayDimensions() {
        return arrayDimensions;
    }

    /**
     * Sets the value of the arrayDimensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setArrayDimensions(BigInteger value) {
        this.arrayDimensions = value;
    }

    /**
     * Gets the value of the isBridge property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsBridge() {
        return isBridge;
    }

    /**
     * Sets the value of the isBridge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsBridge(Boolean value) {
        this.isBridge = value;
    }

    /**
     * Gets the value of the isSynthetic property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSynthetic() {
        return isSynthetic;
    }

    /**
     * Sets the value of the isSynthetic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSynthetic(Boolean value) {
        this.isSynthetic = value;
    }

    /**
     * Gets the value of the isVarArgs property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsVarArgs() {
        return isVarArgs;
    }

    /**
     * Sets the value of the isVarArgs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsVarArgs(Boolean value) {
        this.isVarArgs = value;
    }

    /**
     * Gets the value of the isDefault property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDefault() {
        return isDefault;
    }

    /**
     * Sets the value of the isDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDefault(Boolean value) {
        this.isDefault = value;
    }

}
