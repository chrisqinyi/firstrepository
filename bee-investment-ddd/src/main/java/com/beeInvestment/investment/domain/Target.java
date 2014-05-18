package com.beeInvestment.investment.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.all;
import static pl.com.bottega.ecommerce.system.Functions.property;
import static pl.com.bottega.ecommerce.system.Predicates.invoke;
import static pl.com.bottega.ecommerce.system.Predicates.propertyNotEquals;
import static pl.com.bottega.ecommerce.system.Reduce.reduce;
import static pl.com.bottega.ecommerce.system.ReduceFunctions.sum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import pl.com.bottega.ddd.support.domain.BaseAggregateRoot;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.application.InvestEvent;
import com.beeInvestment.application.LoadTargetEvent;
import com.beeInvestment.transaction.domain.TransactionStatus;
import com.google.common.collect.FluentIterable;

@Entity
public class Target extends BaseAggregateRoot {

	public TargetStatus getStatus() {
		return status;
	}
	private Date publishDate=null;
	@Access(value = AccessType.PROPERTY)
	private TargetStatus status = TargetStatus.NEW;

	@Inject
	@Transient
	private InvestmentFactory investmentFactory;

	private Target() {
	}

	public Set<Investment> getInvestments() {
		return investments;
	}

	Target(AggregateId aggregateId, Money totalFund, BigDecimal interestRate,
			BigDecimal periods) {
		this.aggregateId = aggregateId;
		this.totalFund = totalFund;
		this.periods = periods;
		this.interestRate = interestRate;
	}
	@Access(value=AccessType.PROPERTY)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "target")
	@Fetch(FetchMode.JOIN)
	@OrderBy(value = "generateTimeStamp")
	private Set<Investment> investments = new HashSet<Investment>();

	private void setInvestments(Set<Investment> investments) {
		this.investments = investments;
		this.outstandingInvestments=from(investments).filter(propertyNotEquals("status",TransactionStatus.SUCCESS));
	}
	@Transient
	private FluentIterable<Investment> outstandingInvestments;
	public FluentIterable<Investment> getOutstandingInvestments() {
		return outstandingInvestments;
	}

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
		return totalFund.subtract(reduce(
				from(investments).transform(property("payload", Money.class)),
				sum()));
	};

	public void load() {
		this.eventPublisher.publish(new LoadTargetEvent(this.aggregateId
				.getId()));
	}

	public void publish() {
		checkArgument(getStatus().equals(TargetStatus.NEW),
				"Targets can only be published in new status");
		status = TargetStatus.OFFERRING;
		publishDate=new Date();
	}

	public void approve() {
		checkArgument(getStatus().equals(TargetStatus.WAITING_APPROVAL),
				"Targets can only be approved in waiting approval status");
		all(investments,invoke("approve"));
		status = TargetStatus.REWARDING;
		
	}

	public void cancel(){
		checkArgument(status.equals(TargetStatus.OFFERRING),
				"Targets can only get cancelled in offerring status");
		checkArgument(investments.isEmpty(),
				"Targets can only get cancelled when there is no investment");
		this.status=TargetStatus.NEW;
	}
	
	public void timeout(){
		checkArgument(status.equals(TargetStatus.OFFERRING),
				"Targets can only get time out in offerring status");
		all(investments,invoke("cancel"));
		investments.clear();
		this.status=TargetStatus.NEW;
	}
	
	public void transferInvestment(Integer investmentIndex, Account account) {
		new ArrayList<Investment>(investments).get(investmentIndex).transferTo(
				account);
	}

	public void reward(BigDecimal periodIndex) {
		System.out.println("status:status:status:status:status:status:status:status:"+status);
		//if(1==1)throw new RuntimeException();
		checkArgument(status.equals(TargetStatus.REWARDING),
				"Rewards can only be processed with target in rewarding status");
		all(investments, invoke("reward", periodIndex));
		if(outstandingInvestments.isEmpty()){
			this.status=TargetStatus.SUCCESS;
		}
	}

	public Investment invest(Account account, Money fund) {
		checkArgument(!getStatus().equals(TargetStatus.WAITING_APPROVAL),
				"target full");
		checkArgument(status.equals(TargetStatus.OFFERRING),
				"Targets can only be invested in offerring status");
		Investment investment = investmentFactory.create(this, account, fund);
		checkArgument(!fund.lessThan(new Money(100)),
				"investment fund cannot be less than 100.00");
		Money remainingFund = getRemainingFund();
		checkArgument(!remainingFund.lessThan(fund),
				"no enough remaining investment fund");
		Money subtract = remainingFund.subtract(fund);
		checkArgument(
				!(subtract.greaterThan(Money.ZERO) && subtract
						.lessThan(new Money(100))),
				"remaining investment fund cannot be less than 100.00");
		investments.add(investment);
		eventPublisher.publish(new InvestEvent());
		if (status.equals(TargetStatus.OFFERRING)
				&& getRemainingFund().equals(Money.ZERO)) {
			status = TargetStatus.WAITING_APPROVAL;
		}
		return investment;
	}

	private void setStatus(TargetStatus status) {
		this.status = status;
	}

}
