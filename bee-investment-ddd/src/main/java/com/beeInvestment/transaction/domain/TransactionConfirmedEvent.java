package com.beeInvestment.transaction.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransactionConfirmedEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -123148321119388627L;
	private final String accountId;
	private final BigDecimal transactionIndex;

	public BigDecimal getTransactionIndex() {
		return transactionIndex;
	}

	public String getAccountId() {
		return accountId;
	}

	public TransactionConfirmedEvent(String accountId,
			BigDecimal transactionIndex) {
		super();
		this.accountId = accountId;
		this.transactionIndex = transactionIndex;
	}

}
