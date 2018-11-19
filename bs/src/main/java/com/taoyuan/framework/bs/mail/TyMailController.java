package com.taoyuan.framework.bs.mail;

import com.taoyuan.framework.bs.mail.config.AppConfig;
import com.taoyuan.framework.bs.mail.lib.MessageSend;
import com.taoyuan.framework.bs.mail.utils.ConfigLoader;
import com.taoyuan.framework.common.http.TyResponse;
import com.taoyuan.framework.common.http.TySuccessResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class TyMailController {

    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    public TyResponse getMailVerificationCode(@RequestBody String phone){
        AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);
        MessageSend submail = new MessageSend(config);
        submail.addTo(phone);
        int code = new Random().nextInt(899999) + 100000;
        submail.addContent("【测试】你好，你的验证码是"+code);
        submail.send();
        return new TySuccessResponse(null);
    }

    public static void main(String[] args) {
        for(int i =0;i<10; i++) {
            System.out.println(new Random().nextInt(899999) + 100000);
        }
    }
}
