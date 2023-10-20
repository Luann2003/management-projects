package com.managementprojects.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managementprojects.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
