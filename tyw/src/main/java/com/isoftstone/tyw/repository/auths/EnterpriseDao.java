package com.isoftstone.tyw.repository.auths;

import com.isoftstone.tyw.entity.auths.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EnterpriseDao extends PagingAndSortingRepository<User, String>{
	
}
