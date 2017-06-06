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
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 *         @Target({TYPE}) @Retention(RUNTIME)
 *         public @interface NamedEntityGraph {
 *           String name() default "";
 *           NamedAttributeNode[] attributeNodes() default {};
 *           boolean includeAllAttributes() default false;
 *           NamedSubgraph[] subgraphs() default {};
 *           NamedSubGraph[] subclassSubgraphs() default {};
 *         }
 * 
 *       
 * 
 * <p>Java class for named-entity-graph complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="named-entity-graph">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="named-attribute-node" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}named-attribute-node" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subgraph" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}named-subgraph" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subclass-subgraph" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}named-subgraph" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="include-all-attributes" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "named-entity-graph", propOrder = {
    "namedAttributeNode",
    "subgraph",
    "subclassSubgraph"
})
public class NamedEntityGraph {

    @XmlElement(name = "named-attribute-node")
    protected List<NamedAttributeNode> namedAttributeNode;
    protected List<NamedSubgraph> subgraph;
    @XmlElement(name = "subclass-subgraph")
    protected List<NamedSubgraph> subclassSubgraph;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "include-all-attributes")
    protected Boolean includeAllAttributes;

    /**
     * Gets the value of the namedAttributeNode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the namedAttributeNode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNamedAttributeNode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedAttributeNode }
     * 
     * 
     */
    public List<NamedAttributeNode> getNamedAttributeNode() {
        if (namedAttributeNode == null) {
            namedAttributeNode = new ArrayList<NamedAttributeNode>();
        }
        return this.namedAttributeNode;
    }

    /**
     * Gets the value of the subgraph property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subgraph property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubgraph().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedSubgraph }
     * 
     * 
     */
    public List<NamedSubgraph> getSubgraph() {
        if (subgraph == null) {
            subgraph = new ArrayList<NamedSubgraph>();
        }
        return this.subgraph;
    }

    /**
     * Gets the value of the subclassSubgraph property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subclassSubgraph property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubclassSubgraph().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedSubgraph }
     * 
     * 
     */
    public List<NamedSubgraph> getSubclassSubgraph() {
        if (subclassSubgraph == null) {
            subclassSubgraph = new ArrayList<NamedSubgraph>();
        }
        return this.subclassSubgraph;
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
     * Gets the value of the includeAllAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeAllAttributes() {
        return includeAllAttributes;
    }

    /**
     * Sets the value of the includeAllAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeAllAttributes(Boolean value) {
        this.includeAllAttributes = value;
    }

}
