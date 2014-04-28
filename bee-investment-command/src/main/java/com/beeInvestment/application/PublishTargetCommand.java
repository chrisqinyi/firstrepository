package com.beeInvestment.application;

import pl.com.bottega.cqrs.annotations.Command;

@Command()
public class PublishTargetCommand {
	private final String targetId;

	public PublishTargetCommand(String targetId) {
		super();
		this.targetId = targetId;
	}

	public String getTargetId() {
		return targetId;
	}
}
