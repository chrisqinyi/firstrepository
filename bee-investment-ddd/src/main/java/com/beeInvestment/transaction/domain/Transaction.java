package com.beeInvestment.transaction.domain;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import pl.com.bottega.ddd.support.domain.BaseAggregateRoot;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Transaction extends BaseAggregateRoot implements Comparable<Transaction>{
	@Access(value=AccessType.PROPERTY)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	@Fetch(FetchMode.JOIN)
	@OrderBy(value = "generateTimeStamp")
	protected Set<Transaction> transactions=new HashSet<Transaction>();

	private Set<Transaction> getTransactions() {
		return transactions;
	}

	protected void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	private Long generateTimeStamp=null;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_id")
	protected Transaction parent;
	Long getGenerateTimeStamp() {
		return generateTimeStamp;
	}

	private void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public int compareTo(Transaction t) {
		return generateTimeStamp.compareTo(t.getGenerateTimeStamp());
	}

	protected Transaction() {
	}

	public Integer getTransactionIndex() {
		if (null == account)
			return -1;
		return this.account.getTransactions().indexOf(this);
	}

	protected Transaction(AggregateId transactionId, Account account,
			Money payload, TransactionDirection direction) {
		super();
		this.aggregateId = transactionId;
		this.account = account;
		this.payload = payload;
		account.createTransaction(this);
		this.direction = direction;
		this.status=TransactionStatus.NEW;
		generateTimeStamp=System.currentTimeMillis();
	}

	protected Transaction(AggregateId transactionId, Account account,Transaction parent,
			Money payload, TransactionDirection direction) {
		this(transactionId, account,
				payload, direction);
		this.parent=parent;
	}
	
	protected TransactionDirection direction;

	public TransactionDirection getDirection() {
		return direction;
	}

	protected TransactionStatus status = TransactionStatus.NEW;

	public TransactionStatus getStatus() {
		return status;
	}

	@Embedded
	protected Money payload;

	public Money getDirectionalPayload() {
		return getPayload().multiplyBy(new BigDecimal(this.direction
				.equals(TransactionDirection.INCOME) ? 1 : -1));
	}
	@Access(value = AccessType.PROPERTY)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	protected Account account;

	public Account getAccount() {
		if(null!=parent){
			return parent.getAccount();
		}
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
		this.status=TransactionStatus.FAILED;
	}

	public void close() {
		account.closeTransaction(this);
		//account = null;
	}

	public abstract void process();

	public void confirm() {
		this.status = TransactionStatus.PROCESSING;
		//parent=null;
		// this.eventPublisher.publish(new
		// TransactionConfirmedEvent(this.aggregateId.getId()));
	}

	@Override
	public String toString() {
		String string=super.toString();
		try {
			string = BeanUtils.describe(this).toString();
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		return string;
	}

}