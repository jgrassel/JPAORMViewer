package com.ibm.ws.jpa.diagnostics.utils.nio2streamhandler;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Nio2FileSystemURLStreamHandler extends URLStreamHandler {

    public Nio2FileSystemURLStreamHandler() {
    }

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        Nio2FileSystemURLConnection newConn = new Nio2FileSystemURLConnection(u);
        newConn.connect();
        return newConn;
    }

}
