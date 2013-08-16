/**
 * Copyright 2008 - 2012 Simcore.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isoftstone.tyw.controller.web;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefaults;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.isoftstone.tyw.dto.info.BaseSearchDto;
import com.isoftstone.tyw.dto.info.ESPage;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.BaseModule;
import com.isoftstone.tyw.entity.info.BaseTag;
import com.isoftstone.tyw.entity.info.BaseTagModule;
import com.isoftstone.tyw.entity.info.Files;
import com.isoftstone.tyw.entity.info.FilesPage;
import com.isoftstone.tyw.entity.info.FilesPageCount;
import com.isoftstone.tyw.entity.info.Form;
import com.isoftstone.tyw.entity.info.Image;
import com.isoftstone.tyw.entity.info.KeyWord;
import com.isoftstone.tyw.entity.info.News;
import com.isoftstone.tyw.entity.info.PdTag;
import com.isoftstone.tyw.entity.info.Powerpoint;
import com.isoftstone.tyw.entity.info.PowerpointPage;
import com.isoftstone.tyw.entity.info.Segment;
import com.isoftstone.tyw.entity.info.SegmentDir;
import com.isoftstone.tyw.entity.info.Source;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.entity.info.Video;
import com.isoftstone.tyw.entity.pub.Comments;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.BaseService;
import com.isoftstone.tyw.service.BookmarkService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.ElasticsearchService;
import com.isoftstone.tyw.service.FdfsService;
import com.isoftstone.tyw.service.FilesPageService;
import com.isoftstone.tyw.service.FilesService;
import com.isoftstone.tyw.service.FormService;
import com.isoftstone.tyw.service.ImageService;
import com.isoftstone.tyw.service.InfoService;
import com.isoftstone.tyw.service.KeyWordService;
import com.isoftstone.tyw.service.LikeCountService;
import com.isoftstone.tyw.service.NewsService;
import com.isoftstone.tyw.service.PowerpointService;
import com.isoftstone.tyw.service.PubService;
import com.isoftstone.tyw.service.SourceService;
import com.isoftstone.tyw.service.SpecialService;
import com.isoftstone.tyw.service.TagService;
import com.isoftstone.tyw.service.VideoService;
import com.isoftstone.tyw.util.DateManager;
import com.isoftstone.tyw.util.IOUtil;
import com.isoftstone.tyw.util.MapPoint;
import com.isoftstone.tyw.util.Pager;

/**
 * @author jqz
 */
@Controller
public class KnowledgeController extends BaseController {
    @Autowired
    private InfoService infoService;

    @Autowired
    private TagService tagService;
    @Autowired
    private KeyWordService keyWordService;
    @Autowired
    private FilesService filesService;

    @Autowired
    private FormService formService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PubService pubService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private BaseService baseService;

    @Autowired
    private SpecialService specialService;

    @Autowired
    private FdfsService fdfsService;

    @Autowired
    private FilesPageService fpService;

    @Autowired
    private SourceService sourceService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private LikeCountService likeCountService;

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    PowerpointService pptService;
    
    

    @Autowired
    IOUtil ioUtil;
   
    
    private static final Logger log = Logger.getLogger(KnowledgeController.class);

    private String highlightStart = "<font color='red'>"; // 高亮前缀

    private String highlightEnd = "</font>";// 高亮后缀

    /**
     * 首页搜索
     * 
     * @param pageable
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/goPageList", method = RequestMethod.GET)
    public String goPageList(@PageableDefaults(value = 5) Pageable pageable, Model model, HttpServletRequest request,@RequestParam(value = "tag_name", required = false) String tag_name) {
        String keyword = "输入要找的内容".equals(request.getParameter("keyword")) ? "" : request.getParameter("keyword");
        Integer typeId = StringUtils.isEmpty(request.getParameter("typeId")) ? 0 : Integer.valueOf(request.getParameter("typeId"));
        String onex = request.getParameter("onex");
        // 前10条数据
        Page<Base> page = infoService.listBase(this.getWhereClause(keyword, typeId), pageable);
        Pageable topPageable = new PageRequest(0, 10);
        List<Base> pageTopNum = infoService.listBase(this.getWhereClause(null, 0), topPageable).getContent();
        List<Base> ncontent = Lists.newArrayList();
        for (Base base : pageTopNum) {
            Base dto = new Base();
            dto.setId(base.getId());
            dto.setTitle(base.getTitle());
            dto.setInfoType(base.getInfoType());
            ncontent.add(dto);
        }
        model.addAttribute("page", page);
        model.addAttribute("pageTopNum", ncontent);
        model.addAttribute("onex", onex);
        model.addAttribute("keyword", keyword);
        model.addAttribute("typeId", typeId);
        model.addAttribute("tag_name", tag_name);

        return "page/knowledge/search";
    }

    @RequestMapping(value = "/goLuxurySearch", method = RequestMethod.GET)
    public String goLuxurySearch(@PageableDefaults(value = 5) Pageable pageable, Model model, HttpServletRequest request,@RequestParam(value = "tag_name", required = false) String tag_name) {
        Integer typeId = StringUtils.isEmpty(request.getParameter("typeId")) ? 0 : Integer.valueOf(request.getParameter("typeId"));
        String onex = StringUtils.isEmpty(request.getParameter("onex")) ? "konwledgeType0" : request.getParameter("onex");
        // 前10条数据
        // Page<Base> page=infoService.listBase(this.getWhereClause(keyword, typeId), pageable);
        // model.addAttribute("page",page);
        Pageable topPageable = new PageRequest(0, 10);
        List<Base> pageTopNum = infoService.listBase(this.getWhereClause(null, 0), topPageable).getContent();
        List<Base> ncontent = Lists.newArrayList();
        for (Base base : pageTopNum) {
            Base dto = new Base();
            dto.setId(base.getId());
            dto.setTitle(base.getTitle());
            dto.setInfoType(base.getInfoType());
            ncontent.add(dto);
        }
        model.addAttribute("pageTopNum", ncontent);
        request.setAttribute("typeId", typeId);
        request.setAttribute("goLuxurySearch", 1);
        request.setAttribute("onex", onex);
        model.addAttribute("tag_name", tag_name);
        return "page/knowledge/search";
    }

    /**
     * 类型转化，和搜索引擎库对应
     * 
     * @param typeId 类型
     * @return
     */
    public String resultType(Integer typeId) {
        String resultType = "";
        switch (typeId) {
        case 0:
            resultType = null;
            break;
        case 1:
            resultType = "file";
            break;
        case 2:
            resultType = "segment";
            break;
        case 3:
            resultType = "video";
            break;
        case 4:
            resultType = "form";
            break;
        case 5:
            resultType = "image";
            break;
        case 6:
            resultType = "ppt";
            break;
        case 100:
            resultType = "segment,file,ppt";
            break;
        }
        return resultType;
    }

    /**
     * 计算某词 在某段话中出现的所有位置
     * 
     * @param selChar
     * @param content
     * @return
     */
    public Map getIndex(String selChar, String content) {
        int count = 0;
        int index = 0;
        String sToFind = selChar;
        Map map = new HashMap();
        int[] charIndex = new int[2];
        for (int i = 0; i < content.length(); i++) {
            index = content.indexOf(sToFind, count);
            if (index == -1)
                break;
            map.put(i, index);
            // charIndex[i]=index;
            count = (index += 4);
        }
        return map;
    }

    /**
     * 高亮截取字符
     */
    public String subStr(String content, int subindex) {
        boolean isSub = false;
        Map highlightStartIndex = new HashMap();
        Map highlightEndtIndex = new HashMap();
        // int subindex=70;
        String subContent = content.replaceAll(highlightStart, "").replaceAll(highlightEnd, "");
        if (subContent.length() > subindex) {
            highlightStartIndex = getIndex(highlightStart, content);
            highlightEndtIndex = getIndex(highlightEnd, content);
            int endindex = 0;
            for (int j = 0; j < highlightStartIndex.size(); j++) {
                isSub = subindex >= (Integer) highlightStartIndex.get(j) && ((Integer) highlightEndtIndex.get(j) + highlightEnd.length()) >= subindex;
                endindex = (Integer) highlightEndtIndex.get(j);
                if (isSub) {
                    break;
                }
            }
            if (isSub) {
                subContent = content.substring(0, endindex + highlightEnd.length()) + "...";
            } else {
                subContent = content.substring(0, subindex) + "...";
            }

        } else {
            subContent = content;
        }
        return subContent;
    }

