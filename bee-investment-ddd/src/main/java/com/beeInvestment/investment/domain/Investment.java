package com.beeInvestment.investment.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import pl.com.bottega.ddd.support.domain.BaseEntity;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.CustomerData;
import pl.com.bottega.ecommerce.sharedkernel.Money;

@Entity
public class Investment extends BaseEntity {
	@Inject
	@Transient
	private RewardFactory rewardFactory;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "target_id")
	private Target target;
	@Embedded
	private Money fund;

	@Embedded
	private CustomerData customerData;

	public Money getFund() {
		return fund;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_id")
	private Investment parent;

	public Target getTarget() {
		return target;
	}

	private Investment() {
	}

	public Set<Reward> getRewards() {
		return rewards;
	}

	Investment(Target target, CustomerData customerData, Money fund,
			Investment parent) {
		this.fund = fund;
		this.target = target;
		this.customerData = customerData;
		this.parent = parent;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "investment")
	@Fetch(FetchMode.JOIN)
	@OrderColumn(name = "itemNumber")
	private Set<Reward> rewards = new HashSet<Reward>();

	public Set<Reward> calculateRewards() {
		Set<Reward> result = new HashSet<Reward>();
		Money rewardFund = this.fund.multiplyBy(target.getInterestRate())
				.divideBy(this.target.getRemainingPeriods());
		for (int i = target.getPeriods().subtract(target.getRemainingPeriods())
				.intValue(); i < target.getPeriods().intValue(); i++) {
			Reward create = rewardFactory.create(new BigDecimal(i), rewardFund,
					this);
			result.add(create);
		}
		this.rewards = result;
		return result;
	}
}
