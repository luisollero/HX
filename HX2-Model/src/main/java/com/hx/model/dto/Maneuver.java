package com.hx.model.dto;

public enum Maneuver {
	ASSAULT, HEAVY, MEDIUM, LIGHT, GUERRILLA;
	
	public int getManeuverFactor(Orography oro) {
		switch (oro) {
		case NONE:
			switch (this) {
				case ASSAULT:
					return 4;
				case HEAVY:
					return 3;
				case MEDIUM:
					return 2;
				case LIGHT:
					return 1;
				case GUERRILLA:
					return 0;
				default:
					break;
			}
			break;
		case LIGHT:
			switch (this) {
				case ASSAULT:
					return 3;
				case HEAVY:
					return 4;
				case MEDIUM:
					return 3;
				case LIGHT:
					return 2;
				case GUERRILLA:
					return 1;
				default:
					break;
			}
			break;
		case MEDIUM:
			switch (this) {
				case ASSAULT:
					return 1;
				case HEAVY:
					return 2;
				case MEDIUM:
					return 4;
				case LIGHT:
					return 3;
				case GUERRILLA:
					return 2;
				default:
					break;
		}
		break;
		case HEAVY:
			switch (this) {
				case ASSAULT:
					return 0;
				case HEAVY:
					return 1;
				case MEDIUM:
					return 2;
				case LIGHT:
					return 4;
				case GUERRILLA:
					return 5;
				default:
					break;
		}
			break;
		default:
			break;
		}
		return 0;
	}
}
