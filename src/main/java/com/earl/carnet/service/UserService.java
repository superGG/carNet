package com.earl.carnet.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.sercurity.role.Role;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.domain.sercurity.user.UserQuery;

public interface UserService extends BaseService<User, UserQuery> {
	
	User findOneByUsername(String username);

	/**
	 * 修改用户密码.
	 * @param Id
	 * @param oldPassord
	 * @param newPassword
     * @return
     */
	Boolean changePassword(Long Id,String oldPassord, String newPassword);

	/**
	 * 查找用户拥有的角色
	 * @author 黄祥谦.
	 * @param l
	 * @return 
	 */
	List<Role> findRole(Long userId);

	void deleteCascade(Long userId);

	/**
	 * 更新头像
	 * @author 宋.
	 * @param userfile
	 */
	Boolean updateImg(MultipartFile userfile, Long Id);

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
	Boolean saveUser(User user);

	User findOneByLoginId(String loginid);

	/**
	 * 注册用户.
	 * @param loginid
	 * @param password
     */
	void registerAccount(String loginid, String password);

	/**
	 * 修改亲人号码.
	 * @param s
	 * @param id
	 * @param newPhone
	 */
	void changeRelatedPhone(String verifyCode, String id, String newPhone);

	/**
	 * 绑定亲人号码.
	 * @param id
	 * @param relatedPhone
	 * @param verifyCode
     */
	void addRelatedPhone(String id, String relatedPhone, String verifyCode);
}