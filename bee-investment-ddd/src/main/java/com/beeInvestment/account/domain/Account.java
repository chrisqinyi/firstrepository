package com.beeInvestment.account.domain;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.or;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.all;
import static pl.com.bottega.ecommerce.system.Functions.property;
import static pl.com.bottega.ecommerce.system.Predicates.*;
import static pl.com.bottega.ecommerce.system.Reduce.reduce;
import static pl.com.bottega.ecommerce.system.ReduceFunctions.sum;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
	@Transient
	@Inject
	private WithdrawFactory withdrawFactory;
	@Transient
	@Inject
	private IncomeFactory incomeFactory;
	@Transient
	@Inject
	private OutgoFactory outgoFactory;
	private Account() {
	}
	private boolean frozen;
	public void freeze(){
		frozen=true;
	} 
	boolean isFrozen() {
		return frozen;
	}
	public Withdraw withdraw(Money amount){
		checkArgument(!frozen,"Account frozen!");
		checkArgument(canAfford(amount),"Can not afford: " + amount);
		Withdraw withdraw = withdrawFactory.create(this, amount);
		return withdraw;
	}
	public Deposit deposit(Money amount) {
		Deposit deposit = depositFactory.create(this, amount);
		return deposit;
	}

	public void closeTransaction(Transaction transaction) {
		currentBalance = currentBalance.add(transaction.getDirectionalPayload());
	}

	Account(AggregateId aggregateId, CustomerData customerData) {
		this.aggregateId = aggregateId;
		this.customerData = customerData;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
	@Fetch(FetchMode.JOIN)
	@OrderBy(value = "generateTimeStamp")
	private Set<Transaction> transactions = new HashSet<Transaction>();

	public List<Transaction> getTransactions() {
		return Collections.unmodifiableList(new ArrayList<Transaction>(transactions));
	}

	public void createTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

	public void cancelTransaction(Transaction transaction) {
		transactions.remove(transaction);
	}

	private Money currentBalance = Money.ZERO;

	public Money getTotalBalance() {
		return currentBalance.add(reduce(from(transactions).transform(property("processedPayload",Money.class)),sum()));
	}

	public Money getAvailableBalance() {
		return getTotalBalance().subtract(reduce(from(transactions).filter(and(propertyNotEquals("direction",TransactionDirection.INCOME),propertyNotEquals("status",TransactionStatus.SUCCESS))).transform(property("payload",Money.class)),sum()));
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
		checkArgument(canAfford(amount),"Can not afford: " + amount);
		currentBalance = currentBalance.subtract(amount);
		return paymentFactory.createPayment(this.customerData, amount);
	}
	public void income(Money amount){
		Income create = incomeFactory.create(this, amount);
		create.confirm();
	}
	public Outgo outgo(Transaction transaction,Money amount){
		return outgoFactory.create(this,transaction, amount);
	}
	public boolean canAfford(Money amount) {
		return !this.getAvailableBalance().lessThan(amount);
	}

	public void register(Customer customer) {
	}

	@Embedded
	private CustomerData customerData;
}
