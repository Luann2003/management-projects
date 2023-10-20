package com.managementprojects.dto;

import java.time.Instant;

import com.managementprojects.entities.Project;

public class ProjectDTO {
	
	private Long id;
	private String name;
	private String description;
	private Instant startDate;
	private Instant finishDate;
	
	public ProjectDTO() {
	}

	public ProjectDTO(Long id, String name, String description, Instant startDate, Instant finishDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.finishDate = finishDate;
	}
	
	public ProjectDTO(Project entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		startDate = entity.getStartDate();
		finishDate = entity.getFinishDate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Instant finishDate) {
		this.finishDate = finishDate;
	}
}
