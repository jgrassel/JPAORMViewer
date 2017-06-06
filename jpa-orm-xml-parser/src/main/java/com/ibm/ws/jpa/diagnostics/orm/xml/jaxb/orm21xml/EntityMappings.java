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
// Generated on: 2017.04.12 at 04:14:13 PM CDT 
//


package com.ibm.ws.jpa.diagnostics.orm.xml.jaxb.orm21xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 
 *         The entity-mappings element is the root element of a mapping
 *         file. It contains the following four types of elements:
 * 
 *         1. The persistence-unit-metadata element contains metadata
 *         for the entire persistence unit. It is undefined if this element
 *         occurs in multiple mapping files within the same persistence unit.
 *         
 *         2. The package, schema, catalog and access elements apply to all of
 *         the entity, mapped-superclass and embeddable elements defined in
 *         the same file in which they occur.
 * 
 *         3. The sequence-generator, table-generator, converter, named-query,
 *         named-native-query, named-stored-procedure-query, and 
 *         sql-result-set-mapping elements are global to the persistence
 *         unit. It is undefined to have more than one sequence-generator
 *         or table-generator of the same name in the same or different
 *         mapping files in a persistence unit. It is undefined to have
 *         more than one named-query, named-native-query, sql-result-set-mapping,
 *         or named-stored-procedure-query of the same name in the same 
 *         or different mapping files in a persistence unit.  It is also
 *         undefined to have more than one converter for the same target
 *         type in the same or different mapping files in a persistence unit.
 * 
 *         4. The entity, mapped-superclass and embeddable elements each define
 *         the mapping information for a managed persistent class. The mapping
 *         information contained in these elements may be complete or it may
 *         be partial.
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
 *         &lt;element name="persistence-unit-metadata" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}persistence-unit-metadata" minOccurs="0"/>
 *         &lt;element name="package" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="schema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="catalog" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="access" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}access-type" minOccurs="0"/>
 *         &lt;element name="sequence-generator" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}sequence-generator" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="table-generator" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}table-generator" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="named-query" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}named-query" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="named-native-query" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}named-native-query" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="named-stored-procedure-query" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}named-stored-procedure-query" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sql-result-set-mapping" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}sql-result-set-mapping" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mapped-superclass" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}mapped-superclass" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="entity" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}entity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="embeddable" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}embeddable" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="converter" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}converter" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}versionType" fixed="2.1" />
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
    "persistenceUnitMetadata",
    "_package",
    "schema",
    "catalog",
    "access",
    "sequenceGenerator",
    "tableGenerator",
    "namedQuery",
    "namedNativeQuery",
    "namedStoredProcedureQuery",
    "sqlResultSetMapping",
    "mappedSuperclass",
    "entity",
    "embeddable",
    "converter"
})
@XmlRootElement(name = "entity-mappings")
public class EntityMappings implements com.ibm.ws.jpa.diagnostics.orm.xml.entitymapping.IEntityMappings {

    protected String description;
    @XmlElement(name = "persistence-unit-metadata")
    protected PersistenceUnitMetadata persistenceUnitMetadata;
    @XmlElement(name = "package")
    protected String _package;
    protected String schema;
    protected String catalog;
    @XmlSchemaType(name = "token")
    protected AccessType access;
    @XmlElement(name = "sequence-generator")
    protected List<SequenceGenerator> sequenceGenerator;
    @XmlElement(name = "table-generator")
    protected List<TableGenerator> tableGenerator;
    @XmlElement(name = "named-query")
    protected List<NamedQuery> namedQuery;
    @XmlElement(name = "named-native-query")
    protected List<NamedNativeQuery> namedNativeQuery;
    @XmlElement(name = "named-stored-procedure-query")
    protected List<NamedStoredProcedureQuery> namedStoredProcedureQuery;
    @XmlElement(name = "sql-result-set-mapping")
    protected List<SqlResultSetMapping> sqlResultSetMapping;
    @XmlElement(name = "mapped-superclass")
    protected List<MappedSuperclass> mappedSuperclass;
    protected List<Entity> entity;
    protected List<Embeddable> embeddable;
    protected List<Converter> converter;
    @XmlAttribute(name = "version", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String version;

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
     * Gets the value of the persistenceUnitMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link PersistenceUnitMetadata }
     *     
     */
    public PersistenceUnitMetadata getPersistenceUnitMetadata() {
        return persistenceUnitMetadata;
    }

