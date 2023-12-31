package com.managementprojects.entities;

import java.time.Instant;
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
	private Instant startDate;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant finishDate;
	private String description;
	private String name;
	
	private boolean completed;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User responsible;
	
	public Task() {
	}

	public Task(Long id, Instant startDate, Instant finishDate, String description, String name, Project project, User responsible,  boolean completed) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.description = description;
		this.name = name;
		this.project = project;
		this.responsible = responsible;
		this.completed = completed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public User getResponsible() {
		return responsible;
	}

	public void setResponsible(User responsible) {
		this.responsible = responsible;
	}
	
	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
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
