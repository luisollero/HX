package com.hx.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hx.model.dto.Combat;
import com.hx.model.dto.Regiment;

public class CombatEngine {

	private Random r = new Random();
	private List<Combat> combatList = new ArrayList<Combat>();
	private IRegimentEngine regimentEngine = null;

	public void solveCombats() {
		
		r.setSeed(System.currentTimeMillis());
		
		//TODO: Retrieve all pending combats from the database
		
		for (Combat combat : combatList) {
			solveCombat(combat);
		}
	}

	private void solveCombat(Combat combat) {
		int fullAttackPower = 0;
		int fullDefensePower = 0;
		
		for (Regiment regiment : combat.getAttackerRegiments()) {
			fullAttackPower = fullAttackPower + regiment.getAttack();
			fullAttackPower = fullAttackPower + regiment.getRank().getValue();
			fullAttackPower = fullAttackPower + regiment.getManeuver().getManeuverFactor(combat.getSector().getOrography());
			fullAttackPower = fullAttackPower + regiment.getUpkeep().getValue();
			fullAttackPower = fullAttackPower + regiment.getColonel().getAbility().getValue();
			fullAttackPower = fullAttackPower + regiment.getMorale().getValue();
			fullAttackPower = fullAttackPower + luck();
		}
		
		

		for (Regiment regiment : combat.getDefenderRegiments()) {
			fullDefensePower = fullDefensePower + regiment.getAttack();
			fullDefensePower = fullDefensePower + regiment.getRank().getValue();
			fullDefensePower = fullDefensePower + regiment.getManeuver().getManeuverFactor(combat.getSector().getOrography());
			fullDefensePower = fullDefensePower + regiment.getUpkeep().getValue();
			fullDefensePower = fullDefensePower + regiment.getColonel().getAbility().getValue();
			fullDefensePower = fullDefensePower + regiment.getMorale().getValue();
			fullDefensePower = fullDefensePower + luck();
		}
		
		
	}
	
	private int luck() {
		return r.nextInt(2);
	}

	public void setRegimentEngine(IRegimentEngine regimentEngine) {
		this.regimentEngine = regimentEngine;
	}
	
}
