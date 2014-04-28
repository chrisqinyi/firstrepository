package com.beeInvestment.investment.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
import com.beeInvestment.transaction.domain.Transaction;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

@Entity
public class Target extends BaseAggregateRoot {

	public TargetStatus getStatus() {
		if (status.equals(TargetStatus.OFFERRING)
				&& getRemainingFund().equals(new Money(new BigDecimal(0)))) {
			status = TargetStatus.WAITING_APPROVAL;
		}
		return status;
	}

	@Access(value = AccessType.PROPERTY)
	private TargetStatus status = TargetStatus.NEW;

	@Inject
	@Transient
	private InvestmentFactory investmentFactory;

	private Target() {
	}

	public Set<Investment> getInvestments() {
		// ArrayList<Investment> list = new ArrayList<Investment>(investments);
		// List unmodifiableList = Collections.unmodifiableList(list);
		// Collections.sort(list);
		return investments;
	}

	Target(AggregateId aggregateId, Money totalFund, BigDecimal interestRate,
			BigDecimal periods) {
		this.aggregateId = aggregateId;
		this.totalFund = totalFund;
		this.periods = periods;
		// this.remainingPeriods=periods;
		this.interestRate = interestRate;
	}

	private void setInvestments(Set<Investment> investments) {
		final Target target = this;
		this.investments = investments;
		// System.out.println("getRemainingFund():"+getRemainingFund());
		// if(status.equals(TargetStatus.OFFERRING)&&getRemainingFund().equals(new
		// Money(new BigDecimal(0)))){
		// status=TargetStatus.WAITING_APPROVAL;
		// }
	}

	@Access(value = AccessType.PROPERTY)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "target")
	@Fetch(FetchMode.JOIN)
	@OrderBy(value = "generateTimeStamp")
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
		if (!status.equals(TargetStatus.NEW)) {
			throw new RuntimeException(
					"Targets can only be published in new status");
		}
		status = TargetStatus.OFFERRING;
	}

	public void approve() {
		if (!getStatus().equals(TargetStatus.WAITING_APPROVAL)) {
			throw new RuntimeException(
					"Targets can only be approved in waiting approval status");
		}
		//Collection<Transaction> rewards=new ArrayList<Transaction>();
		for (Investment investment : investments) {
			//Collection<Reward> calculateRewards = investment.calculateRewards();
			investment.confirm();
			investment.process();
			//rewards.addAll(calculateRewards);
		}
		status = TargetStatus.REWARDING;
	}

	public void transferInvestment(Integer investmentIndex, Account account) {
		new ArrayList<Investment>(investments).get(investmentIndex).transferTo(
				account);
	}

	public void reward(BigDecimal periodIndex) {
		if (!status.equals(TargetStatus.REWARDING)) {
			throw new RuntimeException(
					"Rewards can only be processed with target in rewarding status");
		}
		for (Investment investment : investments) {
			investment.reward(periodIndex);
		}
	}

	public Investment invest(Account account, Money fund) {
		if (status.equals(TargetStatus.WAITING_APPROVAL)) {
			throw new RuntimeException("target full");
		}
		if (!status.equals(TargetStatus.OFFERRING)) {
			throw new RuntimeException(
					"Targets can only be invested in offerring status");
		}
		Investment investment = investmentFactory.create(this, account, fund);
		if (fund.lessThan(new Money(new BigDecimal(100)))) {
			throw new RuntimeException(
					"investment fund cannot be less than 100.00");
		}
		Money remainingFund = getRemainingFund();
		if (remainingFund.lessThan(fund)) {
			throw new RuntimeException("no enough remaining investment fund");
		}
		Money subtract = remainingFund.subtract(fund);
		System.out.println("subtract:" + subtract);
		if (subtract.greaterThan(new Money(new BigDecimal(0)))
				&& subtract.lessThan(new Money(new BigDecimal(100)))) {
			throw new RuntimeException(
					"remaining investment fund cannot be less than 100.00");
		}
		investments.add(investment);
		eventPublisher.publish(new InvestEvent());

		return investment;
	}

	private void setStatus(TargetStatus status) {
		this.status = status;
	}

	public Set<Reward> getRewards() {
		return rewards;
	}
}
