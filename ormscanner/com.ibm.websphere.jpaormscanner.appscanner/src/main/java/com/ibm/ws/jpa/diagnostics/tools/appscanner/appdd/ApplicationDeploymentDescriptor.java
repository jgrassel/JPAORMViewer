package com.ibm.ws.jpa.diagnostics.tools.appscanner.appdd;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.ws.jpa.diagnostics.tools.appscanner.ApplicationParserException;

public class ApplicationDeploymentDescriptor {
    public static ApplicationDeploymentDescriptor readApplicationDocument(InputStream is) throws ApplicationParserException{
        ApplicationDeploymentDescriptor add = new ApplicationDeploymentDescriptor();
        add.parseDocument(is);
        return add;
    }
    
    public static ApplicationDeploymentDescriptor readApplicationDocument(File f) throws ApplicationParserException{
        ApplicationDeploymentDescriptor add = new ApplicationDeploymentDescriptor();
        add.parseDocument(f);
        return add;
    }

    final private List<Module> moduleList = new ArrayList<Module>();
    private String libraryDirectory = "lib/";
    
    public List<Module> getModuleList() {
        return Collections.unmodifiableList(moduleList);
    }
    
    public String getLibraryDirectory() {
        return libraryDirectory;
    }
    
    @Override
    public String toString() {
        return "ApplicationDeploymentDescriptor [moduleList=" + moduleList + ", libraryDirectory=" + libraryDirectory
                + "]";
    }

    private ApplicationDeploymentDescriptor() {
        
    }

    private void parseDocument(InputStream is) throws ApplicationParserException {
        try {
            final DocumentBuilder db = newDocumentBuilder();
            final Document doc = db.parse(is);
            parseApplicationDeploymentDescriptorDocument(doc); 
        } catch (ApplicationParserException ape) {
            throw ape;
        } catch (Throwable t) {
            throw new ApplicationParserException(t);
        }             
    }
    
    private void parseDocument(File f) throws ApplicationParserException {
        try {
            final DocumentBuilder db = newDocumentBuilder();
            final Document doc = db.parse(f);
            parseApplicationDeploymentDescriptorDocument(doc); 
        } catch (ApplicationParserException ape) {
            throw ape;
        } catch (Throwable t) {
            throw new ApplicationParserException(t);
        }       
    }
    private DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
//        dbf.setValidating(true);
        return dbf.newDocumentBuilder();
    }
    
    private void parseApplicationDeploymentDescriptorDocument(final Document doc) throws ApplicationParserException {
        final Element root = doc.getDocumentElement();
        if (!"application".equalsIgnoreCase(root.getTagName())) {
            // Root element should be "application".  Not a valid application.xml document
            throw new ApplicationParserException("The application deployment descriptor does not have <application> " + 
            "as the root element, but instead \"" + root.getTagName() + "\".");
        }
        
        // Process Modules
        moduleList.addAll(parseModuleNodes(root.getElementsByTagName("module")));
        
        // Process Library Directory
        NodeList libDirNodeList = root.getElementsByTagName("library-directory");
        if (libDirNodeList != null && libDirNodeList.getLength() > 0) {
            Node libDirNode = libDirNodeList.item(0);
            String libDirNodeVal = libDirNode.getTextContent();
            if (libDirNodeVal != null && !libDirNodeVal.isEmpty()) {
                String libDirValue = libDirNodeVal;
                if (libDirValue.startsWith("/")) {
                    libDirValue = libDirValue.substring(1);
                }
                if (!libDirValue.endsWith("/")) {
                    libDirValue += "/";
                }
                libraryDirectory = libDirValue;
            }
        }     
    }
    
    private List<Module> parseModuleNodes(final NodeList moduleNodeList) {
        if (moduleNodeList == null) {
            return new ArrayList<Module>();
        }
        
        final List<Module> moduleList = new ArrayList<Module>();
        for (int index = 0; index < moduleNodeList.getLength(); index++) {
            Module module = parseModuleNode(moduleNodeList.item(index));
            if (module != null) {
                moduleList.add(module);
            }
        }
        return moduleList;
    }
    
    private Module parseModuleNode(final Node moduleNode) {
        // Fetch ID attribute
        final NamedNodeMap moduleNodeAttributes = moduleNode.getAttributes();
        final Node idAttributeNode = moduleNodeAttributes.getNamedItem("id");
        final String id = (idAttributeNode != null) ? idAttributeNode.getTextContent() : null;        
        final NodeList childNodes = moduleNode.getChildNodes();
        
        for (int index = 0; index < childNodes.getLength(); index++) {
            final Node childNode = childNodes.item(index);
            final String nodeName = childNode.getNodeName();
            if (nodeName.startsWith("#")) {
                continue;
            }
            
            final ModuleType moduleType = ModuleType.resolveByNodeName(nodeName);
            switch (moduleType) {
                case connector:
                    return new ConnectorModule(childNode, id);
                case ejb:
                    return new EJBModule(childNode, id);
                case java:
                    return new JavaModule(childNode, id);
                case web:
                    return new WebModule(childNode, id);
                case alt_dd:
                    return new AltDDModule(childNode, id);
                default:
                    return null;
            } 
        }
        
        return null;   
    }
}
