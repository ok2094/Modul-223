package ch.gibm.dao;

import ch.gibm.entity.City;

public class CityDAO extends GenericDAO<City> {

	private static final long serialVersionUID = 1L;

	public CityDAO() {
		super(City.class);
	}

	public void delete(City city) {
		super.delete(city.getId(), City.class);
	}

}