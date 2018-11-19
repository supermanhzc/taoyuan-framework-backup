package com.taoyuan.framework.bs.mail.lib;

import com.taoyuan.framework.bs.mail.config.AppConfig;
import com.taoyuan.framework.bs.mail.lib.base.ISender;
import com.taoyuan.framework.bs.mail.lib.base.SenderWapper;

/**
 * voice/verify 是 SUBMAIL 的语音验证码 API ，语音现在和短信共享发送许可。
 *
 * @author submail
 */
public class VoiceVerify extends SenderWapper {

    protected AppConfig config = null;
    public static final String TO = "to";
    public static final String CODE = "code";

    public VoiceVerify(AppConfig config) {

        this.config = config;

    }

    public void addTo(String to) {
        requestData.addWithComma(TO, to);
        ;
    }


    public void addCode(String code) {
        requestData.addWithComma(CODE, code);
    }


    @Override
    public ISender getSender() {
        return new Voice(this.config);
    }

    public String verify() {
        return getSender().verify(requestData);
    }


}
	
	
	
	


