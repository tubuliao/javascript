package com.isoftstone.tyw.controller.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.isoftstone.tyw.dto.info.BookmarkDTO;
import com.isoftstone.tyw.dto.info.NewsDTO;
import com.isoftstone.tyw.dto.info.SiteRecordDTO;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Bookmark;
import com.isoftstone.tyw.entity.info.Classification;
import com.isoftstone.tyw.entity.info.News;
import com.isoftstone.tyw.entity.info.SiteRecord;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.NewsService;
import com.isoftstone.tyw.service.SiteRecordService;
import com.isoftstone.tyw.util.MapPoint;
import com.isoftstone.tyw.util.RefectEntity;

/**
 * @author liuyulong
 */
@Controller
public class SiteRecordComtroller {
    @Autowired
    private SiteRecordService siteRecordService;

    @Autowired
    private AccountService accountService;

    /**
     * 现场记录
     * 
     * @param pageable
     * @param url 视频或者图片的url
     * @param content 内容
     * @param id 记录id 如果有则传，没有则不传
     * @param latitude 维度
     * @param longitude 精度
     * @param type 类型1图片，2视频
     * @return
     */
    @RequestMapping(value = "/api/record", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String information(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "url") String url,
            @RequestParam(value = "content", required = false) String content, @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "latitude") String latitude, @RequestParam(value = "longitude") String longitude,
            @RequestParam(value = "type") Integer type) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = accountService.loadUserByUsername(userDetails.getUsername());
        String result = "";
        try {
            SiteRecord sr = new SiteRecord();
            sr.setId(id);
            sr.setContent(content);
            sr.setLatitude(latitude);
            sr.setLongitude(longitude);
            sr.setType(type);
            sr.setUid(user.getId());
            sr.setUrl(url);
            sr.setLocation(MapPoint.getLocation(latitude, longitude));
            SiteRecord sre = siteRecordService.save(sr);
            result = sre.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{id:" + result + "}";
    }

    /**
     * 删除现场记录
     * 
     * @param pageable
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/record", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String delSiteRecord(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "id") String id) {
        int result = 0;
        try {
            siteRecordService.delSiteRecord(id);
            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{result:" + result + "}";
    }

    /**
     * 获得现场记录
     * 
     * @param pageable
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/record", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<SiteRecordDTO> recordList(@PageableDefaults(value = 30) Pageable pageable, HttpServletRequest request) {
        Page<SiteRecordDTO> result = null;
        List<SiteRecordDTO> ncontent = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = accountService.loadUserByUsername(userDetails.getUsername());
        try {
            Page<SiteRecord> list = null;
            list = siteRecordService.findSiteRecord(user.getId(), pageable);
            List<SiteRecord> content = list.getContent();
            ncontent = Lists.newArrayList();
            for (SiteRecord siteRecord : content) {
                SiteRecordDTO srd = new SiteRecordDTO();
                RefectEntity.copy(srd, siteRecord);
                srd.setId(siteRecord.getId());
                ncontent.add(srd);
            }
            result = new PageImpl<SiteRecordDTO>(ncontent, new PageRequest(list.getNumber(), list.getSize()), list.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得某一个现场记录
     * 
     * @param pageable
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/record/one", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SiteRecordDTO getfavorites(@PageableDefaults(value = 30) Pageable pageable, HttpServletRequest request,
            @RequestParam(value = "id") String id) {
        SiteRecordDTO srd = new SiteRecordDTO();
        try {
            SiteRecord siteRecord = siteRecordService.findById(id);
            RefectEntity.copy(srd, siteRecord);
            srd.setId(siteRecord.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return srd;
    }
}
