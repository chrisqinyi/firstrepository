package com.beeInvestment.investment.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionDirection;

@Entity
public class Reward extends Transaction {
	private Reward() {
	}
	public void transferTo(Account account) {
		this.account = account;
	}
	Reward(AggregateId transactionId,BigDecimal periodIndex, Money reward, Investment investment) {
		super(transactionId,investment.getAccount(),reward,TransactionDirection.INCOME);
		this.target = investment.getTarget();
		this.investment = investment;
		this.periodIndex = periodIndex;
	}

	public BigDecimal getPeriodIndex() {
		return periodIndex;
	}

	public Money getReward() {
		return this.payload;
	}

	private BigDecimal periodIndex;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "target_id")
	private Target target;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "investment_id")
	private Investment investment;

	public void fulfill() {
		investment.reward(this);
	}
	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}
}
