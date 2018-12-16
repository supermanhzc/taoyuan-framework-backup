package com.taoyuan.framework.bs.mail.impl.base;

public abstract class AbstractSender {

	protected MailMsgTreeMap requestData = new MailMsgTreeMap();

	public abstract ISender getSender();
}
