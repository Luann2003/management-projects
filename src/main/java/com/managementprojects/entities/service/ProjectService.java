package com.managementprojects.entities.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.managementprojects.dto.ProjectDTO;
import com.managementprojects.entities.Project;
import com.managementprojects.entities.service.exceptions.DatabaseException;
import com.managementprojects.entities.service.exceptions.ResourceNotFoundException;
import com.managementprojects.repository.ProjectRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository repository;
	
	@Transactional(readOnly = true)
	public List<ProjectDTO> findAll () {
		List<Project> list = repository.findAll();
		return list.stream().map(x -> new ProjectDTO(x)).toList();
	}
	
	@Transactional(readOnly = true)
	public ProjectDTO findById(Long id) {
		Project project = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new ProjectDTO(project);
	}
	
	@Transactional()
	public ProjectDTO insert(ProjectDTO dto) {
		Project entity = new Project();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProjectDTO(entity);
	}
	
	@Transactional()
	public ProjectDTO update(Long id, ProjectDTO dto) {
		try {
			Project entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProjectDTO(entity);
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
	private void copyDtoToEntity(ProjectDTO dto, Project entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setStartDate(dto.getStartDate());
		entity.setFinishDate(dto.getFinishDate());
	}
}
