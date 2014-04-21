package com.beeInvestment.customer.domain;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
@DomainFactory
public class CustomerFactory {
	@Inject
	private AutowireCapableBeanFactory spring;
	public Customer create() {
		AggregateId aggregateId = AggregateId.generate();
		Customer customer = new Customer(aggregateId);
		System.out.println("aggregateId:"+aggregateId);
		spring.autowireBean(customer);
		return customer;
	}
}
