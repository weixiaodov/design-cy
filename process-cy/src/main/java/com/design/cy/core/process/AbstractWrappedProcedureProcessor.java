package com.design.cy.core.process;

import com.design.cy.core.exception.ProcedureException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public abstract class AbstractWrappedProcedureProcessor<T> implements CommonProcedureProcessor {

    /**
     * context 中增加一对keyvalue记录
     * 此方法对于添加同key记录会报错！
     * @param context
     * @param key
     * @param value
     */
    protected void putAdditionalParam(ProcedureContext context, String key, Object value) {
        if(context.getAdditionalInfo() == null) {
            context.setAdditionalInfo(new HashMap<>());
        }
        if(context.getAdditionalInfo().containsKey(key)) {
            throw new ProcedureException("This method does not support key overlap. Try the other put.");
        }
        context.getAdditionalInfo().put(key, value);
    }

    /**
     * context 中增加一对keyvalue记录
     * @param context
     * @param key
     * @param value
     */
    protected void forcePutAdditionalParam(ProcedureContext context, String key, Object value) {
        if(context.getAdditionalInfo() == null) {
            context.setAdditionalInfo(new HashMap<>());
        }
        context.getAdditionalInfo().put(key, value);
    }

    /**
     * 取出key对应的value
     * @param context
     * @param key
     * @return
     */
    protected Object getAdditionalParam(ProcedureContext context, String key) {
        if(context.getAdditionalInfo() == null) {
            return null;
        }
        return context.getAdditionalInfo().get(key);
    }

    @Override
    public void rollback(ProcedureContext context) {
        log.debug("This processor nothing need to rollback");
    }
}
