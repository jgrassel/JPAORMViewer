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

package com.ibm.ws.jpa.diagnostics.orm.ano;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.AnnotationElementType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.AnnotationElementValueType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.AnnotationInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.AnnotationsType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ClassInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ClassInformationType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ExceptionType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ExceptionsType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.FieldInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.FieldsType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.InnerClassesType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.InterfacesType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.MethodInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.MethodsType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ModifierType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ModifiersType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ParameterType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ParametersType;

public final class ClassAnalyzer {
    final public static String NULL = "<null>";
    
    public static final ClassInfoType analyzeClass(Class<?> targetClass) throws ClassScannerException {
        if (targetClass == null) {
            throw new NullPointerException();
        }

        try {
            return scanClass(targetClass);
        } catch (Throwable t) {
            throw new ClassScannerException(t);
        }
    }
    
    public static final byte[] produceClassInfoTypeXML(ClassInformationType cit) {
        try {
            final JAXBContext jaxbCtx = JAXBContext.newInstance("com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10");
            final Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            marshaller.marshal(cit, baos);
            return baos.toByteArray();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }  
        
    }
    
    private static ClassInfoType scanClass(Class<?> cls) {
        if (cls == null) {
            throw new NullPointerException();
        }
        
        final ClassInfoType cit = new ClassInfoType();
        
        // Populate ClassInfoType attributes
        cit.setClassName(cls.getName());
        cit.setSuperclassName(cls.getSuperclass().getName());
        cit.setPackageName(cls.getPackage().getName());
        cit.setIsAnonymous(cls.isAnonymousClass());
        cit.setIsEnum(cls.isEnum());
        cit.setIsInterface(cls.isInterface());
        cit.setIsSynthetic(cls.isSynthetic());
        
        // Scan modifiers
        final ModifiersType mst = new ModifiersType();
        scanModifiers(mst, cls.getModifiers());
        cit.setModifiers(mst);
        
        // Scan Class Annotations
        final Annotation[] classAnnotations = cls.getDeclaredAnnotations();
        if (classAnnotations != null && classAnnotations.length != 0) {
            final AnnotationsType annoType = new AnnotationsType();
            cit.setAnnotations(annoType);            
            final List<AnnotationInfoType> annoList = annoType.getAnnotation();
            
            for (Annotation clsAnno : classAnnotations) {
                annoList.add(scanAnnotationInfo(clsAnno));               
            }
        }
        
        // Identify Interfaces implemented by the class
        final Class<?>[] ifaces = cls.getInterfaces();
        if (ifaces != null && ifaces.length > 0) {
            InterfacesType it = cit.getInterfaces();
            if (it == null) {
                it = new InterfacesType();
                cit.setInterfaces(it);
            }
            
            final List<String> ifaceList = it.getInterface();            
            for (Class<?> iface : ifaces) {
                ifaceList.add(iface.getName());
            }
        }
        
        // Scan Fields
        scanFields(cls, cit);     
        
        // Scan Constructors and Methods
        scanConstructors(cls, cit);
        scanMethods(cls, cit);   
        
        // Scan Inner Classes
        scanInnerClasses(cls, cit);
        
        return cit;
    }
    
    private static void scanFields(final Class<?> cls, final ClassInfoType cit) {
        final Field[] classFields = cls.getDeclaredFields();
        if (classFields != null && classFields.length > 0) {
            FieldsType ft = cit.getFields();
            if (ft == null) {
                ft = new FieldsType();
                cit.setFields(ft);
            }
            
            final List<FieldInfoType> fieldList = ft.getField();
            for (Field field : classFields) {
                FieldInfoType fit = new FieldInfoType();
                fieldList.add(fit);
                
                fit.setName(field.getName());
                fit.setType(field.getType().getName());
                fit.setIsSynthetic(field.isSynthetic());
                
                final ModifiersType fmod = new ModifiersType();
                scanModifiers(fmod, field.getModifiers());
                fit.setModifiers(fmod);
                
                final Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
                if (fieldAnnotations != null && fieldAnnotations.length != 0) {
                    final AnnotationsType annoType = new AnnotationsType();
                    fit.setAnnotations(annoType);            
                    final List<AnnotationInfoType> annoList = annoType.getAnnotation();
                    
                    for (Annotation fieldAnno : fieldAnnotations) {
                        annoList.add(scanAnnotationInfo(fieldAnno));               
                    }
                }
            }           
        }  
    }
    
    private static void scanMethods(final Class<?> cls, final ClassInfoType cit) {
        final Method[] classMethods = cls.getDeclaredMethods();
        if (classMethods == null || classMethods.length == 0) {
            return;
        }
        
        MethodsType mt = cit.getMethods();
        if (mt == null) {
            mt = new MethodsType();
            cit.setMethods(mt);;
        }
        final List<MethodInfoType> methodList = mt.getMethod();
        cit.setMethods(mt);
        
        for (Method method : classMethods) {
            MethodInfoType mit = new MethodInfoType();
            methodList.add(mit);
            
            mit.setMethodName(method.getName());
            mit.setReturnType(method.getReturnType().getName());
            mit.setIsCtor(false);
            
            // Method Parameters
            processMethodParameters(method.getParameterTypes(), method.getParameterAnnotations(), mit);
            
            // Method Exceptions
            processMethodExceptions(method.getExceptionTypes(), mit);
            
            // Method Modifiers
            final ModifiersType mmod = new ModifiersType();
            mit.setModifiers(mmod);
            scanModifiers(mmod, method.getModifiers());
            
            // Method Annotations
            processMethodAnnotations(method.getDeclaredAnnotations(), mit);                
        }
    }
    
