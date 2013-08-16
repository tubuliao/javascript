
package com.isoftstone.tyw.repository.info;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.KeyWord;

public interface KeyWordDao extends PagingAndSortingRepository<KeyWord, String>, JpaSpecificationExecutor<KeyWord>{
	
    @Query(nativeQuery=true,value="select * from info_keyword where istag=:istag order by create_date limit 0,50")
    List<KeyWord> findkeyword(@Param("istag")int istag);
     
}
