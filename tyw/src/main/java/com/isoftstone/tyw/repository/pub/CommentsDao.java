package com.isoftstone.tyw.repository.pub;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.pub.Comments;

public interface CommentsDao extends PagingAndSortingRepository<Comments, String>{
	
	/**
	 * 根据知识ID和用户ID查找对应的评论
	 * @param infoBaseId
	 * @param authsUserId
	 * @return
	 */
	Page<Comments> findByInfoBaseIdAndAuthsUserId(String infoBaseId,String authsUserId,Pageable pageable);
}
