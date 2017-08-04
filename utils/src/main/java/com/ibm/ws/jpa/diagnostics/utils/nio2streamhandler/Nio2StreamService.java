package com.ibm.ws.jpa.diagnostics.utils.nio2streamhandler;

import java.net.URL;
import java.nio.file.FileSystem;
import java.util.Objects;

public final class Nio2StreamService {
    private static boolean hasRegistered = false;
    
    public static synchronized void registerNio2StreamService() {
        if (!hasRegistered) {
            URL.setURLStreamHandlerFactory(new Nio2FileSystemURLStreamHandlerFactory());
            hasRegistered = true;
        }        
    }
    
    public static synchronized Nio2FileSystemRegistryToken registerFileSystem(FileSystem fs) {
        registerNio2StreamService();
        Objects.nonNull(fs);
        return Nio2StreamServiceRegistry.registerFileSystem(fs);
    }
    
    public static synchronized boolean deregisterFileSystem(Nio2FileSystemRegistryToken token) {
        registerNio2StreamService();
        Objects.nonNull(token);
        token.deregister();
        return Nio2StreamServiceRegistry.deregisterFileSystem(token);
    }
}
