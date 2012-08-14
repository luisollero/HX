package com.hx.engine.implementation;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.hx.model.dao.ethereal.IDAOCombat;
import com.hx.model.dao.ethereal.IDAORegiment;
import com.hx.model.dao.ethereal.IDAOSector;
import com.hx.model.dto.AttackOrder;
import com.hx.model.dto.Combat;
import com.hx.model.dto.House;
import com.hx.model.dto.Regiment;
import com.hx.model.dto.Sector;

public class CombatEngine {

	private Random r = new Random();
	private Collection<Combat> combatList = null;
	private String currentCombatResult;

	private IDAORegiment daoRegiment = null;
	private IDAOSector daoSector;
	private IDAOCombat daoCombat;
	
	private static String RESULT_DRAW = "result_draw"; 
	private static String RESULT_ATTACKER_VICTORY = "result_attacker_victory"; 
	private static String RESULT_ATTACKER_TOTAL_VICTORY = "result_attacker_total_victory"; 
	private static String RESULT_DEFENDER_VICTORY = "result_defender_victory"; 
	private static String RESULT_DEFENDER_TOTAL_VICTORY = "result_defender_total_victory"; 

	/**
	 * Solve every pending combat
	 */
	public void solveCombats() {
		
		r.setSeed(System.currentTimeMillis());
		
		combatList = daoCombat.findByPendingWithLazies(true);
		
		if (combatList != null) {
			for (Combat combat : combatList) {
				solveCombat(combat);
				if (currentCombatResult.equals(RESULT_ATTACKER_VICTORY) || currentCombatResult.equals(RESULT_ATTACKER_TOTAL_VICTORY)) {
					launchConquestTrigger(combat.getSector(), combat.getAttackerRegiments().get(0).getHouse());
				}
				combat.setPending(false);
				daoCombat.saveOrUpdate(combat);
			}
		}
	}
	

	/**
	 * 
	 * @param combat
	 */
	private void solveCombat(Combat combat) {
		int fullAttackPower = 0;
		int fullDefensivePower = 0;
		
		for (Regiment regiment : combat.getAttackerRegiments()) {
			int partialAttackPower = 0;
			// Retreating regiments that are attacking doesn't add power
			if (!regiment.getAttackOrder().equals(AttackOrder.RETREAT)) {
				partialAttackPower = partialAttackPower + regiment.getAttack();
				partialAttackPower = partialAttackPower + regiment.getRank().getValue();
				partialAttackPower = partialAttackPower + regiment.getManeuver().getManeuverFactor(combat.getSector().getOrography());
				partialAttackPower = partialAttackPower + regiment.getUpkeep().getValue();
				partialAttackPower = partialAttackPower + regiment.getColonel().getAbility().getValue();
				partialAttackPower = partialAttackPower + regiment.getMorale().getValue();
				partialAttackPower = partialAttackPower + luck();
			}
			// Defensive regiments that are attacking only add half their power
			if (regiment.getAttackOrder().equals(AttackOrder.DEFENSIVE)) {
				partialAttackPower = partialAttackPower / 2;
			}
			fullAttackPower = fullAttackPower + partialAttackPower;
		}
		

		for (Regiment regiment : combat.getDefenderRegiments()) {
			int partialDefensePower = 0;
			partialDefensePower = partialDefensePower + regiment.getAttack();
			partialDefensePower = partialDefensePower + regiment.getRank().getValue();
			partialDefensePower = partialDefensePower + regiment.getManeuver().getManeuverFactor(combat.getSector().getOrography());
			partialDefensePower = partialDefensePower + regiment.getUpkeep().getValue();
			partialDefensePower = partialDefensePower + regiment.getColonel().getAbility().getValue();
			partialDefensePower = partialDefensePower + regiment.getMorale().getValue();
			partialDefensePower = partialDefensePower + luck();
			
			if (regiment.getAttackOrder().equals(AttackOrder.RETREAT)) {
				partialDefensePower = partialDefensePower / 2;
			}
			
			fullDefensivePower = fullDefensivePower + partialDefensePower; 
		}

		fullDefensivePower = fullDefensivePower + combat.getSector().getDefenseBonus(); 
		
		computeCasualtiesAndResult(combat, fullAttackPower, fullDefensivePower);
	}
	
