package com.managementprojects.dto;

import java.time.Instant;

import com.managementprojects.entities.Task;

public class TaskDTO {
	
	private Long id;
	private String description;
	private String name;
	private Instant finishDate;
	private Instant startDate;
	private Long projectId;
	
	private String projectName;
	
	public TaskDTO() {
	}
	
	public TaskDTO(Long id, String description, String name, Instant finishDate, Instant startDate, Long projectId, String projectName) {
		this.id = id;
		this.description = description;
		this.name = name;
		this.finishDate = finishDate;
		this.startDate = startDate;
		this.projectId = projectId;
		this.projectName = projectName;
	}

	public TaskDTO(Task entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		startDate = entity.getStartDate();
		finishDate = entity.getFinishDate();
		projectName =  entity.getProject().getName();
		
		projectId = entity.getProject().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Instant finishDate) {
		this.finishDate = finishDate;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}
