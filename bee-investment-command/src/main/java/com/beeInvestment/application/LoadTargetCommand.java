package com.beeInvestment.application;

import java.math.BigDecimal;

import pl.com.bottega.cqrs.annotations.Command;

@Command()
public class LoadTargetCommand {
	public LoadTargetCommand(BigDecimal totalFund, BigDecimal interestRate,
			BigDecimal periods) {
		super();
		this.totalFund = totalFund;
		this.periods = periods;
		this.interestRate = interestRate;
	}
	private final BigDecimal totalFund;
	private final BigDecimal interestRate;
	private final BigDecimal periods;

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public BigDecimal getTotalFund() {
		return totalFund;
	}

	public BigDecimal getPeriods() {
		return periods;
	}

}
