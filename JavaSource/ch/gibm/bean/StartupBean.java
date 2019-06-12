package ch.gibm.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ch.gibm.dao.EntityManagerHelper;

@ApplicationScoped
@ManagedBean(eager=true)
public class StartupBean {

	/**
	 * initialize EntityManagerFactory at application startup
	 */
	@PostConstruct
	public void init() {
		EntityManagerHelper.init();
	}
}
