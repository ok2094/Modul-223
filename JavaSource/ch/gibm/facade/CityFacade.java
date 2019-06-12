package ch.gibm.facade;

import java.io.Serializable;
import java.util.List;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.dao.CityDAO;
import ch.gibm.entity.City;

public class CityFacade implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private CityDAO cityDAO = new CityDAO();

	public void createCity(City city) {
		EntityManagerHelper.beginTransaction();
		cityDAO.save(city);
		EntityManagerHelper.commitAndCloseTransaction();
	}

	public void updateCity(City city) {
		EntityManagerHelper.beginTransaction();
		City persistedLng = cityDAO.find(city.getId());
		persistedLng.setName(city.getName());
		cityDAO.update(persistedLng);
		EntityManagerHelper.commitAndCloseTransaction();
	}
	
	public void deleteCity(City city) {
		EntityManagerHelper.beginTransaction();
		City persistedLng = cityDAO.findReferenceOnly(city.getId());
		cityDAO.delete(persistedLng);
		EntityManagerHelper.commitAndCloseTransaction();
	}

	public City findCity(int cityId) {
		EntityManagerHelper.beginTransaction();
		City city = cityDAO.find(cityId);
		EntityManagerHelper.commitAndCloseTransaction();
		return city;
	}

	public List<City> listAll() {
		EntityManagerHelper.beginTransaction();
		List<City> result = cityDAO.findAll();
		EntityManagerHelper.commitAndCloseTransaction();
		return result;
	}
}