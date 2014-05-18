package com.beeInvestment.application;

import pl.com.bottega.cqrs.annotations.Command;

@Command()
public class CancelTargetCommand {
	private final String targetId;

	public CancelTargetCommand(String targetId) {
		super();
		this.targetId = targetId;
	}

	public String getTargetId() {
		return targetId;
	}
}
