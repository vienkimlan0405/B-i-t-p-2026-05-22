package crm_app12.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app12.UserEntity;
import crm_app12.entity.RoleEntity;
import crm_app12.services.UserServices;

@WebServlet(name = "userController", urlPatterns = {"/user", "/user-add"})
public class UserController extends HttpServlet{
	
	private UserServices userServices = new UserServices();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Lấy đường dẫn servet mà client gọi
		String path = req.getServletPath();
		if (path.equals("/user")) {
			List<UserEntity> listUserEntities = userServices.getAll();
			
			req.setAttribute("listUsers", listUserEntities);
			
			req.getRequestDispatcher("user-table.jsp").forward(req, resp);
		} else if (path.equals("/user-add")) {
			
			List<RoleEntity> listRoleEntities = userServices.getAllRoles();
			
			req.setAttribute("listRoles", listRoleEntities);
			
			req.getRequestDispatcher("user-add.jsp").forward(req, resp);
		}
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if (path.equals("/user")) {
			
		} else if (path.equals("/user-add")) {
			String fullname = req.getParameter("fullname");
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String phone = req.getParameter("phone");
			String idRole = req.getParameter("role");
			
			
			// 2. Gọi service xử lý logic thêm mới
	        boolean isSuccess = userServices.insertUser(fullname, email, password, phone, idRole);
	        
	        if (isSuccess) {
	            // Nếu thành công, chuyển hướng (Redirect) về lại trang danh sách user để cập nhật bảng mới
	            resp.sendRedirect(req.getContextPath() + "/user");
	        } else {
	            // Nếu thất bại, thông báo lỗi hoặc quay lại trang add và hiển thị thông báo
	            req.setAttribute("errorMessage", "Thêm thành viên thất bại!");
	            
	            // Cần lấy lại list roles để hiển thị lại thẻ <select> trên giao diện add
	            List<RoleEntity> listRoleEntities = userServices.getAllRoles();
	            req.setAttribute("listRoles", listRoleEntities);
	            
	            req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	        }
		}
	}
}
