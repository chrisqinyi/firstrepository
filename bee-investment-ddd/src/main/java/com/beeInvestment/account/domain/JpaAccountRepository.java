package com.beeInvestment.account.domain;

import pl.com.bottega.ddd.annotations.domain.DomainRepositoryImpl;
import pl.com.bottega.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
@DomainRepositoryImpl
public class JpaAccountRepository extends GenericJpaRepository<Account> implements AccountRepository{

}
