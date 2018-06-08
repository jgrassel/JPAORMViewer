package com.ibm.ws.jpa.diagnostics.utils.encapsulation;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

public class TestEncapsulatedData {
    private final String strData = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
            "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis " + 
            "nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute " + 
            "irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " + 
            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit " + 
            "anim id est laborum.";  
    
    @Test
    public void testEncapsulatedDataWithZIPCompression() throws Exception {
        final String name = "aName";
        final String id = "42";
        final CompressionType ct = CompressionType.ZIP;
        final String hashAlg = "MD5";
        
        byte[] data = strData.getBytes();     
        
        EncapsulatedData ed = EncapsulatedData.createEncapsulatedData(name, id, ct, hashAlg, data);
        assertNotNull(ed);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ed.writeToString(baos);
        
//        System.out.println(baos);
        
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        EncapsulatedData edRead = EncapsulatedData.readEncapsulatedData(bais);
        assertNotNull(edRead);
        assertEquals(name, edRead.getName());
        assertEquals(id, edRead.getId());
        assertEquals(ct, edRead.getCompressionType());
        assertEquals(hashAlg, edRead.getHashAlgorithm());
        assertEquals(ed.getHashValue(), edRead.getHashValue());
        
        assertArrayEquals(data, edRead.getData());
    }
    
    @Test
    public void testEncapsulatedDataWithNONECompression() throws Exception {
        final String name = "aName";
        final String id = "42";
        final CompressionType ct = CompressionType.NONE;
        final String hashAlg = "MD5";
        
        byte[] data = strData.getBytes();
        
        EncapsulatedData ed = EncapsulatedData.createEncapsulatedData(name, id, ct, hashAlg, data);
        assertNotNull(ed);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ed.writeToString(baos);
        
//        System.out.println(baos);
                
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        EncapsulatedData edRead = EncapsulatedData.readEncapsulatedData(bais);
        assertNotNull(edRead);
        assertEquals(name, edRead.getName());
        assertEquals(id, edRead.getId());
        assertEquals(ct, edRead.getCompressionType());
        assertEquals(hashAlg, edRead.getHashAlgorithm());
        assertEquals(ed.getHashValue(), edRead.getHashValue());
        
        assertArrayEquals(data, edRead.getData());
    }
    
    @Test
    public void testEncapsulatedDataWithGZIPCompression() throws Exception {
        final String name = "aName";
        final String id = "42";
        final CompressionType ct = CompressionType.GZIP;
        final String hashAlg = "MD5";
        
        byte[] data = strData.getBytes();
        
        EncapsulatedData ed = EncapsulatedData.createEncapsulatedData(name, id, ct, hashAlg, data);
        assertNotNull(ed);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ed.writeToString(baos);
        
//        System.out.println(baos);
                
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        EncapsulatedData edRead = EncapsulatedData.readEncapsulatedData(bais);
        assertNotNull(edRead);
        assertEquals(name, edRead.getName());
        assertEquals(id, edRead.getId());
        assertEquals(ct, edRead.getCompressionType());
        assertEquals(hashAlg, edRead.getHashAlgorithm());
        assertEquals(ed.getHashValue(), edRead.getHashValue());
        
        assertArrayEquals(data, edRead.getData());
    }
    
