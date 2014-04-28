package com.beeInvestment.investment.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.account.domain.Outgo;
import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionDirection;
import com.beeInvestment.transaction.domain.TransactionStatus;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

@Entity
public class Investment extends Transaction {
	@Inject
	@Transient
	private RewardFactory rewardFactory;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "target_id")
	private Target target;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "outgo_id")
	private Outgo fundVariance;

	@Override
	protected void setTransactions(Set<Transaction> transactions) {
		// TODO Auto-generated method stub
		super.setTransactions(transactions);
	}

	public Money getFund() {
		return getPayload();
	}

	void transferTo(Account account) {
		this.account = account;
		// this.status=TransactionStatus.PROCESSING;
		// for (Reward reward : rewards) {
		// reward.transferTo(account);
		// }
		fundVariance.transferTo(account);
	}

	public Target getTarget() {
		return target;
	}

	private Investment() {
	}

	private Money processFund(Target target, Money fund) {
		Money result = fund;
		while (!result.divideBy(target.getPeriods())
				.multiplyBy(target.getPeriods()).equals(result)) {
			result = result.subtract(new Money(new BigDecimal(0.01)));
		}
		return result;
	}

	Investment(AggregateId transactionId, Target target, Account account,
			Money fund) {
		super(transactionId, account, fund, TransactionDirection.OUTGO);
		this.target = target;
	}

	Set<Reward> getRewards() {
		return rewards;
	}

	// @Access(value = AccessType.PROPERTY)
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "parent")
	// @Fetch(FetchMode.JOIN)
	// @OrderBy(value = "generateTimeStamp")
	@Transient
	private Set<Reward> rewards = new HashSet<Reward>();

	private void setRewards(Set<Reward> rewards) {
		this.rewards = Sets.filter(rewards, new Predicate<Reward>() {
			public boolean apply(Reward r) {
				return r.getStatus().compareTo(TransactionStatus.NEW) == 0;
			}
		});

		// this.rewards=new ArrayList(this.rewards);
	}

	Collection<Reward> calculateRewards() {
		Set<Reward> result = new HashSet<Reward>();
		Money rewardFund = this.payload.multiplyBy(target.getInterestRate())
				.divideBy(this.target.getPeriods());
		for (int i = 1; i <= target.getPeriods().intValue(); i++) {
			Reward create = rewardFactory.create(new BigDecimal(i), rewardFund,
					this);
			result.add(create);
		}
		// this.rewards = result;
		transactions.addAll(rewards);
		return result;
	}

	public Iterable<Reward> sdfsdfRewards() {
		return Iterables.filter(rewards, new Predicate<Reward>() {
			public boolean apply(Reward r) {
				return r.getStatus().compareTo(TransactionStatus.NEW) == 0;
			}
		});

	}

	public void reward(BigDecimal periodIndex) {
		if (!target.getStatus().equals(TargetStatus.REWARDING)) {
			throw new RuntimeException(
					"Rewards can only be processed with target in rewarding status");
		}
		Iterator<Reward> iterator = rewards.iterator();
		while (iterator.hasNext()) {
			Reward reward = iterator.next();
			if (reward.getPeriodIndex().intValue() == periodIndex.intValue()) {
				reward.confirm();
				// iterator.remove();
			}
		}

//		if (null != fundVariance)
//			fundVariance.confirm();
//		account.outgo(payload).confirm();
		
		// Money amount =
		// this.payload.multiplyBy(this.getOutstandingRewards().size());
		// this.payload = this.payload.subtract(amount);
		// account.income(payload);

		// remainingPeriods=remainingPeriods.subtract(new BigDecimal(1));
	}

	@Override
	public void confirm() {
		super.confirm();
		Money fund = this.processFund(target, payload);
		Money f = fund.divideBy(target.getPeriods());
		fundVariance = account.outgo(this,payload.subtract(fund).add(f));
		for (int i = 0; i < target.getPeriods().intValue()-1; i++) {
			transactions.add(account.outgo(this,f));
		}
		calculateRewards();
		this.payload = Money.ZERO;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		this.status = TransactionStatus.SUCCESS;
	}

	Boolean isReleased() {
		return null == this.target;
	}
}
