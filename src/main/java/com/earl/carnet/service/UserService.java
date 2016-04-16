package com.earl.carnet.service;

import java.security.MessageDigest;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.sercurity.role.Role;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.domain.sercurity.user.UserQuery;

public interface UserService extends BaseService<User, UserQuery> {
	
	User findOneByUsername(String username);
	
	void changePassword(Object Id, String newPassword);

	/**
	 * 查找用户拥有的角色
	 * @author 黄祥谦.
	 * @param l
	 * @return 
	 */
	List<Role> findRole(Long userId);

	void deleteCascade(Long userId);

	/**
	 * shangc
	 * @author 黄祥谦.
	 * @param userfile
	 */
	void uploadFile(MultipartFile userfile);

	/**
	 * 登录系统
	 * @author 黄祥谦.
	 * @param username
	 * @param password
	 */
	void doLogin(String username, String password);

	/**
	 * 找到名字
	 * @author 黄祥谦.
	 * @param l
	 * @return
	 */
	List<String> findRoleName(Long l);

	/**
	 * 查找权限
	 * @author 黄祥谦.
	 * @param l
	 * @return
	 */
	List<String> findPrivilegeCode(long l);

	/**
	 * 添加用户.
	 * @author song.
	 * @param user
     */
	void saveUser(User user);

	User findOneByLoginId(String loginid);

	void registerAccount(String loginid, String password);
}