    /**
     * search页面异步搜索
     * 
     * @param pageable
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/doListajax", method = RequestMethod.POST)
    public String doListajax(@PageableDefaults(value = 10) Pageable pageable, Model model, HttpServletRequest request,    @RequestParam(value = "sortField", required = false) String sortField,
            @RequestParam(value = "sortOrder", required = false) String sortOrder) {
        String tag_name = request.getParameter("tag_name").replace("AND()", "").replace("()AND", "").replace("()", "");
        Integer typeId = StringUtils.isEmpty(request.getParameter("typeId")) ? 0 : Integer.valueOf(request.getParameter("typeId"));
        Integer pageNo = Integer.valueOf(request.getParameter("pageNo")) <= 1 ? 1 : Integer.valueOf(request.getParameter("pageNo"));
        String keyword = "输入要找的内容".equals(request.getParameter("keyword")) ? "" : request.getParameter("keyword").replaceFirst("[?]", "")
                .replaceFirst("[*]", "");
       // sortField=StringUtils.isEmpty(sortField) ? "ClickCount" : sortField;
       // sortOrder=StringUtils.isEmpty(sortOrder) ? "ASC" : sortOrder;
        List<Base> baseResult = new ArrayList<Base>();
        Integer pageSize = 10;
        String resultType = resultType(typeId);
        Pager pager = null;
        Long esPageCount = 0L;
        boolean ischar = true;
        while (ischar) {// 过滤掉?和*这些特殊字符
            String firstChar = keyword.length() > 0 ? keyword.substring(0, 1) : keyword;
            if ("?".equals(firstChar) || "*".equals(firstChar)) {
                keyword = keyword.replaceFirst("[?]", "").replaceFirst("[*]", "");
            } else {
                ischar = false;
            }
        }
    
        try {
            if ("".equals(tag_name.trim()) && "".equals(keyword.trim())) {
                pager = new Pager(pageSize, pageNo, 0, baseResult);
              //  esPageCount = 0L;
            }
            if (!"".equals(tag_name.trim()) || !"".equals(keyword.trim())) {
                BaseSearchDto base = null;
                ESPage esPage = elasticsearchService.queryAndFilterByString("tyw", resultType, keyword, tag_name, highlightStart, highlightEnd, sortField,
                        sortOrder, pageSize, pageNo);
                esPageCount = esPage.getTotalElements();
                List<Map<String, Object>> listMap = esPage.getHighlighterResult();
                KnowledgeController kc = new KnowledgeController();
                for (Map<String, Object> mapObj : listMap) {
                    base = new BaseSearchDto();
                    for (Entry<String, Object> m : mapObj.entrySet()) {
                        if (m.getKey().equals("Title")) {
                            base.setTitle(kc.subStr(m.getValue().toString(), 70));
                        }
                        if (m.getKey().equals("Source")) {
                            base.setSource(m.getValue().toString());
                        }
                        if (m.getKey().equals("Type")) {
                            base.setInfoType(Integer.valueOf(m.getValue().toString()));
                        }
                        if (m.getKey().equals("Id")) {
                            base.setId(m.getValue().toString());
                        }
                        if (m.getKey().equals("CreateDate")) {
                            Date date = DateManager.stringFormat(m.getValue().toString());
                            base.setCreateDate(date);
                        }
                        if (m.getKey().equals("Content")) {
                            base.setContent(kc.subStr(m.getValue().toString(), 170));
                        }
                        if (m.getKey().equals("FavCount")) {
                            base.setFavCount(Integer.valueOf(m.getValue().toString()));
                        }
                        if (m.getKey().equals("LikeCount")) {
                            base.setLikeCount(Integer.valueOf(m.getValue().toString()));
                        }
                    }
                    baseResult.add(base);
                }
                log.info("查询关键词 " + keyword);
                pager = new Pager(pageSize, pageNo, esPageCount, baseResult);
            }
        } catch (Exception e) {
            pager = new Pager(0, 0, 0, baseResult);
            e.printStackTrace();
        }
        request.setAttribute("esPageCount", esPageCount);
        model.addAttribute("pageTopNum", null);
        model.addAttribute("pager", pager);
        request.setAttribute("keyword", keyword);
        request.setAttribute("typeId", typeId);
        return "page/knowledge/search_ajax";
    }

    /**
     * 自动补全
     * 
     * @param keyword
     * @param request
     * @return
     */
    @RequestMapping(value = "/list/suggest/{keyword}", method = RequestMethod.POST)
    @ResponseBody
    public List<String> doSuggest(@PathVariable("keyword") String keyword, HttpServletRequest request) {
        return elasticsearchService.findSuggestions("tyw", "Title", keyword, 10);
    }

