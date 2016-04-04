package com.earl.carnet.service.impl;

import com.earl.carnet.commons.dao.BaseDao;
import com.earl.carnet.commons.service.BaseServiceImpl;
import com.earl.carnet.commons.util.IdGen;
import com.earl.carnet.commons.util.MD5Util;
import com.earl.carnet.dao.UserDao;
import com.earl.carnet.domain.sercurity.role.Role;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.domain.sercurity.user.UserQuery;
import com.earl.carnet.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserQuery>
        implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Resource
    private UserDao userDao;

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
    public void changePassword(Object Id, String newPassword) {
        // TODO 未测试.
        userDao.changePassword(newPassword, Id);
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
    public void uploadFile(MultipartFile userfile) {
        // TODO 未测试.
        try {

            String uuid = IdGen.uuid();
            System.out.println(userfile.getName());
            userfile.transferTo(new File("d:/aaa.jpg"));
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //TODO 用户权限发生改变的时候，后台自动重新登录系统
    @Override
    public void doLogin(String username, String password) {
        // TODO 未测试.
        SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
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

    public void saveUser(User user) {
        logger.info("进入saveUser方法");
        String oldPassword = user.getPassword();
        String newPassword = MD5Util.md5(oldPassword);
        user.setPassword(newPassword);
        userDao.insert(user);
        logger.info("退出saveUser方法");
    }

}
