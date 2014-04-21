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
	public void handle(RegisterEvent event){
		testHelper.setCustomerId(event.getCustomerId());
		
	}
	@EventListener
	public void handle(LoadTargetEvent event){
		testHelper.setTargetId(event.getTargetId());
		
	}
}
