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
	void transferTo(Account account) {
		this.account = account;
	}
	Reward(AggregateId transactionId,BigDecimal periodIndex, Money reward, Investment investment) {
		super(transactionId,investment.getAccount(),investment,reward,TransactionDirection.INCOME);
		this.target = investment.getTarget();
//		this.investment = investment;
		this.periodIndex = periodIndex;
	}

	@Override
	public Account getAccount() {
//		if(null!=investment){
//			return investment.getAccount();
//		}
		return super.getAccount();
	}
	BigDecimal getPeriodIndex() {
		return periodIndex;
	}

	Money getReward() {
		return this.payload;
	}

	private BigDecimal periodIndex;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "target_id")
	private Target target;
//	@ManyToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name = "investment_id")
//	private Investment investment;
	@Override
	public void process() {
		// TODO Auto-generated method stub
		//investment.reward(this);
		//this.investment=null;
	}
	@Override
	public void confirm() {
		super.confirm();
//		this.account=investment.getAccount();
//		this.investment=null;
	}
	Boolean isReleased(){
		return null==this.target && null==this.parent;
	}
}
