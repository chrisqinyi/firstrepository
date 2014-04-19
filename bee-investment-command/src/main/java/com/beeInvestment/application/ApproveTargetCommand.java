package com.beeInvestment.application;

import pl.com.bottega.cqrs.annotations.Command;

@Command()
public class ApproveTargetCommand {
	private final String targetId;

	public ApproveTargetCommand(String targetId) {
		super();
		this.targetId = targetId;
	}

	public String getTargetId() {
		return targetId;
	}
}
