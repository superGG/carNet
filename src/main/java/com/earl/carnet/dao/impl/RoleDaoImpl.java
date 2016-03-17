package com.earl.carnet.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.RoleDao;
import com.earl.carnet.dao.RolePrivilegeDao;
import com.earl.carnet.domain.sercurity.role.Role;
import com.earl.carnet.domain.sercurity.roleprivilege.RolePrivilege;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao{
   
	@Resource
	RolePrivilegeDao rolePrivilegeDao;
	
	/**
	 * 删除角色的时候，级联删除角色权限关系.
	 * @author 黄祥谦.
	 */
	@Override
	public void deleteCascade(Long roleId){
		this.delete(roleId);
		RolePrivilege rolePrivilege = new RolePrivilege();
		rolePrivilege.setRoleId(roleId);
		rolePrivilegeDao.deleteByQuery(rolePrivilege);
	}
}