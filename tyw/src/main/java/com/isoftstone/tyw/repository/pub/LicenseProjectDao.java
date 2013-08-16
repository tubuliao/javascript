package com.isoftstone.tyw.repository.pub;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.pub.LicenseProject;

public interface LicenseProjectDao extends PagingAndSortingRepository<LicenseProject, String>{
	LicenseProject findByLicenseId(String licenseId);
}
