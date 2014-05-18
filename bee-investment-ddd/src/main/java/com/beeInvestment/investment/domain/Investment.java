package com.beeInvestment.investment.domain;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.all;
import static pl.com.bottega.ecommerce.system.Predicates.invoke;
import static pl.com.bottega.ecommerce.system.Predicates.propertyEquals;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionDirection;
import com.beeInvestment.transaction.domain.TransactionStatus;
import com.google.common.collect.FluentIterable;

@Entity
public class Investment extends Transaction {
	@Inject
	@Transient
	private RewardFactory rewardFactory;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "target_id")
	private Target target;

	@Override
	protected void setTransactions(Set<Transaction> transactions) {
		super.setTransactions(transactions);
		this.outstandingRewards=from(transactions).filter(Reward.class).filter(propertyEquals("status",TransactionStatus.NEW));
	}

	void transferTo(Account account) {
		this.account = account;
	}

	private Investment() {
	}

	Investment(AggregateId transactionId, Target target, Account account,
			Money fund) {
		super(transactionId, account, fund, TransactionDirection.OUTGO);
		this.target = target;
	}

	public void reward(BigDecimal periodIndex) {
		all(from(outstandingRewards).filter(propertyEquals("periodIndex",periodIndex)),invoke("confirm"));
		if(outstandingRewards.isEmpty()){
			this.process();
		}
	}
	@Transient
	private FluentIterable<Reward> outstandingRewards=FluentIterable.from(new HashSet<Reward>());
	
	public FluentIterable<Reward> getOutstandingRewards() {
		return outstandingRewards;
	}

	public void approve() {
		Money f = payload.subtract(payload.modBy(target.getPeriods())).divideBy(target.getPeriods());
		for (int i=1; this.payload.greaterThan(Money.ZERO);i++) {
			Money ff=this.payload.divideBy(2).lessThan(f)?this.payload:f;
			transactions.add(account.outgo(this, ff));
			System.out.println("new BigDecimal(i)new BigDecimal(i)new BigDecimal(i)new BigDecimal(i)new BigDecimal(i)new BigDecimal(i):"+i);
			transactions.add(rewardFactory.create(new BigDecimal(i),ff.multiplyBy(target.getInterestRate()), this));
			this.payload=this.payload.subtract(ff);
		}
	}

	@Override
	public void process() {
		this.status = TransactionStatus.SUCCESS;
	}

	Boolean isReleased() {
		return null == this.target;
	}
}
