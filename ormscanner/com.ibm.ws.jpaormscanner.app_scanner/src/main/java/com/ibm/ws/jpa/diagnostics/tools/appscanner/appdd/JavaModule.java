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