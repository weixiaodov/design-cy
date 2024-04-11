package com.design.cy.core.biz;

import com.design.cy.core.process.AbstractWrappedProcedureProcessor;
import com.design.cy.core.process.ProcedureContext;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ZrrxxQydlcjInitProcessor extends AbstractWrappedProcedureProcessor<ProcedureContext> {


    @Override
    public void process(ProcedureContext context) {
    }

    @Override
    public Class<? extends ProcedureContext> getAcceptableParamClass() {
        return ProcedureContext.class;
    }
}
