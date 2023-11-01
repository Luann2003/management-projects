package com.managementprojects.factory;

import java.time.Instant;

import com.managementprojects.dto.ProjectDTO;
import com.managementprojects.entities.Project;

public class ProjectFactory {
	
	public static Project CreatedProject() {
		Project project = new Project(1L, "UPA 24", "DESCRIÇÃO UPA", Instant.parse("2023-12-30T23:59:59Z") , Instant.parse("2023-12-31T23:59:59Z"));
		return project;
	}
	public static ProjectDTO CreatedProjectDTO() {
		Project project = new Project(1L, "UPA 24", "DESCRIÇÃO UPA", Instant.parse("2023-12-30T23:59:59Z") , Instant.parse("2023-12-31T23:59:59Z"));
		ProjectDTO projectDTO = new ProjectDTO(project);
		return projectDTO;
	}

}
