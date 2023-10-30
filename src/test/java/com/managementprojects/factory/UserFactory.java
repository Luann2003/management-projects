package com.managementprojects.factory;

import com.managementprojects.dto.UserDTO;
import com.managementprojects.entities.User;

public class UserFactory {
	
	public static User createUserEntity() {
		User user = new User(2L, "Maria", "maria@gmail.com", "$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
		return user;
	}
	
	public static UserDTO createUserDTOEntity() {
		User user = new User(2L, "Maria", "maria@gmail.com", "$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
		return new UserDTO(user);
	}
}
