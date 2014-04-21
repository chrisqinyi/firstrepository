package com.beeInvestment.application;

import java.io.Serializable;

public class LoadTargetEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -560041006265814916L;
	private String targetId;

	public String getTargetId() {
		return targetId;
	}

	public LoadTargetEvent(String targetId) {
		super();
		this.targetId = targetId;
	}
}
