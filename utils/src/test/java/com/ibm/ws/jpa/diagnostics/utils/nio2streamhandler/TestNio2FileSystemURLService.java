package com.ibm.ws.jpa.diagnostics.utils.nio2streamhandler;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestNio2FileSystemURLService {
    private static File cDir;
    private static File resDir;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        cDir = new File(System.getProperty("user.dir"));
        resDir = new File(cDir, "src/test/resources");
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
    public void testRegisterAndDeregisterFileSystem() throws Exception {
        Path zipFilePath = Paths.get(resDir.getAbsolutePath(), "SimpleZip.zip");
        FileSystem zfs = FileSystems.newFileSystem(zipFilePath, null);
        assertNotNull(zfs);
        
        Nio2FileSystemRegistryToken token = Nio2StreamService.registerFileSystem(zfs);
        assertNotNull(token);
        
        UUID uuid = token.getUuid();
        assertNotNull(uuid);
        assertNotNull(token.getFileSystem());
        assertSame(zfs, token.getFileSystem());
        
        Path aFileTxtPath = zfs.getPath("aFile.txt");
        assertTrue(Files.exists(aFileTxtPath));
        
        URL url = token.url(aFileTxtPath, false);
        
        String expectedURLStr = "nio2fs://" + uuid.toString() + "/aFile.txt";
        assertEquals(expectedURLStr, url.toExternalForm());
        
        URL urlZF = token.url(aFileTxtPath, true);
        
        String expectedZFURLStr = "nio2fs://" + uuid.toString() + "/aFile.txt?jarFileFormat=true";
        assertEquals(expectedZFURLStr, urlZF.toExternalForm());
        
        assertTrue(token.isRegistered());
        assertTrue(Nio2StreamService.deregisterFileSystem(token));
        assertFalse(token.isRegistered());
        try {
            token.url(aFileTxtPath, false);
            fail("Didn't throw expected IllegalStateException.");
        } catch (IllegalStateException ise) {
            // Expected
        }
        assertFalse(Nio2StreamService.deregisterFileSystem(token));
    }

    @Test
    public void testReadFileFromURL() throws Exception {
        Path zipFilePath = Paths.get(resDir.getAbsolutePath(), "SimpleZip.zip");
        FileSystem zfs = FileSystems.newFileSystem(zipFilePath, null);
        assertNotNull(zfs);
        
        Nio2FileSystemRegistryToken token = Nio2StreamService.registerFileSystem(zfs);
        assertNotNull(token);
        
        Path aFileTxtPath = zfs.getPath("aFile.txt");
        assertTrue(Files.exists(aFileTxtPath));
        
        URL url = token.url(aFileTxtPath, false);
        assertNotNull(url);
        
        URLConnection urlConn = url.openConnection();
        assertNotNull(urlConn);
        
        InputStream is = url.openStream();
        assertNotNull(is);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = br.readLine();
        assertNotNull(line);
        assertEquals("This is a file to read.", line);
        br.close();
    }
    
    @Test
    public void testReadAsJarInputStream() throws Exception {
        Path zipFilePath = Paths.get(resDir.getAbsolutePath(), "SimpleZip.zip");
        FileSystem zfs = FileSystems.newFileSystem(zipFilePath, null);
        assertNotNull(zfs);
        
        Nio2FileSystemRegistryToken token = Nio2StreamService.registerFileSystem(zfs);
        assertNotNull(token);
        
        Path aFileTxtPath = zfs.getPath("/");
        assertTrue(Files.exists(aFileTxtPath));
        
        URL url = token.url(aFileTxtPath, true);
        assertNotNull(url);
        
        URLConnection urlConn = url.openConnection();
        assertNotNull(urlConn);
        
        InputStream is = url.openStream();
        assertNotNull(is);
        
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry ze = zis.getNextEntry();
        assertNotNull(ze);
        
        assertEquals("aFile.txt", ze.getName());
        byte[] buffer = new byte[1024];
        int bytesRead = zis.read(buffer, 0, 1024);
        assertEquals(24, bytesRead);
        
        int index = 0;
        for (byte b : "This is a file to read.\n".getBytes()) {
            assertTrue (b == buffer[index++]);
        }

        zis.close();
    }
}
