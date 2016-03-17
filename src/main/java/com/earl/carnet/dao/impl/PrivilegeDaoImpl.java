package com.earl.carnet.dao.impl;

import org.springframework.stereotype.Repository;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.PrivilegeDao;
import com.earl.carnet.domain.sercurity.privilege.Privilege;

@Repository("privilegeDao")
public class PrivilegeDaoImpl extends BaseDaoImpl<Privilege> implements PrivilegeDao{

    public PrivilegeDaoImpl(){
        System.out.println("xixi");
    }
}