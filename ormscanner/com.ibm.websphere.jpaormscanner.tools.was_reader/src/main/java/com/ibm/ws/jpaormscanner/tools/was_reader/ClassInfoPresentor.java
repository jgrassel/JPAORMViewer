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

package com.ibm.ws.jpaormscanner.tools.was_reader;

import java.util.List;

import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.AnnotationInfoType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.AnnotationValueType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.AnnotationsType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.ArrayInstanceType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.ClassInfoType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.ExceptionType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.FieldInfoType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.FieldsType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.MethodInfoType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.MethodsType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.ModifierType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.ModifiersType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.ParameterType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.ParametersType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.ValueInstanceType;
import com.ibm.ws.jpa.diagnostics.class_scanner.ano.jaxb.classinfo10.ValueType;

public class ClassInfoPresentor {
    public static String process(ClassInfoType cit) {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(cit.getPackageName()).append(";").append("\n");
        sb.append("\n");
        
        processAnnotationsType(cit.getAnnotations(), "", sb, false);
      
        printModifiersType(cit.getModifiers(), sb);
        
        if (cit.isIsInterface()) {
            sb.append("interface ");
        } else {
            sb.append("class ");
        }
        
        sb.append(cit.getName());
        sb.append(" {\n");
        
        printFields(cit, sb);
        sb.append("\n");
        printMethods(cit, sb);
        
        sb.append("\n}\n");
        return sb.toString();
    }
    
    private static void printModifiersType(ModifiersType mods, StringBuilder sb) {
        if (mods == null) {
            return;
        }
        
        List<ModifierType> modList = mods.getModifier();
        for (ModifierType mt : modList) {
            sb.append(mt.toString().toLowerCase());
            sb.append(" ");
        }
    }
    
    private static void printFields(ClassInfoType cit, StringBuilder sb) {
        FieldsType ft = cit.getFields();
        if (ft == null) {
            return;
        }
        sb.append("  // Fields\n");
        
        boolean first = true;
        List<FieldInfoType> fiList = ft.getField();
        for (FieldInfoType fit : fiList) {
            if (first) {
                first = false;
            } else {
                sb.append("\n");
            }
            processAnnotationsType(fit.getAnnotations(), "  ", sb, false);
            sb.append("  ");
            printModifiersType(fit.getModifiers(), sb);
            sb.append(fit.getType()).append(" ");
            sb.append(fit.getName()).append(";\n");            
        }
    }
    
    private static void printMethods(ClassInfoType cit, StringBuilder sb) {
        MethodsType mt = cit.getMethods();
        if (mt == null) {
            return;
        }
        
        sb.append("  // Methods\n");
        
        boolean first = true;
        List<MethodInfoType> miList = mt.getMethod();
        for (MethodInfoType mit : miList) {
            if (first) {
                first = false;
            } else {
                sb.append("\n");
            }
            
            processAnnotationsType(mit.getAnnotations(), "  ", sb, false);
            sb.append("  ");
            printModifiersType(mit.getModifiers(), sb);
            
            if (mit.isIsCtor()) {
                sb.append(cit.getName());
            } else {
                sb.append(mit.getReturnType()).append(" ");
                sb.append(mit.getMethodName());
            }
            
            sb.append("(");
                
            ParametersType pt = mit.getParameters();
            if (pt != null) {
                boolean first2 = true;
                
                List<ParameterType> ptList = pt.getParameter();
                for (ParameterType parmType : ptList) {
                    if (first2) {
                        first2 = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(parmType.getType());
                    if (parmType.isIsArray()) {
                        sb.append("[]");
                    }
                }
            }
            
            sb.append(")");
            
            if (mit.getExceptions() != null) {
                List<ExceptionType> exList = mit.getExceptions().getException();
                sb.append(" throws ");
                
                boolean first2 = true;
                for (ExceptionType et : exList) {
                    if (first2) {
                        first2 = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(et.getExceptionType());
                }
                sb.append(" ");
            }
            
            sb.append(";\n");
            
        }
    }
    
    private static void processAnnotationsType(AnnotationsType at, String indent, StringBuilder sb, boolean printComma) {
        if (indent == null) {
            indent = "";
        }
        
        if (at == null) {
            return;
        }
        
        boolean first = true;

        List<AnnotationInfoType> annoInfoTypeList = at.getAnnotation();
        for (AnnotationInfoType ait: annoInfoTypeList) {
            if (printComma) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
            }
            processAnnotationInfoType(ait, indent, sb);
        }
    }
    
    private static void processAnnotationInfoType(AnnotationInfoType ait, String indent, StringBuilder sb) {
        String type = ait.getType();
        sb.append(indent).append("@").append(type);
        
        List<AnnotationValueType> avtList = ait.getAnnoKeyVal();
        if (avtList.size() > 0) {
            sb.append("(");
            
            boolean first = true;
            
            for (AnnotationValueType avt : avtList) {
                String name = avt.getName();
                
                AnnotationInfoType ait2 = avt.getAnnotation();
                AnnotationsType at2 = avt.getAnnotations();
                ValueInstanceType vit = avt.getValue();
                ArrayInstanceType arrit = avt.getValues();
                
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(name).append("=");
                
                if (vit != null) {
                    ValueType valType = vit.getType();
                    if (valType == ValueType.ARRAY) {
                        // TODO                          
                    } else if (valType == ValueType.LIST) {
                        // TODO
                    } else if (valType == ValueType.MAP) {
                        // TODO
                    } else if (valType == ValueType.OBJECT) {
                        // TODO
                    } else if (valType == ValueType.JAVA_LANG_STRING) {
                        sb.append("\"").append(vit.getSimple()).append("\"");
                    } else {
                        sb.append(vit.getSimple());
                    }                     
                } else if (ait2 != null) {
                    processAnnotationInfoType(ait2, indent + "  ", sb);
                } else if (at2 != null) {
                    sb.append("{ ");
                    processAnnotationsType(at2, indent + "  ", sb, true);
                    sb.append("} ");
                }
            }
            
            sb.append(")");
        }
        
        sb.append("\n");
            
    }
}
