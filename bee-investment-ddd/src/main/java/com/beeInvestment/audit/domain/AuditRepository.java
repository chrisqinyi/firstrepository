package com.beeInvestment.audit.domain;

import pl.com.bottega.ddd.annotations.domain.DomainRepository;

@DomainRepository
public interface AuditRepository {

	void save(Audit create);

}
