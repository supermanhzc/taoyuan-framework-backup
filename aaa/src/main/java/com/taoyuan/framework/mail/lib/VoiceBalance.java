package com.taoyuan.framework.mail.lib;

import com.taoyuan.framework.mail.config.AppConfig;
import com.taoyuan.framework.mail.lib.base.ISender;
import com.taoyuan.framework.mail.lib.base.SenderWapper;

public class VoiceBalance extends SenderWapper {
	protected AppConfig config = null;

	public VoiceBalance(AppConfig config) {
		this.config = config;
	}

	@Override
	public ISender getSender() {
		return new Voice(this.config);
	}

	public String balance() {
		return getSender().balance(requestData);
	}

}