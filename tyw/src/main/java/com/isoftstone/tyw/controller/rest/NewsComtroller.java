package com.isoftstone.tyw.controller.rest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.isoftstone.tyw.dto.info.NewsDTO;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.BaseModule;
import com.isoftstone.tyw.entity.info.News;
import com.isoftstone.tyw.service.NewsService;
import com.isoftstone.tyw.util.ApiPager;
import com.isoftstone.tyw.util.JsonDateFormat;
import com.isoftstone.tyw.util.RefectEntity;

/**
 * @author liuyulong
 */
@Controller
public class NewsComtroller {
    @Autowired
    private NewsService newsService;

    /**
     * 最新动态和最新资讯
     * 
     * @param pageable
     * @param type
     * @return
     */
    @RequestMapping(value = "/api/information", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject searchByid(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "type", required = false) Integer type) {
        JSONObject jb = null;
        JsonConfig jsonConfig = new JsonConfig();
        List<News> list=null;
        BigInteger count=null;
        try {
            // 1 最新动态，2最新更新
            type = null == type ? 1 : type;
            String id = "";
            if (type == 1) {
                id = "2c9f850e3e63bd8d013e6888e84d04c7";
                list = newsService.searchByid(id, pageable.getPageNumber() * pageable.getPageSize(), pageable.getPageSize());
                count = newsService.searchCountByid(id);
            }
            if (type == 2) {
                list = newsService.latestChange(0, 8);
                count = newsService.searchCountByid(id);
             }
          //  List<News> list = newsService.searchByid(id, pageable.getPageNumber() * pageable.getPageSize(), pageable.getPageSize());
          //  BigInteger count = newsService.searchCountByid(id);
            jsonConfig.registerJsonValueProcessor(Date.class, (JsonValueProcessor) new JsonDateFormat());
            ApiPager apiPager = new ApiPager(pageable.getPageSize(), pageable.getPageNumber(), Integer.valueOf(count.toString()), list);
            jb = JSONObject.fromObject(apiPager, jsonConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jb;
    }

    /**
     * 新闻详情，暂时废弃
     * @param pageable
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/information/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public News searchNewsDetail(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "id") String id) {
        News news = newsService.searchNewsDetail(id);
        return news;
    }
}
