/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

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