package ch.gibm.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ch.gibm.entity.Role;
import ch.gibm.facade.RoleFacade;

@ViewScoped
@ManagedBean(name="roleBean")
public class RoleBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Role role;
	private List<Role> roles;
	
	private RoleFacade roleFacade;

	public void createRole() {
		try {
			getRoleFacade().createRole(role);
			closeDialog();
			displayInfoMessageToUser("Created with success");
			loadRoles();
			resetRole();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while saving. Try again later");
			e.printStackTrace();
		}
	}

	private void loadRoles() {
		roles = getRoleFacade().listAll();
		System.out.println(roles);
	}

	public void resetRole() {
		role = new Role();
	}
	
	public RoleFacade getRoleFacade() {
		if (roleFacade == null) {
			roleFacade = new RoleFacade();
		}
		return roleFacade;
	}	

	public List<Role> getAllRoles() {
		if (role == null) {
			loadRoles();
		}

		return roles;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}