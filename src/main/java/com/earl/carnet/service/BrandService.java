package com.earl.carnet.service;

import com.earl.carnet.commons.service.BaseService;
import com.earl.carnet.domain.carnet.brand.Brand;
import com.earl.carnet.domain.sercurity.role.Role;
import com.earl.carnet.domain.sercurity.user.User;
import com.earl.carnet.domain.sercurity.user.UserQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BrandService extends BaseService<Brand, Brand> {
	
}