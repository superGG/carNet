package com.earl.carnet.dao.impl;

import org.springframework.stereotype.Repository;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.UserRoleDao;
import com.earl.carnet.domain.sercurity.userrole.UserRole;

@Repository("userRoleDao")
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements UserRoleDao{
   
	
}