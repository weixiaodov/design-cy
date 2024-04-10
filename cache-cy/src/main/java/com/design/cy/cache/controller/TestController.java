package com.design.cy.cache.controller;

import com.design.cy.cache.ICacheService;
import com.design.cy.cache.core.cache.TestMapCache;
import com.design.cy.cache.core.dto.TestDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ICacheService cacheService;

    @Resource
    private TestMapCache testMapCache;


    @GetMapping("test")
    public String test() {
        cacheService.setCacheObject("111:222", 1);
        cacheService.setCacheObject("111:223", 2);


        cacheService.setCacheMapValue("111", "222", 1);
        cacheService.setCacheMapValue("111", "223", 2);
        cacheService.setCacheMapValue("111", "224", 6);
        ArrayList<Object> strings = new ArrayList<>();
        strings.add("222");
        strings.add("223");
        List<Integer> cacheObject = cacheService.getCacheObject("111");

        List<Integer> multiCacheMapVa1lue = cacheService.getMultiCacheMapValue("111", strings);
        return "send success!";
    }

    @GetMapping("get")
    public String get() {
        Map<String, TestDTO> stringTestDTOMap = testMapCache.get();
        return "send success!" + stringTestDTOMap;
    }

    @GetMapping("get1")
    public String get1() {
        TestDTO stringTestDTOMap = testMapCache.get("111");
        return "send success!" + stringTestDTOMap;
    }

    @GetMapping("remove")
    public String remove() {
        testMapCache.remove();
        return "send success!";
    }

    @GetMapping("remove1")
    public String remove1() {
        testMapCache.remove("111");
        return "send success!";
    }

}
