package com.design.cy.cache.core.cache;

import com.design.cy.cache.core.dto.TestDTO;
import com.design.cy.cache.core.enums.InitStrategy;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TestMapCache extends AbstractMapCache<TestDTO> {

    @Override
    public InitStrategy strategy() {
        return InitStrategy.always;
    }

    @Override
    protected TestDTO load(String key) {
        Random random = new Random();
        TestDTO testDTO1 = new TestDTO();
        testDTO1.setId(random.nextInt());
        testDTO1.setName(key);
        testDTO1.setValue(key);
        return testDTO1;
    }

    @Override
    protected Map<String, TestDTO> load() {

        TestDTO testDTO1 = new TestDTO();
        testDTO1.setId(1);
        testDTO1.setName("111");
        testDTO1.setValue("111");


        TestDTO testDTO12 = new TestDTO();
        testDTO12.setId(2);
        testDTO12.setName("2");
        testDTO12.setValue("2");


        TestDTO testDTO13 = new TestDTO();
        testDTO13.setId(3);
        testDTO13.setName("3");
        testDTO13.setValue("3");
        ArrayList<TestDTO> objects = Lists.newArrayList(testDTO1, testDTO13, testDTO12);

        return objects.stream().collect(Collectors.toMap(TestDTO::getName, x -> x));
    }

}
