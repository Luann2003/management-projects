package com.managementprojects.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managementprojects.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
