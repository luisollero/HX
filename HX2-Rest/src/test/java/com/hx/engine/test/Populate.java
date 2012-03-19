package com.hx.engine.test;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.hx.model.dto.Communication;
import com.hx.model.dto.Experience;
import com.hx.model.dto.House;
import com.hx.model.dto.Personality;
import com.hx.model.dto.Regiment;
import com.hx.model.dto.Scope;
import com.hx.model.dto.Sector;
import com.hx.model.dto.User;
import com.hx.model.dto.UserStatus;
import com.hx.model.util.HXKeys;

/**
 * Fill the DB. FIXME: Move from here.
 * @author Luis Ollero
 *
 */
public class Populate extends TestBase {

	private User user1;
	private User user2;
	private Personality personality;

	/**
	 * Comenta lo que no sea necesario que se regenere
	 */
	@Test
	public void populate() {
//		insertHouses();
//		insertSectors();
//		insertRegiments();
		insertUsers();
		insertCommunications();
//		insertPersonalities();
	}
	

	/**
	 * Casas
	 */
	private void insertHouses() {
		
		House house;
		
		house = new House(HXKeys.COMSTAR);
		house.setName("Comstar");
		getHouseEngine().saveOrUpdate(house);
		
		house = new House(HXKeys.DAVION);
		house.setName("Casa Davion");
		getHouseEngine().saveOrUpdate(house);
		
		house = new House(HXKeys.LIAO);
		house.setName("Casa Liao");
		getHouseEngine().saveOrUpdate(house);
		
		house = new House(HXKeys.KURITA);
		house.setName("Casa Kurita");
		getHouseEngine().saveOrUpdate(house);
		
		house = new House(HXKeys.STEINER);
		house.setName("Casa Steiner");
		getHouseEngine().saveOrUpdate(house);
		
		house = new House(HXKeys.MARIK);
		house.setName("Casa Marik");
		getHouseEngine().saveOrUpdate(house);
	}

	/**
	 * Sectores
	 */
	private void insertSectors() {
		
		saveSector("0,0","Terra",0,0,100,2,false,5,new House(HXKeys.COMSTAR),new House(HXKeys.COMSTAR),100);
		saveSector("0,+2", "Dieron", 0, 2, 5, 2, false, 5, new House(HXKeys.KURITA), new House(HXKeys.KURITA), 7);
		saveSector("+1,+1", "Quentin", 1, 1, 5, 2, false, 5, new House(HXKeys.DAVION), new House(HXKeys.DAVION), 7);
		saveSector("+2,-2", "New Aragon", 2, -2, 2, 1, false, 5, new House(HXKeys.DAVION), new House(HXKeys.DAVION), 3);
		saveSector("+2,0", "Addicks", 2, 0, 4, 2, false, 5, new House(HXKeys.DAVION), new House(HXKeys.DAVION), 7);
		saveSector("+1,-1", "Tikonov", 1, -1, 7, 2, false, 5, new House(HXKeys.LIAO), new House(HXKeys.LIAO), 12);
		saveSector("+1,-3", "Aldebaran", 1, -3, 6, 2, false, 4, new House(HXKeys.LIAO), new House(HXKeys.LIAO), 8);
		saveSector("0,-2", "Carver V", 0, -2, 3, 1, false, 4, new House(HXKeys.LIAO), new House(HXKeys.LIAO), 5);
		saveSector("-1,-1", "Irian", -1, -1, 7, 2, false, 5, new House(HXKeys.MARIK), new House(HXKeys.MARIK), 10);
		saveSector("-1,+1", "New Earth", -1, 1, 3, 2, false, 5, new House(HXKeys.STEINER), new House(HXKeys.STEINER), 4);
		
	}

	
	private void insertCommunications() {
		Communication comm = new Communication();
		comm.setSubject("Dummy subject");
		comm.setMessage("Dummy message");
		comm.setPublishedIn("Star of Sian");
		comm.setSendingDate(new Date());
		comm.setScope(Scope.INNER_SPHERE);
		comm.setFrom(personality);
		getCommunicationEngine().saveOrUpdate(comm);
	}

	
	/**
	 * 
	 * Guarda un sector
	 * @param id
	 * @param name
	 * @param coordX
	 * @param coordY
	 * @param defenseBonus
	 * @param depth
	 * @param disputed
	 * @param happiness
	 * @param house
	 * @param loyalty
	 * @param production
	 * @return
	 */
	private Sector saveSector(String id, String name, int coordX, int coordY, int defenseBonus,
			int depth, boolean disputed, int happiness, House house, House loyalty, int production) {
		Sector sector = new Sector();
		sector.setId(id);
		sector.setName(name);
		sector.setCoordX(coordX);
		sector.setCoordY(coordY);
		sector.setDefenseBonus(defenseBonus);
		sector.setDepth(depth);
		sector.setDisputed(disputed);
		sector.setHappiness(happiness);
		sector.setHouse(house);
		sector.setLoyalty(loyalty);
		sector.setProduction(production);
		
		getSectorEngine().saveOrUpdate(sector);
		return sector;
	}
	
