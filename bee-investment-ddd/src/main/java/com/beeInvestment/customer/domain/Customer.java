package com.beeInvestment.customer.domain;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import pl.com.bottega.ddd.support.domain.BaseAggregateRoot;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.CustomerData;

import com.beeInvestment.application.RegisterCommand;
import com.beeInvestment.application.RegisterEvent;
import com.beeInvestment.application.UpdateLoginCredentialCommand;

@Entity
public class Customer extends BaseAggregateRoot {
	@Inject
	@Transient
	private CredentialFactory credentialFactory;
	private Customer() {
	}

	Customer(AggregateId aggregateId) {
		this.aggregateId = aggregateId;
		
	}

	public CustomerData generateSnapshot() {
		return new CustomerData(aggregateId, name);
	}

	private String name;
	@OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
	private UsernamePasswordCredential loginCredential;
	@OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
	private SecurityQuestionCredential recoveryCredential;

	public void register() {
		this.loginCredential = credentialFactory.createLogin(this);
		this.recoveryCredential = credentialFactory.createSecurityQuestion(this);
		this.eventPublisher.publish(new RegisterEvent(this.aggregateId.getId()));
	}

	public void updateLoginCredential() {
		this.loginCredential.removeCustomer();
		this.loginCredential = credentialFactory.createLogin(this);
		
	}
}
