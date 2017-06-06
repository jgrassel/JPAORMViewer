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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 *         @Target({}) @Retention(RUNTIME)
 *         public @interface ForeignKey {
 *           String name() default "";
 * 	  ConstraintMode value() default CONSTRAINT;
 *           String foreign-key-definition() default "";
 * 
 *         Note that the elements that embed the use of the annotation
 *         default this use as @ForeignKey(PROVIDER_DEFAULT).
 * 
 *         }
 * 
 *       
 * 
 * <p>Java class for foreign-key complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="foreign-key">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="constraint-mode" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}constraint-mode" />
 *       &lt;attribute name="foreign-key-definition" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "foreign-key", propOrder = {
    "description"
})
public class ForeignKey {

    protected String description;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "constraint-mode")
    protected ConstraintMode constraintMode;
    @XmlAttribute(name = "foreign-key-definition")
    protected String foreignKeyDefinition;

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
     * Gets the value of the constraintMode property.
     * 
     * @return
     *     possible object is
     *     {@link ConstraintMode }
     *     
     */
    public ConstraintMode getConstraintMode() {
        return constraintMode;
    }

    /**
     * Sets the value of the constraintMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConstraintMode }
     *     
     */
    public void setConstraintMode(ConstraintMode value) {
        this.constraintMode = value;
    }

    /**
     * Gets the value of the foreignKeyDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignKeyDefinition() {
        return foreignKeyDefinition;
    }

    /**
     * Sets the value of the foreignKeyDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignKeyDefinition(String value) {
        this.foreignKeyDefinition = value;
    }

}
