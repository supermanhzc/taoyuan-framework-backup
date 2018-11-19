package com.taoyuan.framework.bs.mail.lib;

import com.taoyuan.framework.bs.mail.config.AppConfig;
import com.taoyuan.framework.bs.mail.lib.base.ISender;
import com.taoyuan.framework.bs.mail.lib.base.SenderWapper;

public class MessageBalance extends SenderWapper {

	protected AppConfig config = null;

	public MessageBalance(AppConfig config) {
		this.config = config;
	}

	@Override
	public ISender getSender() {
		return new Message(this.config);
	}

	public String balance() {
		return getSender().balance(requestData);
	}

}
