package com.beeInvestment.investment.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionDirection;

@Entity
public class Investment extends Transaction {
	@Inject
	@Transient
	private RewardFactory rewardFactory;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "target_id")
	private Target target;

	public Money getFund() {
		return this.payload;
	}

	public void transferTo(Account account) {
		this.account = account;
		for(Reward reward:rewards){
			reward.transferTo(account);
		}
	}

	public Target getTarget() {
		return target;
	}

	private Investment() {
	}

	public Set<Reward> getRewards() {
		return rewards;
	}

	Investment(AggregateId transactionId, Target target, Account account,
			Money fund) {
		super(transactionId,account,fund,TransactionDirection.OUTCOME);
		this.target = target;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "investment")
	@Fetch(FetchMode.JOIN)
	@OrderColumn(name = "itemNumber")
	private Set<Reward> rewards = new HashSet<Reward>();

	public Set<Reward> calculateRewards() {
		Set<Reward> result = new HashSet<Reward>();
		Money rewardFund = this.payload.multiplyBy(
				target.getInterestRate()).divideBy(
				this.target.getPeriods());
		for (int i = 1; i <= target.getPeriods().intValue(); i++) {
			Reward create = rewardFactory.create(new BigDecimal(i), rewardFund,
					this);
			result.add(create);
		}
		this.rewards = result;
		return result;
	}

	public void reward(Reward reward) {
		this.rewards.remove(reward);
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}
}
