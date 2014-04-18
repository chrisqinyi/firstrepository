package com.beeInvestment.account.domain;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.CustomerData;

import com.beeInvestment.customer.domain.Customer;
@DomainFactory
public class AccountFactory {
	@Inject
	private AutowireCapableBeanFactory spring;
public Account create(CustomerData customerData){
	AggregateId aggregateId=AggregateId.generate();
	Account account = new Account(aggregateId,customerData);
	spring.autowireBean(account);
	return account;
}
}
