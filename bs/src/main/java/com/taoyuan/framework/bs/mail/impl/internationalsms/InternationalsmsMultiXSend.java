package com.taoyuan.framework.bs.mail.impl.internationalsms;

import com.taoyuan.framework.bs.mail.config.AppConfig;
import com.taoyuan.framework.bs.mail.impl.base.ISender;
import com.taoyuan.framework.bs.mail.impl.base.AbstractSender;
import net.sf.json.JSONObject;
/**
 * internationalsms/multixsend 是 SUBMAIL 的国际短信一对多（即1条API请求发送多个号码，
 * 并可以灵活控制每个联系人的文本变量）和群发 API 。
 * @author submail
 *
 */
public class InternationalsmsMultiXSend extends AbstractSender {
	
	protected AppConfig config = null;
	public static final String TO = "to";
	public static final String PROJECT = "project";
	public static final String  Vars= "vars";
	public static final String  MULTI= "multi";
	
    public InternationalsmsMultiXSend(AppConfig config) {
		
		this.config = config;
		
	}
	
	public void addTo(String to) {
		requestData.addWithComma(TO, to);;
	}
	
	
	public void addProject(String project) {
		requestData.addWithComma(PROJECT, project);;
	}
	
	
	public void addVars(JSONObject json){	
	    requestData.setVarJson(json);
	}
	
	public void addMulti(String toval){

		requestData.addMulti(Vars, TO, toval,MULTI);
	}

	
	@Override
	public ISender getSender() {
		return new Internationalsms(this.config);
	}

	public String multixsend(){
		return getSender().multixsend(requestData);
	}
	
	
}
	
	
	
	


