package com.earl.carnet.dao;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.domain.sercurity.role.Role;

/**
 * @author 黄祥谦.
 * @date:2016-1-13 下午9:12:42
 * @version :
 */
public interface RoleDao extends BaseDao<Role>{

	void deleteCascade(Long roleId);

}
