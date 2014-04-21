package com.beeInvestment.application;

import java.math.BigDecimal;

import pl.com.bottega.cqrs.annotations.Command;

@Command()
public class InvestCommand {
	private final String targetId;
	private final BigDecimal fund;
	private final String customerId;
	public BigDecimal getFund() {
		return fund;
	}

	public String getTargetId() {
		return targetId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public InvestCommand(String targetId,String customerId, BigDecimal fund) {
		super();
		this.fund = fund;
		this.targetId = targetId;
		this.customerId=customerId;
	}

}
