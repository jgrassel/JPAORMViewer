package com.ibm.ws.jpa.diagnostics.utils.encapsulation;

public enum CompressionType {
    NONE,
    GZIP,
    ZIP;
    
    public String toString() {
        switch (this) {
            case GZIP:
                return "GZIP";
            case ZIP:
                return "ZIP";
            default:
                return "NONE";
        }
    }
    
    public static CompressionType fromString(String s) {
        switch (s) {
            case "GZIP":
                return GZIP;
            case "ZIP":
                return ZIP;
            default:
                return NONE;
        }
    }
}
