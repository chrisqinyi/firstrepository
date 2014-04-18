package com.beeInvestment.customer.domain;

import pl.com.bottega.ddd.annotations.domain.DomainRepository;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
@DomainRepository
public interface CustomerRepository {
	Customer load(AggregateId aggregateId);

	void save(Customer customer);
}
