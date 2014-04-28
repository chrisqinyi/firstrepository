package com.beeInvestment.investment.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

import pl.com.bottega.ddd.support.domain.BaseAggregateRoot;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.application.InvestEvent;
import com.beeInvestment.application.LoadTargetEvent;

@Entity
public class Target extends BaseAggregateRoot {
	@Inject
	@Transient
	private InvestmentFactory investmentFactory;

	private Target() {
	}

	Target(AggregateId aggregateId, Money totalFund, BigDecimal interestRate,
			BigDecimal periods) {
		this.aggregateId = aggregateId;
		this.totalFund = totalFund;
		this.periods = periods;
		// this.remainingPeriods=periods;
		this.interestRate = interestRate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "target")
	@Fetch(FetchMode.JOIN)
	@OrderColumn(name = "itemNumber")
	private Set<Investment> investments = new HashSet<Investment>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "target")
	@Fetch(FetchMode.JOIN)
	@OrderColumn(name = "itemNumber")
	private Set<Reward> rewards = new HashSet<Reward>();
	// private BigDecimal remainingPeriods;
	// public BigDecimal getRemainingPeriods() {
	// return remainingPeriods;
	// }

	private BigDecimal periods;

	BigDecimal getPeriods() {
		return periods;
	}

	@Embedded
	private Money totalFund;

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	private BigDecimal interestRate;

	public Money getRemainingFund() {
		Money investedFund = new Money(0);
		for (Investment investment : investments) {
			investedFund = investedFund.add(investment.getFund());
		}
		return totalFund.subtract(investedFund);
	};

	public void load() {
		this.eventPublisher.publish(new LoadTargetEvent(this.aggregateId
				.getId()));
	}

	public void publish() {

	}

	public void approve() {
		for (Investment investment : investments) {
			Set<Reward> calculateRewards = investment.calculateRewards();
			rewards.addAll(calculateRewards);
		}
	}

	public void reward(BigDecimal periodIndex) {
		List tmp = new ArrayList<Reward>();
		for (Reward reward : rewards) {
			if (reward.getPeriodIndex().intValue() == periodIndex.intValue()) {
				reward.fulfill();
				// rewards.remove(reward);
				tmp.add(reward);
			}
		}
		rewards.removeAll(tmp);
		// remainingPeriods=remainingPeriods.subtract(new BigDecimal(1));
	}

	public void transfer(Account account, BigDecimal InvestmentIndex) {
		Investment investment = new ArrayList<Investment>(investments)
				.get(InvestmentIndex.intValue());
		investment.transferTo(account);
		// Investment create = investmentFactory.create(this, customerData,
		// investment.getFund(), investment);
		// Set<Reward> calculateRewards = investment.calculateRewards();
		// investments.add(create);
		// investments.remove(investment);
		// rewards.removeAll(investment.getRewards());
		// rewards.addAll(calculateRewards);
		// this.investments.toArray().
		// List<Object> asList = Arrays.asList(this.investments);

	}

	public void invest(Account account, Money fund) {
		Investment investment = investmentFactory.create(this, account, fund);
		if (fund.lessThan(new Money(new BigDecimal(100)))) {
			throw new RuntimeException(
					"investment fund cannot be less than 100.00");
		}
		Money remainingFund = getRemainingFund();
		System.out.println("remainingFund:" + remainingFund);
		if (remainingFund.equals(new Money(new BigDecimal(0)))) {
			throw new RuntimeException("target full");
		}
		if (remainingFund.lessThan(fund)) {
			throw new RuntimeException("no enough remaining investment fund");
		}
		Money subtract = remainingFund.subtract(fund);
		if (subtract.greaterThan(new Money(new BigDecimal(0)))
				&& subtract.lessThan(new Money(new BigDecimal(100)))) {
			throw new RuntimeException(
					"remaining investment fund cannot be less than 100.00");
		}
		investments.add(investment);
		eventPublisher.publish(new InvestEvent());

	}
}
