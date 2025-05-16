package com.sri.user.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTodoRequestModel {
	
	@NotNull(message="name cannot be null")
	private String name;
	
	@NotNull(message="description cannot be null")
	@Size(min=6,message="Enter more than 2 characters")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
