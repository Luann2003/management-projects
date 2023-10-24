package com.managementprojects.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managementprojects.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByAuthority(String authority);
}
