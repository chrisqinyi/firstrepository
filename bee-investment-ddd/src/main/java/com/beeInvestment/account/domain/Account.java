package com.beeInvestment.account.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import pl.com.bottega.ddd.annotations.domain.AggregateRoot;
import pl.com.bottega.ddd.support.domain.BaseAggregateRoot;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.CustomerData;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.customer.domain.Customer;
import com.beeInvestment.payment.domain.Payment;
import com.beeInvestment.payment.domain.PaymentFactory;
import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionDirection;
import com.beeInvestment.transaction.domain.TransactionHolder;
import com.beeInvestment.transaction.domain.TransactionStatus;

@Entity
@AggregateRoot
public class Account extends BaseAggregateRoot implements TransactionHolder {
	@Transient
	private PaymentFactory paymentFactory;
	@Transient
	@Inject
	private DepositFactory depositFactory;

	private Account() {
	}

	public Deposit deposit(Money amount) {
		Deposit deposit = depositFactory.create(this, amount);
		//deposit.confirm();
		//deposit.process();
		//deposit.close();
		System.out.println("DepositDeposit: "+transactions);
		return deposit;
	}

	public void closeTransaction(Transaction transaction) {
		totalBalance = totalBalance.add(transaction.getDirectionalPayload());
		transactions.remove(transaction);
	}

	Account(AggregateId aggregateId, CustomerData customerData) {
		this.aggregateId = aggregateId;
		this.customerData = customerData;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
	@Fetch(FetchMode.JOIN)
	@OrderColumn(name = "itemNumber")
	private Set<Transaction> transactions = new HashSet<Transaction>();

	public Set<Transaction> getTransactions() {
		System.out.println("transactionstransactions: "+transactions);
		return Collections.unmodifiableSet(transactions);
	}

	public void createTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

	public void cancelTransaction(Transaction transaction) {
		transactions.remove(transaction);
	}

	private Money totalBalance = new Money(new BigDecimal(0));

	public Money getTotalBalance() {
		Money additionalBalance = new Money(0);
		for (Transaction transaction : transactions) {
			additionalBalance = additionalBalance.add(transaction
					.getProcessedPayload());
		}
		return totalBalance.add(additionalBalance);
	}

	public Money getAvailableBalance() {
		Money frozenBalance = new Money(0);
		for (Transaction transaction : transactions) {
			if (transaction.getDirection().equals(TransactionDirection.INCOME)) {
				continue;
			}
			if (transaction.getStatus().equals(TransactionStatus.SUCCESS)) {
				continue;
			}
			frozenBalance = frozenBalance.add(transaction.getPayload());
		}
		return getTotalBalance().subtract(frozenBalance);
	}

	/**
	 * Sample model: one aggregate creates another.<br>
	 * Client model does not compose Payment - therefore it does not manage
	 * Payment lifecycle.<br>
	 * Application layer is responsible for managing Payment lifecycle;
	 * 
	 * @param amount
	 * @return
	 */
	public Payment charge(Money amount) {
		if (!canAfford(amount)) {
			domainError("Can not afford: " + amount);
		}
		totalBalance = totalBalance.subtract(amount);
		return paymentFactory.createPayment(this.customerData, amount);
	}

	public boolean canAfford(Money amount) {
		return this.getAvailableBalance().greaterThan(amount);
	}

	public void register(Customer customer) {
	}

	@Embedded
	private CustomerData customerData;
}
