/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
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
// Generated on: 2018.06.07 at 01:36:00 PM CDT 
//


package com.ibm.ws.jpa.diagnostics.orm.xml.jaxb.orm22xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 *         @Target({TYPE, METHOD, FIELD}) @Retention(RUNTIME)
 *         public @interface PrimaryKeyJoinColumn {
 *           String name() default "";
 *           String referencedColumnName() default "";
 *           String columnDefinition() default "";
 *         }
 * 
 *       
 * 
 * <p>Java class for primary-key-join-column complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="primary-key-join-column">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="referenced-column-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="column-definition" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "primary-key-join-column")
public class PrimaryKeyJoinColumn {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "referenced-column-name")
    protected String referencedColumnName;
    @XmlAttribute(name = "column-definition")
    protected String columnDefinition;

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
     * Gets the value of the referencedColumnName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencedColumnName() {
        return referencedColumnName;
    }

    /**
     * Sets the value of the referencedColumnName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencedColumnName(String value) {
        this.referencedColumnName = value;
    }

    /**
     * Gets the value of the columnDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnDefinition() {
        return columnDefinition;
    }

    /**
     * Sets the value of the columnDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnDefinition(String value) {
        this.columnDefinition = value;
    }

}
