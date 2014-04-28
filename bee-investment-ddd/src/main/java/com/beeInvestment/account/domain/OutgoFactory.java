package com.beeInvestment.account.domain;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import com.beeInvestment.transaction.domain.Transaction;

@DomainFactory
public class OutgoFactory {
	@Inject
	private AutowireCapableBeanFactory spring;
	Outgo create(Account account,Money amount){
		AggregateId aggregateId=AggregateId.generate();
		Outgo outgo = new Outgo(aggregateId,account,amount);
		spring.autowireBean(outgo);
		return outgo;
	}
	Outgo create(Account account,Transaction parent,Money amount){
		AggregateId aggregateId=AggregateId.generate();
		Outgo outgo = new Outgo(aggregateId,account,parent,amount);
		spring.autowireBean(outgo);
		return outgo;
	}
}
