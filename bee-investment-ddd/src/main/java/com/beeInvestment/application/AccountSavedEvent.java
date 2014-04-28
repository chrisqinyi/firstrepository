package com.beeInvestment.application;

import java.io.Serializable;

import pl.com.bottega.ddd.annotations.event.Event;
@Event
public class AccountSavedEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2387756167609556368L;
	private final String accountId;

	public AccountSavedEvent(String accountId) {
		super();
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}
}
