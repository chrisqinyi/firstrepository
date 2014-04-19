package com.beeInvestment.application;

import java.math.BigDecimal;

import pl.com.bottega.cqrs.annotations.Command;

@Command()
public class TransferCommand {
private final String customerId;
private final String targetId;
private final BigDecimal investmentIndex;
public TransferCommand( String targetId,
		BigDecimal investmentIndex,String customerId) {
	super();
	this.customerId = customerId;
	this.targetId = targetId;
	this.investmentIndex = investmentIndex;
}
public String getCustomerId() {
	return customerId;
}
public String getTargetId() {
	return targetId;
}
public BigDecimal getInvestmentIndex() {
	return investmentIndex;
}

}
