package com.beeInvestment.account.domain;

import javax.persistence.Entity;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionDirection;
import com.beeInvestment.transaction.domain.TransactionStatus;

@Entity
public class Withdraw extends Transaction {
	private Withdraw() {
	}

	public Withdraw(AggregateId transactionId, Account account, Money payload) {
		super(transactionId, account, payload, TransactionDirection.OUTGO);
		this.status = TransactionStatus.NEW;
	}

	@Override
	public void process() {
		if(account.isFrozen()){
			domainError("Account frozen!");
		}
		//TODO
		this.status = TransactionStatus.SUCCESS;
	}

}
