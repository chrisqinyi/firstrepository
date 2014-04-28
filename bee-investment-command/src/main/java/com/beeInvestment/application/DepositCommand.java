package com.beeInvestment.application;

import java.math.BigDecimal;

import pl.com.bottega.cqrs.annotations.Command;

@Command()
public class DepositCommand {
	public DepositCommand(String customerId,BigDecimal amount) {
		super();
		this.amount = amount;
		this.customerId = customerId;
	}
	private final BigDecimal amount;
	private final String customerId;
	public BigDecimal getAmount() {
		return amount;
	}
	public String getCustomerId() {
		return customerId;
	}
}
