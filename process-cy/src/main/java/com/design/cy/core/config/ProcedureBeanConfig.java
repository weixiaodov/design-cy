package com.design.cy.core.config;

import com.design.cy.core.biz.ZrrxxQydlcjInitProcessor;
import com.design.cy.core.biz.ZrrxxQydlcjSaveZrrdaxxProcessor;
import com.design.cy.core.process.IProcedureChainManager;
import com.design.cy.core.process.ProcedureChainManagerImpl;
import com.design.cy.core.process.ProcedureContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcedureBeanConfig {

    @Bean("beanChainManager1")
    public ProcedureChainManagerImpl getProcedureChainManager() {
        return ProcedureChainManagerImpl.builder()
                .addProcedure(new ZrrxxQydlcjInitProcessor())
                .addProcedure(new ZrrxxQydlcjInitProcessor())
                .addProcedure(new ZrrxxQydlcjSaveZrrdaxxProcessor()).build();
    }

}
