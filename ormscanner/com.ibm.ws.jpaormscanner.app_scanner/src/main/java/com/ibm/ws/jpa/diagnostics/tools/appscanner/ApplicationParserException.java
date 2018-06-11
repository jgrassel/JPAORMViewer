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

package com.ibm.ws.jpa.diagnostics.tools.appscanner;

public class ApplicationParserException extends Exception {
    private static final long serialVersionUID = -6407042783784302965L;

    public ApplicationParserException() {
    }

    public ApplicationParserException(String message) {
        super(message);
    }

    public ApplicationParserException(Throwable cause) {
        super(cause);
    }

    public ApplicationParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationParserException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
