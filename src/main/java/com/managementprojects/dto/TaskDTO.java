package com.managementprojects.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.managementprojects.entities.Task;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskDTO {
	
	private Long id;
	private String description;
	
	@NotBlank(message = "Campo obrigatório")
	private String name;
	@NotNull(message = "A data não deve ser informada")
	private Instant startDate;
	@NotNull(message = "A data não deve ser informada")
	private Instant finishDate;
	
	@NotNull(message = "Você deve informar o id do projeto")
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
	
	@JsonIgnore
	@AssertTrue(message = "A data de término deve ser posterior à data de início")
	public boolean isFinishDateAfterStartDate() {
		if (startDate != null && finishDate != null) {
			return finishDate.isAfter(startDate);
		}
		return true; // Se alguma data for nula, a validação passa
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
