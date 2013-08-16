package com.isoftstone.tyw.repository.pub;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.pub.LicensePayment;

public interface LicensePaymentDao extends PagingAndSortingRepository<LicensePayment, String>, JpaSpecificationExecutor<LicensePayment>{

	public LicensePayment findLicensePaymentByvOid(String vOid);
	
	
}
