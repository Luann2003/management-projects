package com.managementprojects.entities;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_task")
public class Task {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDate startDate;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDate finishDate;
	
	private String description;
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	public Task() {
	}

	public Task(Long id, LocalDate startDate, LocalDate finishDate, String description, String name) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.description = description;
		this.name = name;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public LocalDate getStartDate() {
		return startDate;
	}


	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	public LocalDate getFinishDate() {
		return finishDate;
	}


	public void setFinishDate(LocalDate finishDate) {
		this.finishDate = finishDate;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(id, other.id);
	}
}
