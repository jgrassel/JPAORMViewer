package com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd;

import org.w3c.dom.Node;

/**
 * Connector Module, for JPA we don't care about this.
 *
 */
public class ConnectorModule extends Module {

    public ConnectorModule(Node node, String id) {
        super(id);
    }
    
    public ModuleType getModuleType() {
        return ModuleType.connector;
    }
}