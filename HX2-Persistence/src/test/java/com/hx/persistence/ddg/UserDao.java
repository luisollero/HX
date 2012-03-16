package com.hx.persistence.ddg;

import java.util.Date;
import java.util.List;

public interface UserDao extends CommonDao<User> {

	User getById(long id);

	User notNullGetById(long id);

	User getByName(String name);

	User notNullGetByName(String name);

	List<User> findByName(String name);

	User getByBirthday(Date birthday);

	User notNullGetByBirthday(Date birthday);

	List<User> findByBirthday(Date birthday);
}
