package com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd;

public enum ModuleType {
    connector ("connector", ConnectorModule.class),
    ejb ("ejb", EJBModule.class),
    java ("java", JavaModule.class),
    web ("web", WebModule.class),
    alt_dd ("alt-dd", AltDDModule.class);
    
    private String tagName;
    private Class<?> moduleClassType;
    private ModuleType(String tagName, Class<?> moduleClassType) {
        this.tagName = tagName;
        this.moduleClassType = moduleClassType;
    }
    
    public String getTagName() {
        return tagName;
    }
    
    public static ModuleType resolveByNodeName(String nodeName) {
        if (nodeName == null) {
            return null;
        }
        
        for (ModuleType mt : ModuleType.values()) {
            if (mt.getTagName().equalsIgnoreCase(nodeName)) {
                return mt;
            }
        }
        
        return null;
    }
}