package com.earl.carnet.dao.impl;

import org.springframework.stereotype.Repository;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.RolePrivilegeDao;
import com.earl.carnet.domain.sercurity.roleprivilege.RolePrivilege;

@Repository("rolePrivilegeDao")
public class RolePrivilegeDaoImpl extends BaseDaoImpl<RolePrivilege> implements RolePrivilegeDao{
   
}