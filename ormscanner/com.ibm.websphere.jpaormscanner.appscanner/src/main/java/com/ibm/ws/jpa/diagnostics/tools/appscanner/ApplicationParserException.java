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
