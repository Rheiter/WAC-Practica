package nl.hu.v1wac.firstapp.model;

/* Deze imports worden gebruikt t/m les 6
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
*/

import java.util.List;

import nl.hu.v1wac.firstapp.persistence.CountryDAO;

public class WorldService {
	private static CountryDAO countryDAO = new CountryDAO();
	
	public List<Country> getAllCountries() {
		return countryDAO.findAll();
	}
	
	public List<Country> get10LargestPopulations() {
		return countryDAO.find10LargestPopulations();
	}

	public List<Country> get10LargestSurfaces() {
		return countryDAO.find10LargestSurfaces();
	}
	
	public Country getCountryByCode(String code) {
		return countryDAO.findByCode(code);
	}
	
	public Country updateCountry(Country country) {
		return countryDAO.update(country);
	}
	
	public boolean deleteCountry(Country country) {
		return countryDAO.delete(country);
	}
	
	public Country insertCountry(Country country) {
		return countryDAO.save(country);
	}
	
	public List<Country> searchCountries(String searchValue) {
		return countryDAO.search(searchValue);
	}
}
