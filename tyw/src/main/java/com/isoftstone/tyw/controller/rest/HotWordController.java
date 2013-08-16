package com.isoftstone.tyw.controller.rest;

import java.util.List;

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
import com.isoftstone.tyw.dto.info.HotWordsDTO;
import com.isoftstone.tyw.entity.info.HotWords;
import com.isoftstone.tyw.service.HotWordService;
import com.isoftstone.tyw.util.DateManager;

/**
 * @author liuyulong
 */
@Controller
public class HotWordController {
    @Autowired
    private HotWordService hotWordService;

    /**
     * 热词搜索
     * 
     * @param pageable
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/api/hotwords", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<HotWordsDTO> hotwords(@PageableDefaults(value = 30) Pageable pageable, @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) {
        Page<HotWordsDTO> result = null;
        try {
            Page<HotWords> list = hotWordService.searchHotWords(DateManager.stringFormat(startDate), DateManager.stringFormat(endDate), pageable);
            List<HotWords> content = list.getContent();
            List<HotWordsDTO> ncontent = Lists.newArrayList();
            for (HotWords HotWord : content) {
                HotWordsDTO fto = new HotWordsDTO(HotWord.getWord(), HotWord.getNum());
                ncontent.add(fto);
            }
            result = new PageImpl<HotWordsDTO>(ncontent, new PageRequest(list.getNumber(), list.getSize()), list.getTotalElements());
        } catch (Exception e) {
            result = new PageImpl<HotWordsDTO>(null, new PageRequest(0, 0), 0);
        }
        return result;
    }
}
