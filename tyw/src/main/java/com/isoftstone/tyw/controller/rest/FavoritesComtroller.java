package com.isoftstone.tyw.controller.rest;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.isoftstone.tyw.dto.info.HotWordsDTO;
import com.isoftstone.tyw.dto.info.NewsDTO;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Bookmark;
import com.isoftstone.tyw.entity.info.Classification;
import com.isoftstone.tyw.entity.info.HotWords;
import com.isoftstone.tyw.entity.info.LikeCount;
import com.isoftstone.tyw.entity.info.News;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.BookmarkService;
import com.isoftstone.tyw.service.ClassificationService;
import com.isoftstone.tyw.service.LikeCountService;
import com.isoftstone.tyw.service.NewsService;
import com.isoftstone.tyw.util.DateManager;
import com.isoftstone.tyw.util.RefectEntity;

/**
 * @author liuyulong
 */
@Controller
public class FavoritesComtroller {
    @Autowired
    private NewsService newsService;

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LikeCountService likeCountService;

    @Autowired
    private ClassificationService classificationService;

    /**
     * 保存收藏文件
     * 
     * @param pageable
     * @param folder_id
     * @param title
     * @param url
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/favorites", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String savefavorites(@PageableDefaults(value = 30) Pageable pageable,
            @RequestParam(value = "folder_id", required = false) String folder_id, @RequestParam(value = "title") String title,
            @RequestParam(value = "url") String url, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = accountService.loadUserByUsername(userDetails.getUsername());
        Bookmark rebm = bookmarkService.findByUserIdUrl(user.getId(), url);
        Bookmark bm = new Bookmark();
        Classification cf = new Classification();
        int result = 0;
        try {
            bm.setTitle(title);
            bm.setUrl(url);
            bm.setCreateDate(new Date());
            bm.setUserId(user.getId());
            bm.setDataType(1);
            if (folder_id != null) {
                cf.setId(folder_id);
                bm.setClassification(cf);
            }
            if (null == rebm) {
                bookmarkService.saveBookMark(bm);
                result = 1;
            } else {
                result = 2;
            }

        } catch (Exception e) {
            result = 0;
            e.printStackTrace();
        }
        return "{result:" + result + "}";
    }

    /**
     * 删除收藏文章
     * 
     * @param pageable
     * @param id 文章id
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/favorites", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String delfavorites(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "id") String id, HttpServletRequest request) {
        int result = 0;
        try {
            bookmarkService.removeBookmark(id);
            result = 1;
        } catch (Exception e) {
            result = 0;
            e.printStackTrace();
        }
        return "{result:" + result + "}";
    }

    /**
     * 获取收藏文章列表
     * 
     * @param pageable
     * @param request
     * @param folderId 文件夹Id 可选
     * @return
     */
    @RequestMapping(value = "/api/favorites", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<BookmarkDTO> getfavorites(@PageableDefaults(value = 30) Pageable pageable, HttpServletRequest request,
            @RequestParam(value = "folderId", required = false) String folderId) {
        Page<BookmarkDTO> result = null;
        List<BookmarkDTO> ncontent = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Page<Bookmark> list = null;
            if (StringUtils.isNotEmpty(folderId)) {
                Classification cf = new Classification();
                cf.setId(folderId);
                list = bookmarkService.findByClassification(cf, pageable);
            } else {
                User user = accountService.loadUserByUsername(userDetails.getUsername());
                list = bookmarkService.findByUserId(user.getId(), pageable);
            }
            List<Bookmark> content = list.getContent();
            ncontent = Lists.newArrayList();
            for (Bookmark bookmark : content) {
                BookmarkDTO bkdto = new BookmarkDTO();
                RefectEntity.copy(bkdto, bookmark);
                ncontent.add(bkdto);
            }
            result = new PageImpl<BookmarkDTO>(ncontent, new PageRequest(list.getNumber(), list.getSize()), list.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 喜欢
     * 
     * @param pageable
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/likeCount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String likeCountSave(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "id") String id, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = accountService.loadUserByUsername(userDetails.getUsername());
        LikeCount likeCount = new LikeCount();
        int result = 0;
        try {
            likeCount.setResourcesId(id);
            likeCount.setUid(user.getId());
            likeCountService.saveLc(likeCount);
            result = 1;
        } catch (Exception e) {
            result = 0;
            e.printStackTrace();
        }
        return "{result:" + result + "}";
    }

    /**
     * 获得喜欢数量
     * 
     * @param pageable
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/likeCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String likeCount(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "id") String id, HttpServletRequest request) {
        long lc = 0L;
        try {
            lc = likeCountService.searchCount(id).longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{result:" + lc + "}";
    }

    /**
     * 删除收藏文件件，如果有收藏文章禁止删除
     * 
     * @param pageable
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/favorites/folder", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deletefolder(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "id") String id, HttpServletRequest request) {
        int result = 0;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = accountService.loadUserByUsername(userDetails.getUsername());
        try {
            Classification classification = new Classification();
            classification.setId(id);
            List<Bookmark> cf = bookmarkService.findByClassificationAndUserId(classification, user.getId());
            if (cf.size() == 0) {
                classificationService.deleteClassificationById(id);
                result = 1;
            } else {
                result = 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{result:" + result + "}";
    }

    /**
     * 添加收藏文件夹
     * 
     * @param pageable
     * @param title
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/favorites/folder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String savefolder(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "title") String title, HttpServletRequest request) {
        int result = 0;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = accountService.loadUserByUsername(userDetails.getUsername());
        try {
            Classification classification = new Classification();
            classification.setTitle(title);
            classification.setUserId(user.getId());
            classificationService.addNewClassification(classification);
            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{result:" + result + "}";
    }

    /**
     * 获取所有文件件
     * 
     * @param pageable
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/favorites/folder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<Classification> getfolder(@PageableDefaults(value = 30) Pageable pageable, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = accountService.loadUserByUsername(userDetails.getUsername());
        Page<Classification> result = null;
        try {
            Page<Classification> list = classificationService.findByUserId(user.getId(), pageable);
            List<Classification> content = list.getContent();
            result = new PageImpl<Classification>(content, new PageRequest(list.getNumber(), list.getSize()), list.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
