package com.isoftstone.tyw.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.BaseModule;
import com.isoftstone.tyw.entity.info.News;
import com.isoftstone.tyw.repository.info.BaseDao;
import com.isoftstone.tyw.repository.info.NewsDao;

@Component
@Transactional(readOnly = true)
public class NewsService {
    @Autowired
    private NewsDao newsDao;

    @Autowired
    private BaseDao baseDao;

    public Page<News> searchNews(Integer type, Pageable pageable) {
        // return newsDao.findByTypeOrderByReleaseDateDesc(type,pageable);
        return null;
    }

    public List<News> searchByid(String id, Integer pageNum, Integer pageSize) {
        return newsDao.searchByid(id, pageNum, pageSize);
    }

    /**
     * 最新更新
     * 
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<News> latestChange(Integer pageNum, Integer pageSize) {
        return newsDao.LatestChange(pageNum, pageSize);
    }

    public BigInteger searchCountByid(String id) {
        return newsDao.searchCountByid(id);
    }

    public News searchNewsDetail(String id) {
        return newsDao.findById(id);
    }
}
