package ch.gibm.dao;

import java.util.List;
import ch.gibm.entity.Role;

public class RoleDAO  extends GenericDAO<Role>{
	
	private static final long serialVersionUID = 1L;

	public RoleDAO() {
		super(Role.class);
	}

	public List<Role> findAll() {
		return super.findAll();
	}

	public void create (Role role) {
        	super.save(role);
	}
	
	public Role findById(int roleId) {
		return super.find(roleId);
	}
}
