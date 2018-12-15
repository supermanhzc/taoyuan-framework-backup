package com.taoyuan.framework.bs.mail.impl.internationalsms;

import com.taoyuan.framework.bs.mail.config.AppConfig;
import com.taoyuan.framework.bs.mail.impl.base.ISender;
import com.taoyuan.framework.bs.mail.impl.base.AbstractSender;

public class InternationalsmsBalance extends AbstractSender {
	protected AppConfig config = null;

	public InternationalsmsBalance(AppConfig config) {
		this.config = config;
	}

	@Override
	public ISender getSender() {
		return new Internationalsms(this.config);
	}

	public String balance() {
		return getSender().balance(requestData);
	}

}
