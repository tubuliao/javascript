package com.isoftstone.tyw.repository.auths;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.auths.User;

public interface UserDao extends PagingAndSortingRepository<User, String>{
	
	User findByUsername(String username);
	
	User findByMail(String mail);
	
	User findByPhone(String phone);
	
	 @Modifying 
	 @Query("update User user set user.password = :password where user.id =:id") 
	 public int modifyPassword(@Param("password")String password,@Param("id")String id); 
	 
	 @Modifying 
	 @Query("update User user set user.status = :status where user.id =:id") 
	 public int modifyStatus(@Param("status")String status,@Param("id")String id); 
	 
	 @Modifying 
	 @Query(nativeQuery=true,value="update auths_user set grade=:grade where phone = :phone") 
	 public int modifyGrade(@Param("grade")int grade,@Param("phone")String phone); 
	 
	 
	 @Modifying 
	 @Query(nativeQuery=true,value="select * from auths_user where phone=:phone and id !=:userId") 
	 List<User> checkPhoneExist(@Param("phone")String phone,@Param("userId")String userId); 
	 
	 @Query(value="from User u where u.username = :username")
	 public User findUserByUsername(@Param("username")String username);
	 
	 @Query(nativeQuery=true, value="SELECT u.id FROM auths_user u WHERE u.username = :username AND u.user_type = '3' ")
	 public String findUserIdByUsername(@Param("username")String username);
	 
	 @Query(nativeQuery=true, value="SELECT u.* FROM auths_user u WHERE u.user_type = '3' ")
	 public List<User> findAgentNameList();
}
