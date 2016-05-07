package com.earl.carnet.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.commons.util.FileUploadImpl;
import com.earl.carnet.dao.UserDao;
import com.earl.carnet.domain.carnet.VerifyCode.VerifyCode;
import com.earl.carnet.domain.sercurity.role.Role;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.domain.sercurity.user.UserQuery;
import com.earl.carnet.exception.DomainSecurityException;
import com.earl.carnet.service.UserService;
import com.earl.carnet.service.VerifyCodeService;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserQuery>
        implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

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
        // TODO 未测试.
        logger.info("进入修改密码changePassword方法");
        Boolean result = false;
        User user = new User();
        user.setId(Id);
        String oldPassword_Md5 = new SimpleHash("SHA-1",oldPassword).toString();
        String password = userDao.searchQuery(user).get(0).getPassword();
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
            if (!user.getUserImg().equals("img/userImg.jpg")) {
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
//        try {
//
//            String uuid = IdGen.uuid();
//            System.out.println(userfile.getName());
//            userfile.transferTo(new File("d:/aaa.jpg"));
//        } catch (IllegalStateException | IOException e) {
//            e.printStackTrace();
//        }
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
        String oldPassword = user.getPassword();
        String newPassword = new SimpleHash("SHA-1", oldPassword).toString();
//        String newPassword = MD5Util.md5(oldPassword);
        user.setPassword(newPassword);
        int save = userDao.insert(user);
        if (save != 0) result = true;
        logger.info("退出saveUser方法");
        return result;
    }

    @Override
    public User findOneByLoginId(String loginid) {
        // TODO 未测试.
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
        if(tmpuser != null){
            throw new DomainSecurityException("该账号已经被注册");
        }else{
            String password_Md5 = new SimpleHash("SHA-1",password).toString();
            user.setPassword(password_Md5);
            user.setUserImg("/img/userImg.jpg");
            userDao.insert(user);
        }
        logger.info("退出用户注册registerAccount方法");
    }

    @Override
    public void changeRelatedPhone(String id, String newPhone, String verifyCode) {
        logger.info("进入service层changeRelatedPhone方法");
        VerifyCode verifyCode_data_search = new VerifyCode(newPhone);
        List<VerifyCode> verifyCode_data = verifyCodeService.searchQuery(verifyCode_data_search);
        if (verifyCode_data.size() != 0) {
            if (verifyCode.equals(verifyCode_data.get(0).getVerifyCode())) {
                User user = new User();
                user.setId(Long.parseLong(id));
                user.setRelatedPhone(newPhone);
                getDao().updateByPrimaryKeySelective(user);
            } else {
                throw new SecurityException("验证码错误");
            }
        } else {
            throw new SecurityException("请重新获取验证码");
        }
    }

    @Override
    public void addRelatedPhone(String id, String relatedPhone, String verifyCode) {
        VerifyCode verifyCode_data_search = new VerifyCode(relatedPhone);
        List<VerifyCode> verifyCode_data = verifyCodeService.searchQuery(verifyCode_data_search);
        if (verifyCode_data.size() != 0) {
            if (verifyCode.equals(verifyCode_data.get(0).getVerifyCode())) {
                User user = new User();
                user.setId(Long.parseLong(id));
                user.setRelatedPhone(relatedPhone);
                getDao().updateByPrimaryKeySelective(user);
            } else {
                throw new SecurityException("验证码错误");
            }
        } else {
            throw new SecurityException("请重新获取验证码");
        }
    }

}
