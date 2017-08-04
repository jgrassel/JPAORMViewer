package com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd;

import org.w3c.dom.Node;

/**
 * Alt-DD Module, for JPA we don't care about this.
 *
 */
public class AltDDModule extends Module {

    public AltDDModule(Node node, String id) {
        super(id);
    }
    
    public ModuleType getModuleType() {
        return ModuleType.alt_dd;
    }
}