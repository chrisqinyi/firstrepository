package com.beeInvestment.application;

import pl.com.bottega.cqrs.annotations.Command;
import pl.com.bottega.ecommerce.sharedkernel.Money;

@Command()
public class InvestCommand {
	private final String targetId;
	private final Money fund;
	private final String customerId;
	public Money getFund() {
		return fund;
	}

	public String getTargetId() {
		return targetId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public InvestCommand(String targetId,String customerId, Money fund) {
		super();
		this.fund = fund;
		this.targetId = targetId;
		this.customerId=customerId;
	}

}
