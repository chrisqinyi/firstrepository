package com.beeInvestment.investment.domain;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sharedkernel.Money;

@DomainFactory
public class TargetFactory {
	@Inject
	private AutowireCapableBeanFactory spring;
	public Target create(Money totalFund,BigDecimal interestRate,BigDecimal periods){
		AggregateId aggregateId=AggregateId.generate();
		Target target = new Target(aggregateId, totalFund,interestRate, periods);
		spring.autowireBean(target);
		return target;
	}
}