    private static void scanConstructors(final Class<?> cls, final ClassInfoType cit) {
        final Constructor<?>[] classCtors = cls.getDeclaredConstructors();
        if (classCtors == null || classCtors.length == 0) {
            return;
        }
        
        MethodsType mt = cit.getMethods();
        if (mt == null) {
            mt = new MethodsType();
            cit.setMethods(mt);;
        }
        final List<MethodInfoType> methodList = mt.getMethod();
        cit.setMethods(mt);
        
        for (Constructor<?> ctor : classCtors) {
            MethodInfoType mit = new MethodInfoType();
            methodList.add(mit);
            
            mit.setMethodName(ctor.getName());
            mit.setReturnType(cls.getName());
            mit.setIsCtor(true);
            
            // Method Parameters
            processMethodParameters(ctor.getParameterTypes(), ctor.getParameterAnnotations(), mit);
            
            // Method Exceptions
            processMethodExceptions(ctor.getExceptionTypes(), mit);
            
            // Method Modifiers
            final ModifiersType mmod = new ModifiersType();
            mit.setModifiers(mmod);
            scanModifiers(mmod, ctor.getModifiers());
            
            // Method Annotations
            processMethodAnnotations(ctor.getDeclaredAnnotations(), mit);                
        }
    }
    
    private static void processMethodAnnotations(final Annotation[] methodAnnotations, final MethodInfoType mit) {
        if (methodAnnotations != null && methodAnnotations.length != 0) {
            final AnnotationsType annoType = new AnnotationsType();
            mit.setAnnotations(annoType);            
            final List<AnnotationInfoType> annoList = annoType.getAnnotation();
            
            for (Annotation methodAnno : methodAnnotations) {
                annoList.add(scanAnnotationInfo(methodAnno));               
            }
        }
    }
    
    private static void processMethodExceptions(final Class<?>[] exceptions, final MethodInfoType mit) {
        if (exceptions != null && exceptions.length > 0) {
            ExceptionsType et = mit.getExceptions();
            if (et == null) {
                et = new ExceptionsType();
                mit.setExceptions(et);
            }
            
            List<ExceptionType> exList = et.getException();
            for (Class<?> exCls : exceptions) {
                ExceptionType exType = new ExceptionType();
                exType.setExceptionType(exCls.getName());
                exType.setIsRuntimeException(java.lang.RuntimeException.class.isAssignableFrom(exCls));
                exType.setSuperclassType(exCls.getSuperclass().getName());
                
                exList.add(exType);
            }
        }
    }
    
    private static void processMethodParameters(final Class<?>[] parms, final Annotation[][] parmAnnos, final MethodInfoType mit) {
        if (parms != null && parms.length > 0) {
            ParametersType pt = mit.getParameters();
            if (pt == null) {
                pt = new ParametersType();
                mit.setParameters(pt);
            }
            
            final List<ParameterType> parmList = pt.getParameter();                    
            for (int index = 0; index < parms.length; index++) {
                final Class<?> parm = parms[index];
                final Annotation[] parmAnnoArr = parmAnnos[index];

                final ParameterType parmType = new ParameterType();
                parmList.add(parmType);
                
                // parmType.setName(); // TODO: Might not be possible to get parm name
                parmType.setType(parm.getName());
                parmType.setIsArray(parm.isArray());
                // parmType.setArrayDimensions(); // TODO
                // parmType.setIsBridge(parm.is); // TODO
                // parmType.setDefault(); // TODO
                parmType.setIsPrimitive(parm.isPrimitive());
                parmType.setIsSynthetic(parm.isSynthetic());
                // parmType.setIsVarArgs(parm.); // TODO           
               
                if (parmAnnoArr != null && parmAnnoArr.length != 0) {
                    final AnnotationsType annoType = new AnnotationsType();
                    parmType.setAnnotations(annoType);            
                    final List<AnnotationInfoType> annoList = annoType.getAnnotation();
                    
                    for (Annotation parmAnno : parmAnnoArr) {
                        annoList.add(scanAnnotationInfo(parmAnno));               
                    }
                }
            }
        }
    }
    
