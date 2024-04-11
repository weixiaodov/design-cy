package com.design.cy.core.process;

public interface IProcedureManager {

    /**
     * 提交一个处理流程
     * 会根据处理流程name取出处理流程列表
     * 如果流程失败，则发送失败的mq消息
     * @param task
     */
    void submit(ProcedureSubmitTask task);

}
