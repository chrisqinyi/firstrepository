package com.beeInvestment.account.domain;

import javax.persistence.Entity;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionDirection;

@Entity
public class Withdraw extends Transaction {
	private Withdraw() {
	}

	public Withdraw(AggregateId transactionId, Account account, Money payload,
			TransactionDirection direction) {
		super(transactionId, account, payload, TransactionDirection.OUTCOME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub

	}

}
