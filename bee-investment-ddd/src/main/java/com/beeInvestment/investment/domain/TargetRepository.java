package com.beeInvestment.investment.domain;

import pl.com.bottega.ddd.annotations.domain.DomainRepository;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;

@DomainRepository
public interface TargetRepository {

	void save(Target target);

	Target load(AggregateId aggregateId);

}
