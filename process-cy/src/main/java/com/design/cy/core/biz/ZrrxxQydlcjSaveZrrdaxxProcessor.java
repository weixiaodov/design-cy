package com.design.cy.core.biz;

import com.design.cy.core.process.AbstractWrappedProcedureProcessor;
import com.design.cy.core.process.ProcedureContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ZrrxxQydlcjSaveZrrdaxxProcessor extends AbstractWrappedProcedureProcessor<ProcedureContext> {

    @Override
    public void process(ProcedureContext context) {
    }

    @Override
    public Class<? extends ProcedureContext> getAcceptableParamClass() {
        return ProcedureContext.class;
    }
}
