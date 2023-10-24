package com.managementprojects.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.managementprojects.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	@Query("SELECT obj FROM Project obj JOIN FETCH obj.task")
	List<Project> searchProject();

}
