package com.hx.model;

import java.io.InputStream;
import java.util.Date;

import com.hx.model.dto.Communication;
import com.hx.model.dto.House;
import com.hx.model.dto.Personality;
import com.hx.model.dto.Role;
import com.hx.model.dto.Scope;
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
	private static User tsunami;
	
	// Personalities
	private static Personality kineasLiao;
	private static Personality vitorDavion;

	// Sectors
	private static Sector terra;
	private static Sector quentin;
	private static Sector tikonov;

	// Here we go
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

		comstar = createFaction(HXKeys.COMSTAR, "Comstar", null);
		liao = createFaction(HXKeys.LIAO, "House Liao", "Capellan Confederation");
		davion = createFaction(HXKeys.DAVION, "House Davion", "Federated Suns");
		kurita = createFaction(HXKeys.KURITA, "House Kurita", "Draconis Combine");
		steiner = createFaction(HXKeys.STEINER, "House Steiner", "Lyran Commonwealth");
		marik = createFaction(HXKeys.MARIK, "House Marik", "Free Worlds League");
		
		createFaction(HXKeys.TAURUS, "Taurus Concordate", null);
		createFaction(HXKeys.CANOPUS, "Canopus Magistry", null);
		createFaction(HXKeys.CIRCINUS, "Circinus Federation", null);
		createFaction(HXKeys.TORTUGA, "Tortuga Dominion", null);
		createFaction(HXKeys.ILLYRIAN, "Illyrian Palatinate", null);
		createFaction(HXKeys.MARIAN, "Marian Hegemony", null);
		createFaction(HXKeys.OBERON, "Oberon Confederation", null);
		createFaction(HXKeys.OUTERWORLDS, "Outer Worlds Alliance", null);
		createFaction(HXKeys.LOTHARIO, "Lothario League", null);
		createFaction(HXKeys.ELYSSIA, "Elyssia Fields", null);
		createFaction(HXKeys.VALKYRIATE, "Great Valkyriate", null);
		
		createFaction(HXKeys.UNEXPLORED, "Unexplored", null);
		createFaction(HXKeys.DISPUTED, "Disputed", null);
		
		config.flush();
	}


	private static void insertSectors() {
		String map = null;
		Integer coordX, coordY = null;
		String name = null;
		Sector sector = null;
		
		InputStream in = Population.class
                .getResourceAsStream("/regular_map.dat");
		if (in != null) {
			map = convertStreamToString(in);
			String[] mapArrayLines = map.split("\n");
			for (int i = 0; i < mapArrayLines.length; i++) {
				String[] line = mapArrayLines[i].split(",");
				String[] coords = line[0].split("-");
				coordX = Integer.valueOf(coords[0]);
				coordY = Integer.valueOf(coords[1]);
				name = line[1];
				
				House house = config.getDaoHouse().getById(line[2].trim());
				if (house == null) {
					System.out.println(line[2].trim());
				}
				System.out.println(house.toString());
				
				sector = saveSector(name, coordX, coordY, 100, 2, false, 5, house,
						house, 100);
				
				// Special cases. Saved apart for the dummy data
				if (name.equals("Terra"))
					terra = sector;
				if (name.equals("Quentin"))
					quentin = sector;
				if (name.equals("Tikonov"))
					tikonov = sector;
			}
		}
		
		
//		terra = saveSector("Terra", 0, 0, 100, 2, false, 5, comstar, comstar, 100);
//		saveSector("Dieron", 0, 2, 5, 2, false, 5, kurita, kurita, 7);
//		quentin = saveSector("Quentin", 1, 1, 5, 2, false, 5, davion, davion, 7);
//		saveSector("New Aragon", 2, -2, 2, 1, false, 5, davion, davion, 3);
//		saveSector("Addicks", 2, 0, 4, 2, false, 5, davion, davion, 7);
//		tikonov = saveSector("Tikonov", 1, -1, 7, 2, false, 5, liao, liao, 12);
//		saveSector("Aldebaran", 1, -3, 6, 2, false, 4, liao, liao, 8);
//		saveSector("Carver V", 0, -2, 3, 1, false, 4, liao, liao, 5);
//		saveSector("Irian", -1, -1, 7, 2, false, 5, marik, marik, 10);
//		saveSector("New Earth", -1, 1, 3, 2, false, 5, steiner, steiner, 4);
		
	}
	
	private static Sector saveSector(String name, int coordX, int coordY, int defenseBonus,
			int depth, boolean disputed, int happiness, House house, House loyalty, int production) {
		Sector sector = new Sector();
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
		
		return sector;
	}
	
	private static void insertUsers() {
		luisOllero  = new User();
		luisOllero.setApplication("Test application");
		luisOllero.setMail("luisollero@gmail.com");
		luisOllero.setName("Luis Ollero");
		config.getDaoUser().saveOrUpdate(luisOllero);
		
		tsunami  = new User();
		tsunami.setApplication("Test application");
		tsunami.setMail("tsunami@tsunami.com");
		tsunami.setName("Tsunami");
		config.getDaoUser().saveOrUpdate(tsunami);
	}
	
	private static void insertPersonalities() {
		kineasLiao = new Personality();
		kineasLiao.setHouse(liao);
		kineasLiao.setName("Kineas");
		kineasLiao.setCompleteName("Kineas Liao");
		kineasLiao.setUser(luisOllero);
		kineasLiao.setInfluence(0);
		kineasLiao.setRole(Role.PRINCE);
		kineasLiao.setStatus(UserStatus.ACTIVE);
		kineasLiao.setHome(tikonov);
		config.getDaoPersonality().saveOrUpdate(kineasLiao);
		
		vitorDavion = new Personality();
		vitorDavion.setHouse(davion);
		vitorDavion.setName("Vitor");
		vitorDavion.setCompleteName("Vitor Davion");
		vitorDavion.setUser(tsunami);
		vitorDavion.setInfluence(0);
		vitorDavion.setRole(Role.PRINCE);
		vitorDavion.setStatus(UserStatus.ACTIVE);
		vitorDavion.setHome(quentin);
		config.getDaoPersonality().saveOrUpdate(vitorDavion);
	}

	private static void insertCommunications() {
		Communication comm = new Communication();
		comm.setSubject("The troops attacking New Syrtis!");
		comm.setMessage("In an unexpected turn of announcements, our brave troops are advancing without problems over the defenses of the Federated Suns.");
		comm.setPublishedIn("Star of Sian");
		comm.setSendingDate(new Date());
		comm.setScope(Scope.INNER_SPHERE);
		comm.setFrom(kineasLiao);
		config.getDaoCommunication().saveOrUpdate(comm);
		
		comm = new Communication();
		comm.setSubject("Our troops exercising in New Syrtis");
		comm.setMessage("Our troops are currently playing some war games in turtle bay. We encourage our citizens not to move over there during the next days.");
		comm.setPublishedIn("Herald of New Syrtis");
		comm.setSendingDate(new Date());
		comm.setScope(Scope.INNER_SPHERE);
		comm.setFrom(vitorDavion);
		config.getDaoCommunication().saveOrUpdate(comm);
	}
	
	private static House createFaction(String houseId, String houseName, String alternateName) {
		House house = new House(houseId);
		house.setName(houseName);
		house.setAlternateName(alternateName);
		config.getDaoHouse().saveOrUpdate(house);
		return house;
	}
	
	public static String convertStreamToString(java.io.InputStream is) {
	    try {
	        return new java.util.Scanner(is).useDelimiter("\\A").next();
	    } catch (java.util.NoSuchElementException e) {
	        return "";
	    }
	}

}
