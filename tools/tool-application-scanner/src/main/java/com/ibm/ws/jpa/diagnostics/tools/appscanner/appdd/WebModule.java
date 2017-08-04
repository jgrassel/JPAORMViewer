package com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Web Module, for JPA we do care about this.
 *
 */
public class WebModule extends Module {
    private String id;
    private String contextRoot;
    private String webUri;
    
    public WebModule(final Node webModuleNode, final String id) {
        super(id);
        
        final NamedNodeMap nodeAttributes = webModuleNode.getAttributes();
        final Node idAttributeNode = nodeAttributes.getNamedItem("id");
        this.id = (idAttributeNode != null) ? idAttributeNode.getTextContent() : null;  
        
        final NodeList childNodes = webModuleNode.getChildNodes();
        for (int index = 0; index < childNodes.getLength(); index++) {
            final Node childNode = childNodes.item(index);
            final String nodeName = childNode.getNodeName();
            if (nodeName.startsWith("#")) {
                continue;
            }
            if ("context-root".equalsIgnoreCase(nodeName)) {
                contextRoot = childNode.getTextContent();
            } else if ("web-uri".equalsIgnoreCase(nodeName)) {
                webUri = childNode.getTextContent();
            }
        }
    }
    
    public ModuleType getModuleType() {
        return ModuleType.web;
    }
    
    public String getWebId() {
        return id;
    }
    
    public String getContextRoot() {
        return contextRoot;
    }
    
    public String getWebUri() {
        return webUri;
    }

    @Override
    public String toString() {
        return "WebModule [webId=" + id + ", contextRoot=" + contextRoot + ", webUri=" + webUri + 
                ", getModuleId()=" + getModuleId() + "]";
    }
}