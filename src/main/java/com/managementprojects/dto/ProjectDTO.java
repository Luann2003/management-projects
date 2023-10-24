package com.managementprojects.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.managementprojects.entities.Project;
import com.managementprojects.entities.Task;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProjectDTO {
	
	private Long id;
	@NotBlank(message = "Campo obrigatório")
	private String name;
	private String description;
	@NotNull(message = "A data não deve ser informada")
	private Instant startDate;
	@NotNull(message = "A data não deve ser informada")
	private Instant finishDate;
	
	private List<TaskDTO> tasks = new ArrayList<>();
	
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
		
		for (Task task : entity.getTask()) {
			tasks.add(new TaskDTO(task));    
		 }
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

	public List<TaskDTO> getTasks() {
		return tasks;
	}
}
