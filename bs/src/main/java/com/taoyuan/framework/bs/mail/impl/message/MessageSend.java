package com.taoyuan.framework.bs.mail.impl.message;

import com.taoyuan.framework.bs.mail.config.AppConfig;
import com.taoyuan.framework.bs.mail.impl.base.ISender;
import com.taoyuan.framework.bs.mail.impl.base.AbstractSender;
/**
 * message/send API 不仅提供强大的短信发送功能, 
 * 并在API中集成了地址薄发送功能。你可以通过设定一些参数来确定 API 以哪种模式发送。
 * @author submail
 *
 */
public class MessageSend extends AbstractSender {
	
	protected AppConfig config = null;
	public static final String TO = "to";
	public static final String CONTENT = "content";
	
    public MessageSend(AppConfig config) {
		
		this.config = config;
		
	}
	
	public void addTo(String to) {
		requestData.addWithComma(TO, to);;
	}
	
	public void addContent(String content){
		requestData.addWithComma(CONTENT, content);
	}

	
	@Override
	public ISender getSender() {
		return new  Message(this.config);
	}

	public String send(){
		return getSender().send(requestData);
	}
	
	
}
	
	
	
	


