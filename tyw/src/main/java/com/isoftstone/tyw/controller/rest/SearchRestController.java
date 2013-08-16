package com.isoftstone.tyw.controller.rest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.isoftstone.tyw.controller.web.KnowledgeController;
import com.isoftstone.tyw.dto.info.BaseDTO;
import com.isoftstone.tyw.dto.info.BaseModuleDTO;
import com.isoftstone.tyw.dto.info.BaseRecommendDTO;
import com.isoftstone.tyw.dto.info.BaseSearchDto;
import com.isoftstone.tyw.dto.info.ESPage;
import com.isoftstone.tyw.dto.info.NewsDTO;
import com.isoftstone.tyw.dto.info.SearchDetailDTO;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.BaseModule;
import com.isoftstone.tyw.entity.info.Files;
import com.isoftstone.tyw.entity.info.Form;
import com.isoftstone.tyw.entity.info.Image;
import com.isoftstone.tyw.entity.info.News;
import com.isoftstone.tyw.entity.info.Segment;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.entity.info.Video;
import com.isoftstone.tyw.repository.info.BaseModuleDtoDao;
import com.isoftstone.tyw.service.BaseService;
import com.isoftstone.tyw.service.ElasticsearchService;
import com.isoftstone.tyw.service.FilesService;
import com.isoftstone.tyw.service.FormService;
import com.isoftstone.tyw.service.ImageService;
import com.isoftstone.tyw.service.InfoService;
import com.isoftstone.tyw.service.TagService;
import com.isoftstone.tyw.service.VideoService;
import com.isoftstone.tyw.util.ApiPager;
import com.isoftstone.tyw.util.DateManager;
import com.isoftstone.tyw.util.JsonDateFormat;
import com.isoftstone.tyw.util.Pager;
import com.isoftstone.tyw.util.RefectEntity;

/**
 * @author liuyulong
 */
@Controller
public class SearchRestController {
    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private FilesService filesService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private FormService formService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BaseService baseService;