    @Test
    public void testEncapsulatedDataProperties() throws Exception {
        final String name = "aName";
        final String id = "420";
        final CompressionType ct = CompressionType.GZIP;
        final String hashAlg = "MD5";
        
        byte[] data = strData.getBytes();
        
        EncapsulatedData ed = EncapsulatedData.createEncapsulatedData(name, id, ct, hashAlg, data);
        assertNotNull(ed);
        
        ed.setProperty("A Prop", "Some Value");
        assertEquals(1, ed.getProperties().size());
        assertTrue(ed.getProperties().containsKey("A Prop"));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ed.writeToString(baos);
        
//        System.out.println(baos);
                
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        EncapsulatedData edRead = EncapsulatedData.readEncapsulatedData(bais);
        assertNotNull(edRead);
        assertEquals(name, edRead.getName());
        assertEquals(id, edRead.getId());
        assertEquals(ct, edRead.getCompressionType());
        assertEquals(hashAlg, edRead.getHashAlgorithm());
        assertEquals(ed.getHashValue(), edRead.getHashValue());
        
        assertArrayEquals(data, edRead.getData());
        
        assertEquals(1, edRead.getProperties().size());
        assertTrue(edRead.getProperties().containsKey("A Prop"));
        
        edRead.setProperty("Another Prop", "Some Other Value");
        assertEquals(2, edRead.getProperties().size());
        assertTrue(edRead.getProperties().containsKey("A Prop"));
        assertTrue(edRead.getProperties().containsKey("Another Prop"));
        
        baos = new ByteArrayOutputStream();
        edRead.writeToString(baos);
        
//        System.out.println(baos);
        
        bais = new ByteArrayInputStream(baos.toByteArray());
        edRead = EncapsulatedData.readEncapsulatedData(bais);
        assertNotNull(edRead);
        assertEquals(name, edRead.getName());
        assertEquals(id, edRead.getId());
        assertEquals(ct, edRead.getCompressionType());
        assertEquals(hashAlg, edRead.getHashAlgorithm());
        assertEquals(ed.getHashValue(), edRead.getHashValue());
        
        assertArrayEquals(data, edRead.getData());
        
        assertEquals(2, edRead.getProperties().size());
        assertTrue(edRead.getProperties().containsKey("A Prop"));
        assertTrue(edRead.getProperties().containsKey("Another Prop"));
        
        edRead.removeProperty("A Prop");
        assertEquals(1, edRead.getProperties().size());
        assertFalse(edRead.getProperties().containsKey("A Prop"));
        assertTrue(edRead.getProperties().containsKey("Another Prop"));
        
        baos = new ByteArrayOutputStream();
        edRead.writeToString(baos);
        
//        System.out.println(baos);
        
        bais = new ByteArrayInputStream(baos.toByteArray());
        edRead = EncapsulatedData.readEncapsulatedData(bais);
        assertNotNull(edRead);
        assertEquals(name, edRead.getName());
        assertEquals(id, edRead.getId());
        assertEquals(ct, edRead.getCompressionType());
        assertEquals(hashAlg, edRead.getHashAlgorithm());
        assertEquals(ed.getHashValue(), edRead.getHashValue());
        
        assertArrayEquals(data, edRead.getData());
        
        assertEquals(1, edRead.getProperties().size());
        assertFalse(edRead.getProperties().containsKey("A Prop"));
        assertTrue(edRead.getProperties().containsKey("Another Prop"));
    }

    @Test
    public void testEncapsulatedDataGroup() throws Exception {
        EncapsulatedData ed = EncapsulatedData.createEncapsulatedData("aName", "42", CompressionType.GZIP, 
                "MD5", strData.getBytes());
        assertNotNull(ed);
        
        EncapsulatedDataGroup edg = EncapsulatedDataGroup.createEncapsulatedDataGroup("aGroup", "21");
        assertNotNull(edg);
        edg.putDataItem(ed);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        edg.writeToString(baos);
        
//        System.out.println(baos);
        
    }
    
    @Test
    public void testEncapsulatedDataGroupWithProperties() throws Exception {
        EncapsulatedData ed = EncapsulatedData.createEncapsulatedData("aName", "42", CompressionType.GZIP, 
                "MD5", strData.getBytes());
        assertNotNull(ed);
        
        EncapsulatedDataGroup edg = EncapsulatedDataGroup.createEncapsulatedDataGroup("aGroup", "21");
        assertNotNull(edg);
        edg.putDataItem(ed);
        
        edg.setProperty("A Prop", "Some Value");
        assertEquals(1, edg.getProperties().size());
        assertTrue(edg.getProperties().containsKey("A Prop"));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        edg.writeToString(baos);
        
        System.out.println(baos);
        
    }
}
