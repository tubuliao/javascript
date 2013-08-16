package com.isoftstone.tyw.controller.web;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.info.BaseUtilModule;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.entity.info.TagCount;
import com.isoftstone.tyw.service.InfoService;
import com.isoftstone.tyw.service.KnowledgeSortService;
import com.isoftstone.tyw.service.TagService;
import com.isoftstone.tyw.util.Cache;
import com.isoftstone.tyw.util.CacheManager;
import com.isoftstone.tyw.util.DateManager;

/**
 * @author zhaowenli
 */
public class BaseController {

    @Autowired
    TagService tagService;

    @Autowired
    InfoService infoService;

    @Autowired
    KnowledgeSortService knowledgeSortService;

    /**
     * @param request
     * @param fbfx,分部分项编码 (选填) 默认编码为 10000000000000000 默认结束编码 19999999999999999
     * @param province, 省级（市）地区 (选填 ) 默认编码：20000000000000000 如 ：湖北省
     * @param titile, 标题 (选填)
     * @param zsxzpath,知识性质编码 (选填) 默认编码 40601000000000000 默认结束编码 40601999999999999
     * @param items_per_page,分页 每页容量 (必填)
     * @param pageIndex, 分页 当前页码号(必填)
     * @param peopleCode 人员编号 (选填) 当要进行人员筛选时 此字段为必填字段
     * @param tagService
     * @param infoService
     * @return 返回指定条件的 Map集合 其中包括 total和 result 2个key total:总个数 result:分页结果集合
     */
    public Map dolistBaseBy(HttpServletRequest request, TagService tagService, InfoService infoService) {
        Map map = new HashMap();
        String fbfx = "10000000000000000";
        if (StringUtils.isNotBlank(request.getParameter("fbfx"))) {
            fbfx = request.getParameter("fbfx");
        }
        Tag tag = tagService.getTagByCode(new Long(fbfx));
        String fbfxend = "19999999999999999";
        if (tag != null && tag.getLevel() != 1) {
            String end = fbfx.substring((tag.getLevel() - 1) * 2 - 1, (tag.getLevel() - 1) * 2 + 1);
            int num = new Integer(end);
            if (num < 9) {
                fbfxend = fbfx.substring(0, (tag.getLevel() - 1) * 2 - 1) + "0" + (num + 1) + fbfx.substring((tag.getLevel() - 1) * 2 + 1);
            } else {
                fbfxend = fbfx.substring(0, (tag.getLevel() - 1) * 2 - 1) + (num + 1) + fbfx.substring((tag.getLevel() - 1) * 2 + 1);
            }
            Long fbend = new Long(fbfxend);
            fbfxend = (fbend - 1) + "";
        }
        String area = request.getParameter("province");
        String areaCode = null; // 默认是全国包括各个省市的
        if (StringUtils.isNotBlank(area)) {
            Tag tagarea = tagService.findTagByName(request.getParameter("province"));
            if(tagarea != null && (!"23100000000000000".equals(tagarea.getCode()+""))){
            	areaCode = tagarea.getCode() + ",23100000000000000";
            }else{
            	areaCode = "0";
            }
        }
        String title = "";
        if (StringUtils.isNotBlank(request.getParameter("title"))) {
            title = "%" + request.getParameter("title").trim() + "%";
        }
        String pageSize = request.getParameter("items_per_page");
        String pageNo = request.getParameter("pageIndex");
        String zsxz = request.getParameter("zsxzpath");
        if (StringUtils.isBlank(zsxz)) {
            zsxz = "40601000000000000";
        }
        String zsxzend = "40601999999999999";

        Tag zsxztag = tagService.getTagByCode(new Long(zsxz));
        if (zsxztag != null && zsxztag.getLevel() != 1) {
            String end = zsxz.substring((zsxztag.getLevel() - 1) * 2 - 1, (zsxztag.getLevel() - 1) * 2 + 1);
            int num = new Integer(end);
            if (num < 9) {
                zsxzend = zsxz.substring(0, (zsxztag.getLevel() - 1) * 2 - 1) + "0" + (num + 1) + zsxz.substring((zsxztag.getLevel() - 1) * 2 + 1);
            } else {
                zsxzend = zsxz.substring(0, (zsxztag.getLevel() - 1) * 2 - 1) + (num + 1) + zsxz.substring((zsxztag.getLevel() - 1) * 2 + 1);
            }
            Long fbend = new Long(zsxzend);
            zsxzend = (fbend - 1) + "";
        }
        String peoplestart = "30000000000000000";
        String peopleend = "39999999999999999";
        boolean isPeopleSelect = false;
        if (StringUtils.isNotBlank(request.getParameter("peopleCode"))) {
            peoplestart = request.getParameter("peopleCode");
        }
        Tag people = tagService.getTagByCode(new Long(peoplestart));
        if (people != null && people.getLevel() != 1) {
            String end = peoplestart.substring((people.getLevel() - 1) * 2 - 1, (people.getLevel() - 1) * 2 + 1);
            int num = new Integer(end);
            if (num < 9) {
                peopleend = peoplestart.substring(0, (people.getLevel() - 1) * 2 - 1) + "0" + (num + 1)
                        + peoplestart.substring((people.getLevel() - 1) * 2 + 1);
            } else {
                peopleend = peoplestart.substring(0, (people.getLevel() - 1) * 2 - 1) + (num + 1)
                        + peoplestart.substring((people.getLevel() - 1) * 2 + 1);
            }
            Long fbend = new Long(peopleend);
            peopleend = (fbend - 1) + "";
            isPeopleSelect = true;
        }
        String temp[] = null;
        if (areaCode == null||"0".equals(areaCode)) {
            temp = new String[] {};
        } else {
            temp = new String[] { areaCode };
        }
        String isfbfx = request.getParameter("channel");
		boolean isfx = false;
		if(StringUtils.equals("fbfx", isfbfx)){
			isfx =true;
		}
		boolean issortbydate = false;
		String issort = request.getParameter("isSortbyDate");
		if(StringUtils.isNotBlank(issort)&&"true".equals(issort)){
			issortbydate = true;
		}
		
        BigInteger sgzllistCount = infoService.listBaseByfbfxAndzsxzAndpersonAndareaCount(fbfx, fbfxend, isfx,zsxz, zsxzend, isPeopleSelect, peoplestart,
                peopleend, temp, title);
        List<BaseUtilModule> sgzllist = infoService.listBaseByfbfxAndzsxzAndpersonAndarea(issortbydate,fbfx, fbfxend,isfx, zsxz, zsxzend, isPeopleSelect, peoplestart,
                peopleend, temp, title, pageSize, pageNo);
        map.put("total", sgzllistCount);
        map.put("result", sgzllist);
        return map;
    }
    // 缓存首页数量
    @RequestMapping(value = "/BaseController/getCount", method = RequestMethod.POST)
    @ResponseBody
    public List<TagCount> getCount(HttpServletRequest request) {
        Cache c = CacheManager.getCacheInfo("indexNum");
        List<TagCount> ltc = null;
        Cache cache = new Cache();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (c != null) {
            if (CacheManager.iscacheExpired(c) == false) {
                ltc = knowledgeSortService.tagCountIndex();
                CacheManager.clearOnly("indexNum");
                String dateafter = DateManager.DateAfterHours(sdf.format(date), 6);// 六小时失效
                cache.setKey("indexNum");
                cache.setValue(ltc);
                cache.setTimeOut(Long.valueOf(DateManager.dateFormatTimes(DateManager.stringFormat(dateafter))));
                CacheManager.putCache("indexNum", cache);
            } else {
                ltc = (List<TagCount>) CacheManager.getCacheInfo("indexNum").getValue();
            }
        } else {
            // 初始化
            ltc = knowledgeSortService.tagCountIndex();
            String dateafter = DateManager.DateAfterHours(sdf.format(date), 6);// 六小时失效
            Long timeout = Long.valueOf(DateManager.dateFormatTimes(DateManager.stringFormat(dateafter)));// 失效时间
            cache.setKey("indexNum");
            cache.setValue(ltc);
            cache.setTimeOut(timeout);
            CacheManager.putCache("indexNum", cache);
        }
        return ltc;
    }
    @RequestMapping(value = "/BaseController/getCountfbfx", method = RequestMethod.POST)
    @ResponseBody
    public  TagCount getCountfbfx(HttpServletRequest request) {
        return knowledgeSortService.tagCountfbfx();
    }
    @RequestMapping(value = "/BaseController/isFirstVisit", method = RequestMethod.POST)
    @ResponseBody
    public String isFirstVisit(HttpServletRequest request,@RequestParam(value = "provinceVal", required = false) String provinceVal) {
        String result=null;
        HttpSession session = request.getSession();
        String province=(String) session.getAttribute("provinceVal");
        if(province==null){
             session.setAttribute("provinceVal", provinceVal);
            result=null;
        }else{
            result=province;
        }
        return "{result:" + result + "}";
    }
   
}