	public void insertPersonalities() {
		

//		getPersonalityEngine().saveOrUpdate(personality);
		
//		personality = new Personality();
//		personality.setHouse(new House(HXKeys.COMSTAR));
//		personality.setId("1");
//		getPersonalityEngine().saveOrUpdate(personality);
//		
//		personality = new Personality();
//		personality.setHouse(new House(HXKeys.DAVION));
//		personality.setId("1");
//		getPersonalityEngine().saveOrUpdate(personality);
//		
//		personality = new Personality();
//		personality.setHouse(new House(HXKeys.KURITA));
//		personality.setId("2");
//		getPersonalityEngine().saveOrUpdate(personality);
//		
//		
//		personality = new Personality();
//		personality.setHouse(new House(HXKeys.LIAO));
//		personality.setId("3");
//		getPersonalityEngine().saveOrUpdate(personality);
//
//		personality = new Personality();
//		personality.setHouse(new House(HXKeys.MARIK));
//		personality.setId("4");
//		getPersonalityEngine().saveOrUpdate(personality);
		
	}

	private void insertUsers() {
		Set<Personality> set = new HashSet<Personality>();
		personality = new Personality();
		personality.setHouse(new House(HXKeys.COMSTAR));
		personality.setId("0");
		personality.setCompleteName("Kineas Liao");
		set.add(personality);
		
		user1  = new User();
//		user1.setId("Luis Ollero");
		user1.setPersonalities(set);
		user1.setStatus(UserStatus.ACTIVE);
		getUserEngine().saveOrUpdate(user1);
		
	}
	/**
	 * Regiments.
	 */
	private void insertRegiments() {
		
		saveRegiment("1","Lanceros de Tikonov", new House(HXKeys.LIAO), 
				getSectorEngine().findByName("Tikonov").iterator().next(), 
				5, 5, 5, 5, Experience.ELITE, 10);
		saveRegiment("2","Lanceros de Quentin", new House(HXKeys.DAVION), 
				getSectorEngine().findByName("Quentin").iterator().next(), 
				3, 3, 5, 6, Experience.VERDE, 10);
	}
	
	/**
	 * Guarda un {@link Regiment}
	 * @param id
	 * @param name
	 * @param house
	 * @param homeSector
	 * @param attack
	 * @param defense
	 * @param price
	 * @param maneuver
	 * @param experience
	 * @param initialResistence
	 * @return
	 */
	private Regiment saveRegiment(String id, String name, House house, 
			Sector homeSector, Integer attack, Integer defense, Integer price,
			Integer maneuver, Experience experience, Integer initialResistence) {

		Regiment regiment = new Regiment();

		regiment.setId(id);
		regiment.setHouse(house);
		regiment.setSector(homeSector);
		regiment.setName(name);
		regiment.setAttack(attack);
		regiment.setDefense(defense);
		regiment.setManeuver(maneuver);
		regiment.setResistance(initialResistence);
		regiment.setTotalResistence(initialResistence);
		regiment.setHomeSector(homeSector);
		regiment.setExperience(experience);
		regiment.setPrice(price);
		regiment.setUpkeep(price/4);
		getRegimentEngine().saveOrUpdate(regiment);
		
		return regiment;
	}
	
}
