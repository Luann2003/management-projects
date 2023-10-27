package com.managementprojects.factory;

import java.util.ArrayList;
import java.util.List;

import com.managementprojects.projections.UserDetailsProjection;

public class UserDetailsFactory {
	
	public static List<UserDetailsProjection> createCustomClient(String username) {
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123456", 1L, "ROLE_MEMBER"));
		return list;
	}
	
	public static List<UserDetailsProjection> createCustomResponsible(String username) {
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123456", 2L, "ROLE_RESPONSIBLE"));
		return list;
	}
	
	public static List<UserDetailsProjection> createCustomResponsibleAndClient(String username) {
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123456", 1L, "ROLE_MEMBER"));
		list.add(new UserDetailsImpl(username, "123456", 2L, "ROLE_RESPONSIBLE"));
		return list;
	}
	
	public static List<UserDetailsProjection> createCustomResponsibleAndClientAndAdmin(String username) {
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123456", 1L, "ROLE_MEMBER"));
		list.add(new UserDetailsImpl(username, "123456", 2L, "ROLE_RESPONSIBLE"));
		list.add(new UserDetailsImpl(username, "123456", 3L, "ROLE_ADMINISTRATOR"));
		return list;
	}

}

class UserDetailsImpl implements UserDetailsProjection {
	
	private String username;
	private String password;
	private Long roleId;
	private String authority;
	
	public UserDetailsImpl() {
	}

	public UserDetailsImpl(String username, String password, Long roleId, String authority) {
		this.username = username;
		this.password = password;
		this.roleId = roleId;
		this.authority = authority;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Long getRoleId() {
		return roleId;
	}

	@Override
	public String getAuthority() {
		return authority;
	}	
}
