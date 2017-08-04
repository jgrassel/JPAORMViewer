package com.ibm.ws.jpa.diagnostics.utils.nio2streamhandler;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class Nio2FileSystemURLStreamHandlerFactory implements URLStreamHandlerFactory {
    public final static String PROTOCOL = "nio2fs";

    public Nio2FileSystemURLStreamHandlerFactory() {
        
    }

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (PROTOCOL.equals(protocol)) {
            return new Nio2FileSystemURLStreamHandler();
        }

        return null;
    }

}
