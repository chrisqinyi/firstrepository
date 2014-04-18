package com.beeInvestment.investment.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import pl.com.bottega.ddd.support.domain.BaseEntity;
import pl.com.bottega.ecommerce.sharedkernel.Money;

@Entity
public class Reward extends BaseEntity {
	private Reward() {
	}

	Reward(BigDecimal periodIndex, Money reward, Investment investment) {
		this.target = investment.getTarget();
		this.investment = investment;
		this.periodIndex = periodIndex;
		this.reward = reward;
	}

	public BigDecimal getPeriodIndex() {
		return periodIndex;
	}

	public Money getReward() {
		return reward;
	}

	private BigDecimal periodIndex;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "target_id")
	private Target target;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "investment_id")
	private Investment investment;
	@Embedded
	private Money reward;
	private boolean fulfilled=false;
	public void fulfill() {
		fulfilled=true;
	}
}
