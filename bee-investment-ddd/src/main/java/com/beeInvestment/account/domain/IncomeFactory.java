package com.beeInvestment.account.domain;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

@DomainFactory
public class IncomeFactory {
	@Inject
	private AutowireCapableBeanFactory spring;
	Income create(Account account,Money amount){
		AggregateId aggregateId=AggregateId.generate();
		Income income = new Income(aggregateId,account,amount);
		spring.autowireBean(income);
		return income;
	}
}
