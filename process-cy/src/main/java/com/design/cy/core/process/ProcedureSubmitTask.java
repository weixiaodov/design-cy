package com.design.cy.core.process;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProcedureSubmitTask implements Serializable {

	private String procedureName;

    private String procedureId;

    private ProcedureContext procedureContext;
}
