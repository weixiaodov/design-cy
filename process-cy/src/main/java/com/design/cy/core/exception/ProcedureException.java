package com.design.cy.core.exception;

import lombok.Data;

@Data
public class ProcedureException extends RuntimeException {

    private int code;
    private String msg;
    private String detail;
    private Throwable error;

    public ProcedureException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public ProcedureException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ProcedureException(String msg, int code, String detail) {
        super(msg);
        this.msg = msg;
        this.code = code;
        this.detail = detail;
    }

    public ProcedureException(String msg, int code, String detail, Throwable error) {
        super(msg, error);
        this.msg = msg;
        this.code = code;
        this.detail = detail;
        this.error = error;
    }
}
