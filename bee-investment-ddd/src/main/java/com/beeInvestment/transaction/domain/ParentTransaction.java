package com.beeInvestment.transaction.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ParentTransaction extends Transaction {
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	@Fetch(FetchMode.JOIN)
	@OrderBy(value = "generateTimeStamp")
	protected Set<Transaction> transactions=new HashSet<Transaction>();

	public ParentTransaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected ParentTransaction(AggregateId transactionId, Account account,
			Money payload, TransactionDirection direction) {
		super(transactionId, account, payload, direction);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Money getDirectionalPayload() {
		// TODO Auto-generated method stub
		return super.getDirectionalPayload();
	}

	@Override
	public Money getProcessedPayload() {
		// TODO Auto-generated method stub
		return super.getProcessedPayload();
	}


	
	
}