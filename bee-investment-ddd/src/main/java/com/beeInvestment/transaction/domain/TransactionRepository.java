package com.beeInvestment.transaction.domain;

import pl.com.bottega.ddd.annotations.domain.DomainRepository;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
@DomainRepository
public interface TransactionRepository {
	Transaction load(AggregateId aggregateId);

	void save(Transaction transaction);
}
