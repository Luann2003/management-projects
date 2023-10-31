package com.managementprojects.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.managementprojects.dto.ProjectDTO;
import com.managementprojects.entities.Project;
import com.managementprojects.repository.ProjectRepository;
import com.managementprojects.service.exceptions.DatabaseException;
import com.managementprojects.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ProjectDTO> findAll (String name, Pageable pageable) {
		Page<Project> list = repository.searchByName(name, pageable);
		return list.map(x -> new ProjectDTO(x));
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
