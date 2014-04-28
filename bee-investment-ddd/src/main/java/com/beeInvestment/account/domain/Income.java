package com.beeInvestment.account.domain;

import javax.persistence.Entity;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionDirection;
import com.beeInvestment.transaction.domain.TransactionStatus;
@Entity
public class Income extends Transaction{
	private Income(){}
	Income(AggregateId transactionId, Account account, Money payload) {
		super(transactionId, account, payload, TransactionDirection.INCOME);
		this.status = TransactionStatus.PROCESSING;
	}
	@Override
	public void process() {
		this.status=TransactionStatus.SUCCESS;
	}

}
