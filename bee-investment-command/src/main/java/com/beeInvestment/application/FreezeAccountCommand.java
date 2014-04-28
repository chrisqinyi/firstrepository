package com.beeInvestment.application;

import pl.com.bottega.cqrs.annotations.Command;

@Command()
public class FreezeAccountCommand {
	private final String accountId;

	public String getAccountId() {
		return accountId;
	}

	public FreezeAccountCommand(String accountId) {
		super();
		this.accountId = accountId;
	}

}
