package com.isoftstone.tyw.repository.auths;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.auths.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, String>{
	
}
