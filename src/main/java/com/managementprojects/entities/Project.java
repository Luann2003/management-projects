package com.managementprojects.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_project")
public class Project {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant startDate;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant finishDate;
	
	@OneToMany(mappedBy = "project")
	private List<Task> task = new ArrayList<>();
	
	public Project() {
	}

	public Project(Long id, String name, String description, Instant startDate, Instant finishDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.finishDate = finishDate;
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
	
	public List<Task> getTask() {
		return task;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		return Objects.equals(id, other.id);
	}
}
