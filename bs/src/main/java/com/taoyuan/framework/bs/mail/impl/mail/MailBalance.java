package com.taoyuan.framework.bs.mail.impl.mail;

import com.taoyuan.framework.bs.mail.config.AppConfig;
import com.taoyuan.framework.bs.mail.impl.base.ISender;
import com.taoyuan.framework.bs.mail.impl.base.AbstractSender;

public class MailBalance extends AbstractSender {

	protected AppConfig config = null;

	public MailBalance(AppConfig config) {
		this.config = config;
	}

	@Override
	public ISender getSender() {
		return new Mail(this.config);
	}

	public String balance() {
		return getSender().balance(requestData);
	}

}
