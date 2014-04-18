package com.beeInvestment.customer.domain;

import javax.persistence.Entity;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

@Entity
public class SecurityQuestionCredential extends Credential {
	private SecurityQuestionCredential(){}
	SecurityQuestionCredential(Customer customer){
		this.customer=customer;
	}
	
}
