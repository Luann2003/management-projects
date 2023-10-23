package com.managementprojects.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.managementprojects.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	
	@Query("SELECT new com.managementprojects.entities.Task(task.id, task.startDate, task.finishDate, task.description, task.name, task.project) " +
		       "FROM Task task")
		List<Task> search02();


}
