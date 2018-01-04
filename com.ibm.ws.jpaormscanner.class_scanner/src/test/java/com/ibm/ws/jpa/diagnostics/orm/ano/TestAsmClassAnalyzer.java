package com.ibm.ws.jpa.diagnostics.orm.ano;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.AnnotationInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.AnnotationsType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ClassInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.FieldInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.FieldsType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.InnerClassesType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.MethodInfoType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.MethodsType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ModifierType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ParameterType;
import com.ibm.ws.jpa.diagnostics.orm.ano.jaxb.classinfo10.ParametersType;

public class TestAsmClassAnalyzer {
	private static File cDir;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cDir = new File(System.getProperty("user.dir"));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAsmClassAnalyzerWithSimpleJPAEntity() throws Exception {
		// Find SimpleJPAEntity.class in a way that works in both command line gradle and in eclipse
		final File[] targetFile = new File[1];
		Files.walk(Paths.get(cDir.getAbsolutePath()))
		.filter(p -> p.getFileName().toString().equals("SimpleJPAEntity.class"))
		.forEach(p -> targetFile[0] = p.toFile());

		Assert.assertNotNull(targetFile[0]);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try (FileInputStream fis = new FileInputStream(targetFile[0])) {
			byte[] buffer = new byte[4096];
			int bytesRead;  		
			do {
				bytesRead = fis.read(buffer);
				if (bytesRead > 0)
					baos.write(buffer, 0, bytesRead);
			} while (bytesRead >= 0);
		}

		ClassInfoType cit = AsmClassAnalyzer.analyzeClass("", baos.toByteArray());
		Assert.assertNotNull(cit);

		assertEquals("com.ibm.ws.jpa.diagnostics.orm.ano.testentities.SimpleJPAEntity", cit.getClassName());
		assertEquals("com.ibm.ws.jpa.diagnostics.orm.ano.testentities", cit.getPackageName());
		assertEquals("java.lang.Object", cit.getSuperclassName());

		assertFalse(cit.isIsAnonymous());
		assertFalse(cit.isIsEnum());
		assertFalse(cit.isIsInterface());
		assertFalse(cit.isIsSynthetic());
		assertEquals(1, cit.getModifiers().getModifier().size());
		assertTrue(cit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
		
		// Test Class Annotations
		final AnnotationsType classAnnos = cit.getAnnotations();
        assertNotNull(classAnnos);
        final List<AnnotationInfoType> clsAnnoList = classAnnos.getAnnotation();       
        assertNotNull(clsAnnoList);
        assertEquals(1, clsAnnoList.size());
        AnnotationInfoType clsAit = clsAnnoList.get(0);
        assertNotNull(clsAit);
        assertEquals("Entity", clsAit.getName());
        assertEquals("javax.persistence.Entity", clsAit.getType());
		
		// Test Class Fields
        final FieldsType ft = cit.getFields();
        assertNotNull(ft);
        
        final List<FieldInfoType> fieldsList = ft.getField();
        assertNotNull(fieldsList);
        assertEquals(5, fieldsList.size());
        
        final Map<String, Boolean> fieldsPassed = new HashMap<String, Boolean>();
        fieldsPassed.put("id", Boolean.FALSE);
        fieldsPassed.put("version", Boolean.FALSE);
        fieldsPassed.put("persistentString", Boolean.FALSE);
        fieldsPassed.put("persistentInteger", Boolean.FALSE);
        fieldsPassed.put("persistentIntegerWrapper", Boolean.FALSE);
        
        for (FieldInfoType fit : fieldsList) {
            if ("id".equals(fit.getName())) {
                // Test id
                assertTrue(fit.getModifiers().getModifier().contains(ModifierType.PRIVATE));
                assertEquals("int", fit.getType());
                
                final AnnotationsType fieldAnnos = fit.getAnnotations();
                assertNotNull(fieldAnnos);
                final List<AnnotationInfoType> fieldAnnoList = fieldAnnos.getAnnotation();       
                assertNotNull(fieldAnnoList);
                assertEquals(1, fieldAnnoList.size());
                AnnotationInfoType fieldAit = fieldAnnoList.get(0);
                assertNotNull(fieldAit);
                assertEquals("Id", fieldAit.getName());
                assertEquals("javax.persistence.Id", fieldAit.getType());
                
                fieldsPassed.put("id", Boolean.TRUE);
            } else if ("version".equals(fit.getName())) {
                // Test version
                assertTrue(fit.getModifiers().getModifier().contains(ModifierType.PRIVATE));
                assertEquals("int", fit.getType());
                
                final AnnotationsType fieldAnnos = fit.getAnnotations();
                assertNotNull(fieldAnnos);
                final List<AnnotationInfoType> fieldAnnoList = fieldAnnos.getAnnotation();       
                assertNotNull(fieldAnnoList);
                assertEquals(1, fieldAnnoList.size());
                AnnotationInfoType fieldAit = fieldAnnoList.get(0);
                assertNotNull(fieldAit);
                assertEquals("Version", fieldAit.getName());
                assertEquals("javax.persistence.Version", fieldAit.getType());
                
                fieldsPassed.put("version", Boolean.TRUE);
            } else if ("persistentString".equals(fit.getName())) {
                // Test persistentString
                assertTrue(fit.getModifiers().getModifier().contains(ModifierType.PRIVATE));
                assertEquals("java.lang.String", fit.getType());
                
                final AnnotationsType fieldAnnos = fit.getAnnotations();
                assertNotNull(fieldAnnos);
                final List<AnnotationInfoType> fieldAnnoList = fieldAnnos.getAnnotation();       
                assertNotNull(fieldAnnoList);
                assertEquals(1, fieldAnnoList.size());
                AnnotationInfoType fieldAit = fieldAnnoList.get(0);
                assertNotNull(fieldAit);
                assertEquals("Basic", fieldAit.getName());
                assertEquals("javax.persistence.Basic", fieldAit.getType());
                
                fieldsPassed.put("persistentString", Boolean.TRUE);
            } else if ("persistentInteger".equals(fit.getName())) {
                // Test persistentInteger
                assertTrue(fit.getModifiers().getModifier().contains(ModifierType.PRIVATE));
                assertEquals("int", fit.getType());
                
                final AnnotationsType fieldAnnos = fit.getAnnotations();
                assertNotNull(fieldAnnos);
                final List<AnnotationInfoType> fieldAnnoList = fieldAnnos.getAnnotation();       
                assertNotNull(fieldAnnoList);
                assertEquals(1, fieldAnnoList.size());
                AnnotationInfoType fieldAit = fieldAnnoList.get(0);
                assertNotNull(fieldAit);
                assertEquals("Basic", fieldAit.getName());
                assertEquals("javax.persistence.Basic", fieldAit.getType());
                
                fieldsPassed.put("persistentInteger", Boolean.TRUE);
            } else if ("persistentIntegerWrapper".equals(fit.getName())) {
                // Test persistentIntegerWrapper
                assertTrue(fit.getModifiers().getModifier().contains(ModifierType.PRIVATE));
                assertEquals("java.lang.Integer", fit.getType());
                
                final AnnotationsType fieldAnnos = fit.getAnnotations();
                assertNotNull(fieldAnnos);
                final List<AnnotationInfoType> fieldAnnoList = fieldAnnos.getAnnotation();       
                assertNotNull(fieldAnnoList);
                assertEquals(1, fieldAnnoList.size());
                AnnotationInfoType fieldAit = fieldAnnoList.get(0);
                assertNotNull(fieldAit);
                assertEquals("Basic", fieldAit.getName());
                assertEquals("javax.persistence.Basic", fieldAit.getType());
                
                fieldsPassed.put("persistentIntegerWrapper", Boolean.TRUE);
            }
        }
        
        for (String key : fieldsPassed.keySet()) {
            assertEquals("Test Field: " + key, Boolean.TRUE, fieldsPassed.get(key));
        }
        
        // Test Class Methods
        final Map<String, Boolean> methodsPassed = new HashMap<String, Boolean>();
        methodsPassed.put("getId", Boolean.FALSE);
        methodsPassed.put("setId", Boolean.FALSE);
        methodsPassed.put("getVersion", Boolean.FALSE);
        methodsPassed.put("setVersion", Boolean.FALSE);
        methodsPassed.put("getPersistentString", Boolean.FALSE);
        methodsPassed.put("setPersistentString", Boolean.FALSE);
        methodsPassed.put("getPersistentInteger", Boolean.FALSE);
        methodsPassed.put("setPersistentInteger", Boolean.FALSE);
        methodsPassed.put("getPersistentIntegerWrapper", Boolean.FALSE);
        methodsPassed.put("setPersistentIntegerWrapper", Boolean.FALSE);
        methodsPassed.put("equals", Boolean.FALSE);
        methodsPassed.put("hashCode", Boolean.FALSE);
        methodsPassed.put("toString", Boolean.FALSE);
        methodsPassed.put("doPanic", Boolean.FALSE);
        
        boolean[] foundCtors = { false, false };
        
        final MethodsType mt = cit.getMethods();
        assertNotNull(mt);
        final List<MethodInfoType> methodsList = mt.getMethod();
        assertNotNull(methodsList);
        assertEquals(methodsPassed.keySet().size() + 2, methodsList.size()); // +2 because the two ctors have the same name (so same key)
        
        for (MethodInfoType mit : methodsList) {
            if ("equals".equals(mit.getMethodName())) {
                // Test equals
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("boolean", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNotNull(pt);
                final List<ParameterType> pList = pt.getParameter();
                assertNotNull(pList);
                assertEquals(1, pList.size());
                ParameterType parm1 = pList.get(0);
                assertNotNull(parm1);
                assertEquals("java.lang.Object", parm1.getType());
                assertFalse(parm1.isIsPrimitive());
                assertFalse(parm1.isIsArray());
                assertFalse(parm1.isIsSynthetic());

                methodsPassed.put("equals", Boolean.TRUE);
            } else if ("toString".equals(mit.getMethodName())) {
                // Test toString
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("java.lang.String", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNull(pt);

                methodsPassed.put("toString", Boolean.TRUE);
            } else if ("hashCode".equals(mit.getMethodName())) {
                // Test hashCode
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("int", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNull(pt);

                methodsPassed.put("hashCode", Boolean.TRUE);
            } else if ("getId".equals(mit.getMethodName())) {
                // Test getId
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("int", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNull(pt);

                methodsPassed.put("getId", Boolean.TRUE);
            } else if ("setId".equals(mit.getMethodName())) {
                // Test setId
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("void", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNotNull(pt);
                final List<ParameterType> pList = pt.getParameter();
                assertNotNull(pList);
                assertEquals(1, pList.size());
                ParameterType parm1 = pList.get(0);
                assertNotNull(parm1);
                assertEquals("int", parm1.getType());
                assertTrue(parm1.isIsPrimitive());
                assertFalse(parm1.isIsArray());
                assertFalse(parm1.isIsSynthetic());

                methodsPassed.put("setId", Boolean.TRUE);
            } else if ("getVersion".equals(mit.getMethodName())) {
                // Test getVersion
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("int", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNull(pt);

                methodsPassed.put("getVersion", Boolean.TRUE);
            } else if ("setVersion".equals(mit.getMethodName())) {
                // Test setVersion
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("void", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNotNull(pt);
                final List<ParameterType> pList = pt.getParameter();
                assertNotNull(pList);
                assertEquals(1, pList.size());
                ParameterType parm1 = pList.get(0);
                assertNotNull(parm1);
                assertEquals("int", parm1.getType());
                assertTrue(parm1.isIsPrimitive());
                assertFalse(parm1.isIsArray());
                assertFalse(parm1.isIsSynthetic());

                methodsPassed.put("setVersion", Boolean.TRUE);
            } else if ("getPersistentString".equals(mit.getMethodName())) {
                // Test getPersistentString
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("java.lang.String", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNull(pt);

                methodsPassed.put("getPersistentString", Boolean.TRUE);
            } else if ("setPersistentString".equals(mit.getMethodName())) {
                // Test setPersistentString
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("void", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNotNull(pt);
                final List<ParameterType> pList = pt.getParameter();
                assertNotNull(pList);
                assertEquals(1, pList.size());
                ParameterType parm1 = pList.get(0);
                assertNotNull(parm1);
                assertEquals("java.lang.String", parm1.getType());
                assertFalse(parm1.isIsPrimitive());
                assertFalse(parm1.isIsArray());
                assertFalse(parm1.isIsSynthetic());

                methodsPassed.put("setPersistentString", Boolean.TRUE);
            } else if ("getPersistentInteger".equals(mit.getMethodName())) {
                // Test getPersistentInteger
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("int", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNull(pt);

                methodsPassed.put("getPersistentInteger", Boolean.TRUE);
            } else if ("setPersistentInteger".equals(mit.getMethodName())) {
                // Test setPersistentInteger
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("void", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNotNull(pt);
                final List<ParameterType> pList = pt.getParameter();
                assertNotNull(pList);
                assertEquals(1, pList.size());
                ParameterType parm1 = pList.get(0);
                assertNotNull(parm1);
                assertEquals("int", parm1.getType());
                assertTrue(parm1.isIsPrimitive());
                assertFalse(parm1.isIsArray());
                assertFalse(parm1.isIsSynthetic());

                methodsPassed.put("setPersistentInteger", Boolean.TRUE);
            } else if ("getPersistentIntegerWrapper".equals(mit.getMethodName())) {
                // Test getPersistentIntegerWrapper
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("java.lang.Integer", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNull(pt);

                methodsPassed.put("getPersistentIntegerWrapper", Boolean.TRUE);
            } else if ("setPersistentIntegerWrapper".equals(mit.getMethodName())) {
                // Test setPersistentIntegerWrapper
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("void", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNotNull(pt);
                final List<ParameterType> pList = pt.getParameter();
                assertNotNull(pList);
                assertEquals(1, pList.size());
                ParameterType parm1 = pList.get(0);
                assertNotNull(parm1);
                assertEquals("java.lang.Integer", parm1.getType());
                assertFalse(parm1.isIsPrimitive());
                assertFalse(parm1.isIsArray());
                assertFalse(parm1.isIsSynthetic());

                methodsPassed.put("setPersistentIntegerWrapper", Boolean.TRUE);
            } else if ("doPanic".equals(mit.getMethodName())) {
                // Test doPanic
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertFalse(mit.isIsCtor());
                assertEquals("void", mit.getReturnType());
                
                final ParametersType pt = mit.getParameters();
                assertNull(pt);

                methodsPassed.put("doPanic", Boolean.TRUE);
            } else if ("<init>".equals(mit.getMethodName())) {
                // One of the two Ctors
                assertTrue(mit.getModifiers().getModifier().contains(ModifierType.PUBLIC));
                assertTrue(mit.isIsCtor());
//                assertEquals("com.ibm.ws.jpa.diagnostics.orm.ano.testentities.SimpleJPAEntity", mit.getReturnType());
                
                ParametersType pt =  mit.getParameters();
                if (pt == null || pt.getParameter().isEmpty()) {
                    // Ctor #1 found
                    foundCtors[0] = true;
                } else {
                    // Ctor #2 found
                    List<ParameterType> ptList = pt.getParameter();
                    assertEquals(4, ptList.size());
                    // TODO: Should validate parameters in more depth
                    foundCtors[1] = true;
                }
            }
        }
        
        for (String key : methodsPassed.keySet()) {
            assertEquals("Test Method: " + key, Boolean.TRUE, methodsPassed.get(key));
        }
        for (boolean b : foundCtors) {
            assertTrue(b);
        }
        
        // Verify inner class
        // Note: Does not seem to detect anonymous classes in methods, so just expect one inner class, for now.
        final InnerClassesType ict = cit.getInnerclasses();
        assertNotNull(ict);
        final List<ClassInfoType> ictList = ict.getInnerclass();
        assertFalse(ictList.isEmpty());
        
        ClassInfoType innerCit = ictList.get(0);
        assertNotNull(innerCit);
        assertEquals("com.ibm.ws.jpa.diagnostics.orm.ano.testentities.SimpleJPAEntity$DumbInnerClass", innerCit.getClassName());

	}
}
