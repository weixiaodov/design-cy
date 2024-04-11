package com.design.cy.core.process;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class ProcedureContext implements Serializable {

    private String code;

    private String message;

    private Boolean success = Boolean.TRUE;

    private Object params;

    private Object[] args;

    private Boolean sendMq = Boolean.FALSE;

    private Boolean rollbackSwitch = Boolean.FALSE;

    private Integer rollbackCursor;

    private Map<String, Object> additionalInfo;

    public void put(String key, Object value) {
        if (additionalInfo == null) {
            additionalInfo = new HashMap<>();
        }
        if (additionalInfo.containsKey(key)) {
            throw new RuntimeException("This method does not support key overlap. Try the other put.");
        }
        additionalInfo.put(key, value);
    }

    public Object get(String key) {
        if (additionalInfo == null) {
            return null;
        }
        return additionalInfo.get(key);
    }

    public void remove(String key){
        if(additionalInfo ==null){
            return;
        }
        additionalInfo.remove(key);
    }

    public void setSendMq(Boolean sendMq){
        if(!this.sendMq){
            this.sendMq = sendMq;
        }
    }
}
