package com.design.cy.core.process;

/**
 * 工作流程管理
 * 每一个IProcedureChainManager代表了一个业务的处理流程
 * 里面包含了由多个CommonProcedureProcessor组成的流程
 */
public interface IProcedureChainManager {

    /**
     * 业务处理流程
     * 负责主要业务流程的处理，以及处理过程中加入上下文的回滚数据记录
     * @param context
     */
    void process(ProcedureContext context);

    /**
     * 回滚流程
     * 回滚process处理流程
     * @param context
     */
    void rollback(ProcedureContext context);

}
