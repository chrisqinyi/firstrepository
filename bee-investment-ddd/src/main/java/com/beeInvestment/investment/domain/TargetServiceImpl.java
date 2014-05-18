package com.beeInvestment.investment.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pl.com.bottega.ddd.annotations.application.ApplicationService;

@ApplicationService
public class TargetServiceImpl implements TargetService {
	@PersistenceContext
	protected EntityManager entityManager;
	@Inject
	private TargetRepository targetRepository;
	private int numberOfdays=1;

	public void setNumberOfdays(int numberOfdays) {
		this.numberOfdays = numberOfdays;
	}

	@Override
	public void targetTimeout() {
		TypedQuery<Target> createQuery = entityManager.createQuery(
				"SELECT t FROM Target t WHERE t.status="
						+ TargetStatus.OFFERRING.ordinal()
						+ " AND t.publishDate IS NOT NULL AND t.publishDate <= :date", Target.class);
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, numberOfdays);
		createQuery.setParameter("date", calendar.getTime());
		List<Target> resultList = createQuery.getResultList();
		for (Target target : resultList) {
			target.timeout();
			targetRepository.save(target);
		}
	}
}
