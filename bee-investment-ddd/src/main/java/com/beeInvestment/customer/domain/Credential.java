package com.beeInvestment.customer.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import pl.com.bottega.ddd.support.domain.BaseEntity;
@Entity 
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  
public abstract class Credential extends BaseEntity{
	
	@OneToOne
	@Fetch(FetchMode.JOIN)
	//@OrderColumn(name = "itemNumber")
	@JoinColumn(name = "customer_id")
	protected Customer customer;
	public void removeCustomer(){
		this.customer=null;
	}
	
}
