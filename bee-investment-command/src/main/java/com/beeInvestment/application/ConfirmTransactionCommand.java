package com.beeInvestment.application;

import pl.com.bottega.cqrs.annotations.Command;

@Command()
public class ConfirmTransactionCommand {

	private final String TransactionId;

	public String getTransactionId() {
		return TransactionId;
	}

	public ConfirmTransactionCommand(String TransactionId) {
		super();
		this.TransactionId = TransactionId;
	}
}
