package com.skyail.auth.simple.endpoint;

import com.skyail.auth.simple.annotation.AuthIgnore;
import com.skyail.auth.simple.entity.User;
import com.skyail.auth.simple.service.IUserService;
import com.skyail.auth.simple.util.JwtUtil;
import com.skyail.common.constant.CommonConstants;
import com.skyail.common.constant.ExceptionConstants;
import com.skyail.common.util.R;
import com.skyail.enc.util.sm.EncAndDecUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthEndpoint {

    @Resource
    private IUserService userService;

    @PostMapping("/token")
    @AuthIgnore
    public R getToken(@RequestBody User user){

        //1.首先验证user
        if(user == null){
            return R.fail(ExceptionConstants.TOKEN_USERNAME_OR_PASSWORD_ERROR);
        }
        //2.与配置或者数据库中的用户进行比较
        //因为配置中的密码和数据库中的密码都是加密存储的，这里将传输过来的密码进行加密，然后进行比较
        try {
            user.setPassword(EncAndDecUtil.encryptSM4(CommonConstants.SECRET_KEY, user.getPassword()));
        }catch (Exception e){
            log.error("加密密码出错："+e.getMessage());
            e.printStackTrace();
            return R.fail(ExceptionConstants.TOKEN_GENERATE_ERROR);
        }
        User userS = userService.loadUserByUsername(user.getUsername());
        if(!userS.getUsername().equals(user.getUsername()) || !userS.getPassword().equals(user.getPassword())){
            return R.fail(ExceptionConstants.TOKEN_USERNAME_OR_PASSWORD_ERROR);
        }

        //验证通过则发放token
        try {
            String token = JwtUtil.generateToken(user,  CommonConstants.TOKEN_DEFAULT_EXPIRE);
            return R.data(token);
        }catch (Exception e){
            e.printStackTrace();
            return R.fail(ExceptionConstants.TOKEN_GENERATE_ERROR);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(EncAndDecUtil.encryptSM4(CommonConstants.SECRET_KEY,"admin"));
    }
}
