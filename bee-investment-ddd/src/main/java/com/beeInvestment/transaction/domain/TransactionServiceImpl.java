package com.beeInvestment.transaction.domain;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.com.bottega.ddd.annotations.application.ApplicationService;

import com.beeInvestment.account.domain.AccountRepository;

@ApplicationService
public class TransactionServiceImpl implements TransactionService {
	@PersistenceContext
	protected EntityManager entityManager;
	@Inject
	private TransactionRepository transactionRepository;
	@Inject
	private AccountRepository accountRepository;
	
	public void closeTransaction() {
		TypedQuery<Transaction> createQuery = entityManager.createQuery("SELECT t FROM Transaction t WHERE t.status="+TransactionStatus.SUCCESS.ordinal(),
				Transaction.class);
		List<Transaction> resultList = createQuery.getResultList();
		for (Transaction transaction : resultList) {
			transaction.close();
			accountRepository.save(transaction.getAccount());
			transactionRepository.delete(transaction.getAggregateId());
		}
	}

	public void processTransaction() {
		TypedQuery<Transaction> createQuery = entityManager.createQuery("SELECT t FROM Transaction t WHERE t.status="+TransactionStatus.PROCESSING.ordinal(),
				Transaction.class);
		List<Transaction> resultList = createQuery.getResultList();
		for (Transaction transaction : resultList) {
			transaction.process();
			transactionRepository.save(transaction);
		}
	}
}
