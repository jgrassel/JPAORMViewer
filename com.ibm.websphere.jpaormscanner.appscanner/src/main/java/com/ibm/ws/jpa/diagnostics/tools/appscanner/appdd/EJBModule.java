package com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd;

import org.w3c.dom.Node;

/**
 * EJB Module, for JPA we do care about this.
 *
 */
public class EJBModule extends Module {
    private String ejbUri;
    
    public EJBModule(Node ejbModuleNode, String id) {
        super(id);
        
        Node ejbUriNode = ejbModuleNode.getFirstChild();
        if (ejbUriNode != null) {
            ejbUri = ejbUriNode.getTextContent();
        }
    }
    
    public ModuleType getModuleType() {
        return ModuleType.ejb;
    }

    public String getEjbUri() {
        return ejbUri;
    }
    
    @Override
    public String toString() {
        return "EJBModule [ejbUri=" + ejbUri + ", getModuleId()=" + getModuleId() + "]";
    }   
}