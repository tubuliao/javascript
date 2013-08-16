package com.isoftstone.tyw.controller.rest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

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
import com.isoftstone.tyw.dto.info.BaseRecommendDTO;
import com.isoftstone.tyw.dto.info.TagRootDTO;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.KnowledgeSort;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.entity.info.TagCount;
import com.isoftstone.tyw.service.BaseService;
import com.isoftstone.tyw.service.KnowledgeSortService;
import com.isoftstone.tyw.service.TagService;

@Controller
public class TagRestController {

    @Autowired
    private TagService tagService;

    @Autowired
    private KnowledgeSortService knowledgeSortService;

    @Autowired
    private BaseService baseService;

    @RequestMapping(value = "/api/tag", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<Tag> list(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "syncDate", required = false) String syncDate,
            @RequestParam(value = "infoType") Integer infoType) {
        return tagService.listBySyncDate(infoType, syncDate, pageable);
    }

    @RequestMapping(value = "/api/tagCoount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String tagCoount(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "id") String id) {
        Tag tag = tagService.getTagById(id);
        int level = tag.getLevel();
        String code = null == tag.getCode() ? "" : (tag.getCode().toString()).substring(0, level * 2 - 1) + "%";
        BigInteger tagCount = tagService.searchBaseCount(code);
        return "{result:" + tagCount + "}";
    }

    /**
     * 获得根标签，level等于1的
     * 
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/api/tagRoot", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONArray tagRoot(@PageableDefaults(value = 30) Pageable pageable) {
        List<Tag> list = tagService.tagRoot();
        TagRootDTO tagRootDTO = null;
        List<TagRootDTO> trd = new ArrayList<TagRootDTO>();
        JSONArray ja = new JSONArray();
        for (Tag t : list) {
            tagRootDTO = new TagRootDTO();
            tagRootDTO.setId(t.getId());
            tagRootDTO.setName(t.getName());
            trd.add(tagRootDTO);
        }
        return ja.element(trd);
    }

    /**
     * 获得某一标签下的子标签
     * 
     * @param pageable
     * @param id 父标签id
     * @return
     */
    @RequestMapping(value = "/api/tagNext", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<TagRootDTO> tagNext(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "id") String id) {
        Page<TagRootDTO> result = null;
        List<TagRootDTO> ncontent = null;
        try {
            Page<Tag> list = tagService.tagNext(id, pageable);
            List<Tag> content = list.getContent();
            ncontent = Lists.newArrayList();
            for (Tag t : content) {
                TagRootDTO trd = new TagRootDTO(t.getId(), t.getName(), t.getLeaf());
                ncontent.add(trd);
            }
            result = new PageImpl<TagRootDTO>(ncontent, new PageRequest(list.getNumber(), list.getSize()), list.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得某一标签下的子标签和所有子标签关联的章数
     * 
     * @param pageable
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/tag/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONArray getKnowledgeSortList(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "id") String id) {
        JSONArray ja = new JSONArray();
        List<KnowledgeSort> knowledge = new ArrayList<KnowledgeSort>();
        try {
            List<TagCount> tls = knowledgeSortService.knowledgeList(id);
            for (TagCount tag : tls) {
                KnowledgeSort ks = new KnowledgeSort();
                ks.setId(tag.getId());
                ks.setCount(tag.getCount());
                ks.setName(tag.getName());
                knowledge.add(ks);
            }
            ja = ja.element(knowledge);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja;
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
     * 获取推荐内容接口
     * 
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/api/tag/recommend", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONArray recommend(@PageableDefaults(value = 8) Pageable pageable) {
        Page<KnowledgeSort> result = null;
        List<KnowledgeSort> ncontent = new ArrayList<KnowledgeSort>();
        JSONArray ja = new JSONArray();
        TagRestController trc = new TagRestController();
        BigInteger baseCount = BigInteger.valueOf(0);
        KnowledgeSort nto = null;
        try {
            List<Tag> list = tagService.findByRecommend();
            for (Tag tag : list) {
                nto = new KnowledgeSort();
                int level = tag.getLevel();
                String[] codes = trc.getCode(tag.getCode().toString(), level);
                baseCount = baseService.searchBaseModuleCount(tag.getId(), tag.getId());
                nto.setId(tag.getId());
                nto.setName(tag.getName());
                nto.setCount(Integer.valueOf(baseCount.toString()));
                ncontent.add(nto);
            }
            ja.add(ncontent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja;
    }

    @RequestMapping(value = "/api/tag/page", method = RequestMethod.GET)
    public String toAdd(Model model) throws Exception {
        return "page/tag/tag";
    }
}