    /**
     * Sets the value of the persistenceUnitMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersistenceUnitMetadata }
     *     
     */
    public void setPersistenceUnitMetadata(PersistenceUnitMetadata value) {
        this.persistenceUnitMetadata = value;
    }

    /**
     * Gets the value of the package property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackage() {
        return _package;
    }

    /**
     * Sets the value of the package property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackage(String value) {
        this._package = value;
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
     * Gets the value of the sequenceGenerator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sequenceGenerator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSequenceGenerator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SequenceGenerator }
     * 
     * 
     */
    public List<SequenceGenerator> getSequenceGenerator() {
        if (sequenceGenerator == null) {
            sequenceGenerator = new ArrayList<SequenceGenerator>();
        }
        return this.sequenceGenerator;
    }

    /**
     * Gets the value of the tableGenerator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tableGenerator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTableGenerator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TableGenerator }
     * 
     * 
     */
    public List<TableGenerator> getTableGenerator() {
        if (tableGenerator == null) {
            tableGenerator = new ArrayList<TableGenerator>();
        }
        return this.tableGenerator;
    }

    /**
     * Gets the value of the namedQuery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the namedQuery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNamedQuery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedQuery }
     * 
     * 
     */
    public List<NamedQuery> getNamedQuery() {
        if (namedQuery == null) {
            namedQuery = new ArrayList<NamedQuery>();
        }
        return this.namedQuery;
    }

    /**
     * Gets the value of the namedNativeQuery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the namedNativeQuery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNamedNativeQuery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedNativeQuery }
     * 
     * 
     */
    public List<NamedNativeQuery> getNamedNativeQuery() {
        if (namedNativeQuery == null) {
            namedNativeQuery = new ArrayList<NamedNativeQuery>();
        }
        return this.namedNativeQuery;
    }

    /**
     * Gets the value of the namedStoredProcedureQuery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the namedStoredProcedureQuery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNamedStoredProcedureQuery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedStoredProcedureQuery }
     * 
     * 
     */
    public List<NamedStoredProcedureQuery> getNamedStoredProcedureQuery() {
        if (namedStoredProcedureQuery == null) {
            namedStoredProcedureQuery = new ArrayList<NamedStoredProcedureQuery>();
        }
        return this.namedStoredProcedureQuery;
    }

    /**
     * Gets the value of the sqlResultSetMapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sqlResultSetMapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSqlResultSetMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SqlResultSetMapping }
     * 
     * 
     */
    public List<SqlResultSetMapping> getSqlResultSetMapping() {
        if (sqlResultSetMapping == null) {
            sqlResultSetMapping = new ArrayList<SqlResultSetMapping>();
        }
        return this.sqlResultSetMapping;
    }

    /**
     * Gets the value of the mappedSuperclass property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mappedSuperclass property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMappedSuperclass().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MappedSuperclass }
     * 
     * 
     */
    public List<MappedSuperclass> getMappedSuperclass() {
        if (mappedSuperclass == null) {
            mappedSuperclass = new ArrayList<MappedSuperclass>();
        }
        return this.mappedSuperclass;
    }

    /**
     * Gets the value of the entity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the entity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Entity }
     * 
     * 
     */
    public List<Entity> getEntity() {
        if (entity == null) {
            entity = new ArrayList<Entity>();
        }
        return this.entity;
    }

    /**
     * Gets the value of the embeddable property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the embeddable property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmbeddable().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Embeddable }
     * 
     * 
     */
    public List<Embeddable> getEmbeddable() {
        if (embeddable == null) {
            embeddable = new ArrayList<Embeddable>();
        }
        return this.embeddable;
    }

    /**
     * Gets the value of the converter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the converter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConverter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Converter }
     * 
     * 
     */
    public List<Converter> getConverter() {
        if (converter == null) {
            converter = new ArrayList<Converter>();
        }
        return this.converter;
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
     * 
     */
    public List<com.ibm.ws.jpa.diagnostics.orm.xml.entitymapping.IEntity> getEntityList() {
        List<com.ibm.ws.jpa.diagnostics.orm.xml.entitymapping.IEntity> entityList = new ArrayList<com.ibm.ws.jpa.diagnostics.orm.xml.entitymapping.IEntity>();
        entityList.addAll(getEntity());
        return entityList;
    }
}
