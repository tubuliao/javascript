package com.isoftstone.tyw.repository.pub;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.pub.License;

public interface LicenseDao extends PagingAndSortingRepository<License, String>, JpaSpecificationExecutor<License>{

	List<License> findByLicenseNum(String licenseNum);
	
	License findById(String licenseId);
	
	 @Modifying 
	 @Query(nativeQuery=true,value="update pub_license set user_id = :userId where id =:licenseId") 
	 public int modifyUserId(@Param("userId")String userId,@Param("licenseId")String licenseId); 
	 
	 @Modifying 
	 @Query(nativeQuery=true,value="update pub_license set license_status = :licenseStatus where id =:licenseId") 
	 public int modifyLicenseStatus(@Param("licenseStatus")String licenseStatus,@Param("licenseId")String licenseId); 
	 
	 @Modifying 
	 @Query(nativeQuery=true,value="update auths_user set grade = :grade where id =:userId") 
	 public int modifyUserGrade(@Param("grade")int grade,@Param("userId")String userId); 
	 
	 @Modifying 
	 @Query(nativeQuery=true,value="update pub_license set overplus_count = :overPlusCount where id =:licenseId") 
	 public int modifyOverPlusCount(@Param("overPlusCount")int overPlusCount,@Param("licenseId")String licenseId);
}
