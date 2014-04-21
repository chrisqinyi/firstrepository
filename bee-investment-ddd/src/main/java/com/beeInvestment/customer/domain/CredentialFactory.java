package com.beeInvestment.customer.domain;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

import com.beeInvestment.application.RegisterCommand;
@DomainFactory
public class CredentialFactory {
	@Inject
	private AutowireCapableBeanFactory spring;
	public UsernamePasswordCredential createLogin(Customer customer) {
		UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredential(customer);
		spring.autowireBean(usernamePasswordCredential);
		return usernamePasswordCredential;
	}

	public SecurityQuestionCredential createSecurityQuestion(Customer customer) {
		SecurityQuestionCredential securityQuestionCredential = new SecurityQuestionCredential(customer);
		spring.autowireBean(securityQuestionCredential);
		return securityQuestionCredential;
	}
}
