package com.beeInvestment.account.domain;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

@DomainFactory
public class WithdrawFactory {
	@Inject
	private AutowireCapableBeanFactory spring;

	Withdraw create(Account account, Money amount) {
		AggregateId aggregateId=AggregateId.generate();
		Withdraw Withdraw = new Withdraw(aggregateId,account,amount);
		spring.autowireBean(Withdraw);
		return Withdraw;
	}
}
