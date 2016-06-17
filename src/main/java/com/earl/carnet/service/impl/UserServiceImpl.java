package com.earl.carnet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.earl.carnet.domain.carnet.car.Car;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.CodecException;
import org.apache.shiro.crypto.UnknownAlgorithmException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.commons.util.EhCacheHelper;
import com.earl.carnet.commons.util.FileUploadImpl;
import com.earl.carnet.dao.UserDao;
import com.earl.carnet.domain.sercurity.role.Role;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.domain.sercurity.user.UserQuery;
import com.earl.carnet.exception.ApplicationException;
import com.earl.carnet.exception.DomainSecurityException;
import com.earl.carnet.service.UserService;
import com.earl.carnet.service.VerifyCodeService;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, User>
        implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    private static Cache VerifyCode_CACHE = EhCacheHelper.getCacheManage().getCache("verifyCode");

    @Resource
    private UserDao userDao;

    @Resource
    private VerifyCodeService verifyCodeService;

    @Resource(name = "fileUpload")
    FileUploadImpl fileUpload;

    @Override
    protected BaseDao<User> getDao() {
        // TODO 未测试.
        return userDao;
    }

    @Override
    public User findOneByUsername(String username) {
        // TODO 未测试.
        User user = userDao.findOneByUsername(username);
        return user;
    }

    @Override
    public Boolean changePassword(Long Id, String oldPassword, String newPassword) {
        logger.info("进入修改密码changePassword方法");
        Boolean result = false;
        User user = getDao().findOneById(Id);
        String oldPassword_Md5 = new SimpleHash("SHA-1", oldPassword).toString();
        String password = user.getPassword();
        if (password.equals(oldPassword_Md5)) {
            String newPassword_Md5 = new SimpleHash("SHA-1", newPassword).toString();
            user.setPassword(newPassword_Md5);
            userDao.updateByPrimaryKeySelective(user);
            result = true;
        } else {
            throw new DomainSecurityException("旧密码错误");
        }
        logger.info("退出修改密码changePassword方法");
        return result;
    }


    @Override
    public List<Role> findRole(Long userId) {
        // TODO 未测试.
        return userDao.findRole(userId);
    }

    @Override
    public void deleteCascade(Long userId) {
        // TODO 未测试.
        userDao.deleteCascade(userId);
    }

    @Override
    public Boolean updateImg(MultipartFile userfile, Long id) {
        try {
            if (userfile == null || userfile.isEmpty()) {
                throw new IllegalStateException("没有图片上传上来");
            }
            String uploadUserFile = fileUpload.uploadUserFile(userfile);
            System.out.println(uploadUserFile);

            User user = userDao.findOneById(id);
            if (!user.getUserImg().equals("img/earl.jpg")) {
                Boolean result = fileUpload.deleFile(user.getUserImg()); // 删除原来的头像图片
                if (result) { // 删除成功
                    user.setUserImg(uploadUserFile);
                    userDao.updateByPrimaryKeySelective(user);
                }
            } else {
                user.setUserImg(uploadUserFile);
                userDao.updateByPrimaryKeySelective(user);
            }
            return true;
        } catch (Exception e) {
            System.out.println("更新用户头像失败");
            e.printStackTrace();
            return false;
        }
    }

    //TODO 用户权限发生改变的时候，后台自动重新登录系统
    @Override
    public void doLogin(String username, String password) {
        // TODO 未测试.
        logger.info("进入doLogin方法");
        String password_Md5 = new SimpleHash("SHA-1", password).toString();
        logger.info("password_Md5:" + password_Md5);
        SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password_Md5));
        logger.info("退出doLogin方法");
    }

    @Override
    public List<String> findRoleName(Long userId) {
        // TODO 未测试.
        return userDao.findRoleName(userId);
    }

    @Override
    public List<String> findPrivilegeCode(long l) {
        // TODO 未测试.
        return userDao.findPrivilegeCode(l);
    }

    public Boolean saveUser(User user) {
        logger.info("进入saveUser方法");
        Boolean result = false;

        logger.info("对用户密码进行加密");
        String oldPassword = user.getPassword();
        String newPassword = new SimpleHash("SHA-1", oldPassword).toString();
        user.setPassword(newPassword);

        int userId = userDao.insertBackId(user);
        if (userId != 0) {
            //设置默认值
            user.setId((long) userId);
            user.setUsername("用户" + userId);
            user.setUserImg("img/earl.jpg");
            int save = updateByPrimaryKeySelective(user);
            if (save != 0) {
                result = true;
            }
        }
        logger.info("退出saveUser方法");
        return result;
    }

    @Override
    public User findOneByLoginId(String loginid) {
        UserQuery userQuery = new UserQuery();
        userQuery.setLoginid(loginid);
        List<User> user = userDao.searchQuery(userQuery);
        if (user.isEmpty()) {
            return null;
        }
        return user.get(0);
    }

    @Override
    public void registerAccount(String loginid, String password) {
        logger.info("进入用户注册registerAccount方法");
        User user = new User();
        user.setLoginid(loginid);

        User tmpuser = userDao.findOneByLoginId(loginid);
        if (tmpuser != null) {
            throw new DomainSecurityException("该账号已经被注册");
        } else {
            try {
                String password_Md5 = new SimpleHash("SHA-1", password).toString();
                user.setPassword(password_Md5);
                user.setUserImg("/img/earl.jpg");
                user.setAlarmMessage(true);
                user.setPropertyMessage(true);
                user.setStateMessage(true);
                userDao.insert(user);
            } catch (Exception e) {
                e.printStackTrace();
                throw new DomainSecurityException("注册失败");
            }
        }
        logger.info("退出用户注册registerAccount方法");
    }

    @Override
    public void changeRelatedPhone(String id, String newPhone, String verifyCode) {
        logger.info("进入service层changeRelatedPhone方法");
        if (confirmVerifyCode(newPhone, verifyCode)) {
            User user = new User();
            user.setId(Long.parseLong(id));
            user.setRelatedPhone(newPhone);
            getDao().updateByPrimaryKeySelective(user);
        } else {
            throw new SecurityException("验证码错误");
        }
    }

    @Override
    public void confirmSafePassword(Long id, String safePassword) {
        User user = getDao().findOneById(id);
        String safePassword_Md5 = new SimpleHash("SHA-1", safePassword).toString();
        logger.info(safePassword_Md5);
        if(!safePassword_Md5.equals(user.getSafePassword())){
        	throw new ApplicationException("安全密码验证错误");
        }		
    }

    @Override
    public Boolean changeSafePassword(Long id, String oldSafePassword, String newSafePassword) {
        logger.info("进入修改安全密码changeSafePassword方法");
        Boolean result = false;
        User user = getDao().findOneById(id);
        if (user.getSafePassword() != null) {
            String oldPassword_Md5 = new SimpleHash("SHA-1", oldSafePassword).toString();
            String password = user.getSafePassword();
            if (password.equals(oldPassword_Md5)) {
                String newPassword_Md5 = new SimpleHash("SHA-1", newSafePassword).toString();
                user.setSafePassword(newPassword_Md5);
                userDao.updateByPrimaryKeySelective(user);
                result = true;
            } else {
                throw new DomainSecurityException("旧安全密码错误");
            }
        } else {  //当初次更新安全密码时
            String newPassword_Md5 = new SimpleHash("SHA-1", newSafePassword).toString();
            user.setSafePassword(newPassword_Md5);
            userDao.updateByPrimaryKeySelective(user);
            result = true;
        }
        logger.info("退出修改安全密码changeSafePassword方法");
        return result;
    }

    @Override
    public Car getCurrentCar(String loginid) {
        User user = userDao.findOneByLoginId(loginid);
        return userDao.getCurrentCar(user.getId());
    }


//    @Override
//    public void addRelatedPhone(String id, String relatedPhone, String verifyCode) {
//        if (confirmVerifyCode(relatedPhone, verifyCode)) {
//            User user_new = new User();
//            user_new.setId(Long.parseLong(id));
//            user_new.setRelatedPhone(relatedPhone);
//            getDao().updateByPrimaryKeySelective(user_new);
//        } else {
//            throw new SecurityException("验证码错误");
//        }
//    }

    /**
     * 确认用户输入认证码是否正确.
     *
     * @param relatedPhone 用户手机
     * @param verifyCode   用户输入的验证码
     * @return
     */
    private Boolean confirmVerifyCode(String relatedPhone, String verifyCode) {
        Element element = VerifyCode_CACHE.get(relatedPhone);
        if (element != null) {
            return verifyCode.equals(element.getObjectValue());
        }
        return false;
    }

}
