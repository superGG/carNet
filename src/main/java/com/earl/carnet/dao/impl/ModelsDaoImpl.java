package com.earl.carnet.dao.impl;

import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.ModelsDao;
import com.earl.carnet.dao.UserDao;
import com.earl.carnet.dao.UserRoleDao;
import com.earl.carnet.domain.sercurity.models.Models;
import com.earl.carnet.domain.sercurity.role.Role;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.domain.sercurity.userrole.UserRole;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository("modelsDao")
public class ModelsDaoImpl extends BaseDaoImpl<Models> implements ModelsDao{
   

}