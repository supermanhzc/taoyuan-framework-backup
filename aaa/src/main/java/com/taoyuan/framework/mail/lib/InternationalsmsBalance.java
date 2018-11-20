package com.taoyuan.framework.mail.lib;

import com.taoyuan.framework.mail.config.AppConfig;
import com.taoyuan.framework.mail.lib.base.ISender;
import com.taoyuan.framework.mail.lib.base.SenderWapper;

public class InternationalsmsBalance extends SenderWapper {
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
