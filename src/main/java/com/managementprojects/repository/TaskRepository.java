package com.managementprojects.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.managementprojects.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	@Query("SELECT new com.managementprojects.entities.Task(task.id, task.startDate, task.finishDate, task.description, task.name, task.project, task.responsible, task.completed) " +
		       "FROM Task task "
		       + "WHERE UPPER(task.name) LIKE UPPER(CONCAT('%', :name, '%'))")
		Page<Task> search02(String name, Pageable pageable);
}
