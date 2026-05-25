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
	
	public boolean insertUser(String fullname, String email, String password, String phone, String idRole) {
	    
	    // Tách chuỗi fullname thành firstName và lastName (Tên và Họ)
	    String firstName = "";
	    String lastName = "";
	    if (fullname != null && !fullname.trim().isEmpty()) {
	        String[] nameParts = fullname.trim().split("\\s+");
	        if (nameParts.length > 0) {
	            firstName = nameParts[nameParts.length - 1]; // Từ cuối cùng làm tên
	            
	            // Các từ còn lại gom thành họ và tên đệm
	            StringBuilder sb = new StringBuilder();
	            for (int i = 0; i < nameParts.length - 1; i++) {
	                sb.append(nameParts[i]).append(" ");
	            }
	            lastName = sb.toString().trim();
	        }
	    }
	    
	    // Ép kiểu idRole từ String sang int
	    int roleId = Integer.parseInt(idRole);
	    
	    // Gọi xuống Repository để lưu vào DB
	    int rowsAffected = userRepository.save(email, password, firstName, lastName, phone, roleId);
	    
	    // Nếu số dòng bị ảnh hưởng > 0 tức là thêm thành công
	    return rowsAffected > 0;
	}
}
