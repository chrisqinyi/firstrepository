package com.beeInvestment.transaction.domain;

public interface TransactionService {
	void closeTransaction();
	void processTransaction();
}
