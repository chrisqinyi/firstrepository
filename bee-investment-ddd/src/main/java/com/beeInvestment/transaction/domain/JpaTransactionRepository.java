package com.beeInvestment.transaction.domain;

import pl.com.bottega.ddd.annotations.domain.DomainRepositoryImpl;
import pl.com.bottega.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
@DomainRepositoryImpl
public class JpaTransactionRepository extends GenericJpaRepository<Transaction> implements TransactionRepository{

}
