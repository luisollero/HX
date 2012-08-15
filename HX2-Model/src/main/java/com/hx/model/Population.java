package com.hx.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.hx.model.dto.AttackOrder;
import com.hx.model.dto.Combat;
import com.hx.model.dto.Communication;
import com.hx.model.dto.House;
import com.hx.model.dto.Maneuver;
import com.hx.model.dto.Message;
import com.hx.model.dto.Morale;
import com.hx.model.dto.Personality;
import com.hx.model.dto.Rank;
import com.hx.model.dto.Regiment;
import com.hx.model.dto.Role;
import com.hx.model.dto.Scope;
import com.hx.model.dto.Sector;
import com.hx.model.dto.Upkeep;
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

	private static Regiment deathCommandos;

	private static Regiment newSyrtisLancers;

	// Here we go
	public static void main(String[] args) {
		config.setUp();
		insertFactions();
		insertSectors();
		insertUsers();
		insertPersonalities();
		insertCommunications();
		insertMessages();
		insertRegiments();
		insertCombats();
		config.tearDown();
	}

	
	private static void insertFactions() {

		comstar = createFaction(HXKeys.FACTION_COMSTAR, "Comstar", null);
		liao = createFaction(HXKeys.FACTION_LIAO, "House Liao", "Capellan Confederation");
		davion = createFaction(HXKeys.FACTION_DAVION, "House Davion", "Federated Suns");
		kurita = createFaction(HXKeys.FACTION_KURITA, "House Kurita", "Draconis Combine");
		steiner = createFaction(HXKeys.FACTION_STEINER, "House Steiner", "Lyran Commonwealth");
		marik = createFaction(HXKeys.FACTION_MARIK, "House Marik", "Free Worlds League");
		
		createFaction(HXKeys.FACTION_TAURUS, "Taurus Concordate", null);
		createFaction(HXKeys.FACTION_CANOPUS, "Canopus Magistry", null);
		createFaction(HXKeys.FACTION_CIRCINUS, "Circinus Federation", null);
		createFaction(HXKeys.FACTION_TORTUGA, "Tortuga Dominion", null);
		createFaction(HXKeys.FACTION_ILLYRIAN, "Illyrian Palatinate", null);
		createFaction(HXKeys.FACTION_MARIAN, "Marian Hegemony", null);
		createFaction(HXKeys.FACTION_OBERON, "Oberon Confederation", null);
		createFaction(HXKeys.FACTION_OUTERWORLDS, "Outer Worlds Alliance", null);
		createFaction(HXKeys.FACTION_LOTHARIO, "Lothario League", null);
		createFaction(HXKeys.FACTION_ELYSSIA, "Elyssia Fields", null);
		createFaction(HXKeys.FACTION_VALKYRIATE, "Great Valkyriate", null);
		
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
				
				// Special cases. Saved apart for use with the dummy data
				if (name.equals("Terra"))
					terra = sector;
				if (name.equals("Quentin"))
					quentin = sector;
				if (name.equals("Tikonov"))
					tikonov = sector;
			}
		}
		
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
		luisOllero.setFavoriteHouse(liao);
		luisOllero.setFavoriteRole(Role.PRINCE);
		config.getDaoUser().saveOrUpdate(luisOllero);
		
		tsunami  = new User();
		tsunami.setApplication("Test application");
		tsunami.setMail("tsunami@tsunami.com");
		tsunami.setName("Tsunami");
		tsunami.setFavoriteHouse(davion);
		tsunami.setFavoriteRole(Role.PRINCE);
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
	
	/**
	 * 
	 */
	private static void insertMessages() {
		Message message = new Message();
		message.setFrom(kineasLiao);
		message.setSubject("My beloved ally");
		message.setMessage("Burn in hell!");
		message.setSendingDate(new Date());
		
		Set<Personality> setTo = new HashSet<Personality>();
		setTo.add(vitorDavion);
		message.setTo(setTo);
		
		config.getDaoMessage().saveOrUpdate(message);
	}
	
	/**
	 * Initial regiments
	 */
	private static void insertRegiments() {
		deathCommandos = insertRegiment(5, AttackOrder.NORMAL, kineasLiao, tikonov, liao, Maneuver.MEDIUM, Morale.SUPERB, 
				"Death Commandos", 100, Rank.ELITE, 10, tikonov, 10, Upkeep.FULL, 2);
		newSyrtisLancers = insertRegiment(7, AttackOrder.NORMAL, vitorDavion, quentin, davion, Maneuver.HEAVY, Morale.SUPERB, 
				"New Syrtis Lancers", 100, Rank.REGULAR, 10, tikonov, 10, Upkeep.FULL, 2);
	}


	/**
	 * 
	 */
	private static void insertCombats() {
		
		ArrayList<Regiment> attackingRegiments = new ArrayList<Regiment>();
		ArrayList<Regiment> defendingRegiments = new ArrayList<Regiment>();
		
		attackingRegiments.add(newSyrtisLancers);
		defendingRegiments.add(deathCommandos);
		
		Combat combat = new Combat();
		combat.setAttackerRegiments(attackingRegiments);
		combat.setDefenderRegiments(defendingRegiments);
		combat.setPending(true);
		combat.setSector(tikonov);
		
		config.getDaoCombat().saveOrUpdate(combat);
	}
	
	/**
	 * Insert a single regiment
	 * 
	 * @param attack
	 * @param order
	 * @param personality
	 * @param homeSector
	 * @param house
	 * @param maneuver
	 * @param morale
	 * @param name
	 * @param price
	 * @param rank
	 * @param resistance
	 * @param currentSector
	 * @param totalResistence
	 * @param upkeep
	 * @param upkeepCost
	 */
	private static Regiment insertRegiment(int attack, AttackOrder order,
			Personality personality, Sector homeSector, House house,
			Maneuver maneuver, Morale morale, String name, int price, Rank rank,
			int resistance, Sector currentSector, int totalResistence, Upkeep upkeep, int upkeepCost) {
		Regiment regiment = new Regiment();
		regiment.setAttack(attack);
		regiment.setAttackOrder(order);
		regiment.setColonel(personality);
		regiment.setHomeSector(homeSector);
		regiment.setHouse(house);
		regiment.setManeuver(maneuver);
		regiment.setMorale(morale);
		regiment.setName(name);
		regiment.setPrice(price);
		regiment.setRank(rank);
		regiment.setResistance(resistance);
		regiment.setSector(currentSector);
		regiment.setTotalResistence(totalResistence);
		regiment.setUpkeep(upkeep);
		regiment.setUpkeepCost(upkeepCost);
		
		config.getDaoRegiment().saveOrUpdate(regiment);
		
		return regiment;
	}
	
	
	/**
	 * 
	 * @param houseId
	 * @param houseName
	 * @param alternateName
	 * @return
	 */
	private static House createFaction(String houseId, String houseName, String alternateName) {
		House house = new House(houseId);
		house.setName(houseName);
		house.setAlternateName(alternateName);
		config.getDaoHouse().saveOrUpdate(house);
		return house;
	}
	
	/**
	 * Extracted from StackOverflow website:
	 * 
	 * I learned this trick from "Stupid Scanner tricks" article. The reason it
	 * works is because Scanner iterates over tokens in the stream, and in this
	 * case we separate tokens using "beginning of the input boundary" (\A) thus
	 * giving us only one token for the entire contents of the stream.
	 * 
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(java.io.InputStream is) {
	    try {
	        return new java.util.Scanner(is).useDelimiter("\\A").next();
	    } catch (java.util.NoSuchElementException e) {
	        return "";
	    }
	}

}
