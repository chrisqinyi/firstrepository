package com.beeInvestment.transaction.domain;

import javax.inject.Inject;

import pl.com.bottega.ddd.annotations.event.EventListener;
import pl.com.bottega.ddd.annotations.event.EventListeners;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.account.domain.AccountRepository;

@EventListeners
public class TransactionEventListener {
	@Inject
	private AccountRepository accountRepository;

	@EventListener
	public void handle(TransactionConfirmedEvent event){
		Account account = accountRepository.load(new AggregateId(event.getAccountId()));
		Transaction transaction=(Transaction) account.getTransactions().toArray()[event.getTransactionIndex().intValue()];
		transaction.process();
		accountRepository.save(account);
		
	}
}
