package com.design.cy.core.process;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

/**
 * 处理流程入口
 *
 * @author longlin
 * @date 18/5/2
 */
@Slf4j
@Data
public class ProcedureManagerImpl extends NormalMessageListener<ProcedureSubmitTask>  implements
        IProcedureManager {

    @Autowired
    private listeber sendMqHelper;

    @Autowired

    private listener mqTagNames;
    /*
    流程名对应列表映射map
    key为流程名
    value是处理流程的processor列表
     */
    private Map<String, IProcedureChainManager> procedureChainManagerMap;

    @Override
    public void submit(ProcedureSubmitTask task) {
        task.setProcedureId(UUID.randomUUID().toString());
        log.info("Workflow started: {}", task.getProcedureId());
        if(procedureChainManagerMap == null) {
            throw new RuntimeException();
        }
        IProcedureChainManager procedureChainManager = procedureChainManagerMap.get(task.getProcedureName());
        try {
            procedureChainManager.process(task.getProcedureContext());
            log.info("Workflow succeed: {}", task.getProcedureId());
        } catch (RollbackException e) {
            log.info("Workflow rolling back: {}", task.getProcedureId());
            Map<String,Object> rollbackMap = (Map<String,Object>)task.getProcedureContext().getAdditionalInfo().get
                    (ZrrXxGlConstant.OPERATE_ROLLBACK_KEY);
            ProcedureSubmitTask taskRollBack = new ProcedureSubmitTask();
            taskRollBack.setProcedureName(task.getProcedureName());
            ProcedureContext context = new ProcedureContext();
            context.setAdditionalInfo(rollbackMap);
            context.setRollbackSwitch(task.getProcedureContext().getRollbackSwitch());
            context.setRollbackCursor(task.getProcedureContext().getRollbackCursor());
            taskRollBack.setProcedureContext(context);
            MessageDTO<ProcedureSubmitTask> message = new MessageDTO<>(mqTagNames.getZrrProcedureRollbackTag(),
                    taskRollBack, "rollback");
            sendMqHelper.sendNormalMessage(message, MqProducerNames.PROCEDURE_ROLLBACK_PRODUCER_BEAN);
        }
    }

    /**
     * 回滚流程
     * 会被收到的回滚消息触发
     * @param task
     */
    private void rollback(ProcedureSubmitTask task) {
        IProcedureChainManager procedureChainManager = procedureChainManagerMap.get(task.getProcedureName());
        procedureChainManager.rollback(task.getProcedureContext());
        log.info("Workflow roll back succeed: {}", task.getProcedureId());
    }

    @Override protected void doBusiness(MessageDTO<ProcedureSubmitTask> messageDTO) {
        rollback(messageDTO.getBody());
    }
}
