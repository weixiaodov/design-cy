package com.design.cy.core.process;

public interface CommonProcedureProcessor<T extends ProcedureContext> {

    default boolean processorTransactional() {
        return false;
    }

    void process(T context);

    void rollback(T context);

    Class<? extends ProcedureContext> getAcceptableParamClass();
}
