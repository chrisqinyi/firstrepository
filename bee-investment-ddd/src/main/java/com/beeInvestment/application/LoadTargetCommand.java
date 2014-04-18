package com.beeInvestment.application;

import java.math.BigDecimal;

import pl.com.bottega.cqrs.annotations.Command;
import pl.com.bottega.ecommerce.sharedkernel.Money;

@Command()
public class LoadTargetCommand {
	public LoadTargetCommand(Money totalFund, BigDecimal interestRate,
			BigDecimal periods) {
		super();
		this.totalFund = totalFund;
		this.periods = periods;
		this.interestRate = interestRate;
	}
	private final Money totalFund;
	private final BigDecimal interestRate;
	private final BigDecimal periods;

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public Money getTotalFund() {
		return totalFund;
	}

	public BigDecimal getPeriods() {
		return periods;
	}

}
