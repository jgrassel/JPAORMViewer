package com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd;

import org.w3c.dom.Node;

/**
 * Java Module, for JPA we don't care about this.
 *
 */
public class JavaModule extends Module {

    public JavaModule(Node node, String id) {
        super(id);
    }
    
    public ModuleType getModuleType() {
        return ModuleType.java;
    }
}