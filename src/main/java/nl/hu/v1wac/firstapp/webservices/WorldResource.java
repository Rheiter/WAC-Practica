package nl.hu.v1wac.firstapp.webservices;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.json.*;
import javax.ws.rs.*;

import nl.hu.v1wac.firstapp.model.Country;
import nl.hu.v1wac.firstapp.model.ServiceProvider;
import nl.hu.v1wac.firstapp.model.WorldService;

@Path("/countries")
public class WorldResource {
	
	JsonObjectBuilder countryToJson(Country c) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		
			job.add("iso2", c.getCode()).add("iso3", c.getIso3Code())
			.add("name", c.getName()).add("continent", c.getContinent())
			.add("capital", c.getCapital()).add("region", c.getRegion())
			.add("surface", c.getSurface()).add("population", c.getPopulation())
			.add("government", c.getGovernment());
			
			return job;
	}
	
	@GET
	@Produces("application/json")
	public String getAllCountries() {
		WorldService service = ServiceProvider.getWorldService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		if (service.getAllCountries() == null) {
			throw new WebApplicationException("No such order!");
		}
		
		for (Country c : service.getAllCountries()) {
			job.add("iso2", c.getCode()).add("iso3", c.getIso3Code())
			.add("name", c.getName()).add("continent", c.getContinent())
			.add("capital", c.getCapital()).add("region", c.getRegion())
			.add("surface", c.getSurface()).add("population", c.getPopulation())
			.add("government", c.getGovernment()).add("lat", c.getLatitude())
			.add("lng", c.getLongitude());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("{code}")
	@Produces("application/json")
	public String getCountryInfo(@PathParam("code") String code) {
		WorldService service = ServiceProvider.getWorldService();
		Country country = service.getCountryByCode(code);
		
		if (country == null) {
			throw new WebApplicationException("No such order!");
		}
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("iso2", country.getCode()).add("iso3", country.getIso3Code())
		.add("name", country.getName()).add("continent", country.getContinent())
		.add("capital", country.getCapital()).add("region", country.getRegion())
		.add("surface", country.getSurface())
		.add("population", country.getPopulation())
		.add("government", country.getGovernment()).add("lat", country.getLatitude())
		.add("lng", country.getLongitude());
		
		return job.build().toString();
	}
	
	@GET
	@Path("/largestsurfaces")
	@Produces("application/json")
	public String getLargestSurfaces() {
		WorldService service = ServiceProvider.getWorldService();
		List<Country> largestSurfaces = service.get10LargestSurfaces();
		
		if (largestSurfaces == null) {
			throw new WebApplicationException("No such order!");
		}
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		for (Country c : largestSurfaces) {
			job.add("iso2", c.getCode()).add("iso3", c.getIso3Code())
			.add("name", c.getName()).add("continent", c.getContinent())
			.add("capital", c.getCapital()).add("region", c.getRegion())
			.add("surface", c.getSurface()).add("population", c.getPopulation())
			.add("government", c.getGovernment()).add("lat", c.getLatitude())
			.add("lng", c.getLongitude());
			
			jab.add(job);
		}
		
		return jab.build().toString();
	}
	
	@GET
	@Path("/largestpopulations")
	@Produces("application/json")
	public String getLargestPopulations() {
		WorldService service = ServiceProvider.getWorldService();
		List<Country> largestPopulations = service.get10LargestPopulations();
		
		if (largestPopulations == null) {
			throw new WebApplicationException("No such order!");
		}
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		for (Country c : largestPopulations) {
			job.add("iso2", c.getCode()).add("iso3", c.getIso3Code())
			.add("name", c.getName()).add("continent", c.getContinent())
			.add("capital", c.getCapital()).add("region", c.getRegion())
			.add("surface", c.getSurface()).add("population", c.getPopulation())
			.add("government", c.getGovernment()).add("lat", c.getLatitude())
			.add("lng", c.getLongitude());
			
			jab.add(job);
		}
		
		return jab.build().toString();
	}
	
	@PUT
	@RolesAllowed("user")
	@Path("{updateIso3}")
	@Produces("application/json")
	public String update(@PathParam("updateIso3") String iso3,
							@FormParam("updateName") String name,
							@FormParam("updateIso2") String iso2,
							@FormParam("updateCapital") String capital,
							@FormParam("updateContinent") String continent,
							@FormParam("updateRegion") String region,
							@FormParam("updateSurface") int surface,
							@FormParam("updatePopulation") int population,
							@FormParam("updateGovernment") String government,
							@FormParam("updateLatitude") double latitude,
							@FormParam("updateLongitude") double longitude) {
		
		WorldService service = ServiceProvider.getWorldService();
		Country found = null;
		Country country = new Country(iso2, iso3, name, capital, continent, region,
				surface, population, government, latitude, longitude);
		
		found = service.updateCountry(country);
		
		
		return countryToJson(found).build().toString();
	}
	
	@DELETE
	@RolesAllowed("user")
	@Path("{updateIso3}")
	public boolean delete(@PathParam("updateIso3") String iso3,
							@FormParam("updateName") String name,
							@FormParam("updateIso2") String iso2,
							@FormParam("updateCapital") String capital,
							@FormParam("updateContinent") String continent,
							@FormParam("updateRegion") String region,
							@FormParam("updateSurface") int surface,
							@FormParam("updatePopulation") int population,
							@FormParam("updateGovernment") String government,
							@FormParam("updateLatitude") double latitude,
							@FormParam("updateLongitude") double longitude) {
		WorldService service = ServiceProvider.getWorldService();
		
		Country country = new Country(iso2, iso3, name, capital, continent, region,
				surface, population, government, latitude, longitude);
		
		return service.deleteCountry(country);
	}
	
	@POST
	@RolesAllowed("user")
	@Produces("application/json")
	public String insert(@FormParam("insertIso3") String iso3,
							@FormParam("insertName") String name,
							@FormParam("insertIso2") String iso2,
							@FormParam("insertCapital") String capital,
							@FormParam("insertContinent") String continent,
							@FormParam("insertRegion") String region,
							@FormParam("insertSurface") int surface,
							@FormParam("insertPopulation") int population,
							@FormParam("insertGovernment") String government,
							@FormParam("insertLatitude") double latitude,
							@FormParam("insertLongitude") double longitude) {
		WorldService service = ServiceProvider.getWorldService();
		Country found = null;
		
		Country country = new Country(iso2, iso3, name, capital, continent, region,
				surface, population, government, latitude, longitude);
		
		found = service.insertCountry(country);

		return countryToJson(found).build().toString();
	}
	
	@GET
	@Path("/search/{searchValue}")
	@Produces("application/json")
	public String search(@PathParam("searchValue") String searchValue) {
		WorldService service = ServiceProvider.getWorldService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		if (service.getAllCountries() == null) {
			throw new WebApplicationException("No such order!");
		}
		
		for (Country c : service.searchCountries(searchValue)) {
			job.add("iso2", c.getCode()).add("iso3", c.getIso3Code())
			.add("name", c.getName()).add("continent", c.getContinent())
			.add("capital", c.getCapital()).add("region", c.getRegion())
			.add("surface", c.getSurface()).add("population", c.getPopulation())
			.add("government", c.getGovernment()).add("lat", c.getLatitude())
			.add("lng", c.getLongitude());
			
			jab.add(job);
		}
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
}
