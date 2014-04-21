package com.beeInvestment.audit.domain;

import pl.com.bottega.ddd.annotations.domain.DomainRepositoryImpl;
import pl.com.bottega.ddd.support.infrastructure.repository.jpa.GenericJpaRepository;
@DomainRepositoryImpl
public class JpaAuditRepository extends GenericJpaRepository<Audit> implements AuditRepository{

}
