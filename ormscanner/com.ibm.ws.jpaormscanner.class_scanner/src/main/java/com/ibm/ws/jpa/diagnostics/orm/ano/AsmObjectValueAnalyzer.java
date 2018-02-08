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

package com.ibm.ws.jpa.diagnostics.orm.ano;

import org.objectweb.asm.Type;

import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.EnumerationInstanceType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ValueInstanceType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ValueType;

public class AsmObjectValueAnalyzer {
    public static ValueInstanceType processValue(Object value) {
        ValueInstanceType vit = new ValueInstanceType();
        
        if (value == null) {
            vit.setType(ValueType.NULL);
            return vit;
        }
        
        final Class<?> cls = value.getClass();
        
        if (cls.isPrimitive()) {
            if (cls.equals(boolean.class)) {
                vit.setType(ValueType.BOOLEAN);
                vit.setSimple(String.format("%b", value));
            } else if (cls.equals(byte.class)) {
                vit.setType(ValueType.BYTE);
                vit.setSimple(String.format("%d", value));
            } else if (cls.equals(char.class)) {
                vit.setType(ValueType.CHAR);
                vit.setSimple(String.format("%c", value));
            } else if (cls.equals(double.class)) {
                vit.setType(ValueType.DOUBLE);
                vit.setSimple(String.format("%f", value)); // TODO: Make sure this supports double precision
            } else if (cls.equals(float.class)) {
                vit.setType(ValueType.FLOAT);
                vit.setSimple(String.format("%f", value));
            } else if (cls.equals(int.class)) {
                vit.setType(ValueType.INT);
                vit.setSimple(String.format("%d", value));
            } else if (cls.equals(long.class)) {
                vit.setType(ValueType.LONG);
                vit.setSimple(String.format("%d", value));
            } else if (cls.equals(short.class)) {
                vit.setType(ValueType.SHORT);
                vit.setSimple(String.format("%d", value));
            } else {
                vit.setType(ValueType.UNKNOWN);
            }
            
            return vit;
        } 
        
        if (java.lang.Number.class.isAssignableFrom(cls)) {
            // AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, DoubleAccumulator, 
            // DoubleAdder, Float, Integer, Long, LongAccumulator, LongAdder, Short
            
            if (cls.equals(java.lang.Byte.class)) {
                vit.setType(ValueType.JAVA_LANG_BYTE);
                vit.setSimple(String.format("%d", value));
            }  else if (cls.equals(java.lang.Double.class)) {
                vit.setType(ValueType.JAVA_LANG_DOUBLE);
                vit.setSimple(String.format("%f", value)); // TODO: Make sure this supports double precision
            } else if (cls.equals(java.lang.Float.class)) {
                vit.setType(ValueType.JAVA_LANG_FLOAT);
                vit.setSimple(String.format("%f", value));
            } else if (cls.equals(java.lang.Integer.class)) {
                vit.setType(ValueType.JAVA_LANG_INT);  // TODO: We want this JAVA_LANG_INTEGER
                vit.setSimple(String.format("%d", value));
            } else if (cls.equals(java.lang.Long.class)) {
                vit.setType(ValueType.JAVA_LANG_LONG);
                vit.setSimple(String.format("%d", value));
            } else if (cls.equals(java.lang.Short.class)) {
                vit.setType(ValueType.JAVA_LANG_SHORT);
                vit.setSimple(String.format("%d", value));
            } else {
                // TODO: Represent the others as an Object instance
                vit.setType(ValueType.UNKNOWN);
            }
            
            return vit;
        }
        
        if (cls.equals(java.lang.Boolean.class)) {
            vit.setType(ValueType.JAVA_LANG_BOOLEAN);
            vit.setSimple(String.format("%b", value));
        } else if (cls.equals(java.lang.Character.class)) {
            vit.setType(ValueType.JAVA_LANG_CHARACTER);
            vit.setSimple(String.format("%c", value));
        } else if (cls.equals(java.lang.String.class)) {
            vit.setType(ValueType.JAVA_LANG_STRING);
            vit.setSimple(String.format("%s", value));
        } else {
            vit.setType(ValueType.UNKNOWN);
        }
        
        return vit;
    }
    
    public static ValueInstanceType processEnum(String name, String desc, String value) {       
        final ValueInstanceType vit = new ValueInstanceType();
        vit.setType(ValueType.ENUM);
        
        final EnumerationInstanceType eit = new EnumerationInstanceType();
        vit.setEnum(eit);
        
        final Type type = Type.getType(desc);
        if (type != null) {
                String processedName = AsmHelper.normalizeClassName(type.getClassName());
                eit.setClassName(processedName);
                eit.setValue(value);;
        }
        
        return vit;
    }
    
}
