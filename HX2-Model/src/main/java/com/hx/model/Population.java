package com.hx.model;

import java.util.Date;

import com.hx.model.dto.Communication;
import com.hx.model.dto.House;
import com.hx.model.dto.Personality;
import com.hx.model.dto.Role;
import com.hx.model.dto.Sector;
import com.hx.model.dto.User;
import com.hx.model.dto.UserStatus;
import com.hx.model.util.HXKeys;

public class Population {

	private static PopulationConfigurator config = new PopulationConfigurator();
	
	// Factions
	private static House comstar;
	private static House davion;
	private static House kurita;
	private static House steiner;
	private static House marik;
	private static House liao;
	
	// Users
	private static User luisOllero;
	
	// Personalities
	private static Personality kineasLiao;

	public static void main(String[] args) {
		config.setUp();
		insertFactions();
		insertSectors();
		insertUsers();
		insertPersonalities();
		insertCommunications();
		config.tearDown();
	}

	
	private static void insertFactions() {

		comstar = new House(HXKeys.COMSTAR);
		comstar.setName("Comstar");
		config.getDaoHouse().saveOrUpdate(comstar);

		davion = new House(HXKeys.DAVION);
		davion.setName("House Davion");
		davion.setAlternateName("Federated Suns");
		config.getDaoHouse().saveOrUpdate(davion);

		liao = new House(HXKeys.LIAO);
		liao.setName("House Liao");
		liao.setAlternateName("Capellan Confederation");
		config.getDaoHouse().saveOrUpdate(liao);
		
		kurita = new House(HXKeys.KURITA);
		kurita.setName("House Kurita");
		kurita.setAlternateName("Draconis Combine");
		config.getDaoHouse().saveOrUpdate(kurita);

		steiner = new House(HXKeys.STEINER);
		steiner.setName("House Steiner");
		steiner.setAlternateName("Lyran Commonwealth");
		config.getDaoHouse().saveOrUpdate(steiner);

		marik = new House(HXKeys.MARIK);
		marik.setName("House Marik");
		marik.setAlternateName("Free Worlds League");
		config.getDaoHouse().saveOrUpdate(marik);
	}
	
	private static void insertSectors() {
		
		saveSector("0,0", "Terra", 0, 0, 100, 2, false, 5, comstar, comstar, 100);
		saveSector("0,2", "Dieron", 0, 2, 5, 2, false, 5, kurita, kurita, 7);
		saveSector("1,1", "Quentin", 1, 1, 5, 2, false, 5, davion, davion, 7);
		saveSector("2,-2", "New Aragon", 2, -2, 2, 1, false, 5, davion, davion, 3);
		saveSector("2,0", "Addicks", 2, 0, 4, 2, false, 5, davion, davion, 7);
		saveSector("1,-1", "Tikonov", 1, -1, 7, 2, false, 5, liao, liao, 12);
		saveSector("1,-3", "Aldebaran", 1, -3, 6, 2, false, 4, liao, liao, 8);
		saveSector("0,-2", "Carver V", 0, -2, 3, 1, false, 4, liao, liao, 5);
		saveSector("-1,-1", "Irian", -1, -1, 7, 2, false, 5, marik, marik, 10);
		saveSector("-1,1", "New Earth", -1, 1, 3, 2, false, 5, steiner, steiner, 4);
		
	}
	
	private static void saveSector(String id, String name, int coordX, int coordY, int defenseBonus,
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
		
		config.getDaoSector().saveOrUpdate(sector);
	}
	
	private static void insertUsers() {
		luisOllero  = new User();
		luisOllero.setId("0");
		luisOllero.setStatus(UserStatus.ACTIVE);
		luisOllero.setApplication("Test application");
		luisOllero.setMail("luisollero@gmail.com");
		luisOllero.setName("Luis Ollero");
		config.getDaoUser().saveOrUpdate(luisOllero);
	}
	
	private static void insertPersonalities() {
		kineasLiao = new Personality();
		kineasLiao.setId("0");
		kineasLiao.setHouse(comstar);
		kineasLiao.setName("Kineas");
		kineasLiao.setCompleteName("Kineas Liao");
		kineasLiao.setUser(luisOllero);
		kineasLiao.setInfluence(0);
		kineasLiao.setRole(Role.PRIMUS);
		config.getDaoPersonality().saveOrUpdate(kineasLiao);
	}

	private static void insertCommunications() {
		Communication comm = new Communication();
		comm.setSubject("Dummy subject");
		comm.setMessage("Dummy message");
		comm.setPublishedIn("Star of Sian");
		comm.setSendingDate(new Date());
		comm.setLevel(kineasLiao.getInfluence());
		comm.setFrom(kineasLiao);
		config.getDaoCommunication().saveOrUpdate(comm);
	}
}
