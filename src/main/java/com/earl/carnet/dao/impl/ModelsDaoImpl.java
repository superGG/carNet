package com.earl.carnet.dao.impl;


import com.earl.carnet.commons.dao.impl.BaseDaoImpl;
import com.earl.carnet.dao.ModelsDao;
import com.earl.carnet.domain.carnet.models.Models;
import org.springframework.stereotype.Repository;

@Repository("modelsDao")
public class ModelsDaoImpl extends BaseDaoImpl<Models> implements ModelsDao {
   

}