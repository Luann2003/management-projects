package com.managementprojects.entities.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.managementprojects.dto.TaskDTO;
import com.managementprojects.entities.Project;
import com.managementprojects.entities.Task;
import com.managementprojects.entities.User;
import com.managementprojects.entities.service.exceptions.DatabaseException;
import com.managementprojects.entities.service.exceptions.ResourceNotFoundException;
import com.managementprojects.repository.ProjectRepository;
import com.managementprojects.repository.TaskRepository;
import com.managementprojects.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository repository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public List<TaskDTO> findAll () {
		List<Task> list = repository.search02();
		System.out.println(list.get(0).getName());
		return list.stream().map(x -> new TaskDTO(x)).toList();
	}
	
	@Transactional(readOnly = true)
	public TaskDTO findById(Long id) {
		Task task = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new TaskDTO(task);
	}
	
	@Transactional()
	public TaskDTO insert(TaskDTO dto) {
		 Task entity = new Task();
		 
		 Project project = projectRepository.getReferenceById(dto.getProjectId());
		 project.setId(dto.getProjectId());
		 
		 User user = userRepository.getReferenceById(dto.getResponsibleId());
		 user.setId(dto.getResponsibleId());
		 
		 copyDtoToEntity(dto, entity);
		 entity.setProject(project);
		 entity.setResponsible(user);
	    
		 entity = repository.save(entity);

		 return new TaskDTO(entity);
		}
	
	@Transactional
	public TaskDTO update(Long id, TaskDTO dto) {
		try {
			Task entity = repository.getReferenceById(id);
			
			User user = userRepository.getReferenceById(dto.getResponsibleId());
			user.setId(dto.getResponsibleId());
			 
			copyDtoToEntity(dto, entity);
			entity.setResponsible(user);
			
			entity = repository.save(entity);
			return new TaskDTO(entity);
		}
		catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
    	if (!repository.existsById(id)) {
    		throw new ResourceNotFoundException("Recurso não encontrado");
    	}
    	try {
            repository.deleteById(id);    		
    	}
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
	
	private void copyDtoToEntity(TaskDTO dto, Task entity) {
	    entity.setName(dto.getName());
	    entity.setDescription(dto.getDescription());
	    entity.setStartDate(dto.getStartDate());
	    entity.setFinishDate(dto.getFinishDate()); 
	}
}
