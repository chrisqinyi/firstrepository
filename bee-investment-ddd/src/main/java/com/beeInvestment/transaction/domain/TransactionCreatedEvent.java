package com.beeInvestment.transaction.domain;

import java.io.Serializable;

public class TransactionCreatedEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 553167008042759990L;
	private final String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public TransactionCreatedEvent(String customerId) {
		super();
		this.customerId = customerId;
	}
}
