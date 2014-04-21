package com.beeInvestment.investment.domain;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.CustomerData;
import pl.com.bottega.ecommerce.sharedkernel.Money;

@DomainFactory
public class InvestmentFactory {
	@Inject
	private AutowireCapableBeanFactory spring;
	
	public Investment create(Target target,CustomerData customerData,Money fund,Investment parent){
		Investment investment = new Investment(target,customerData,fund,parent);
		spring.autowireBean(investment);
		return investment;
	}
}
