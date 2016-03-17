package com.earl.carnet.dao;

import java.util.List;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.domain.sercurity.role.Role;
import com.earl.carnet.domain.sercurity.user.User;


public interface UserDao extends BaseDao<User>{

	User findOneByUsername(String username);

	/**
	 * 更新用户密码.
	 * @author 黄祥谦.
	 * @param newPassword
	 * @param Id TODO
	 */
	void changePassword(String newPassword, Object Id);

	List<Role> findRole(Long userId);

	/**
	 * 级联删除
	 * @author 黄祥谦.
	 * @param userId
	 */
	void deleteCascade(Long userId);

	/**
	 * 找到用户的权限
	 * @author 黄祥谦.
	 * @param id
	 * @return
	 */
	List<String> findPrivilegeCode(Long id);

	/**
	 * 找到用户的角色名称.
	 * @author 黄祥谦.
	 * @param id
	 * @return
	 */
	List<String> findRoleName(Long id);

}