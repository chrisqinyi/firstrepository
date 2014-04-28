package com.beeInvestment.application;

import javax.inject.Inject;

import pl.com.bottega.ddd.annotations.event.EventListener;
import pl.com.bottega.ddd.annotations.event.EventListeners;
import pl.com.bottega.ecommerce.sales.acceptancetests.TestHelper;

@EventListeners
public class TestEventListener {
	@Inject
	private TestHelper testHelper;

	@EventListener
	public void handle(RegisterEvent event) {
		String customerId = event.getCustomerId();
		if (null == testHelper.getCustomerAId())
			testHelper.setCustomerAId(customerId);
		else
			testHelper.setCustomerBId(customerId);

	}

	@EventListener
	public void handle(LoadTargetEvent event) {
		String targetId = event.getTargetId();
		if (null == testHelper.getTarget1Id())
			testHelper.setTarget1Id(targetId);
		else if (null == testHelper.getTarget2Id())
			testHelper.setTarget2Id(targetId);
		else
			testHelper.setTarget3Id(targetId);

	}
}
