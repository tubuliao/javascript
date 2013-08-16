package com.isoftstone.tyw.repository.pub;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.pub.Receipt;

public interface ReceiptDao extends PagingAndSortingRepository<Receipt, String>{

	public Receipt findBylicenseNumber(String licenseNumber);
	
	@Modifying
	@Query(nativeQuery=true, value="UPDATE pub_license_receipt SET status = :status WHERE id = :id")
	public void updateStatus(@Param("id")String id, @Param("status")String status);
	 
}
