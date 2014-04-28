package com.beeInvestment.transaction.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import pl.com.bottega.ddd.support.domain.BaseAggregateRoot;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Transaction extends BaseAggregateRoot {
	protected Transaction() {
	}

	protected Transaction(AggregateId transactionId, Account account,
			Money payload, TransactionDirection direction) {
		super();
		this.aggregateId = transactionId;
		this.account = account;
		this.payload = payload;
		account.createTransaction(this);
		this.direction = direction;
	}

	protected TransactionDirection direction;

	public TransactionDirection getDirection() {
		return direction;
	}

	protected TransactionStatus status=TransactionStatus.NEW;

	public TransactionStatus getStatus() {
		return status;
	}

	@Embedded
	protected Money payload;

	public Money getDirectionalPayload() {
		return payload.multiplyBy(new BigDecimal(this.direction
				.equals(TransactionDirection.INCOME) ? 1 : -1));
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	protected Account account;

	public Account getAccount() {
		return account;
	}

	public Money getPayload() {
		return payload;
	}

	public Money getProcessedPayload() {
		if (this.status.equals(TransactionStatus.SUCCESS))
			return this.getDirectionalPayload();
		else
			return new Money(new BigDecimal(0));
	}

	public void cancel() {
		account.cancelTransaction(this);
		account = null;
	}

	public void close() {
		account.closeTransaction(this);
		account = null;
	}

	public abstract void process();

	public void confirm() {
		this.status = TransactionStatus.CONFIRMED;
		//this.eventPublisher.publish(new TransactionConfirmedEvent(this.aggregateId.getId()));
	}
}