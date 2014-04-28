package com.beeInvestment.account.domain;

import javax.persistence.Entity;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionDirection;
import com.beeInvestment.transaction.domain.TransactionStatus;

@Entity
public class Outgo extends Transaction {
	private Outgo() {
	}

	Outgo(AggregateId transactionId, Account account, Money payload) {
		super(transactionId, account, payload, TransactionDirection.OUTGO);
		this.status = TransactionStatus.SUCCESS;
	}

	Outgo(AggregateId aggregateId, Account account, Transaction parent,
			Money amount) {
		super(aggregateId, account, parent, amount, TransactionDirection.OUTGO);
		this.status = TransactionStatus.SUCCESS;
	}

	@Override
	public void process() {
		this.status = TransactionStatus.SUCCESS;
	}

	public void transferTo(Account account) {
		this.account = account;
	}
}
