package com.managementprojects.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.managementprojects.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	@Query("SELECT obj FROM Project obj LEFT JOIN FETCH obj.task")
	List<Project> searchProject();
	
	@Query("SELECT obj FROM Project obj LEFT JOIN FETCH obj.task " +
            "WHERE UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Project> searchByName(String name, Pageable pageable);


}
