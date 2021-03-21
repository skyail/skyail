package com.skyail.user.controller;

import com.skyail.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
public class TestController {

    @GetMapping("/show")

    //@PermitAll
    //@PreAuthorize("hasRole('admin')")
    public R<String> show(){
        return R.data("this is show");
    }
}
