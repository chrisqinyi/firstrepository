package com.beeInvestment.customer.domain;

import pl.com.bottega.ddd.annotations.domain.DomainRepositoryImpl;
import pl.com.bottega.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
@DomainRepositoryImpl
public class JpaCustomerRepository extends GenericJpaRepository<Customer> implements CustomerRepository{

}
