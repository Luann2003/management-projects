package com.managementprojects.factory;

import java.time.Instant;

import com.managementprojects.dto.TaskDTO;
import com.managementprojects.entities.Task;

public class TaskFactory {
		
	public static Task createTask() {
		Task task = new Task(1L, Instant.parse("2023-12-30T23:59:59Z"), Instant.parse("2023-12-30T23:59:59Z"), "Descrição da task 1", "Task 1",
				ProjectFactory.CreatedProject() , UserFactory.createUserEntity(), true);
		return task;
	}
	
	public static TaskDTO createTaskDTO() {
		Task task = new Task(null, Instant.parse("2023-12-29T23:59:59Z"), Instant.parse("2023-12-30T23:59:59Z"), "Descrição da task 1", "Task 1",
				ProjectFactory.CreatedProject(),  UserFactory.createUserEntity(), true);
		return new TaskDTO(task);
	}

}
