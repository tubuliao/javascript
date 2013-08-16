package com.isoftstone.tyw.service;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.LikeCount;
import com.isoftstone.tyw.entity.pub.LicenseUser;
import com.isoftstone.tyw.repository.info.LikeCountDao;
@Component
@Transactional(readOnly = true)
public class LikeCountService {
    @Autowired
    private LikeCountDao likeCountDao;
    
    /**
     * 新增喜欢
     * @param likeCount
     * @return
     */
    @Transactional(readOnly = false)
    public LikeCount saveLc(LikeCount likeCount){
        return likeCountDao.save(likeCount);
    }
    public BigInteger searchCount(String resourcesId){
        return likeCountDao.searchCount(resourcesId);
    }
    
    /**
     * 根据文章id和用户id查询当前文章是否被喜欢过
     * @param fileId
     * @param userId
     * @return
     */
    @Transactional(readOnly = false)
	public List<LikeCount> findExsitOneInfo(String fileId, String userId){
		return likeCountDao.findExsitOneInfo(fileId, userId);
	}
   
}
