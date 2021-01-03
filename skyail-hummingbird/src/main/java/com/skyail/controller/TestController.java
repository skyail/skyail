package com.skyail.controller;

import com.skyail.auth.simple.annotation.AuthIgnore;
import com.skyail.common.util.R;
import com.skyail.enc.util.sm.EncAndDecUtil;
import com.skyail.entity.Test;
import com.skyail.service.ITestService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
* 
*    前端控制器
*
* @author aixiudou
* @since 2020-11-08
*/
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private ITestService service;

    @GetMapping("/show")
    public String show(String str){
        log.info("this is show");
        return "show: "+str;
    }

    @GetMapping("/save")
    public void save(Test test){
        R.status(service.save(test));
    }

    @GetMapping("/find")
    //@AuthIgnore
    public R find(long id ){
        return R.data(service.findTest(id));
    }

    @GetMapping("/findByCondition")
    @AuthIgnore
    public R findByCondition(String name ){
        Test test = new Test();
        test.setName(name);
        QueryWrapper wrapper = new QueryWrapper(test);
        return R.data(service.list(wrapper));
    }

    @PostMapping("/findByConditionPost")
    public R findByConditionPost( @RequestBody Test test ){
        QueryWrapper wrapper = new QueryWrapper(test);
        return R.data(service.list(wrapper));
    }

    @GetMapping("/delete")
    public void delete( long id ){
        service.deleteTest(id);
    }


    @GetMapping("/testValidate")
    public String testValidate(@Validated Test test){
        if(!StringUtils.isEmpty(test.getId())){
            return "id is "+test.getId();
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
        String key = EncAndDecUtil.generateKeySM4();
        System.out.println(key);
    }
}