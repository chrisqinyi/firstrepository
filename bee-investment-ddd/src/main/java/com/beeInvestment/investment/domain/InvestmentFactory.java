package com.beeInvestment.investment.domain;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.account.domain.Account;

@DomainFactory
public class InvestmentFactory {
	@Inject
	private AutowireCapableBeanFactory spring;
	
	public Investment create(Target target,Account account,Money fund){
		AggregateId aggregateId=AggregateId.generate();
		Investment investment = new Investment(aggregateId,target,account,fund);
		spring.autowireBean(investment);
		return investment;
	}
}
