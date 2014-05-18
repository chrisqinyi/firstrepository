package com.beeInvestment.investment.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionDirection;
import com.beeInvestment.transaction.domain.TransactionStatus;

@Entity
public class Reward extends Transaction {
	private Reward() {
	}
	Reward(AggregateId transactionId,BigDecimal periodIndex, Money reward, Investment investment) {
		super(transactionId,investment.getAccount(),investment,reward,TransactionDirection.INCOME);
		this.periodIndex = periodIndex;
	}
	public BigDecimal getPeriodIndex() {
		return periodIndex;
	}
	private BigDecimal periodIndex;
	@Override
	public void process() {
		this.status=TransactionStatus.SUCCESS;
	}
}
