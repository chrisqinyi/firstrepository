package com.beeInvestment.customer.domain;

import javax.persistence.Entity;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
@Entity 
public class UsernamePasswordCredential extends Credential{
	private UsernamePasswordCredential(){}
	UsernamePasswordCredential(Customer customer){
		this.customer=customer;
	}
}
