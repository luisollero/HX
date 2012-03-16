package com.hx.model.util;


import java.util.ArrayList;

import com.hx.model.dto.AttackOrder;
import com.hx.model.dto.Role;
import com.hx.model.dto.UserStatus;

/**
 * Carga las colecciones.
 * @author Luis Ollero
 *
 */
public class HXCollections {

	public static final ArrayList<Role> ROLE_LIST = loadRoleList();
	public static final ArrayList<UserStatus> USER_STATUS_LIST = loadUserStatusList();
	public static final ArrayList<AttackOrder> ATTACK_ORDER_LIST = loadAttackOrderList();
	
	private static ArrayList<Role> loadRoleList() {
		ArrayList<Role> roleList = null;
		if ( ROLE_LIST == null || ROLE_LIST.size() == 0 ) {
			roleList = new ArrayList<Role>();
			for (int i = 0; i < Role.values().length; i++) {
				roleList.add(Role.values()[i]);
			}
		}
		return roleList;
	}
	
	private static ArrayList<AttackOrder> loadAttackOrderList() {
		
		ArrayList<AttackOrder> attackOrderList = new ArrayList<AttackOrder>();
		
		for (int i = 0; i < AttackOrder.values().length; i++) {
			attackOrderList.add(AttackOrder.values()[i]);
		}
		
		return attackOrderList;
	}
	
	private static ArrayList<UserStatus> loadUserStatusList() {
		
		ArrayList<UserStatus> userStatusList = new ArrayList<UserStatus>();
		
		for (int i = 0; i < UserStatus.values().length; i++) {
			userStatusList.add(UserStatus.values()[i]);
		}
		
		return userStatusList;
	}
	
}
