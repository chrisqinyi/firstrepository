package com.beeInvestment.investment.domain;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

@DomainFactory
public class RewardFactory {
	@Inject
	private AutowireCapableBeanFactory spring;
	public Reward create(BigDecimal periodIndex, Money rewardFund,Investment investment){
		AggregateId aggregateId=AggregateId.generate();
		Reward reward = new Reward(aggregateId,periodIndex, rewardFund, investment);
		spring.autowireBean(reward);
		return reward;
	}
}
