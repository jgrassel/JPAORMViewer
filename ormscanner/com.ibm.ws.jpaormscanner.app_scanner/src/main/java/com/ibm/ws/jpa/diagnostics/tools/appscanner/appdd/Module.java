package com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd;

public abstract class Module {
    private String id;
    
    public Module(String id) {
        this.id = id;
    }
    
    public abstract ModuleType getModuleType();
    
    public String getModuleId() {
        return id;
    }
}
