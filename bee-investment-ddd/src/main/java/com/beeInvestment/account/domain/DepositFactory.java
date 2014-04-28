package com.beeInvestment.account.domain;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

@DomainFactory
public class DepositFactory {
	@Inject
	private AutowireCapableBeanFactory spring;
	Deposit create(Account account,Money amount){
		AggregateId aggregateId=AggregateId.generate();
		Deposit deposit = new Deposit(aggregateId,account,amount);
		spring.autowireBean(deposit);
		return deposit;
	}
}
