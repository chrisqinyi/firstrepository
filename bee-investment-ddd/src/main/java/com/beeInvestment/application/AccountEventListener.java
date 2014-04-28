package com.beeInvestment.application;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.com.bottega.ddd.annotations.event.EventListener;
import pl.com.bottega.ddd.annotations.event.EventListeners;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

import com.beeInvestment.account.domain.Account;
import com.beeInvestment.account.domain.AccountRepository;
import com.beeInvestment.transaction.domain.Transaction;
import com.beeInvestment.transaction.domain.TransactionRepository;
import com.beeInvestment.transaction.domain.TransactionStatus;

@EventListeners
public class AccountEventListener {
	@Inject
	private AccountRepository accountRepository;
	@Inject
	private TransactionRepository transactionRepository;
	@EventListener
	@Transactional(propagation = Propagation.SUPPORTS)
	public void handle(AccountSavedEvent event) {
		System.out.println("AccountEventListener");
		Account account = accountRepository.load(new AggregateId(event
				.getAccountId()));
		List<Transaction> transactions = account.getTransactions();
		for (Transaction transaction : transactions) {
			if (transaction.getStatus().equals(TransactionStatus.PROCESSING)) {
				transaction.process();
//				transaction.close();
//				transactionRepository.save(transaction);
				System.out.println("AccountEventListener process");
			}
		}
		accountRepository.save(account);
	}
}
