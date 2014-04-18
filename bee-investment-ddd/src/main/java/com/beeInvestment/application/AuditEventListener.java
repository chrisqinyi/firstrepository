package com.beeInvestment.application;

import javax.inject.Inject;

import pl.com.bottega.ddd.annotations.event.EventListener;
import pl.com.bottega.ddd.annotations.event.EventListeners;

import com.beeInvestment.audit.domain.Audit;
import com.beeInvestment.audit.domain.AuditFactory;
import com.beeInvestment.audit.domain.AuditRepository;
@EventListeners
public class AuditEventListener {
	@Inject
	private AuditRepository auditRepository;
	@Inject
	private AuditFactory auditFactory;
	@EventListener
	public void handle(RegisterEvent event){
		Audit create = auditFactory.create(event);
		auditRepository.save(create);
		
	}
}
