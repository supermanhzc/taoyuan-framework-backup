package com.taoyuan.framework.aaa.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taoyuan.framework.aaa.config.TyRealm;
import com.taoyuan.framework.aaa.entity.UserInfo;
import com.taoyuan.framework.aaa.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/getpass/{id}", method = RequestMethod.GET)
    public String getPass(@PathVariable(value = "id") Long id){
        TyRealm realm = new TyRealm();

        UserInfo userInfo = userInfoService.getById(id);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getSalt()),//salt=username+salt
                realm.getName()  //realm name
        );


        String newPassword = new SimpleHash("md5", authenticationInfo.getCredentials(),
                ByteSource.Util.bytes(authenticationInfo.getCredentialsSalt()), 2).toHex();

        return  newPassword;
    }

    public static void main(String[] args){
        System.out.println(new AuthController().getPass(1L));
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody UserInfo userInfo){
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUsername(), userInfo.getPassword());

        try {
            subject.login(token);
            jsonObject.put("token", subject.getSession().getId());
            jsonObject.put("msg", "登录成功");
        } catch (IncorrectCredentialsException e) {
            jsonObject.put("msg", "密码错误");
        } catch (LockedAccountException e) {
            jsonObject.put("msg", "登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
            jsonObject.put("msg", "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @RequestMapping(value = "/unauth", method = RequestMethod.GET)
    public String unauth() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "请重新登录");
        return jsonObject.toString();
    }
}
