package com.beeInvestment.account.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pl.com.bottega.ddd.annotations.domain.AggregateRoot;
import pl.com.bottega.ddd.support.domain.BaseAggregateRoot;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.CustomerData;

import com.beeInvestment.customer.domain.Customer;

@Entity
@AggregateRoot
public class Account extends BaseAggregateRoot {
	private Account(){}
	Account(AggregateId aggregateId,CustomerData customerData){
		this.aggregateId=aggregateId;
		this.customerData=customerData;
	}
	public void deposit() {
	}

	public void withDraw() {
	}

	public void freeze() {
	}

	public void register(Customer customer) {
	}

	@Embedded
	private CustomerData customerData;
}
