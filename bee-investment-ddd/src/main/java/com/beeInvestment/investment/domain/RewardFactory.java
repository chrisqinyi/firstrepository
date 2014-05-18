package com.beeInvestment.investment.domain;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.google.common.base.Function;

@DomainFactory
public class RewardFactory {
	@Inject
	private AutowireCapableBeanFactory spring;

	Reward create(BigDecimal periodIndex, Money rewardFund,
			Investment investment) {
		AggregateId aggregateId = AggregateId.generate();
		Reward reward = new Reward(aggregateId, periodIndex, rewardFund,
				investment);
		spring.autowireBean(reward);
		return reward;
	}

	Function<Integer, Reward> createReward(final Money rewardFund, final Investment investment) {
		return new Function<Integer, Reward>() {
			public Reward apply(Integer i) {
				return create(new BigDecimal(i), rewardFund, investment);
			}
		};
	}
}
