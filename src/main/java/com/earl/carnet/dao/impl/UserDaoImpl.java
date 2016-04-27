package com.earl.carnet.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.UserDao;
import com.earl.carnet.dao.UserRoleDao;
import com.earl.carnet.domain.sercurity.role.Role;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.domain.sercurity.user.UserQuery;
import com.earl.carnet.domain.sercurity.userrole.UserRole;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{
   
	@Resource
	private UserRoleDao userRoleDao;
	
	@Override
	//根据用户名查询用户.
	public User findOneByUsername(String username) {
		// TODO 未测试.
		User user = new User();
		user.setUsername(username);
				List<User> unique = sqlManager.select("user.selectWithName",User.class, user);
				if(!unique.isEmpty()){
					return unique.get(0);
				}
				return null;
	}

	@Override
	//根据登录id查询用户.
	public User findOneByLoginId(String loginid) {
		// TODO 未测试.
		UserQuery user = new UserQuery();
		user.setLoginid(loginid);
		List<User> unique = searchQuery(user);
		if(!unique.isEmpty()){
			return unique.get(0);
		}
		return null;
	}

	@Override
	public Boolean changePassword(String newPassword, Object id) {
		// TODO 未测试.
		Boolean result = false;
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("passwd", newPassword);
		hashMap.put("id", id);
		if (sqlManager.update("user.updatePasswd", hashMap) !=0) result = true;
		return result;
	}
	
	/**
	 * 注意：当只有一个参数的时候，默认名字叫_root，这是框架封装好的
	 * select r.* from user u 
	 * left outer join userrole ur on u.id=ur.userId 
	 * left outer join role r on ur.roleId=r.id where u.Id = #_root#
	 */
	@Override
	public List<Role> findRole(Long userId){
		
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("userId", userId);
		return sqlManager.select("user.findRole", Role.class, userId);
	}
	
	/**
	 * 删除用户的时候，级联删除用户角色关系.
	 * @author 黄祥谦.
	 */
	@Override
	public void deleteCascade(Long userId){
		this.delete(userId);
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRoleDao.deleteByQuery(userRole);
	}

	@Override
	public List<String> findPrivilegeCode(Long userId) {
		// TODO 未测试.
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("userId", userId);
		return sqlManager.select("user.findPrivilegeCode", String.class, userId);
	}

	@Override
	public List<String> findRoleName(Long userId) {
		// TODO 未测试.
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("userId", userId);
		return sqlManager.select("user.findRoleName", String.class, userId);
	}

}