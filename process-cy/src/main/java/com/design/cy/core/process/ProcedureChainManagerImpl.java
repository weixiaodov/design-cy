package com.design.cy.core.process;

import com.design.cy.core.exception.ProcedureException;
import com.design.cy.core.exception.ProcedureTerminateException;
import com.design.cy.core.exception.RollbackException;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Builder
@Slf4j
public class ProcedureChainManagerImpl implements IProcedureChainManager {

    private Boolean async = Boolean.FALSE;

    private Boolean rollbackAsync = Boolean.FALSE;

    @Singular("addProcedure")
    private List<CommonProcedureProcessor<ProcedureContext>> procedureProcessorList;

    @Override
    public void process(ProcedureContext context) {
        if (procedureProcessorList == null) {
            throw new RuntimeException("procedureProcessorList is null,can not do WorkFlow process");
        }
        int cursor = 0;
        for (; cursor < procedureProcessorList.size(); cursor++) {
            CommonProcedureProcessor<ProcedureContext> commonProcedureProcessor = procedureProcessorList.get(cursor);
            try {
                commonProcedureProcessor.process(context);
            } catch (ProcedureException e) {
                log.error("WorkFlow fails. procedure stop. ", e);
                context.setRollbackSwitch(true);
                context.setRollbackCursor(cursor);
                context.setSuccess(Boolean.FALSE);
                context.setCode(String.valueOf(e.getCode()));
                context.setMessage(e.getMessage());
                //context.setArgs(e.getArgs());
                if (!context.getSendMq()) {
                    throw e;
                }
                throw new RollbackException();
            } catch (ProcedureTerminateException e) {
                return;
            } catch (Exception e) {
                log.error("WorkFlow fails. procedure stop. ", e);
                context.setSuccess(Boolean.FALSE);
                context.setRollbackSwitch(true);
                context.setRollbackCursor(cursor);
                //context.setCode(//);
                context.setMessage(e.getMessage());
                if (!context.getSendMq()) {
                    throw e;
                }
                throw new RollbackException();
            }
        }
    }

    @Override
    public void rollback(ProcedureContext context) {
        if (context.getRollbackSwitch()) {
            Integer cursor = context.getRollbackCursor();
            for (; cursor >= 0; cursor--) {
                CommonProcedureProcessor<ProcedureContext> commonProcedureProcessor = procedureProcessorList.get(cursor);
                try {
                    commonProcedureProcessor.rollback(context);
                } catch (ProcedureException e) {
                    /*
                    回滚失败，继续回滚，不打断流程
                     */
                    context.setCode(String.valueOf(e.getCode()));
                    context.setMessage(e.getMessage());
                    log.warn("Workflow rollback fail.(This node fails, but the rollback procedure will continue.)", e);
                } catch (ProcedureTerminateException e) {
                    return;
                }
            }
        }
    }
}
