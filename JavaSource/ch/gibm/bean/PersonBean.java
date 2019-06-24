package ch.gibm.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sun.faces.context.flash.ELFlash;

import ch.gibm.entity.City;
import ch.gibm.entity.Language;
import ch.gibm.entity.Person;
import ch.gibm.facade.PersonFacade;

@ViewScoped
@ManagedBean
public class PersonBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String SELECTED_PERSON = "selectedPerson";

	private Language language;
	private Person person;
	private Person personWithLanguages;
	private Person personWithLanguagesForDetail;
	private City city;

	@ManagedProperty(value="#{languageBean}")
	private LanguageBean languageBean;
	
	@ManagedProperty(value="#{cityBean}")
	private CityBean cityBean;

	private List<Person> persons;
	private PersonFacade personFacade;

	public void createPerson() {
		try {
			getPersonFacade().createPerson(person);
			closeDialog();
			displayInfoMessageToUser("Created with success");
			loadPersons();
			resetPerson();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while saving. Try again later");
			e.printStackTrace();
		}
	}

	public void updatePerson() {
		try {
			getPersonFacade().updatePerson(person);
			closeDialog();
			displayInfoMessageToUser("Updated with success");
			loadPersons();
			resetPerson();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while updating. Try again later");
			e.printStackTrace();
		}
	}

	public void deletePerson() {
		try {
			getPersonFacade().deletePerson(person);
			closeDialog();
			displayInfoMessageToUser("Deleted with success");
			loadPersons();
			resetPerson();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while removing. Try again later");
			e.printStackTrace();
		}
	}

	public Person getPerson() {
		if (person == null) {
			person = new Person();
		}

		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Person> getAllPersons() {
		if (persons == null) {
			loadPersons();
		}

		return persons;
	}

	private void loadPersons() {
		persons = getPersonFacade().listAll();
	}

	public void resetPerson() {
		person = new Person();
	}

	public Person getPersonWithLanguages() {
		if (personWithLanguages == null) {
			person = (Person) ELFlash.getFlash().get(SELECTED_PERSON);
			personWithLanguages = getPersonFacade().findPersonWithAllLanguages(person.getId());
		}

		return personWithLanguages;
	}

	public void setPersonWithLanguagesForDetail(Person person) {
		personWithLanguagesForDetail = getPersonFacade().findPersonWithAllLanguages(person.getId());
	}

	public Person getPersonWithLanguagesForDetail() {
		if (personWithLanguagesForDetail == null) {
			personWithLanguagesForDetail = new Person();
			personWithLanguagesForDetail.setLanguages(new ArrayList<Language>());
		}

		return personWithLanguagesForDetail;
	}

	public void resetPersonWithLanguagesForDetail() {
		personWithLanguagesForDetail = new Person();
	}

	private void reloadPersonWithLanguages() {
		personWithLanguages = getPersonFacade().findPersonWithAllLanguages(person.getId());
	}

	public PersonFacade getPersonFacade() {
		if (personFacade == null) {
			personFacade = new PersonFacade();
		}

		return personFacade;
	}
	
	// Language stuff

	public void setLanguageBean(LanguageBean languageBean) {
		this.languageBean = languageBean;
	}
	
	public void addLanguageToPerson() {
		try {
			getPersonFacade().addLanguageToPerson(language.getId(), personWithLanguages.getId());
			closeDialog();
			displayInfoMessageToUser("Added with success");
			reloadPersonWithLanguages();
			resetLanguage();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while saving. Try again later");
			e.printStackTrace();
		}
	}

	public void removeLanguageFromPerson() {
		try {
			getPersonFacade().removeLanguageFromPerson(language.getId(), personWithLanguages.getId());
			closeDialog();
			displayInfoMessageToUser("Removed with success");
			reloadPersonWithLanguages();
			resetLanguage();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while removing. Try again later");
			e.printStackTrace();
		}
	}

	public String editPersonLanguages() {
		ELFlash.getFlash().put(SELECTED_PERSON, person);
		return "/pages/protected/person/personLanguages/personLanguages.xhtml";
	}

	public Language getLanguage() {
		if (language == null) {
			language = new Language();
		}

		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void resetLanguage() {
		language = new Language();
	}
		
	public List<Language> getRemainingLanguages(String name) {
		//get all languages as copy
		List<Language> res = new ArrayList<Language>(this.languageBean.getAllLanguages());
		//remove already added languages
		res.removeAll(personWithLanguages.getLanguages());
		//remove when name not occurs
		res.removeIf(l -> l.getName().toLowerCase().contains(name.toLowerCase()) == false);
		return res;
	}

	// City stuff

	public void setCityBean(CityBean cityBean) {
		this.cityBean = cityBean;
	}
	
	public void setCityToPerson() {
		try {
			getPersonFacade().setCityToPerson(city.getId(), personWithLanguages.getId());
			displayInfoMessageToUser("Saved");
			reloadPersonWithLanguages();
			resetCity();
		} catch (Exception e) {
			displayErrorMessageToUser("A problem occurred while saving. Try again later");
			e.printStackTrace();
		}
	}

	public void removeCityFromPerson() {
		try {
			getPersonFacade().removeCityFromPerson(personWithLanguages.getId());
			closeDialog();
			displayInfoMessageToUser("Removed with success");
			reloadPersonWithLanguages();
			resetCity();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while removing. Try again later");
			e.printStackTrace();
		}
	}

	public String editPersonCity() {
		ELFlash.getFlash().put(SELECTED_PERSON, person);
		return "/pages/protected/person/personCities/personCities.xhtml";
	}

	public City getCity() {
		if (city == null) {
			if(personWithLanguages.getCity() != null) {
				city = personWithLanguages.getCity();
			} else {
				city = new City();
			}
		}

		return city;
	}
	
	public List<City> getAllCities() {
		return cityBean.getAllCities();
	}

	public void setCity(City city) {
		this.city = city;
	}

	public void resetCity() {
		city = new City();
	}
	
	public List<City> getRemainingCities(String name) {
		//get all languages as copy
		List<City> res = new ArrayList<City>(this.cityBean.getAllCities());
		//remove when name not occurs
		res.removeIf(l -> l.getName().toLowerCase().contains(name.toLowerCase()) == false);
		return res;
	}
}