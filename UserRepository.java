package crm_app12.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm_app12.MysqlConfig;
import crm_app12.UserEntity;

/**
 * Quản lý tất cả câu truy vấn liên quan tới bảng user
 */
public class UserRepository {

	/**
	 * select -> find
	 * where -> by
	 * username = '' -> userName
	 * 
	 * Ví dụ: select * from user where username = ''
	 * -> findByUsername();
	 * 
	 */
	
	
	public List<UserEntity> findByEmailAndPassword(String email, String password) {
		List<UserEntity> listUserEntities = new ArrayList<UserEntity>();
		
		try {			
			// Bước 1: Chuẩn bị câu truy vấn
			String query = "SELECT * FROM users u WHERE u.email = ? AND u.password = ?";
			// Bước 2: Mở kết nối CSDL
			Connection connection = MysqlConfig.getConnection();
			// Bước 3: Truyền câu truy vấn vào kết nối
			PreparedStatement statement = connection.prepareStatement(query);
			// Truyền tham số vào dấu ? ở câu query
			statement.setString(1, email);
			statement.setString(2, password);
			
			// Bước 4: Thực thi câu truy cấn
			// excuteQuery: khi câu truy vấn là câu SELECT
			// excuteUpdate: không phải là câu SELECT
			ResultSet resultSet = statement.executeQuery();
						
			while (resultSet.next()) {
				UserEntity userEntity = new UserEntity();
				userEntity.setId(resultSet.getInt("id"));
				userEntity.setFullname(resultSet.getString("fullname"));
				
				listUserEntities.add(userEntity);
			}		
		} catch (Exception e) {
			System.out.println("Error : findByEmailAndPassword " + e.getMessage());
		}
		
		return listUserEntities;
	}
	
	public List<UserEntity> findAll() {
		List<UserEntity> listUserEntities = new ArrayList<UserEntity>();
		String query = "SELECT u.id, u.first_name, u.last_name, u.email, r.name\r\n"
				+ "FROM users u\r\n"
				+ "JOIN roles r ON u.role_id = r.id";
		
		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				UserEntity userEntity = new UserEntity();
				userEntity.setId(resultSet.getInt("id"));
				userEntity.setFirstName(resultSet.getString("first_name"));
				userEntity.setLastName(resultSet.getString("last_name"));
				userEntity.setEmail(resultSet.getString("email"));
				userEntity.setRoleName(resultSet.getString("name"));
				
				listUserEntities.add(userEntity);
			}
			
		} catch (Exception e) {
			System.out.println("Loi findAll " + e.getMessage());
		}
		
		return listUserEntities;
	}

	public int save(String email, String password, String firstName, String lastName, String phone, int roleId) {
	    int result = 0;
	    // Chú ý: Cấu trúc cột database dựa theo hàm findAll() của bạn (first_name, last_name, role_id...)
	    String query = "INSERT INTO users(email, password, first_name, last_name, phone, role_id) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    try {
	        Connection connection = MysqlConfig.getConnection();
	        PreparedStatement statement = connection.prepareStatement(query);
	        
	        statement.setString(1, email);
	        statement.setString(2, password);
	        statement.setString(3, firstName);
	        statement.setString(4, lastName);
	        statement.setString(5, phone);
	        statement.setInt(6, roleId);
	        
	        // Thực thi câu lệnh INSERT/UPDATE/DELETE trả về số dòng ảnh hưởng
	        result = statement.executeUpdate(); 
	        
	    } catch (Exception e) {
	        System.out.println("Loi save user: " + e.getMessage());
	    }
	    
	    return result;
	}
	
}
