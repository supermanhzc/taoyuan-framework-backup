package com.taoyuan.framework.bs.mail.impl.voice;

import com.taoyuan.framework.bs.mail.config.AppConfig;
import com.taoyuan.framework.bs.mail.impl.base.ISender;
import com.taoyuan.framework.bs.mail.impl.base.AbstractSender;

/**
 * Voice/xsend 是 SUBMAIL 的语音通知 API。
 *
 * @author submail
 */
public class VoiceXSend extends AbstractSender {

    protected AppConfig config = null;
    public static final String TO = "to";
    public static final String PROJECT = "project";
    public static final String VARS = "vars";

    public VoiceXSend(AppConfig config) {

        this.config = config;

    }

    public void addTo(String to) {
        requestData.addWithComma(TO, to);
        ;
    }


    public void addProject(String project) {
        requestData.addWithComma(PROJECT, project);
        ;
    }

    public void addVars(String key, String val) {
        requestData.addWithJson(VARS, key, val);
    }


    @Override
    public ISender getSender() {
        return new Voice(this.config);
    }

    public String xsend() {
        return getSender().xsend(requestData);
    }


}
	
	
	
	


