package com.managementprojects.entities.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.managementprojects.dto.TaskDTO;
import com.managementprojects.entities.Task;
import com.managementprojects.entities.service.exceptions.ResourceNotFoundException;
import com.managementprojects.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository repository;
	
	@Transactional(readOnly = true)
	public List<TaskDTO> findAll () {
		List<Task> list = repository.search02();
		System.out.println(list.get(0).getName());
		return list.stream().map(x -> new TaskDTO(x)).toList();
	
	}
	
	
	
	
//	@Transactional(readOnly = true)
//	public List<TaskDTO> findAll () {
//		List<TaskDTO> list = repository.search02();
//		//List<TaskDTO> taskDTOList = new ArrayList<>();
//		System.out.println(list.get(0).getName());
//		return list;
//		
//	    for (TaskDetailsProjection projection : list) {
//	    	
//	        TaskDTO taskDTO = new TaskDTO();
//	        taskDTO.setName(projection.getName());
//	        taskDTO.setDescription(projection.getDescription());
//	        taskDTO.setStartDate(projection.getStartDate());
//	        taskDTO.setFinishDate(projection.getFinishDate());
//
//	        taskDTOList.add(taskDTO);
//	    }
//
//	    return taskDTOList;
//	}
	
	@Transactional(readOnly = true)
	public TaskDTO findById(Long id) {
		Task task = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new TaskDTO(task);
	}
	
	@Transactional()
	public TaskDTO insert(TaskDTO dto) {
		Task entity = new Task();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new TaskDTO(entity);
	}

	private void copyDtoToEntity(TaskDTO dto, Task entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setStartDate(dto.getStartDate());
		entity.setFinishDate(dto.getFinishDate());
	}

}
