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

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.03 at 04:19:29 PM CDT 
//


package com.ibm.ws.jpa.diagnostics.puparser.jaxb.puxml21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_Persistence;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnit;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnitCachingType;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnitTransactionType;
import com.ibm.ws.jpa.diagnostics.puparser.pu.PUP_PersistenceUnitValidationModeType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="persistence-unit" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="jta-data-source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="non-jta-data-source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="mapping-file" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="jar-file" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="class" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="exclude-unlisted-classes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="shared-cache-mode" type="{http://xmlns.jcp.org/xml/ns/persistence}persistence-unit-caching-type" minOccurs="0"/>
 *                   &lt;element name="validation-mode" type="{http://xmlns.jcp.org/xml/ns/persistence}persistence-unit-validation-mode-type" minOccurs="0"/>
 *                   &lt;element name="properties" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="property" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="transaction-type" type="{http://xmlns.jcp.org/xml/ns/persistence}persistence-unit-transaction-type" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://xmlns.jcp.org/xml/ns/persistence}versionType" fixed="2.1" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "persistenceUnit"
})
@XmlRootElement(name = "persistence")
public class Persistence implements PUP_Persistence {

    @XmlElement(name = "persistence-unit", required = true)
    protected List<Persistence.PersistenceUnit> persistenceUnit;
    @XmlAttribute(name = "version", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String version;

    /**
     * Gets the value of the persistenceUnit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the persistenceUnit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersistenceUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Persistence.PersistenceUnit }
     * 
     * 
     */
    public List<Persistence.PersistenceUnit> getPersistenceUnit() {
        if (persistenceUnit == null) {
            persistenceUnit = new ArrayList<Persistence.PersistenceUnit>();
        }
        return this.persistenceUnit;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        if (version == null) {
            return "2.1";
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /*
     * Begin PUP Adds
     */
    
    public List<PUP_PersistenceUnit> pup_getPersistenceUnit() {
        List<PUP_PersistenceUnit> retVal = new ArrayList<PUP_PersistenceUnit>();
        
        List<Persistence.PersistenceUnit> puList = getPersistenceUnit();
        if (puList.isEmpty()) {
            return retVal;
        }
        
        for (Persistence.PersistenceUnit pu : puList) {
            retVal.add(pu);
        }
        
        return retVal;
    }
    
    /*
     * End PUP Adds
     */

    /**
     * 
     * 
     *                 Configuration of a persistence unit.
     * 
     *               
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="jta-data-source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="non-jta-data-source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="mapping-file" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="jar-file" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="class" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="exclude-unlisted-classes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="shared-cache-mode" type="{http://xmlns.jcp.org/xml/ns/persistence}persistence-unit-caching-type" minOccurs="0"/>
     *         &lt;element name="validation-mode" type="{http://xmlns.jcp.org/xml/ns/persistence}persistence-unit-validation-mode-type" minOccurs="0"/>
     *         &lt;element name="properties" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="property" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="transaction-type" type="{http://xmlns.jcp.org/xml/ns/persistence}persistence-unit-transaction-type" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "description",
        "provider",
        "jtaDataSource",
        "nonJtaDataSource",
        "mappingFile",
        "jarFile",
        "clazz",
        "excludeUnlistedClasses",
        "sharedCacheMode",
        "validationMode",
        "properties"
    })
    public static class PersistenceUnit implements PUP_PersistenceUnit {

        protected String description;
        protected String provider;
        @XmlElement(name = "jta-data-source")
        protected String jtaDataSource;
        @XmlElement(name = "non-jta-data-source")
        protected String nonJtaDataSource;
        @XmlElement(name = "mapping-file")
        protected List<String> mappingFile;
        @XmlElement(name = "jar-file")
        protected List<String> jarFile;
        @XmlElement(name = "class")
        protected List<String> clazz;
        @XmlElement(name = "exclude-unlisted-classes", defaultValue = "true")
        protected Boolean excludeUnlistedClasses;
        @XmlElement(name = "shared-cache-mode")
        @XmlSchemaType(name = "token")
        protected PersistenceUnitCachingType sharedCacheMode;
        @XmlElement(name = "validation-mode")
        @XmlSchemaType(name = "token")
        protected PersistenceUnitValidationModeType validationMode;
        protected Persistence.PersistenceUnit.Properties properties;
        @XmlAttribute(name = "name", required = true)
        protected String name;
        @XmlAttribute(name = "transaction-type")
        protected PersistenceUnitTransactionType transactionType;

        /**
         * Gets the value of the description property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescription(String value) {
            this.description = value;
        }

        /**
         * Gets the value of the provider property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProvider() {
            return provider;
        }

        /**
         * Sets the value of the provider property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProvider(String value) {
            this.provider = value;
        }

        /**
         * Gets the value of the jtaDataSource property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJtaDataSource() {
            return jtaDataSource;
        }

        /**
         * Sets the value of the jtaDataSource property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJtaDataSource(String value) {
            this.jtaDataSource = value;
        }

        /**
         * Gets the value of the nonJtaDataSource property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNonJtaDataSource() {
            return nonJtaDataSource;
        }

        /**
         * Sets the value of the nonJtaDataSource property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNonJtaDataSource(String value) {
            this.nonJtaDataSource = value;
        }

        /**
         * Gets the value of the mappingFile property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the mappingFile property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMappingFile().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getMappingFile() {
            if (mappingFile == null) {
                mappingFile = new ArrayList<String>();
            }
            return this.mappingFile;
        }

        /**
         * Gets the value of the jarFile property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the jarFile property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getJarFile().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getJarFile() {
            if (jarFile == null) {
                jarFile = new ArrayList<String>();
            }
            return this.jarFile;
        }

        /**
         * Gets the value of the clazz property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the clazz property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getClazz().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getClazz() {
            if (clazz == null) {
                clazz = new ArrayList<String>();
            }
            return this.clazz;
        }

        /**
         * Gets the value of the excludeUnlistedClasses property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isExcludeUnlistedClasses() {
            return excludeUnlistedClasses;
        }

        /**
         * Sets the value of the excludeUnlistedClasses property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setExcludeUnlistedClasses(Boolean value) {
            this.excludeUnlistedClasses = value;
        }

        /**
         * Gets the value of the sharedCacheMode property.
         * 
         * @return
         *     possible object is
         *     {@link PersistenceUnitCachingType }
         *     
         */
        public PersistenceUnitCachingType getSharedCacheMode() {
            return sharedCacheMode;
        }

        /**
         * Sets the value of the sharedCacheMode property.
         * 
         * @param value
         *     allowed object is
         *     {@link PersistenceUnitCachingType }
         *     
         */
        public void setSharedCacheMode(PersistenceUnitCachingType value) {
            this.sharedCacheMode = value;
        }

        /**
         * Gets the value of the validationMode property.
         * 
         * @return
         *     possible object is
         *     {@link PersistenceUnitValidationModeType }
         *     
         */
        public PersistenceUnitValidationModeType getValidationMode() {
            return validationMode;
        }

        /**
         * Sets the value of the validationMode property.
         * 
         * @param value
         *     allowed object is
         *     {@link PersistenceUnitValidationModeType }
         *     
         */
        public void setValidationMode(PersistenceUnitValidationModeType value) {
            this.validationMode = value;
        }

        /**
         * Gets the value of the properties property.
         * 
         * @return
         *     possible object is
         *     {@link Persistence.PersistenceUnit.Properties }
         *     
         */
        public Persistence.PersistenceUnit.Properties getProperties() {
            return properties;
        }

        /**
         * Sets the value of the properties property.
         * 
         * @param value
         *     allowed object is
         *     {@link Persistence.PersistenceUnit.Properties }
         *     
         */
        public void setProperties(Persistence.PersistenceUnit.Properties value) {
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
         * Gets the value of the transactionType property.
         * 
         * @return
         *     possible object is
         *     {@link PersistenceUnitTransactionType }
         *     
         */
        public PersistenceUnitTransactionType getTransactionType() {
            return transactionType;
        }

        /**
         * Sets the value of the transactionType property.
         * 
         * @param value
         *     allowed object is
         *     {@link PersistenceUnitTransactionType }
         *     
         */
        public void setTransactionType(PersistenceUnitTransactionType value) {
            this.transactionType = value;
        }

        /*
         * Begin PUP Adds
         */
        
        public PUP_PersistenceUnitCachingType pup_getSharedCacheMode() {
            if (getSharedCacheMode() == null) {
                return null;
            }
            return PUP_PersistenceUnitCachingType.valueOf(getSharedCacheMode().value());
        }
        
        public PUP_PersistenceUnitValidationModeType pup_getValidationMode() {
            if (getValidationMode() == null) {
                return null;
            }
            return PUP_PersistenceUnitValidationModeType.valueOf(getValidationMode().value());
        }
        
        public PUP_PersistenceUnitTransactionType pup_getTransactionType() {
            PersistenceUnitTransactionType putt = getTransactionType();
            if (putt == null) {
                return null;
            }
            return PUP_PersistenceUnitTransactionType.valueOf(putt.value());
        }
        
        public Map<String, String> pup_getProperties() {
            HashMap<String, String> retVal = new HashMap<String, String>();
            
            Persistence.PersistenceUnit.Properties propsFromJaxb = getProperties();
            if (propsFromJaxb == null || propsFromJaxb.getProperty().isEmpty()) {
                return retVal;
            }
            
            for (Persistence.PersistenceUnit.Properties.Property p : propsFromJaxb.getProperty()) {
                retVal.put(p.getName(), p.getValue());
            }           
            
            return retVal;
        }
        
        /*
         * End PUP Adds
         */
        

        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="property" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "property"
        })
        public static class Properties {

            protected List<Persistence.PersistenceUnit.Properties.Property> property;

            /**
             * Gets the value of the property property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the property property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getProperty().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Persistence.PersistenceUnit.Properties.Property }
             * 
             * 
             */
            public List<Persistence.PersistenceUnit.Properties.Property> getProperty() {
                if (property == null) {
                    property = new ArrayList<Persistence.PersistenceUnit.Properties.Property>();
                }
                return this.property;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Property {

                @XmlAttribute(name = "name", required = true)
                protected String name;
                @XmlAttribute(name = "value", required = true)
                protected String value;

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
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
                    this.value = value;
                }

            }

        }

    }

}
