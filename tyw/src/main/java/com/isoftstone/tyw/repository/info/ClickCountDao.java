
package com.isoftstone.tyw.repository.info;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.ClickCount;

public interface ClickCountDao extends PagingAndSortingRepository<ClickCount, String>{
    
    @Modifying  
    @Query(nativeQuery=true,value="update info_clickcount set clickcount=(1+clickcount) where id=:id")
    public int modifyclickCount(@Param("id")String id); 
    
}
