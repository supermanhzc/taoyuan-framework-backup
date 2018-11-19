package com.taoyuan.framework.bs.mail.lib;

import com.taoyuan.framework.bs.mail.config.AppConfig;
import com.taoyuan.framework.bs.mail.lib.base.ISender;
import com.taoyuan.framework.bs.mail.lib.base.SenderWapper;
/**
 * 邮件地址薄 API
 * @author submail
 *
 */
public class ADDRESSBOOKMail extends SenderWapper {

	/**
	 * mail模式{@link MailConfig}
	 */
	protected AppConfig config = null;
	public static final String ADDRESS = "address";
	public static final String TARGET = "target";

	public ADDRESSBOOKMail(AppConfig config) {
		this.config = config;
	}
	
	public void setAddress(String address, String name){
		requestData.put(ADDRESS, name + "<" + address + ">");
	}
	
	public void setAddressbook(String target){
		requestData.put(TARGET, target);
	}
	@Override
	public ISender getSender() {
		return new Mail(this.config);
	}
	
	public String subscribe(){
		return getSender().subscribe(requestData);
	}
	
	public String unsubscribe(){
		return getSender().unsubscribe(requestData);
	}
}
