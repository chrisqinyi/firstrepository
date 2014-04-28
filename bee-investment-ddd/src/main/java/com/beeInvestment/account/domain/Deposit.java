package com.beeInvestment.account.domain;

import javax.persistence.Entity;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionDirection;
import com.beeInvestment.transaction.domain.TransactionStatus;
@Entity
public class Deposit extends Transaction{
private Deposit(){}

Deposit(AggregateId transactionId, Account account, Money payload) {
	super(transactionId, account, payload, TransactionDirection.INCOME);
	this.status = TransactionStatus.CONFIRMED;
}

@Override
public void process() {
	//TODO
	this.status=TransactionStatus.SUCCESS;
	System.out.println("process in deposit");
}

}
