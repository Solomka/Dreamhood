package com.google.devrel.training.conference.form;

public class IdeaForm {

	private String topic;
	private String description;

	public IdeaForm(String topic, String description) {
		this.topic = topic;
		this.description = description;
	}

	public String getTopic() {
		return topic;
	}

	public String getDescription() {
		return description;
	}

}
