package com.hx.model.dao.ethereal;

import java.util.Collection;

import com.hx.model.dao.IDAOCommon;
import com.hx.model.dto.Combat;

public interface IDAOCombat extends IDAOCommon<Combat> {
	
	Collection<Combat> findByPendingWithLazies(Boolean pending);

}
