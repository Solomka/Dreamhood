package com.google.devrel.training.conference.form;

public class IdeaForm {

	private String description;
	
	private IdeaForm() {}
	
	public IdeaForm(String description) {

		this.description = description;

	}

	public String getDescription() {
		return description;
	}

}