    /**
     * 搜索引擎接口
     * 
     * @param model
     * @param request
     * @param indices 要查询的索引名称,默认为tyw
     * @param type 类型1:文件,2:切片,3:视频,4:表格,5:图片
     * @param queryString 查询关键字
     * @param tagName 标签名称
     * @param heightPreTag 高亮前缀
     * @param heightPostTag 高亮后缀
     * @param sortField 排序的字段
     * @param sortOrder 排序规则 排序方式 ASC 或者 DESC
     * @param pageSize
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/api/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ESPage search(Model model, HttpServletRequest request, @RequestParam(value = "indices") String indices,
            @RequestParam(value = "type", required = false) Integer type, @RequestParam(value = "queryString") String queryString,
            @RequestParam(value = "tagName", required = false) String tagName,
            @RequestParam(value = "heightPreTag", required = false) String heightPreTag,
            @RequestParam(value = "heightPostTag", required = false) String heightPostTag,
            @RequestParam(value = "sortField", required = false) String sortField,
            @RequestParam(value = "sortOrder", required = false) String sortOrder,
            @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "pageNo", required = false) Integer pageNo) {
        indices = StringUtils.isEmpty(indices) ? "tyw" : indices;
        pageSize = null == pageSize ? 10 : pageSize;
        pageNo = null == pageNo ? 1 : pageNo;
        String resultType = resultType(null == type ? 0 : type);
        ESPage esPage = null;
        try {
            esPage = elasticsearchService.queryAndFilterByString(indices, resultType, queryString, tagName, heightPreTag, heightPostTag, sortField,
                    sortOrder, pageSize, pageNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return esPage;
    }

    /**
     * 查看详情
     * 
     * @param model
     * @param infoType 类型
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/detail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SearchDetailDTO detail(Model model, @RequestParam(value = "infoType") Integer infoType, @RequestParam(value = "id") String id,
            HttpServletRequest request) {
        SearchDetailDTO sdDto = null;
        Segment segment = null;// 切片对象
        Files files = null;// 文件对象
        Form form = null;// 表格对象
        Video video = null;// 视频对象
        Image image = null;// 图片对象
        try {
            baseService.modifyclickCount(id);
            switch (infoType) {
            case 1:// 文件类型知识
                sdDto = new SearchDetailDTO();
                files = filesService.getFileById(id);
                if (null != files) {
                    sdDto.setId(files.getId());
                    sdDto.setContent(files.getContent().replaceAll("\r\n", ""));
                    sdDto.setCreateDate(DateManager.dateFormat(files.getCreateDate()));
                    sdDto.setDescription(files.getDescription());
                    sdDto.setSource(files.getSource());
                    sdDto.setTitle(files.getTitle());
                    sdDto.setUrl(files.getUrl());
                }
                break;
            case 2:// 切片类型知识
                sdDto = new SearchDetailDTO();
                segment = infoService.getSegmentById(id);// 通过ID得到切片知识对象
                if (null != segment) {
                    sdDto.setId(segment.getId());
                    sdDto.setContent(segment.getContent().replaceAll("\r\n", ""));
                    sdDto.setCreateDate(DateManager.dateFormat(segment.getCreateDate()));
                    sdDto.setDescription(segment.getDescription());
                    sdDto.setSource(segment.getSource());
                    sdDto.setTitle(segment.getTitle());
                }
                break;
            case 3:// 视频类型知识
                sdDto = new SearchDetailDTO();
                video = videoService.getVideoById(id);
                if (null != video) {
                    sdDto.setId(video.getId());
                    sdDto.setContent(video.getContent());
                    sdDto.setCreateDate(DateManager.dateFormat(video.getCreateDate()));
                    sdDto.setDescription(video.getDescription());
                    sdDto.setSource(video.getSource());
                    sdDto.setTitle(video.getTitle());
                    sdDto.setUrl(video.getUrl());
                }
                break;
            case 4:// 表格类型知识
                sdDto = new SearchDetailDTO();
                form = formService.getFormById(id);
                if (null != form && !"".equals(form)) {// 如果form不为空，则从中取出URL串
                    sdDto.setId(form.getId());
                    sdDto.setContent(form.getDescContent());
                    sdDto.setCreateDate(DateManager.dateFormat(form.getCreateDate()));
                    sdDto.setDescription(form.getDescription());
                    sdDto.setSource(form.getSource());
                    sdDto.setTitle(form.getTitle());
                    sdDto.setUrl(form.getEmpHiPicUrl());
                }
                break;
            case 5:// 图片类知识
                sdDto = new SearchDetailDTO();
                image = imageService.getImageById(id);
                if (null != image && !"".equals(image)) {// 如果form不为空，则从中取出URL串
                    sdDto.setId(image.getId());
                    sdDto.setContent(image.getContent());
                    sdDto.setCreateDate(DateManager.dateFormat(image.getCreateDate()));
                    sdDto.setDescription(image.getDescription());
                    sdDto.setSource(image.getSource());
                    sdDto.setTitle(image.getTitle());
                    sdDto.setUrl(image.getUrl());
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdDto;
    }

    /**
     * 转换id
     * 
     * @param typeId
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
        }
        return resultType;
    }

    /**
     * 模块查询，查询某id下的所有文章和数量
     * 
     * @param pageable
     * @param id
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/api/search/module", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject tagmodule(@PageableDefaults(value = 2) Pageable pageable, @RequestParam(value = "id") String id,
            @RequestParam(value = "pageSize") Integer pageSize, @RequestParam(value = "pageNum") Integer pageNum) {
        pageSize = null == pageSize ? 10 : pageSize;
        JsonConfig jsonConfig = new JsonConfig();
        SearchRestController src = new SearchRestController();
        JSONObject object = null;
        String[] codes = null;
        BigInteger baseCount = null;
        try {
            baseCount = baseService.searchBaseModuleCount(id, id);
            List<BaseModule> list = baseService.searchBaseModule(id, id, (pageNum - 1) * pageSize, pageSize);
            ApiPager pager = new ApiPager(pageSize, pageNum, Integer.valueOf(baseCount.toString()), list);
            jsonConfig.registerJsonValueProcessor(Date.class, (JsonValueProcessor) new JsonDateFormat());
            object = JSONObject.fromObject(pager, jsonConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public String[] getCode(String code, int level) {
        String[] codes = new String[2];
        StringBuffer codelevel1 = new StringBuffer();
        StringBuffer codelevel2 = new StringBuffer();
        String subcode = String.valueOf(code).substring(level * 2 - 1, String.valueOf(code).length());
        int startlevel = (String.valueOf(code).length() - (level * 2 - 1));
        for (int i = 0; i < startlevel; i++) {
            codelevel1.append("0");
            codelevel2.append("9");
        }
        String startcode = String.valueOf(code).toString().replace(subcode, codelevel1);
        String endtcode = String.valueOf(code).toString().replace(subcode, codelevel2);
        codes[0] = startcode;
        codes[1] = endtcode;
        return codes;
    }

    /**
     * 获取相关内容
     * 
     * @param model
     * @param request
     * @param queryString 相关关键字
     * @param type 类型
     * @param pageSize
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/api/search/related", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject searchrelated(Model model, HttpServletRequest request, @RequestParam(value = "queryString") String queryString,
            @RequestParam(value = "type") Integer type, @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNo") Integer pageNo) {
        String resultType = resultType(type);
        List<BaseModuleDTO> baseResult = new ArrayList<BaseModuleDTO>();
        JSONObject object = null;
        pageSize = null == pageSize ? 10 : pageSize;
        pageNo = null == pageNo ? 1 : pageNo;
        try {
            ESPage esPage = elasticsearchService.queryAndFilterByString("tyw", resultType, queryString, null, "", "", null, null, pageSize, pageNo);
            List<Map<String, Object>> listMap = esPage.getHighlighterResult();
            BaseModuleDTO base = null;
            for (Map<String, Object> mapObj : listMap) {
                base = new BaseModuleDTO();
                for (Entry<String, Object> m : mapObj.entrySet()) {
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
                    if (m.getKey().equals("Title")) {
                        KnowledgeController kc = new KnowledgeController();
                        base.setTitle(kc.subStr(m.getValue().toString().replace("<em>", "").replace("</em>", ""), 70));
                    }
                    if (m.getKey().equals("Url")) {
                        base.setUrl(m.getValue().toString());
                    }
                }
                baseResult.add(base);
            }
            ApiPager pager = new ApiPager(pageSize, pageNo, pageSize, baseResult);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, (JsonValueProcessor) new JsonDateFormat());
            object = JSONObject.fromObject(pager, jsonConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

}
