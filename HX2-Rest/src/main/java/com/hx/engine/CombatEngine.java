package com.hx.engine;

import java.util.ArrayList;
import java.util.List;

import com.hx.model.dto.Combat;

public class CombatEngine {

	private List<Combat> combatList = new ArrayList<Combat>();
	private IRegimentEngine regimentEngine = null;

	public void solveCombats() {

		//TODO: Retrieve all pending combats from the database
		
		for (Combat combat : combatList) {
			solveCombat(combat);
		}
	}

	private void solveCombat(Combat combat) {

	}

	public void setRegimentEngine(IRegimentEngine regimentEngine) {
		this.regimentEngine = regimentEngine;
	}
	
}
