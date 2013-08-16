package com.isoftstone.tyw.repository.pub;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.pub.PersonalTheme;

/**
 * 专题dao
 * @author zhaowenli
 */
public interface PersonalThemeDao extends PagingAndSortingRepository<PersonalTheme, String>, JpaSpecificationExecutor<PersonalTheme>{
	
	@Query(nativeQuery=true,value="select * from pub_personal_theme t where t.user_id=:userId order by t.create_date desc limit :startIndex,:pageSize") 
	 List<PersonalTheme> findPersonalThemeList(@Param("startIndex")int startIndex,@Param("pageSize")int pageSize,@Param("userId")String userId);
	
	 @Query(nativeQuery=true,value="select count(1) from pub_personal_theme t where t.user_id=:userId ") 
	 BigInteger findPersonalThemeListCount(@Param("userId")String userId);
	 
}
