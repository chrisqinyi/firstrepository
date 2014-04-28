package com.beeInvestment.account.domain;

import pl.com.bottega.ddd.annotations.domain.DomainRepository;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

@DomainRepository
public interface AccountRepository{

	void save(Account account);

	Account load(AggregateId aggregateId);

}