    private static void scanInnerClasses(final Class<?> cls, final ClassInfoType cit) {
        Class<?>[] declaredClassesArr = cls.getDeclaredClasses();
        if (declaredClassesArr == null || declaredClassesArr.length == 0) {
            return;
        }
        
        InnerClassesType ict = cit.getInnerclasses();
        if (ict == null) {
            ict = new InnerClassesType();
            cit.setInnerclasses(ict);
        }
        final List<ClassInfoType> innerClassesList = ict.getInnerclass();
 
        for (Class<?> decCls : declaredClassesArr) {
            ClassInfoType innerCit = scanClass(decCls);
            innerClassesList.add(innerCit);
            
//            if (!java.lang.reflect.Modifier.isStatic(decCls.getModifiers())) {
//                // This is an inner class. Do your thing here.
//            } else {
//                // This is a nested class. Not sure if you're interested in this.
//            }
        }
        
    }
    
    private static AnnotationInfoType scanAnnotationInfo(Annotation anno) {
        if (anno == null) {
            throw new NullPointerException();
        }
        
        final AnnotationInfoType ait = new AnnotationInfoType();
        
        final Class<?> annoClass = anno.annotationType();        
        ait.setName(annoClass.getSimpleName());       
        ait.setType(annoClass.getName());
        
        final List<AnnotationElementType> elementList = ait.getElement();
        final Method[] annoMethods = annoClass.getDeclaredMethods();
        for (Method m : annoMethods) {
            final AnnotationElementType annoElement = new AnnotationElementType();
            elementList.add(annoElement);
            
            final String elementName = m.getName(); // TODO: convert from getElementName to ElementName
            annoElement.setName(elementName);
            
            final List<AnnotationElementValueType> valueList = annoElement.getValue();
            
            try {
                Object value = m.invoke(anno, (Object[]) null); 
                
                if (value.getClass().isArray()) {
                    // Value is an array
                    annoElement.setIsArray(true);
                    annoElement.setType(value.getClass().toString());
                                        
                    int length = Array.getLength(value);
                    for (int index = 0; index < length; index++) {
                        Object objAt = Array.get(value, index);
                        if (objAt == null) {
                            AnnotationElementValueType aeVal = new AnnotationElementValueType();
                            aeVal.setSimpleValue(NULL);
                            valueList.add(aeVal);
                        } else if (objAt instanceof Annotation) {
                            AnnotationInfoType subAit = scanAnnotationInfo((Annotation) objAt);
                            
                            AnnotationElementValueType aeVal = new AnnotationElementValueType();
                            aeVal.setAnnotation(subAit);
                            valueList.add(aeVal);
                        } else {
                            AnnotationElementValueType aeVal = new AnnotationElementValueType();
                            aeVal.setSimpleValue(value.toString());
                            valueList.add(aeVal);
                        }
                    }
                } else if (value instanceof Annotation) {
                    // Value is a nested Annotation
                    AnnotationInfoType subAit = scanAnnotationInfo((Annotation) value);
                    
                    AnnotationElementValueType aeVal = new AnnotationElementValueType();
                    aeVal.setAnnotation(subAit);
                    valueList.add(aeVal);
                } else {
                    // Value is a simple type -- ie: @PersistenceUnit(unitName="MyPersistenceUnit")
                    AnnotationElementValueType aeVal = new AnnotationElementValueType();
                    aeVal.setSimpleValue(value.toString());
                    
                    valueList.add(aeVal);
                    annoElement.setType(value.getClass().toString());                   
                }
            } catch (Exception e) {
                // TODO: Report better
                e.printStackTrace();
            }
        }
        
        return ait;
    }
    
    private static void scanModifiers(final ModifiersType mst, final int modifiers) {
        final List<ModifierType> modList = mst.getModifier();
        
        if (java.lang.reflect.Modifier.isAbstract(modifiers)) {
            modList.add(ModifierType.ABSTRACT);
        }
        if (java.lang.reflect.Modifier.isFinal(modifiers)) {
            modList.add(ModifierType.FINAL);
        }
        if (java.lang.reflect.Modifier.isInterface(modifiers)) {
            modList.add(ModifierType.INTERFACE);
        }
        if (java.lang.reflect.Modifier.isNative(modifiers)) {
            modList.add(ModifierType.NATIVE);
        }
        if (java.lang.reflect.Modifier.isPrivate(modifiers)) {
            modList.add(ModifierType.PRIVATE);
        }
        if (java.lang.reflect.Modifier.isProtected(modifiers)) {
            modList.add(ModifierType.PROTECTED);
        }
        if (java.lang.reflect.Modifier.isPublic(modifiers)) {
            modList.add(ModifierType.PUBLIC);
        }
        if (java.lang.reflect.Modifier.isStatic(modifiers)) {
            modList.add(ModifierType.STATIC);
        }
        if (java.lang.reflect.Modifier.isStrict(modifiers)) {
            modList.add(ModifierType.STRICT);
        }
        if (java.lang.reflect.Modifier.isSynchronized(modifiers)) {
            modList.add(ModifierType.SYNCHRONIZED);
        }
        if (java.lang.reflect.Modifier.isTransient(modifiers)) {
            modList.add(ModifierType.TRANSIENT);
        }
        if (java.lang.reflect.Modifier.isVolatile(modifiers)) {
            modList.add(ModifierType.VOLATILE);
        }
    }

}
