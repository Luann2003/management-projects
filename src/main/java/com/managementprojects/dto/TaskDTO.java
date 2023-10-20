package com.managementprojects.dto;

import java.time.LocalDate;

import com.managementprojects.entities.Task;

public class TaskDTO {
	
	private Long id;
	private String description;
	private String name;
	private LocalDate finishDate;
	private LocalDate startDate;
	
	private String projectDTO;
	
	public TaskDTO() {
	}
	
	public TaskDTO(Task entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		startDate = entity.getStartDate();
		finishDate = entity.getFinishDate();
		projectDTO = entity.getProject().getName();
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

	public LocalDate getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(LocalDate finishDate) {
		this.finishDate = finishDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getProjectDTO() {
		return projectDTO;
	}

	public void setProjectDTO(String projectDTO) {
		this.projectDTO = projectDTO;
	}

	
	
}
