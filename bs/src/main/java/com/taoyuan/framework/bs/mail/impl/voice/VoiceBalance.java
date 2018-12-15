package com.taoyuan.framework.bs.mail.impl.voice;

import com.taoyuan.framework.bs.mail.config.AppConfig;
import com.taoyuan.framework.bs.mail.impl.base.ISender;
import com.taoyuan.framework.bs.mail.impl.base.AbstractSender;

public class VoiceBalance extends AbstractSender {
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
