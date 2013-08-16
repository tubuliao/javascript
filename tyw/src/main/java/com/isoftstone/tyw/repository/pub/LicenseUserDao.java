package com.isoftstone.tyw.repository.pub;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.pub.LicenseUser;

public interface LicenseUserDao extends PagingAndSortingRepository<LicenseUser, String>,JpaSpecificationExecutor<LicenseUser>{
	 @Query(nativeQuery=true,value="select * from pub_license_user where license_id=:licenseId order by id asc limit 0,1") 
	 LicenseUser findUserByLicenseId(@Param("licenseId")String licenseId); 
	 
	 @Query(nativeQuery=true,value="select * from pub_license_user where license_id=:licenseId order by id asc limit 1,100") 
	 List<LicenseUser> findUserByLicenseId1(@Param("licenseId")String licenseId);
	 
	 @Query(nativeQuery=true,value="select * from pub_license_user where id=:licenseUserId") 
	 List<LicenseUser> findUserByLicenseUserId(@Param("licenseUserId")String licenseUserId);
	 
	 @Query(nativeQuery=true,value="select * from pub_license_user where license_id=:licenseId and phone=:phone") 
	 List<LicenseUser> findUserByPhone(@Param("licenseId")String licenseId,@Param("phone")String phone);
	 
	 @Query(nativeQuery=true,value="select * from pub_license_user where license_id=:licenseId and user_name=:name and phone=:phone") 
	 List<LicenseUser> findUserByPhoneAndName(@Param("licenseId")String licenseId,@Param("name")String name,@Param("phone")String phone);
	 
	 List<LicenseUser> findByLicenseId(String licenseId);
	 
	 @Modifying 
	 @Query(nativeQuery=true,value="update pub_license_user set user_name=:name where license_id =:licenseId and phone = :phone") 
	 public int modifyLicenseUserName(@Param("name")String name,@Param("phone")String phone,@Param("licenseId")String licenseId); 
	 
	 @Query(nativeQuery=true,value="select * from auths_user where phone=:phone") 
	 List<User> findUserExistByPhone(@Param("phone")String phone);
	
	 
	 LicenseUser findById(@Param("licenseUserId")String licenseUserId);
}
