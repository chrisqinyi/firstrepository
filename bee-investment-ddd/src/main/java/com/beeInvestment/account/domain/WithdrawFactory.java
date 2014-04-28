package com.beeInvestment.account.domain;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;

@DomainFactory
public class WithdrawFactory {
	@Inject
	private AutowireCapableBeanFactory spring;
}
