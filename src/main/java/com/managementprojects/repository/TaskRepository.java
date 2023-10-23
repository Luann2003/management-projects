package com.managementprojects.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.managementprojects.entities.Task;
import com.managementprojects.projections.TaskDetailsProjection;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	@Query(nativeQuery = true, value = "SELECT tb_project.id, tb_project.name AS nameProject, tb_task.id, tb_task.name AS taskName\r\n"
			+ "FROM tb_project\r\n"
			+ "INNER JOIN tb_task ON tb_project.id = tb_task.project_id;\r\n"
			+ "")
	List<TaskDetailsProjection> search01();

	
	@Query("SELECT new com.managementprojects.entities.Task(task.id, task.startDate, task.finishDate, task.description, task.name, task.project) " +
		       "FROM Task task")
		List<Task> search02();


}
