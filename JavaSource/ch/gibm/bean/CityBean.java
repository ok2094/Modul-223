package ch.gibm.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ch.gibm.entity.City;
import ch.gibm.facade.CityFacade;

@ViewScoped
@ManagedBean(name="cityBean")
public class CityBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private City city;
	private List<City> cities;
	private CityFacade cityFacade;

	public CityFacade getCityFacade() {
		if (cityFacade == null) {
			cityFacade = new CityFacade();
		}

		return cityFacade;
	}

	public City getCity() {
		if (city == null) {
			city = new City();
		}

		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public void createCity() {
		try {
			getCityFacade().createCity(city);
			closeDialog();
			displayInfoMessageToUser("Created with success");
			loadCities();
			resetCity();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while saving. Try again later");
			e.printStackTrace();
		}
	}
	
	public void updateCity() {
		try {
			getCityFacade().updateCity(city);
			closeDialog();
			displayInfoMessageToUser("Updated with success");
			loadCities();
			resetCity();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while updating. Try again later");
			e.printStackTrace();
		}
	}
	
	public void deleteCity() {
		try {
			getCityFacade().deleteCity(city);
			closeDialog();
			displayInfoMessageToUser("Deleted with success");
			loadCities();
			resetCity();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while removing. Try again later");
			e.printStackTrace();
		}
	}

	public List<City> getAllCities() {
		if (cities == null) {
			loadCities();
		}

		return cities;
	}

	private void loadCities() {
		cities = getCityFacade().listAll();
	}

	public void resetCity() {
		city = new City();
	}
}