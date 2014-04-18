package com.beeInvestment.investment.domain;

import pl.com.bottega.ddd.annotations.domain.DomainRepositoryImpl;
import pl.com.bottega.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
@DomainRepositoryImpl
public class JpaTargetRepository extends GenericJpaRepository<Target> implements TargetRepository{

}
