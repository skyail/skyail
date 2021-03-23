package com.skyail.service;

import com.skyail.entity.SysRole;
import com.skyail.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SkyailUserDetailServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if("test".equals(s)){
            SysUser user = new SysUser();
            user.setId(1);
            user.setPassword(encoder.encode("123123"));
            user.setStatus(0);
            List<SysRole> roles = new ArrayList<>();
            SysRole role = new SysRole();
            role.setId(1);
            //这个地方比较坑，spring security oauth2 的 @PreAuthorize("hasRole('admin')") 注解中，hasRole表达式默认会添加前缀 ROLE_,
            //所以，如果生成token的时候，没有这个前缀，那么接口权限就无法通过了，会报 403，所以这里需要添加前缀 ROLE_
            role.setRoleName("admin");
            role.setRoleDesc("admin");
            roles.add(role);
            user.setRoles(roles);
            return user;
        }
        return null;
    }
}
