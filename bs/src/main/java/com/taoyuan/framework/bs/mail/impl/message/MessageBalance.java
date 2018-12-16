package com.taoyuan.framework.bs.mail.impl.message;

import com.taoyuan.framework.bs.mail.config.AppConfig;
import com.taoyuan.framework.bs.mail.impl.base.ISender;
import com.taoyuan.framework.bs.mail.impl.base.AbstractSender;

public class MessageBalance extends AbstractSender {

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
