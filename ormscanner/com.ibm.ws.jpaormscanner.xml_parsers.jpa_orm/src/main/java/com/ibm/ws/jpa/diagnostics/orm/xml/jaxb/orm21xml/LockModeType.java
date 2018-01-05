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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for lock-mode-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="lock-mode-type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="READ"/>
 *     &lt;enumeration value="WRITE"/>
 *     &lt;enumeration value="OPTIMISTIC"/>
 *     &lt;enumeration value="OPTIMISTIC_FORCE_INCREMENT"/>
 *     &lt;enumeration value="PESSIMISTIC_READ"/>
 *     &lt;enumeration value="PESSIMISTIC_WRITE"/>
 *     &lt;enumeration value="PESSIMISTIC_FORCE_INCREMENT"/>
 *     &lt;enumeration value="NONE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "lock-mode-type")
@XmlEnum
public enum LockModeType {

    READ,
    WRITE,
    OPTIMISTIC,
    OPTIMISTIC_FORCE_INCREMENT,
    PESSIMISTIC_READ,
    PESSIMISTIC_WRITE,
    PESSIMISTIC_FORCE_INCREMENT,
    NONE;

    public String value() {
        return name();
    }

    public static LockModeType fromValue(String v) {
        return valueOf(v);
    }

}