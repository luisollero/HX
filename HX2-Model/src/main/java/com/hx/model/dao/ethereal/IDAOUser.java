package com.hx.model.dao.ethereal;


import java.util.ArrayList;

import com.hx.model.dao.IDAOCommon;
import com.hx.model.dto.Personality;
import com.hx.model.dto.Role;
import com.hx.model.dto.User;

public interface IDAOUser extends IDAOCommon<User> {

	ArrayList<User> findByPersonality(Personality filtroPersonalidad);
	
}
