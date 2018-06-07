//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.07 at 01:36:00 PM CDT 
//


package com.ibm.ws.jpa.diagnostics.orm.xml.jaxb.orm22xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 *         These defaults are applied to the persistence unit as a whole 
 *         unless they are overridden by local annotation or XML 
 *         element settings. 
 *         
 *         schema - Used as the schema for all tables, secondary tables, join
 *             tables, collection tables, sequence generators, and table 
 *             generators that apply to the persistence unit
 *         catalog - Used as the catalog for all tables, secondary tables, join
 *             tables, collection tables, sequence generators, and table 
 *             generators that apply to the persistence unit
 *         delimited-identifiers - Used to treat database identifiers as
 *             delimited identifiers.
 *         access - Used as the access type for all managed classes in
 *             the persistence unit
 *         cascade-persist - Adds cascade-persist to the set of cascade options
 *             in all entity relationships of the persistence unit
 *         entity-listeners - List of default entity listeners to be invoked 
 *             on each entity in the persistence unit. 
 *       
 * 
 * <p>Java class for persistence-unit-defaults complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="persistence-unit-defaults">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="schema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="catalog" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="delimited-identifiers" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}emptyType" minOccurs="0"/>
 *         &lt;element name="access" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}access-type" minOccurs="0"/>
 *         &lt;element name="cascade-persist" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}emptyType" minOccurs="0"/>
 *         &lt;element name="entity-listeners" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}entity-listeners" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "persistence-unit-defaults", propOrder = {
    "description",
    "schema",
    "catalog",
    "delimitedIdentifiers",
    "access",
    "cascadePersist",
    "entityListeners"
})
public class PersistenceUnitDefaults {

    protected String description;
    protected String schema;
    protected String catalog;
    @XmlElement(name = "delimited-identifiers")
    protected EmptyType delimitedIdentifiers;
    @XmlSchemaType(name = "token")
    protected AccessType access;
    @XmlElement(name = "cascade-persist")
    protected EmptyType cascadePersist;
    @XmlElement(name = "entity-listeners")
    protected EntityListeners entityListeners;

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
     * Gets the value of the schema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the value of the schema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchema(String value) {
        this.schema = value;
    }

    /**
     * Gets the value of the catalog property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * Sets the value of the catalog property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCatalog(String value) {
        this.catalog = value;
    }

    /**
     * Gets the value of the delimitedIdentifiers property.
     * 
     * @return
     *     possible object is
     *     {@link EmptyType }
     *     
     */
    public EmptyType getDelimitedIdentifiers() {
        return delimitedIdentifiers;
    }

    /**
     * Sets the value of the delimitedIdentifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmptyType }
     *     
     */
    public void setDelimitedIdentifiers(EmptyType value) {
        this.delimitedIdentifiers = value;
    }

    /**
     * Gets the value of the access property.
     * 
     * @return
     *     possible object is
     *     {@link AccessType }
     *     
     */
    public AccessType getAccess() {
        return access;
    }

    /**
     * Sets the value of the access property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessType }
     *     
     */
    public void setAccess(AccessType value) {
        this.access = value;
    }

    /**
     * Gets the value of the cascadePersist property.
     * 
     * @return
     *     possible object is
     *     {@link EmptyType }
     *     
     */
    public EmptyType getCascadePersist() {
        return cascadePersist;
    }

    /**
     * Sets the value of the cascadePersist property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmptyType }
     *     
     */
    public void setCascadePersist(EmptyType value) {
        this.cascadePersist = value;
    }

    /**
     * Gets the value of the entityListeners property.
     * 
     * @return
     *     possible object is
     *     {@link EntityListeners }
     *     
     */
    public EntityListeners getEntityListeners() {
        return entityListeners;
    }

    /**
     * Sets the value of the entityListeners property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityListeners }
     *     
     */
    public void setEntityListeners(EntityListeners value) {
        this.entityListeners = value;
    }

}