	/**
	 * 
	 * @param combat
	 * @param fullAttackPower
	 * @param fullDefensePower
	 */
	private void computeCasualtiesAndResult(Combat combat, int fullAttackPower,
			int fullDefensePower) {
		int attackingDifference = fullAttackPower - fullDefensePower;
		
		if (Math.abs(attackingDifference) < 5) {
			// Draw
			currentCombatResult = RESULT_DRAW;
		} else if (Math.abs(attackingDifference) < 10) {
			// Victory
			if (fullAttackPower > fullDefensePower) {
				currentCombatResult = RESULT_ATTACKER_VICTORY;
			} else {
				currentCombatResult = RESULT_DEFENDER_VICTORY;
			}

		} else {
			// Outstanding victory
			if (fullAttackPower > fullDefensePower) {
				currentCombatResult = RESULT_ATTACKER_TOTAL_VICTORY;
			} else {
				currentCombatResult = RESULT_DEFENDER_TOTAL_VICTORY;
			}
			
		}

		computeCasualties(combat);
		
	}

	/**
	 * 
	 * @param combat
	 */
	private void computeCasualties(Combat combat) {
		List<Regiment> regiments = combat.getAttackerRegiments();
		
		for (Regiment attackingRegiment : regiments) {
			AttackOrder order = attackingRegiment.getAttackOrder();
			switch (order) {
			case RETREAT:
				if (currentCombatResult.equals(RESULT_DEFENDER_TOTAL_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 1);
				}
				break;
			case DEFENSIVE:
				if (currentCombatResult.equals(RESULT_DEFENDER_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 1);
				} else if (currentCombatResult.equals(RESULT_DEFENDER_TOTAL_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 2);
				}
				break;
			case NORMAL:
				if (currentCombatResult.equals(RESULT_DRAW)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 1);
				} else if (currentCombatResult.equals(RESULT_ATTACKER_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 1);
				} else if (currentCombatResult.equals(RESULT_DEFENDER_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 1);
				} else if (currentCombatResult.equals(RESULT_DEFENDER_TOTAL_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 2);
				}
				break;
			case OFFENSIVE:
				if (currentCombatResult.equals(RESULT_DRAW)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 2);
				} else if (currentCombatResult.equals(RESULT_ATTACKER_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 2);
				} else if (currentCombatResult.equals(RESULT_ATTACKER_TOTAL_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 1);
				} else if (currentCombatResult.equals(RESULT_DEFENDER_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 2);
				} else if (currentCombatResult.equals(RESULT_DEFENDER_TOTAL_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 3);
				}
				break;
			case KAMIKAZE:
				if (currentCombatResult.equals(RESULT_DRAW)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 3);
				} else if (currentCombatResult.equals(RESULT_ATTACKER_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 3);
				} else if (currentCombatResult.equals(RESULT_ATTACKER_TOTAL_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 2);
				} else if (currentCombatResult.equals(RESULT_DEFENDER_TOTAL_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 3);
				} else if (currentCombatResult.equals(RESULT_DEFENDER_TOTAL_VICTORY)) {
					attackingRegiment.setResistance(attackingRegiment.getResistance() - 4);
				}
				break;

			default:
				break;
			}
		}
	}
	

	/**
	 * Calculate the new status for the sector after a successful invasion
	 * 
	 * @param sector
	 * @param house
	 */
	private void launchConquestTrigger(Sector sector, House house) {
		// TODO: Some sectors have a depth higher than 1. For those, I need to implement a system for partial conquest.
		sector.setHouse(house);
		if (sector.getDefenseBonus() > 0)
			sector.setDefenseBonus(sector.getDefenseBonus() - 1);
		
		daoSector.saveOrUpdate(sector);
	}

	/**
	 * Generates a random int
	 * 
	 * @return value between (0-2)
	 */
	private int luck() {
		return r.nextInt(2);
	}


	public void setDaoRegiment(IDAORegiment daoRegiment) {
		this.daoRegiment = daoRegiment;
	}


	public void setDaoSector(IDAOSector daoSector) {
		this.daoSector = daoSector;
	}


	public IDAORegiment getDaoRegiment() {
		return daoRegiment;
	}


	public IDAOSector getDaoSector() {
		return daoSector;
	}


	public IDAOCombat getDaoCombat() {
		return daoCombat;
	}


	public void setDaoCombat(IDAOCombat daoCombat) {
		this.daoCombat = daoCombat;
	}
	
}
