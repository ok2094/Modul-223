package ch.gibm.facade;

import java.io.Serializable;
import java.util.List;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.dao.RoleDAO;
import ch.gibm.entity.Role;

public class RoleFacade implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RoleDAO roleDAO = new RoleDAO();
	
	public void createRole(Role role) {
		EntityManagerHelper.beginTransaction();
		roleDAO.save(role);
		EntityManagerHelper.commitAndCloseTransaction();
	}

	public List<Role> listAll() {
		EntityManagerHelper.beginTransaction();
		List<Role> result = roleDAO.findAll();
		EntityManagerHelper.commitAndCloseTransaction();
		return result;
	}
	
	public Role getRoleById(int roleId) {
		EntityManagerHelper.beginTransaction();
		Role role = roleDAO.findById(roleId);
		EntityManagerHelper.commitAndCloseTransaction();
		return role;
	}
}