    /**
     * 按照来源搜索和类型进行相关搜索
     * 
     * @param source 来源
     * @param typeId 类型
     * @return
     */
    public List<BaseSearchDto> relatedKnoledge(String source, Integer typeId) {
        String resultType = resultType(typeId);
        List<BaseSearchDto> baseResult = new ArrayList<BaseSearchDto>();
        try {
            ESPage esPage = elasticsearchService.queryByStringBuilder("tyw", resultType, "AND", source, "", "", 10, 1);
            List<Map<String, Object>> listMap = esPage.getHighlighterResult();
            BaseSearchDto base = null;
            for (Map<String, Object> mapObj : listMap) {
                base = new BaseSearchDto();
                for (Entry<String, Object> m : mapObj.entrySet()) {
                    if (m.getKey().equals("Source")) {
                        base.setSource(m.getValue().toString());
                    }
                    if (m.getKey().equals("Type")) {
                        base.setInfoType(Integer.valueOf(m.getValue().toString()));
                    }
                    if (m.getKey().equals("Id")) {
                        base.setId(m.getValue().toString());
                    }
                    if (m.getKey().equals("Title")) {
                        KnowledgeController kc = new KnowledgeController();
                        base.setTitle(kc.subStr(m.getValue().toString().replace("<em>", "").replace("</em>", ""), 70));
                    }
                }
                baseResult.add(base);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseResult;
    }

    @RequestMapping(value = "/fileajax", method = RequestMethod.POST)
    public String fileajax(Model model, @RequestParam(value = "infoType", required = false) Integer infoType,
            @RequestParam(value = "id", required = false) String id, @RequestParam(value = "pageStr", required = false) String pageStr,
            @RequestParam(value = "isQuery", required = false) Integer isQuery, @RequestParam(value = "pageNo", required = false) Integer pageNo,
            HttpServletRequest request, @PageableDefaults(value = 10) Pageable pageable) {
        int pageSize = 2;
        Files files = new Files();// 文件对象
        pageNo = pageNo == null ? 1 : pageNo;
        String[] pageStrs = null;
        FilesPageCount content = null;
        if (StringUtils.isEmpty(pageStr)) {
            pageStr = filesService.findContentPage(id);
        }
        int totalPage = (int) Math.ceil(((pageStr.length() + 0.0) / pageSize));
        pageStrs = pageStr.split(",");
        if (pageNo == 1) {
            content = filesService.findContent(Integer.valueOf(pageStrs[(pageNo * pageSize - pageSize)]),
                    Integer.valueOf(pageStrs[(pageNo * pageSize)]) + 3, id);
            files.setContent(content.getContent());
        }
        if (pageNo != 1) {
            int st = Integer.valueOf(pageStrs[pageSize * pageNo - pageSize]) + 4;
            int ed = Integer.valueOf(pageStrs[(pageNo * pageSize)]) - Integer.valueOf(pageStrs[pageSize * pageNo - pageSize]);
            content = filesService.findContent(st, ed, id);
            files.setContent(content.getContent());
        }
        model.addAttribute("files", files);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageStr", pageStr);
        return "page/knowledge/filesDetail_ajax";
    }

    @RequestMapping(value = "/analyzer/keywords", method = RequestMethod.POST)
    @ResponseBody
    public List<String> keywords(Model model, HttpServletRequest request, @RequestParam(value = "keyword", required = false) String keyword) {
        if(StringUtils.isNotBlank(keyword)){
            List<String> keywords = elasticsearchService.findAnalyze("tyw", "ik_analyzer", keyword);
            return keywords;
        }else{ 
            return null;
        }
    }

 /**
  * 获取客户端访问地址
  * @param model
  * @param request
  * @param x 横坐标
  * @param y 纵坐标
  * @return
  */
    @RequestMapping(value = "/index/getpoint", method = RequestMethod.POST)
    @ResponseBody
    public Object getpoint(Model model, HttpServletRequest request, @RequestParam(value = "x", required = false) String x,
            @RequestParam(value = "y", required = false) String y) {
        String str = MapPoint.getRequestByUrl("http://ditu.google.com/maps/api/geocode/json?latlng=" + x + "," + y + "&sensor=false&&language=zh-CN");
        JSONObject jb = new JSONObject();
        JSONArray array = (JSONArray) jb.fromObject(str).get("results");
        JSONArray address = (JSONArray) ((JSONObject) array.get(0)).get("address_components");
        return address.get(3);
    }

    /**
     * 详情查询
     * 
     * @param model
     * @param infoType
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/{infoType}/{id}", method = RequestMethod.GET)
    public String doDetail(Model model, @PathVariable("infoType") Integer infoType, @PathVariable("id") String id,
            @RequestParam(value = "pageStr", required = false) String pageStr, @RequestParam(value = "isQuery", required = false) Integer isQuery,
            @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "pageNo", required = false) Integer pageNo,
            HttpServletRequest request, HttpServletResponse response, @PageableDefaults(value = 10) Pageable pageable) {
        String returnurl = null;
        try {
        	//打开页面更新点击数
            baseService.modifyclickCount(id);
            List<BaseTag> listtag=tagService.findRelatedWords(id);//相关搜索
            //获取导航路径
            String path = specialService.getTagPathById(id);
            String[] pathArray = null;
            if (!"".equals(path) && null != path){
                pathArray = path.split(",");
            }
            //给知识性质的二级标签加载频道链接路径start
            Map knowMap = new HashMap();
            knowMap.put("土木资讯", "/knowledge/web/newTable?rootCode=40101000000000000");
            knowMap.put("法律法规", "/knowledge/web/displayKnowledgeByDate?rootCode=40102000000000000");
            knowMap.put("标准规范", "/knowledge/web/bookList?rootCode=40103000000000000&sourceType=1");
            knowMap.put("施工图集", "/knowledge/web/bookList?rootCode=40103000000000000&sourceType=2");
            knowMap.put("强制性条文", "/knowledge/web/anotherTable?rootCode=40201000000000000");
            knowMap.put("规范条文", "/specialKnowledge/web/ClauseNew/1");
            knowMap.put("安全条文", "/knowledge/web/newTable?rootCode=40203000000000000");
            knowMap.put("施工工法", "/knowledge/web/otherTable?rootCode=40205000000000000");
            knowMap.put("工艺标准", "/knowledge/web/form?rootCode=40206000000000000");
            knowMap.put("投标施组", "/knowledge/web/newTable?rootCode=40306000000000000");
            knowMap.put("实施性施组", "/knowledge/web/newTable?rootCode=40301000000000000");
            knowMap.put("施工方案", "/knowledge/web/sgfaform?rootCode=40302000000000000");
            knowMap.put("技术交底", "/knowledge/web/form?rootCode=40303000000000000");
            knowMap.put("施工做法", "/knowledge/web/form?rootCode=40401000000000000");
            knowMap.put("经验技巧", "/knowledge/web/form?rootCode=40402000000000000");
            knowMap.put("基础数据", "/knowledge/web/form?rootCode=40403000000000000");
            knowMap.put("工程案例", "/knowledge/web/otherTable?rootCode=40404000000000000");
            knowMap.put("施工计算", "/knowledge/web/form?rootCode=40405000000000000");
            knowMap.put("构造详图", "/knowledge/web/form?rootCode=40406000000000000");
            knowMap.put("质量检测", "/knowledge/web/form?rootCode=40501000000000000");
            knowMap.put("质量控制", "/knowledge/web/form?rootCode=40502000000000000");
            knowMap.put("质量通病", "/knowledge/web/form?rootCode=40503000000000000");
            knowMap.put("工程创优", "/knowledge/web/newTable?rootCode=40504000000000000");
            knowMap.put("工程资料", "/knowledge/web/form?rootCode=40601000000000000");
            knowMap.put("新规范表格", "/knowledge/web/form?rootCode=40603000000000000");
            knowMap.put("检测机构用表", "/knowledge/web/form?rootCode=40602000000000000");
            knowMap.put("管理常用表", "/knowledge/web/newTable?rootCode=40604000000000000");
            knowMap.put("安全管理", "/knowledge/web/newTable?rootCode=40701000000000000");
            knowMap.put("安全技术", "/knowledge/web/form?rootCode=40702000000000000");
            knowMap.put("安全资料", "/knowledge/web/form?rootCode=40703000000000000");
            knowMap.put("环境保护", "/knowledge/web/newTable?rootCode=40704000000000000");
            knowMap.put("工程管理", "/knowledge/web/newTable?rootCode=40804000000000000");
            knowMap.put("招标投标", "/knowledge/web/newTable?rootCode=40803000000000000");
            knowMap.put("工程造价", "/knowledge/web/newTable?rootCode=40805000000000000");
            knowMap.put("工程物资", "/knowledge/web/newTable?rootCode=40806000000000000");
            knowMap.put("机械机具", "/knowledge/web/newTable?rootCode=40801000000000000");
            knowMap.put("检测试验", "/knowledge/web/newTable?rootCode=40802000000000000");
            knowMap.put("工程测量", "/knowledge/web/newTable?rootCode=40807000000000000");
            knowMap.put("培训课程", "/knowledge/web/newTable?rootCode=40903000000000000");
            knowMap.put("执业考试", "/knowledge/web/newTable?rootCode=40902000000000000");
            knowMap.put("视频资源", "/toPackageList");
            knowMap.put("照片图片", "/knowledge/web/newTable?rootCode=40905000000000000");
            knowMap.put("文化论文", "/knowledge/web/newTable?rootCode=40904000000000000");
            knowMap.put("学生专区", "/knowledge/web/newTable?rootCode=40906000000000000");
            knowMap.put("新技术信息", "/knowledge/web/newTable?rootCode=41001000000000000");
            knowMap.put("新技术应用", "/knowledge/web/newTable?rootCode=41002000000000000");
            knowMap.put("工程示范", "/knowledge/web/newTable?rootCode=41003000000000000");
            knowMap.put("专利技术", "/knowledge/web/displayKnowledgeByDate?rootCode=41004000000000000");
            String[] pathArrays = new String[2];
            if(pathArray!=null){
            	for(String s : pathArray){
                	boolean have = false;
                	have = knowMap.containsKey(s);
                	if(have){
                		pathArrays[0]=s;//48个知识性质其中一个
                		pathArrays[1]=(String)knowMap.get(s);//对应的超级链接
                		break;
                	}
                }
            }
          //给知识性质的二级标签加载频道链接路径end
            
            model.addAttribute("TagPath", pathArrays);
            model.addAttribute("listtag", listtag);

            List<BaseSearchDto> listSource = null;
            List<BaseSearchDto> listForm = null;
            List<Files> listFiles=null;
            List<Form> listFormdown=null;
            Segment segment = null;// 切片对象
            Files files = null;// 文件对象
            Form form = null;// 表格对象
            Video video = null;// 视频对象
            Image image = null;// 图片对象
            long likeCount= likeCountService.searchCount(id).longValue(); //根据id查询当前文件被用户喜欢过的次数
            long collectCount = bookmarkService.searchCollectCount(id).longValue(); //根据id查询当前被用户收藏过的次数
            switch (infoType) {
            // 文件类型知识
            case 1:
            	//根据文件id查询当前文件在文件表中的信息
                 files = filesService.getFileById(id);
                if(files!=null&&StringUtils.isNotBlank(files.getUrl())){
                    listFiles=new ArrayList<Files>();
                    String[] fileurl=null;
                    if(files.getUrl().indexOf(";")==0){
                         fileurl=files.getUrl().substring(1, files.getUrl().length()).split(";");
                    }else{
                        fileurl=files.getUrl().split(";");
                    }
                   for(int f=0;f<fileurl.length;f++){
                       Files fildown=new Files();
                       fildown.setUrl(fileurl[f]);
                       String filesuffix=fileurl[f].substring(fileurl[f].lastIndexOf("."),fileurl[f].length());
                       fildown.setTitle(files.getTitle());
                       fildown.setSuffix(filesuffix);
                       listFiles.add(fildown);
                   }
                    // files.setUrl(files.getUrl().replace(";", ""));
                }
                List<FilesPage> listpage = fpService.findFilePageListByFileId(files.getId());
                String pageurlString = "";
                int total = listpage.size();
                for(int i = 0;i<total;i++){
                	pageurlString += "'"+listpage.get(i).getUrl()+"',";
                }
                pageurlString = pageurlString.substring(0,pageurlString.lastIndexOf(","));
                
                model.addAttribute("pageurlString",pageurlString);
                model.addAttribute("listFiles", listFiles);
                if(StringUtils.isBlank(files.getUrl())){
                	files.setUrl("#");
                }else{
                	String urls = files.getUrl();
                	int index = urls.indexOf(";");
                	String url = "";
                	if(index>=0){
                		 url = urls.split(";")[1];
                	}else{
                		url = url;
                	}
                	files.setUrl(url);
                }
                model.addAttribute("files", files);
                model.addAttribute("likeCount", likeCount);
                model.addAttribute("collectCount", collectCount);
                if(files.getTotalPages()!=null&&files.getTotalPages()>0){
                	List<BaseModule> list = baseService.findBaseListById(files.getId());
                	 model.addAttribute("baselist", list);
                	return "/page/knowledge/flexpaperdetail";
                }else{
                	return "page/knowledge/detail_file";
                } 
                
            case 2:// 切片类型知识
                segment = infoService.getSegmentById(id);// 通过ID得到切片知识对象
                model.addAttribute("segment", segment);
                model.addAttribute("keyword", keyword);
                model.addAttribute("likeCount", likeCount);
                model.addAttribute("collectCount", collectCount);
                     return "/page/specialKnowledge/web/clauseDetail";
                
            case 3:// 视频类型知识
                   // model.addAttribute("segment",s);
                video = videoService.getVideoById(id);
                List<Video> videodown=new ArrayList<Video>();
                if (video != null) {// 相关推荐
                    listSource = relatedKnoledge("Source:" + video == null ? null : video.getSource(), 3);// 前十条相关的来源
                    listForm = relatedKnoledge(video.getSource(), 4);// 前十条表格相关的
                }
//                if(video!=null&&StringUtils.isNotBlank(video.getUrl())){
//                     String[] videodowns=null;
//                    if(video.getUrl().indexOf(";")==0){
//                        videodowns=video.getUrl().substring(1, video.getUrl().length()).split(";");
//                    }else{
//                        videodowns=video.getUrl().split(";");
//                    }
//                   for(int f=0;f<videodowns.length;f++){
//                       Video fildown=new Video();
//                       fildown.setUrl(videodowns[f]);
//                       String filesuffix=videodowns[f].substring(videodowns[f].lastIndexOf("."),videodowns[f].length());
//                       fildown.setTitle(video.getTitle());
//                       fildown.setSuffix(filesuffix);
//                       videodown.add(fildown);
//                   }
//                    // files.setUrl(files.getUrl().replace(";", ""));
//                }
              //  model.addAttribute("videodown", videodown);
                model.addAttribute("listSource", listSource);
                model.addAttribute("listForm", listForm);
                model.addAttribute("video", video);
                model.addAttribute("likeCount", likeCount);
                model.addAttribute("collectCount", collectCount);
                     return "page/knowledge/videoDetail";
               
            case 4:// 表格类型知识
                form = formService.getFormById(id);
                if(form!=null&&StringUtils.isNotBlank(form.getEmpUrl())){
                    listFormdown=new ArrayList<Form>();
                    String[] formurls=null;
                    if(form.getEmpUrl().indexOf(";")==0){
                        formurls=form.getEmpUrl().substring(1, form.getEmpUrl().length()).split(";") ;
                    }else{
                        formurls=form.getEmpUrl().split(";") ;
                    }
                    for(int f=0;f<formurls.length;f++){
                        Form formdownload=new Form();
                        String formsuffix=formurls[f].substring(formurls[f].lastIndexOf("."),formurls[f].length());
                        formdownload.setEmpUrl(formurls[f]);
                        formdownload.setSuffix(formsuffix);
                        formdownload.setTitle(form.getTitle());
                        listFormdown.add(formdownload);
                    }
                 }
                List<String> hiPicUrlSet = new ArrayList<String>();
                String hiPicUrl = "";// 声明web展示图片的URL串
                if (null != form && !"".equals(form)) {// 如果form不为空，则从中取出URL串
                    hiPicUrl = form.getEmpHiPicUrl();
                    listForm = relatedKnoledge(form.getSource(), 4);// 前十条表格相关的
                }
                if (hiPicUrl != null && !"".equals(hiPicUrl)) { // 获取WEB图片的SET
                    String hiPicUrlArray[] = hiPicUrl.split(";");
                    for (int i = 0; i < hiPicUrlArray.length; i++) {
                        if (!"".equals(hiPicUrlArray[i]) && hiPicUrlArray[i] != null) {
                            hiPicUrlSet.add(hiPicUrlArray[i]);
                        }
                    }
                }
                model.addAttribute("listFormdown", listFormdown);
                model.addAttribute("keyword", keyword);
                model.addAttribute("listForm", listForm);
                model.addAttribute("picUrls", hiPicUrlSet);
                model.addAttribute("form", form);
                model.addAttribute("id", id);
                model.addAttribute("likeCount", likeCount);
                model.addAttribute("collectCount", collectCount);
                     return "page/knowledge/formDetail";
               
            case 5:// 图片类知识
                image = imageService.getImageById(id);
                List<Image> imageUrlSet = new ArrayList<Image>();
                String imageUrl = "";// 声明web展示图片的URL串
                String description = "";// 描述
                String contexts = "";// 图片集的描述
                List<Image> listimagedowns=new ArrayList<Image>();

                if (null != image && !"".equals(image)) {// 如果form不为空，则从中取出URL串
                    imageUrl = image.getUrl();
                    description = image.getDescription();
                    contexts = image.getContent();
                    listSource = relatedKnoledge("Source:" + image == null ? null : image.getSource(), 5);// 前十条相关的来源
                    // listForm=relatedKnoledge(image.getSource(),4);//前十条表格相关的
                }
                if (imageUrl != null && !"".equals(imageUrl)) { // 获取WEB图片和图片名称的SET
                    String imageUrlArray[] = imageUrl.split(";");
                    String descriptionArray[] = null;
                    if (description != null && !"".equals(description)) {
                        descriptionArray = description.split(";");
                    }
                    for (int i = 0; i < imageUrlArray.length; i++) {
                        String des = "";// 每张照片的名称
                        Image im = new Image();
                        if (!"".equals(imageUrlArray[i]) && imageUrlArray[i] != null) {
                            if (descriptionArray != null && i < descriptionArray.length) {
                                des = descriptionArray[i];
                            }
                            im.setDescription(des);
                            im.setUrl(imageUrlArray[i]);
                            imageUrlSet.add(im);
                        }
                    }
                }
                if (image != null) {// 相关推荐
                    listSource = relatedKnoledge("Source:" + image == null ? null : image.getSource(), 5);// 前十条相关的来源
                    listForm = relatedKnoledge(image.getSource(), 4);// 前十条表格相关的
                }
           
                if(image!=null&&StringUtils.isNotEmpty(image.getImgUrl())){
                     String[] imagedowns=null;
                    if(image.getImgUrl().indexOf(";")==0){
                        imagedowns=image.getImgUrl().substring(1, image.getImgUrl().length()).split(";");
                    }else{
                        imagedowns=image.getImgUrl().split(";");
                    }
                   for(int f=0;f<imagedowns.length;f++){
                       Image imagedown=new Image();
                       imagedown.setImgUrl(imagedowns[f]);
                       String filesuffix=imagedowns[f].substring(imagedowns[f].lastIndexOf("."),imagedowns[f].length());
                       imagedown.setTitle(image.getTitle());
                       imagedown.setSuffix(filesuffix);
                       listimagedowns.add(imagedown);
                   }
                    // files.setUrl(files.getUrl().replace(";", ""));
                }
                model.addAttribute("listimagedowns", listimagedowns);
                model.addAttribute("listSource", listSource);
                model.addAttribute("listForm", listForm);
                model.addAttribute("imageUrls", imageUrlSet);// 图片和图片名称的集合
                model.addAttribute("image", image);
                model.addAttribute("contexts", contexts);// 图片集的描述
                model.addAttribute("likeCount", likeCount);
                model.addAttribute("collectCount", collectCount);
                returnurl = "page/knowledge/imageDetail";
                     return returnurl;
               
            case 6:// ppt
                Page<PowerpointPage> pag = pptService.getPptPageById(id, null);
                List<PowerpointPage> listp = pag.getContent();
                Powerpoint pp = pptService.getPptById(id);
                List<Powerpoint> listpptdown=new ArrayList<Powerpoint>();
                if(pp!=null&&StringUtils.isNotBlank(pp.getUrls())){
                     String[] ppturls=null;
                    if(pp.getUrls().indexOf(";")==0){
                        ppturls=pp.getUrls().substring(1, pp.getUrls().length()).split(";") ;
                    }else{
                        ppturls=pp.getUrls().split(";") ;
                    }
                    for(int f=0;f<ppturls.length;f++){
                        Powerpoint powerpoint=new Powerpoint();
                        String formsuffix=ppturls[f].substring(ppturls[f].lastIndexOf("."),ppturls[f].length());
                        powerpoint.setUrls((ppturls[f]));
                        powerpoint.setSuffix(formsuffix);
                        powerpoint.setTitle(pp.getTitle());
                        listpptdown.add(powerpoint);
                    }
                 }
                
                model.addAttribute("pid", id);
                model.addAttribute("imageUrls", listp);
                model.addAttribute("pp", pp);
                model.addAttribute("likeCount", likeCount);
                model.addAttribute("collectCount", collectCount);
                model.addAttribute("listpptdown", listpptdown);
                     return "page/ppt/pptview";
                 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "forword:/list";
        }

        return null;
    }

    /**
     * 表格详情页面 样表分页
     * 
     * @param request
     * @return
     */
    // @RequestMapping(value="/pagination/exampleForm", method=RequestMethod.GET)
    // @ResponseBody
    // public Map<String, Object> exampleFormPagination(HttpServletRequest request) {
    // Map<String, Object> result = new HashMap<String, Object>();
    // Integer pageIndex = Integer.parseInt(request.getParameter("pageIndex")) ;
    // // Integer itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage")) ;
    // Integer itemsPerPage = 1; // 默认每夜显示一条记录
    // String id = request.getParameter("id");
    // String flag = request.getParameter("flag");
    // Form form = null;
    // if(id != null && !"".equals(id)) {
    // form = formService.getFormById(id);
    // }
    //
    // String formTitle = "";
    // String formSource = "";
    // String formAreaTag = "";
    // List<Form> expFormList = null; // 相关联的样表列表
    // List<List<String>> allExpEmpHiPicUrlList = new ArrayList<List<String>>(); // 一张空表对应的所有样表的WEB图片
    // BigInteger total = null; // 相关联的样表的数量
    // if(form != null) {
    // formTitle = "%" + form.getTitle() + "%";
    // formSource = form.getSource();
    // formAreaTag = formService.getAreaTagById(form.getId());
    // }
    // // 根据空表的标题、来源和地域标签获取相对应的样表
    // if(!"".equals(formTitle) && !"".equals(formSource) && !"".equals(formAreaTag) && "1".equals(flag)) {
    // expFormList = formService.getExampleFormList(formTitle, formSource, formAreaTag, pageIndex, itemsPerPage);
    // total = formService.getExampleFormListCount(formTitle, formSource, formAreaTag);
    // } else if(!"".equals(formTitle) && "0".equals(flag)) {
    // expFormList = formService.getOtherExampleFormList(formTitle, pageIndex, itemsPerPage);
    // total = formService.getOtherExampleFormListCount(formTitle);
    // }
    // if(expFormList != null) {
    // for(int i = 0; i < expFormList.size(); i++) {
    // Form f = expFormList.get(i);
    // List<String> oneExpEmpHiPicUrlList = new ArrayList<String>(); // 单张样表的WEB图片
    // if(f != null) {
    // String expUrl = f.getEmpHiPicUrl(); // 单张样表的WEB图片的String
    // if(!"".equals(expUrl) && expUrl != null) {
    // String expUrlArr[] = expUrl.split(";"); // 单张样表的WEB图片url拆分后的数组
    // for(int j = 0; j < expUrlArr.length; j++) {
    // if(!"".equals(expUrlArr[j])) {
    // oneExpEmpHiPicUrlList.add(expUrlArr[j]);
    // }
    // }
    // }
    // }
    // if(oneExpEmpHiPicUrlList.size() > 0) {
    // allExpEmpHiPicUrlList.add(oneExpEmpHiPicUrlList);
    // }
    // }
    // }
    //
    // if(allExpEmpHiPicUrlList.size() == 0) { // 没有相关联的样表目录或存在目录但没有同步图片
    // List<String> noExampleForm = new ArrayList<String>();
    // noExampleForm.add("no");
    // allExpEmpHiPicUrlList.add(noExampleForm);
    // }
    //
    // result.put("total", total);
    // result.put("result", allExpEmpHiPicUrlList.get(0));
    //
    // return result;
    // }

    /**
     * 表格详情页面 样表分页
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/pagination/exampleForm", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> exampleFormPagination(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        Integer pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        // Integer itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage")) ;
        Integer itemsPerPage = 1; // 默认每页显示一条记录
        String id = request.getParameter("id");
        String flag = request.getParameter("flag");
        Form form = null;
        if (id != null && !"".equals(id)) {
            form = formService.getFormById(id);
        }

        String formTitle = "";
        String formSource = "";
        String formAreaTag = "";
        List<Form> listFormdown=null;
        List<Form> expFormList = null; // 相关联的样表列表
        List<List<Map<String, Object>>> allExpEmpHiPicUrlList = new ArrayList<List<Map<String, Object>>>(); // 一张空表对应的所有样表的WEB图片
        BigInteger total = null; // 相关联的样表的数量
        if (form != null) {
            formTitle = "%" + form.getTitle() + "%";
            formSource = form.getSource();
            formAreaTag = formService.getAreaTagById(form.getId());
        }
        // 根据空表的标题、来源和地域标签获取相对应的样表
        if (!"".equals(formTitle) && !"".equals(formSource) && !"".equals(formAreaTag) && "1".equals(flag)) {
            expFormList = formService.getExampleFormList(formTitle, formSource, formAreaTag, pageIndex, itemsPerPage);
            total = formService.getExampleFormListCount(formTitle, formSource, formAreaTag);
        } else if (!"".equals(formTitle) && "0".equals(flag)) {
            expFormList = formService.getOtherExampleFormList(formTitle, pageIndex, itemsPerPage);
            total = formService.getOtherExampleFormListCount(formTitle);
        }
        if (expFormList != null) {
            for (int i = 0; i < expFormList.size(); i++) {
                Form f = expFormList.get(i);
                List<Map<String, Object>> oneExpList = new ArrayList<Map<String, Object>>();
                if (f != null) {
                    String expUrl = f.getEmpHiPicUrl(); // 单张样表的WEB图片的String
                    String expSource = f.getSource(); // 来源
                    if (!"".equals(expUrl) && expUrl != null) {
                        String expUrlArr[] = expUrl.split(";"); // 单张样表的WEB图片url拆分后的数组
                        for (int j = 0; j < expUrlArr.length; j++) {
                            if (!"".equals(expUrlArr[j])) {
                                Map<String, Object> oneExp = new HashMap<String, Object>();
                                oneExp.put("empHiPicUrl", expUrlArr[j]); // 图片url
                                oneExp.put("source", expSource); // 来源
                                oneExpList.add(oneExp);
                            }
                        }
                    }
                }
                if (oneExpList.size() > 0) {
                    allExpEmpHiPicUrlList.add(oneExpList);
                }
            }
        }

        if (allExpEmpHiPicUrlList.size() == 0) { // 没有相关联的样表目录或存在目录但没有同步图片
            List<Map<String, Object>> noExampleForm = new ArrayList<Map<String, Object>>();
            Map<String, Object> noExample = new HashMap<String, Object>();
            noExample.put("empHiPicUrl", "no");
            noExampleForm.add(noExample);
            allExpEmpHiPicUrlList.add(noExampleForm);
        }
     
            if(expFormList.size()>0&&StringUtils.isNotBlank(expFormList.get(0).getEmpUrl())){
                 String[] formurls=null;
                 listFormdown=new ArrayList<Form>();
                if(expFormList.get(0).getEmpUrl().indexOf(";")==0){
                    formurls=expFormList.get(0).getEmpUrl().substring(1, expFormList.get(0).getEmpUrl().length()).split(";") ;
                }else{
                    formurls=expFormList.get(0).getEmpUrl().split(";") ;
                }
                for(int f=0;f<formurls.length;f++){
                    Form formdownload=new Form();
                    String formsuffix=formurls[f].substring(formurls[f].lastIndexOf("."),formurls[f].length());
                    formdownload.setEmpUrl(formurls[f]);
                    formdownload.setSuffix(formsuffix);
                    formdownload.setTitle(form.getTitle());
                    listFormdown.add(formdownload);
                }
            }
        result.put("total", total);
        result.put("result", allExpEmpHiPicUrlList.get(0));
        result.put("sampleTable", listFormdown);

        return result;
    }

    /**
     * 专题详情查询
     * 
     * @param model
     * @param infoType
     * @param id
     * @return
     */
    @RequestMapping(value = "/knowledge_Detail/{infoType}/{id}", method = RequestMethod.GET)
    public String doknowledge_Detail(Model model, @PathVariable("infoType") Integer infoType, @PathVariable("id") String id,
            HttpServletRequest request, @PageableDefaults(value = 10) Pageable pageable, @RequestParam("date") String date) {
        try {
            Segment segment = null;// 切片对象
            switch (infoType) {
            case 2:// 切片类型知识
                segment = infoService.getSegmentById(id);// 通过ID得到切片知识对象
                model.addAttribute("segment", segment);
                // 喜欢数和赞
                long likeCount = likeCountService.searchCount(id).longValue();
                long collectCount = bookmarkService.searchCollectCount(id).longValue();
                model.addAttribute("likeCount", likeCount);
                model.addAttribute("collectCount", collectCount);

                return "/page/specialKnowledge/web/clauseDetail_context";
            }
        } catch (Exception e) {
            return "forword:/search";
        }
        return null;
    }
    
    /**
     * 切片详情目录列表异步加载
     * @param model
     * @param source
     * @param request
     * @param pageable
     * @param date
     * @return
     */
    @RequestMapping(value = "/knowledge_Dir/{source}", method = RequestMethod.GET)
    public String doknowledge_Dir(Model model,@PathVariable("source") String source,
            HttpServletRequest request, @PageableDefaults(value = 10) Pageable pageable, @RequestParam("date") String date) {
    	List<Segment> segList = null;
        if (source != null&&!"".equals(source)) {
            List<SegmentDir> segDir = new ArrayList<SegmentDir>();
            List segmentDir = specialService.getListBaseBySourceN(source);
            for (int i = 0; i < segmentDir.size(); i++) {
                Object[] oj = (Object[]) segmentDir.get(i);
                String sid = (String) oj[0];
                String segItem = (String) oj[1];
                String titles = (String) oj[2];
                SegmentDir sd = new SegmentDir();
                if (!"".equals(sid) && null != sid)
                    sd.setId(sid);
                if (!"".equals(segItem) && null != segItem)
                    sd.setSegItem(segItem);
                if (!"".equals(titles) && null != titles)
                    sd.setTitle(titles);
                segDir.add(sd);
            }
            model.addAttribute("segList", segDir);
        }
        return "/page/specialKnowledge/web/clauseDetail_dir";
    }

    /**
     * 专题详情查询
     * 
     * @param model
     * @param infoType
     * @param id
     * @return
     */
    @RequestMapping(value = "/specialDetail/{infoType}/{id}", method = RequestMethod.GET)
    public String doSpecialDetail(Model model, @PathVariable("infoType") Integer infoType, @PathVariable("id") String id, HttpServletRequest request,
            @PageableDefaults(value = 10) Pageable pageable) {
        HttpSession session = request.getSession();
        String returnurl = null;
        try {
            List<BaseSearchDto> listSource = null;
            List<BaseSearchDto> listForm = null;
            Segment segment = null;// 切片对象
            Files files = null;// 文件对象
            Form form = null;// 表格对象
            Video video = null;// 视频对象
            Image image = null;// 图片对象

            switch (infoType) {
            case 1:// 文件类型知识
                files = filesService.getFileById(id);
                model.addAttribute("files", files);
                return "page/knowledge/filesDetail";
            case 2:// 切片类型知识
                segment = infoService.getSegmentById(id);// 通过ID得到切片知识对象
                if (segment != null) {
                    listSource = relatedKnoledge("Source:" + segment == null ? null : segment.getSource(), 2);// 前十条相关的来源
                    listForm = relatedKnoledge(segment.getSource(), 4);// 前十条表格相关的
                }
                List<Segment> segList = null;
                if (segment != null && segment.getSource() != null) {
                    /* segList = specialService.getListBaseBySource(segment.getSource()); */
                    List<SegmentDir> segDir = new ArrayList<SegmentDir>();
                    List segmentDir = specialService.getListBaseBySourceN(segment.getSource());
                    for (int i = 0; i < segmentDir.size(); i++) {
                        Object[] oj = (Object[]) segmentDir.get(i);
                        String sid = (String) oj[0];
                        String segItem = (String) oj[1];
                        String title = (String) oj[2];
                        SegmentDir sd = new SegmentDir();
                        if (!"".equals(sid) && null != sid)
                            sd.setId(sid);
                        if (!"".equals(segItem) && null != segItem)
                            sd.setSegItem(segItem);
                        if (!"".equals(title) && null != title)
                            sd.setTitle(title);
                        segDir.add(sd);
                    }
                    model.addAttribute("segList", segDir);
                }
                /* model.addAttribute("segList",segList); */
                model.addAttribute("listSource", listSource);
                model.addAttribute("listForm", listForm);
                model.addAttribute("segment", segment);
                return "/page/specialKnowledge/web/clauseDetail";
            case 3:// 视频类型知识
                   // model.addAttribute("segment",s);
                video = videoService.getVideoById(id);
                //System.out.println(video.getId());
                model.addAttribute("video", video);
                return "page/knowledge/videoDetail";
            case 4:// 表格类型知识
                form = formService.getFormById(id);
                List<String> hiPicUrlSet = new ArrayList<String>();
                String hiPicUrl = "";// 声明web展示图片的URL串
                if (null != form && !"".equals(form)) {// 如果form不为空，则从中取出URL串
                    hiPicUrl = form.getEmpHiPicUrl();
                    listForm = relatedKnoledge(form.getSource(), 4);// 前十条表格相关的
                }
                if (hiPicUrl != null && !"".equals(hiPicUrl)) { // 获取WEB图片的SET
                    String hiPicUrlArray[] = hiPicUrl.split(";");
                    for (int i = 0; i < hiPicUrlArray.length; i++) {
                        if (!"".equals(hiPicUrlArray[i]) && hiPicUrlArray[i] != null) {
                            hiPicUrlSet.add(hiPicUrlArray[i]);
                        }
                    }
                }

                model.addAttribute("listForm", listForm);
                model.addAttribute("picUrls", hiPicUrlSet);
                model.addAttribute("form", form);
                return "page/knowledge/formDetail";
            case 5:// 图片类知识
                image = imageService.getImageById(id);
                List<String> imageUrlSet = new ArrayList<String>();
                String imageUrl = "";// 声明web展示图片的URL串
                if (null != image && !"".equals(image)) {// 如果form不为空，则从中取出URL串
                    imageUrl = image.getUrl();
                    listSource = relatedKnoledge("Source:" + image == null ? null : image.getSource(), 5);// 前十条相关的来源
                    // listForm=relatedKnoledge(segment.getSource(),4);//前十条表格相关的
                }
                if (imageUrl != null && !"".equals(imageUrl)) { // 获取WEB图片的SET
                    String imageUrlArray[] = imageUrl.split(";");
                    for (int i = 0; i < imageUrlArray.length; i++) {
                        if (!"".equals(imageUrlArray[i]) && imageUrlArray[i] != null) {
                            imageUrlSet.add(imageUrlArray[i]);
                        }
                    }
                }
                model.addAttribute("imageUrls", imageUrlSet);
                model.addAttribute("image", image);
                returnurl = "page/knowledge/imageDetail";
                return returnurl;
            }
        } catch (Exception e) {
            // returnurl= "page/knowledge/search";
            return "forword:/list";
        }

        return null;
    }

    public Specification<Base> getWhereClause(final String title, final Integer infoType) {
        return new Specification<Base>() {
            @Override
            public Predicate toPredicate(Root<Base> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                // 标签类型
                // predicate.getExpressions().add(cb.equal(root.<String>get("infoType"), infoType));
                // 创建时间倒叙
                query.orderBy(cb.desc(root.get("createDate")));
                if (title != null && !"".equals(title)) {
                    predicate.getExpressions().add(cb.like(root.<String> get("title"), "%" + title.trim() + "%"));
                }
                if (infoType > 0) {
                    predicate.getExpressions().add(cb.equal(root.<Integer> get("infoType"), infoType));
                }
                return predicate;
            }
        };
    }

    @RequestMapping(value = "/addComments", method = RequestMethod.POST)
    public void addComments(@RequestParam("authsUserId") String authsUserId, HttpServletRequest request,
            @RequestParam("infoBaseId") String infoBaseId, @RequestParam("content") String content, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String resultJson = "{'success':true}";

        Comments cm = new Comments();
        cm.setAuthsUserId(authsUserId);// 评论人ID
        cm.setContent(content);// 评论内容
        cm.setInfoBaseId(infoBaseId);// 知识ID
        cm.setCreateDate(new Date());// 创建时间
        cm.setIssuedBy(user.getUsername());// 评论人名称
        cm.setIssuedByUrl("");// 评论人头像URL
        try {
            pubService.saveComments(cm);
            resultJson = "{'success':true}";
        } catch (Exception e) {
            e.printStackTrace();
            resultJson = "{'msg':'保存失败!'}";
        }
        commonService.responseJsonBody(response, resultJson);
    }

    /**
     * 知识专题--表格--施工资料
     * 
     * @return
     */
    @RequestMapping(value = "/knowledge/web/form", method = RequestMethod.GET)
    public String formPageNew(Model model, HttpServletRequest request) {
        // List<Tag> tags = specialService.listTag();// 28个分部分项
        long pdCode = Long.parseLong(request.getParameter("rootCode"));
        String rstr = "/page/knowledge/table";
        Long code = 10000000000000000l;// 分部分项标签的code
        String rc = request.getParameter("rootCode");
        String tagName = specialService.getTagNameByCode(rc);
        if (tagName != null && !tagName.equals("")) {
            model.addAttribute("tagName", tagName);
        } else {
            model.addAttribute("tagName", "");
        }
        if (pdCode == 40602000000000000L) {
            rstr = "/page/knowledge/table_jcyb";
        }
        if (pdCode == 40703000000000000L || pdCode == 40702000000000000L) {
            rstr = "/page/knowledge/table_aqzl";
        }
        model.addAttribute("Source", this.getPdTag(code, pdCode, 1));
        // model.addAttribute("Source", tags);
        model.addAttribute("rootCode", request.getParameter("rootCode"));
        model.addAttribute("type", request.getParameter("type"));
        model.addAttribute("Source", this.getPdTag(code, pdCode, 1));
        return rstr;
    }

    @RequestMapping(value = "/knowledge/web/sgfaform", method = RequestMethod.GET)
    public String sgfaform(Model model, HttpServletRequest request) {
        // List<Tag> tags = specialService.listTag();// 28个分部分项

        Long code = 10000000000000000l;// 分部分项标签的code
        long pdCode = Long.parseLong(request.getParameter("rootCode"));
        model.addAttribute("Source", this.getPdTag(code, pdCode, 1));

        String rc = request.getParameter("rootCode");
        String tagName = specialService.getTagNameByCode(rc);
        if (tagName != null && !tagName.equals("")) {
            model.addAttribute("tagName", tagName);
        } else {
            model.addAttribute("tagName", "");
        }

        model.addAttribute("Source", this.getPdTag(code, pdCode, 1));
        // model.addAttribute("Source", tags);
        model.addAttribute("rootCode", request.getParameter("rootCode"));
        model.addAttribute("type", request.getParameter("type"));

        return "/page/knowledge/sgfa";
    }

    /**
     * 知识专题--没有分部分项的模板
     * 
     * @return
     */
    @RequestMapping(value = "/knowledge/web/newTable", method = RequestMethod.GET)
    public String formPageNewTable(Model model, HttpServletRequest request) {
    	//28个分部分项
        List<Tag> tags = specialService.listTag();
        //获取rootCode
        String rc = request.getParameter("rootCode");
        //根据rootCode查询标签的名称
        String tagName = specialService.getTagNameByCode(rc);
        //如果标签的名称存在且不为空
        if (tagName != null && !tagName.equals("")) {
        	//将标签的名称显示在当前页面
            model.addAttribute("tagName", tagName);
        } else {
        	//如果没有名称，显示""
            model.addAttribute("tagName", "");
        }
        model.addAttribute("Source", tags);
        model.addAttribute("rootCode", request.getParameter("rootCode"));
        model.addAttribute("type", request.getParameter("type"));

        return "/page/knowledge/newTable";
    }

    
    /**
     * 针对专利技术和法律法规来添加按时间倒叙排列规则
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/knowledge/web/displayKnowledgeByDate", method = RequestMethod.GET)
    public String formPageNewTableSortByDate(Model model, HttpServletRequest request) {
    	//28个分部分项
        List<Tag> tags = specialService.listTag();
        //获取rootCode
        String rc = request.getParameter("rootCode");
        //根据rootCode查询标签的名称
        String tagName = specialService.getTagNameByCode(rc);
        //如果标签的名称存在且不为空
        if (tagName != null && !tagName.equals("")) {
        	//将标签的名称显示在当前页面
            model.addAttribute("tagName", tagName);
        } else {
        	//如果没有名称，显示""
            model.addAttribute("tagName", "");
        }
        model.addAttribute("Source", tags);
        model.addAttribute("rootCode", request.getParameter("rootCode"));
        model.addAttribute("type", request.getParameter("type"));

        return "/page/knowledge/displayByDate";
    }
    
    
    @RequestMapping(value = "/tywViewer", method = RequestMethod.GET)
    public String tywViewer(Model model, HttpServletRequest request) {
    	//28个分部分项
        List<Tag> tags = specialService.listTag();
        //获取rootCode
        String rc = request.getParameter("rootCode");
        //根据rootCode查询标签的名称
        String tagName = specialService.getTagNameByCode(rc);
        //如果标签的名称存在且不为空
        if (tagName != null && !tagName.equals("")) {
        	//将标签的名称显示在当前页面
            model.addAttribute("tagName", tagName);
        } else {
        	//如果没有名称，显示""
            model.addAttribute("tagName", "");
        }
        model.addAttribute("Source", tags);
        model.addAttribute("rootCode", request.getParameter("rootCode"));
        model.addAttribute("type", request.getParameter("type"));

        return "/page/knowledge/flexpaper";
    }
    
    /**
     * 知识专题--案例/工法(只展示28个分布 无右箭头 )
     * 
     * @return
     */
    @RequestMapping(value = "/knowledge/web/otherTable", method = RequestMethod.GET)
    public String formPageNewJustSubitem(Model model, HttpServletRequest request) {
        /*
         * List<Tag> tags = specialService.listTag();// 28个分部分项 String rc = request.getParameter("rootCode"); String
         * tagName = specialService.getTagNameByCode(rc); if (tagName != null && !tagName.equals("")) {
         * model.addAttribute("tagName", tagName); } else { model.addAttribute("tagName", ""); }
         * model.addAttribute("Source", tags); model.addAttribute("rootCode", request.getParameter("rootCode"));
         * model.addAttribute("type", request.getParameter("type")); return "/page/knowledge/tableJustSubitem";
         */
        long pdCode = Long.parseLong(request.getParameter("rootCode"));
        String rstr = "/page/knowledge/tableJustSubitem";
        Long code = 10000000000000000l;// 分部分项标签的code
        String rc = request.getParameter("rootCode");
        String tagName = specialService.getTagNameByCode(rc);
        if (tagName != null && !tagName.equals("")) {
            model.addAttribute("tagName", tagName);
        } else {
            model.addAttribute("tagName", "");
        }
        if (pdCode == 40602000000000000L) {
            rstr = "/page/knowledge/table_jcyb";
        }
        if (pdCode == 40703000000000000L || pdCode == 40702000000000000L) {
            rstr = "/page/knowledge/table_aqzl";
        }
        model.addAttribute("rootCode", request.getParameter("rootCode"));
        model.addAttribute("type", request.getParameter("type"));
        model.addAttribute("Source", this.getPdTag(code, pdCode, 1));
        return rstr;
    }

    /**
     * 知识专题--强制性条文(知识性质子分类去掉 )
     * 
     * @return
     */
    @RequestMapping(value = "/knowledge/web/anotherTable", method = RequestMethod.GET)
    public String formPageNewNoArchitectonic(Model model, HttpServletRequest request) {
        // List<Tag> tags = specialService.listTag();// 28个分部分项
        // String rc = request.getParameter("rootCode");
        // String tagName = specialService.getTagNameByCode(rc);
        // if (tagName != null && !tagName.equals("")) {
        // model.addAttribute("tagName", tagName);
        // } else {
        // model.addAttribute("tagName", "");
        // }
        // model.addAttribute("Source", tags);
        // model.addAttribute("rootCode", request.getParameter("rootCode"));
        // model.addAttribute("type", request.getParameter("type"));
        //
        // return "/page/knowledge/tableNoArchitectonic";

        // List<Tag> tags = specialService.listTag();// 28个分部分项
        long pdCode = Long.parseLong(request.getParameter("rootCode"));
        String rstr = "/page/knowledge/tableNoArchitectonic";
        Long code = 10000000000000000l;// 分部分项标签的code
        String rc = request.getParameter("rootCode");
        String tagName = specialService.getTagNameByCode(rc);
        if (tagName != null && !tagName.equals("")) {
            model.addAttribute("tagName", tagName);
        } else {
            model.addAttribute("tagName", "");
        }
        if (pdCode == 40602000000000000L) {
            rstr = "/page/knowledge/table_jcyb";
        }
        if (pdCode == 40703000000000000L || pdCode == 40702000000000000L) {
            rstr = "/page/knowledge/table_aqzl";
        }
        model.addAttribute("rootCode", request.getParameter("rootCode"));
        model.addAttribute("type", request.getParameter("type"));
        model.addAttribute("Source", this.getPdTag(code, pdCode, 1));
        return rstr;
    }

    /**
     * 频道里通过下面三个参数得到下级存在内容的标签
     * 
     * @param code
     * @param pdCode
     * @param level
     * @return
     */
    private List<PdTag> getPdTag(Long code, Long pdCode, Integer level) {
        //System.out.println("参数code:pdCode:level:" + code + "----" + "pdCode" + "----" + level);
        List<PdTag> tag = new ArrayList<PdTag>();
        List tags = specialService.getListAllTagByParentCodeAndPdCodeAndPLevel(code, pdCode, level);// 取出下一层的所有标签
        for (int i = 0; i < tags.size(); i++) {
            Object[] oj = (Object[]) tags.get(i);

            // Integer leaf = (Integer) oj[0];
            java.lang.Byte bleaf = (java.lang.Byte) oj[0];
            Integer leaf = bleaf.intValue();

            // Integer levels = (Integer) oj[1];
            java.lang.Byte blevels = (java.lang.Byte) oj[1];
            Integer levels = blevels.intValue();

            // Long codes = (Long) oj[2];
            BigInteger bCodes = (BigInteger) oj[2];
            Long codes = bCodes.longValue();

            String name = (String) oj[3];

            // Integer count = (Integer) oj[4];
            BigInteger bcount = (BigInteger) oj[4];
            Integer count = bcount.intValue();
            //System.out.println("查询结果name:count:" + name + "----" + count);
            PdTag pdTag = new PdTag();
            if (!"".equals(leaf) && null != leaf)
                pdTag.setLeaf(leaf);
            if (!"".equals(levels) && null != levels)
                pdTag.setLevel(levels);
            if (!"".equals(codes) && null != codes)
                pdTag.setCode(codes);
            if (!"".equals(name) && null != name)
                pdTag.setName(name);
            if (!"".equals(count) && null != count)
                pdTag.setCount(count);
            tag.add(pdTag);
        }
        return tag;
    }

    /**
     * 表格查询
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/dolistBaseBy", method = RequestMethod.POST)
    @ResponseBody
    public Map dolistBaseBy(HttpServletRequest request, Model model) {
         Map map = new HashMap();
        map = super.dolistBaseBy(request, tagService, infoService);
        return map;
    }

    /**
     * 查询下一级标签
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/doTableTagListajax", method = RequestMethod.GET)
    @ResponseBody
    public List<Tag> doTableTagListajax(Model model, HttpServletRequest request) {
        Long code = Long.parseLong(request.getParameter("code"));// 取出点击标签的code
        List<Tag> tag = specialService.getListAllTagByParentCode(code);// 取出下一层的所有标签
        return tag;
    }

    /**
     * 查询分部分项的一个标签下所有块的知识
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/knowledge/web/fbfxChannel", method = RequestMethod.GET)
    public String fbfxChannel(Model model, HttpServletRequest request) {

        String fbfxcode = request.getParameter("fbfxCode");
        String peoplecode = request.getParameter("peoplecode");
        String areacode = request.getParameter("areacode");

        if (StringUtils.isNotBlank(fbfxcode)) {
            String code = fbfxcode.replace("00", "");
            code += "__";
            int level = (fbfxcode.replace("00", "").length() - 1) / 2;
            for (int i = 0; i < (8 - level - 1); i++) {
                code += "00";
            }
            List<Tag> tags = specialService.listTag(code, fbfxcode);
            model.addAttribute("Source", tags);

            List<Tag> knowledgelist = specialService.getListAllTagByParentCode(new Long("40000000000000000"));
            model.addAttribute("knowledgelist", knowledgelist);
            
            List<BaseTagModule> baselist = infoService.findAllBaseTagModule(fbfxcode, peoplecode, areacode);
            model.addAttribute("baseTaglist", baselist);
        }
        model.addAttribute("tagName", "分部分项");
        model.addAttribute("fbfxCode", request.getParameter("fbfxCode"));
        return "/page/knowledge/fbfxChannel";
    }

    /**
     * 查询分部分项知识列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/knowledge/web/fbfxChannelajax")
    @ResponseBody
    public List<BaseTagModule> fbfxChannelajax(Model model, HttpServletRequest request) {
        List<BaseTagModule> baselist = new ArrayList<BaseTagModule>();
        String fbfxcode = request.getParameter("fbfxCode");
        String peoplecode = request.getParameter("peoplecode");
        String areacode = request.getParameter("areacode");
        Tag tagarea = tagService.findTagByName(areacode);
        // 说明全国的时候按照没有地区处理
        if (tagarea != null && (!"23100000000000000".equals(tagarea.getCode()))) {
            areacode = tagarea.getCode() + "";
        } else {
            areacode = "";
        }

        baselist = infoService.findAllBaseTagModule(fbfxcode, peoplecode, areacode);

        if (StringUtils.isNotBlank(fbfxcode)) {
            String code = fbfxcode.replace("00", "");
            code += "__";
            int level = (fbfxcode.replace("00", "").length() - 1) / 2;
            for (int i = 0; i < (8 - level - 1); i++) {
                code += "00";
            }
            List<Tag> tags = specialService.listTag(code, fbfxcode);
            model.addAttribute("Source", tags);
        }

        return baselist;
    }

    /**
     * 从标签体系获取省份列表
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/tag/getProvinceTag", method = RequestMethod.POST)
    @ResponseBody
    public List<Tag> getProvinceTag(HttpServletRequest request, HttpServletResponse response) {
        List<Tag> taglist = tagService.getAllTagsByParentCode("20000000000000000", 1);
        return taglist;
    }
    /**
     *获得搜索热词
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/tag/findsearchhotword", method = RequestMethod.POST)
    @ResponseBody
    public List<KeyWord> findsearchhotword(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "istag") int istag) {
        List<KeyWord> taglist = keyWordService.searchHotWords(istag);
        return taglist;
    }
    /**
     * rootCode为必填 父级根标签code code 当前标签code 查询下一级标签
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTagList")
    @ResponseBody
    public List<Tag> getTagList(Model model, HttpServletRequest request) {
        String pc = request.getParameter("code");
        if (StringUtils.isBlank(pc)) {
            pc = request.getParameter("rootCode");
        }
        Long code = Long.parseLong(pc);
        List<Tag> tag = new ArrayList<Tag>();
        if (StringUtils.isBlank(request.getParameter("level"))) {
            Tag root = tagService.getTagByCode(code);
            tag.add(root);
        } else {
            tag = specialService.getListAllTagByParentCode(code);
        }
        return tag;
    }

//    /**
//     * 跳转到大文件详细页
//     * 
//     * @return
//     */
//    @RequestMapping(value = "/knowledge/web/filedetail", method = RequestMethod.GET)
//    public String toFileDetail(Model model, HttpServletRequest request) {
//        return "/page/knowledge/detail_file";
//    }

    /**
     * 跳转到来源详细页面
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/knowledge/web/bookdetail/{sourceCompose}", method = RequestMethod.GET)
    public String toBookDetailPage(@PathVariable("sourceCompose") String sourceCompose, Model model, HttpServletRequest request) {

        // 标准名称
        String standardName = "";
        // 标准编号
        String standardNo = "";
        // 来源对象
        Source s = null;
        // 标准目次
        String catalog = "";
        // 解码后的来源
        String sc = "";
        // 标准目次
        String catalogArray[] = null;
        List<String> catalogList = new ArrayList<String>();
        // 标准类型
        String standardType = "";
        // 同类型其他来源
        List<Source> otherList = new ArrayList<Source>();

        if (sourceCompose != null && !"".equals(sourceCompose)) {
            try {
                sc = java.net.URLDecoder.decode(sourceCompose, "UTF-8"); // url参数解码
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (sc.contains("《") || sc.contains("》")) {
                standardName = sc.substring(1, sc.indexOf("》"));
                standardNo = sc.substring(sc.indexOf("》") + 1);
            }
        }
        if (!"".equals(standardName) && !"".equals(standardNo)) { // 知识切片
            if (sourceService.getOneByNameAndNo(standardName, standardNo).size() > 0) {
                s = sourceService.getOneByNameAndNo(standardName, standardNo).get(0); // 若有重复数据 取第一条
            }
        } else if (!"".equals(standardName) && "".equals(standardNo)) { // 表格
            if (sourceService.getOneByName(standardName).size() > 0) {
                s = sourceService.getOneByName(standardName).get(0);
            }
        }
        if (s != null) {
            catalog = s.getCatalog();
            standardType = s.getStandardType();
        }
        // 获取标准目次 拆分 保存在list中
        if (!"".equals(catalog) && catalog != null) {
            catalogArray = catalog.split("\\n");
        }
        if (catalogArray != null) {
            for (int i = 0; i < catalogArray.length; i++) {
                if (!"".equals(catalogArray[i])) {
                    catalogList.add(catalogArray[i]);
                }
            }
        }

        // 获取同类型的其他来源信息
        otherList = sourceService.getSameTypeSourceList(standardType, standardName);

        model.addAttribute("book", s);
        model.addAttribute("catalogList", catalogList);
        model.addAttribute("otherList", otherList);

        return "/page/knowledge/detail_book";
    }

    /**
     * 获取当前大文件的总页数
     */
    @RequestMapping(value = "/bigFilesPagination", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> bigFilesPagination(HttpServletRequest request) {
        String fileId = request.getParameter("fileId");
        Map<String, Object> msg = new HashMap<String, Object>();
        //定义总数
        BigInteger total;
        //根据大文件id查询总页数
        total = fpService.getBigFileTotal(fileId);
        msg.put("total", total);
        return msg;
    }

    /**
     * 根据大文件的页码和文件id查询大文件在服务器上的路径
     */
    @RequestMapping(value = "/searchUrl", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> searchUrl(HttpServletRequest request, HttpServletResponse response, Model model) {

        Map<String, Object> result = new HashMap<String, Object>();

        //获取当前大文件的页码
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        //获取当前大文件的id
        String fileId = request.getParameter("fileId");
        //根据页码和id查询大文件的路径
        String url = fpService.getFileUrl(fileId, pageNum);

        if (url != null && !url.equals("")) {
            result.put("success", true);
        }

        return result;
    }

    /**
     * 根据当前点击的目录的路径查询当前文件的页码，动态改变分页的数字
     */
    @RequestMapping(value = "/changePageNum", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changePageNum(HttpServletRequest request, HttpServletResponse response, Model model) {

        Map<String, Object> result = new HashMap<String, Object>();

        //获取点击目录后要显示的大文件的url
        String gUrl = request.getParameter("gUrl");
        //将路径的锚点截取掉
        String finalUrl = gUrl.substring(0, gUrl.indexOf("#"));
        //获取锚点
        String aPoint = gUrl.substring(gUrl.indexOf("#"));
        //根据大文件的路径查询当前大文件的页码
        int pageNum = fpService.getPageNumByUrl(finalUrl);
        //根据路径查询大文件的id
        String fileId = fpService.getIdByUrl(finalUrl);
        if (finalUrl != null && !finalUrl.equals("")) {
            result.put("success", true);
            result.put("changeNum", pageNum);
            result.put("changeId", fileId);
            result.put("aPoint", aPoint);
        }

        return result;
    }

    //用于大文件内容的显示
    @RequestMapping(value = "/openFile", method = RequestMethod.POST)
     public String openFile(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(value = "fileId", required = false) String fileId,@RequestParam(value = "pageNum", required = false) int pageNum) {
        //获取大文件的文件id
    	//String fileId = request.getParameter("fileId");
    	//获取当前要显示的大文件的页码（在第几页）
       // int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        //根据文件id和所在页码查询此大文件在文件服务器上的路径
        String url = fpService.getFileUrl(fileId, pageNum);
        //将路径的具体ip地址截取掉，保留group1在内的剩余路径
        String fUrl = url.substring(url.indexOf("group1"));
        //根据截取后的路径去文件服务器上下载该文件用于在前台显示
        String fileContent = fdfsService.downloadFile(fUrl);
        model.addAttribute("fileContent", fileContent);
        model.addAttribute("url", url);
       // return "/createFile";
         return "page/files/createFile";

    }

    @RequestMapping(value = "/index/information", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<News> searchByid(@PageableDefaults(value = 8) Pageable pageable, @RequestParam(value = "type", required = false) Integer type) {
        List<News> list = null;
        try {
            // 1 动态，2资讯
            type = null == type ? 1 : type;
            String id = "";
            if (type == 1) {
                id = "2c9f850e3e63bd8d013e6888e84d04c7";
                list = newsService.searchByid(id, 0, 8);
            }
            if (type == 2) {
                 list = newsService.latestChange(0, 8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // @RequestMapping(value = "/toOpenFile", method = RequestMethod.GET)
    // public String toOpenFile(HttpServletRequest request,HttpServletResponse response,Model model){
    // return "page/files/createFile";
    // }
    
    /**
     * 跳转都书籍列表页
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/knowledge/web/bookList", method = RequestMethod.GET)
    public String tobookListPage(HttpServletRequest request, Model model) {

    	 String rc = request.getParameter("rootCode");
         String tagName = specialService.getTagNameByCode(rc);
         if (tagName != null && !tagName.equals("")) {
             model.addAttribute("tagName", tagName);
         } else {
             model.addAttribute("tagName", "");
         }
    	
        model.addAttribute("rootCode", rc);
        // 来源类型 1：标准	2：图集
        model.addAttribute("sourceType", request.getParameter("sourceType"));
        
        return "/page/knowledge/bookList";
    }
    @RequestMapping(value = "/downloadhttp", method = RequestMethod.GET)
    public void downloadhttp(HttpServletRequest request,HttpServletResponse response, Model model, @RequestParam(value = "fileName", required = false) String fileName,@RequestParam(value = "downLoadPath", required = false) String downLoadPath) {
       try {
           if(StringUtils.isNotBlank(downLoadPath)){
               String pathSuffix=downLoadPath.substring(downLoadPath.lastIndexOf("."), downLoadPath.length());
               ioUtil.httpDownloadURL(request, response, fileName+pathSuffix,downLoadPath);
           }
            } catch (Exception e) {
                e.printStackTrace();
            }
        
    }
    /**
     * 书籍列表分页
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/pagination/bookList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> bookListPagination(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Source> sourceList = null;
        BigInteger sourceTotal = null;
        String standardTitle = "";
        String standardType = "";
        // 来源类型 1：标准 2：图集
        String sourceType = request.getParameter("sourceType");
        // 首次访问
        String firstVisit = request.getParameter("firstVisit");
        try {
            Integer pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
            Integer itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
            standardTitle = "%" + java.net.URLDecoder.decode(request.getParameter("standardTitle"), "UTF-8") + "%"; // 标准名称
            String dateFrom = request.getParameter("dateFrom"); // 起始日期
            String dateTo = request.getParameter("dateTo"); // 终止日期
            standardType = java.net.URLDecoder.decode(request.getParameter("standardType"), "UTF-8"); // 标准类型(标签树)
            
            if("true".equals(firstVisit)) {	// 初始化页面，查询所有
            	sourceList = sourceService.getSourceListAll(pageIndex, itemsPerPage, sourceType);
                sourceTotal = sourceService.getSourceTotalAll(sourceType);
            } else if(!("".equals(dateFrom) && "".equals(dateTo)) && "".equals(standardType)) {	// 点击查询按钮查询，有实施日期(暂不带st)
            	if(!"".equals(dateFrom) && !"".equals(dateTo)) {
            		sourceList = sourceService.getSourceListByExecuteDate(standardTitle, dateFrom, dateTo, pageIndex, itemsPerPage, sourceType);
                    sourceTotal = sourceService.getSourceTotalByExecuteDate(standardTitle, dateFrom, dateTo, sourceType);
            	} else if(!"".equals(dateFrom) && "".equals(dateTo)) {
            		sourceList = sourceService.getSourceListByExecuteDateOnlyDateFrom(standardTitle, dateFrom, pageIndex, itemsPerPage, sourceType);
                    sourceTotal = sourceService.getSourceTotalByExecuteDateOnlyDateFrom(standardTitle, dateFrom, sourceType);
            	} else if("".equals(dateFrom) && !"".equals(dateTo)) {
            		sourceList = sourceService.getSourceListByExecuteDateOnlyDateTo(standardTitle, dateTo, pageIndex, itemsPerPage, sourceType);
                    sourceTotal = sourceService.getSourceTotalByExecuteDateOnlyDateTo(standardTitle, dateTo, sourceType);
            	}
            } else if("".equals(dateFrom) && "".equals(dateTo) && "".equals(standardType)) {	// 点击查询按钮查询，无实施日期(暂不带st)
            	 sourceList = sourceService.getSourceListNoExecuteDate(standardTitle, pageIndex, itemsPerPage, sourceType);
                 sourceTotal = sourceService.getSourceTotalNoExecuteDate(standardTitle, sourceType);
            } else if(standardType != null && !"".equals(standardType)) {	// 根据标准类型查询
	           	 sourceList = sourceService.getSourceListByStandardType(pageIndex, itemsPerPage, standardType, sourceType);
	           	 sourceTotal = sourceService.getSourceTotalByStandardType(standardType, sourceType);
           } 
           // date, title ,sourcetype, standardtype 
           
           
            
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        result.put("list", sourceList);
        result.put("total", sourceTotal);
        return result;
    }
    /**
     * 首页搜索
     * 
     * @param pageable
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/goChannel", method = RequestMethod.GET)
    public String goChannel(@PageableDefaults(value = 5) Pageable pageable, Model model, HttpServletRequest request,@RequestParam(value = "rootCode", required = false) String rootCode) {
        model.addAttribute("rootCode", rootCode);
        return "page/newstart/channel";
    }
    
    @RequestMapping(value = "/goChanneltagname", method = RequestMethod.POST)
    @ResponseBody
    public String goChanneltagname(@PageableDefaults(value = 5) Pageable pageable, Model model, HttpServletRequest request,@RequestParam(value = "tag_name", required = false) String tag_name) {
         Tag tag= tagService.findTagByTagName(tag_name);
        return "{result:" + tag.getCode() + "}";
     }
}
