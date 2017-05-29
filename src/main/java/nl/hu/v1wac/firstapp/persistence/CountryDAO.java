package nl.hu.v1wac.firstapp.persistence;

import java.sql.*;
import java.util.*;

import nl.hu.v1wac.firstapp.model.Country;

public class CountryDAO extends BaseDAO{
	
	private List<Country> selectCountries(String query) {
		List<Country> results = new ArrayList<Country>();
		try (Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			
			while (dbResultSet.next()) {
				String iso2cd = dbResultSet.getString("code2");
				String iso3cd = dbResultSet.getString("code");
				String nm = dbResultSet.getString("name");
				String cap = dbResultSet.getString("capital");
				String ct = dbResultSet.getString("continent");
				String reg = dbResultSet.getString("region");
				double sur = dbResultSet.getDouble("surfacearea");
				int pop = dbResultSet.getInt("population");
				String gov = dbResultSet.getString("governmentform");
				double lat = dbResultSet.getDouble("latitude");
				double lng = dbResultSet.getDouble("longitude");
				Country newCountry = new Country(iso2cd, iso3cd, nm, cap, ct, reg,
						sur, pop, gov, lat, lng);
				results.add(newCountry);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return results;

	}
	
	public Country save(Country country) {
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			String query = "INSERT INTO country "
					+ "(code, code2, name, capital, continent, region, surfacearea, "
					+ "population, governmentform, latitude, longitude) "
					+ "VALUES ('" + country.getIso3Code() + "', '" + country.getCode()
					+ "', '" + country.getName() + "', '" + country.getCapital() + "', '"
					+ country.getContinent() + "', '" + country.getRegion() + "', "
					+ country.getSurface() + ", " + country.getPopulation() + ", '"
					+ country.getGovernment() + "', " + country.getLatitude() + ", "
					+ country.getLongitude() + ")";
			stmt.executeUpdate(query);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return findByCode(country.getIso3Code());
	}
	
	public List<Country> findAll() {
		return selectCountries("SELECT * FROM country");
	}
	
	public Country findByCode(String code) {
		return selectCountries("SELECT * FROM country WHERE code = '" + code + "'").get(0);
	}
	
	public List<Country> find10LargestPopulations() {
		return selectCountries("SELECT * FROM country ORDER BY population DESC LIMIT 10");
	}
	
	public List<Country> find10LargestSurfaces() {
		return selectCountries("SELECT * FROM country ORDER BY surfacearea DESC LIMIT 10");
	}

	public List<Country> search(String searchValue) {
		return selectCountries("SELECT * FROM country WHERE name LIKE('%"+searchValue+"%') OR code LIKE('%"+searchValue+"%')");
	}
	public Country update(Country country) {
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			String query = "UPDATE country SET code2='" + country.getCode()
					+ "', name='" + country.getName() + "', capital='"
					+ country.getCapital() + "', continent='" + country.getContinent()
					+ "', region='" + country.getRegion() + "', surfacearea="
					+ country.getSurface() + ", population="
					+ country.getPopulation() + ", governmentform='"
					+ country.getGovernment() + "', latitude=" + country.getLatitude()
					+ ", longitude=" + country.getLongitude() + " WHERE code = '"
					+ country.getIso3Code() + "'";
			stmt.executeUpdate(query);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return findByCode(country.getIso3Code());
	}
	
	public boolean delete(Country country) {
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			String query = "DELETE FROM city WHERE countrycode = '" + country.getIso3Code() + "'";
			stmt.executeUpdate(query);
			query = "DELETE FROM countrylanguage WHERE countrycode = '" + country.getIso3Code() + "'";
			stmt.executeUpdate(query);
			query = "DELETE FROM country WHERE code='" + country.getIso3Code() + "'";
			stmt.executeUpdate(query);
			return true;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}
}
