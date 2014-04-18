package com.beeInvestment.application;

import java.math.BigDecimal;

import pl.com.bottega.cqrs.annotations.Command;

@Command()
public class RewardCommand {
	private final String targetId;

	public RewardCommand(String targetId, BigDecimal periodIndex) {
		super();
		this.targetId = targetId;
		this.periodIndex = periodIndex;
	}

	public String getTargetId() {
		return targetId;
	}

	private final BigDecimal periodIndex;

	public BigDecimal getPeriodIndex() {
		return periodIndex;
	}
}
