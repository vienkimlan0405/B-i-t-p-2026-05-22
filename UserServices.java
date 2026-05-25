package crm_app12.services;

import java.util.List;

import crm_app12.UserEntity;
import crm_app12.entity.RoleEntity;
import crm_app12.repository.RoleRepository;
import crm_app12.repository.UserRepository;

public class UserServices {

	private UserRepository userRepository = new UserRepository();
	private RoleRepository roleRepository = new RoleRepository();
	
	public List<UserEntity> getAll() {
		List<UserEntity> listUserEntities = userRepository.findAll();
		
		return listUserEntities;
	}
	
	public List<RoleEntity> getAllRoles() {
		return roleRepository.findAll();
	}
}
