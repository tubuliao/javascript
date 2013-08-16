package com.isoftstone.tyw.controller.rest;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.isoftstone.tyw.dto.info.BaseAPIDTO;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.Segment;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.InfoService;

@Controller
public class SegmentRestController {
	
	@Autowired
	private InfoService infoService;
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/api/segment", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<Base> listBySyncDate(@PageableDefaults(value=30) Pageable pageable,
									@RequestParam(value = "syncDate", required = false) String syncDate) throws ParseException {
		
		return infoService.listBySyncDate(pageable, 2, syncDate);
	}
	
	
	@RequestMapping(value = "/api/segment", method = RequestMethod.POST)
	@ResponseBody
	public String save(@ModelAttribute("segment") Segment segment,
						@RequestParam(value = "tagIds", required = false) List<String> tagIds){
		
		for(String tagId : tagIds){
			Tag tag = new Tag(tagId);
			segment.getTags().add(tag);
		}
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = accountService.loadUserByUsername(userDetails.getUsername());
		
		segment.setInsertId(user.getId());
		segment.setInsertName(user.getAliasname());
		if(segment.getId()==null||"".equals(segment.getId())){
			segment.setCreateDate(new Date());
		}
		segment = infoService.saveSegment(segment);

		return new String("{id:"+ segment.getId() +"}");
	}
	
	@RequestMapping(value = "/api/segment/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Segment getById(@PathVariable("id") String id){
		return infoService.getSegmentById(id);
	}
	
	@RequestMapping(value = "/api/segment/{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") String id){
		infoService.deleteSegmentById(id);
	}
	
	@RequestMapping(value = "/api/segment/reject", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<BaseAPIDTO> listByState(@PageableDefaults(value=30) Pageable pageable){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = accountService.loadUserByUsername(userDetails.getUsername());
		
		Page<Base> list = infoService.listByStateAndInfoTypeAndInsertId(2,2,user.getId(),pageable);
 		
		List<Base> content = list.getContent();
		List<BaseAPIDTO> ncontent = Lists.newArrayList();
		for(Base base : content){
			BaseAPIDTO dto = new BaseAPIDTO(base.getId(), base.getAuditInfo(),base.getCheckDate());
			ncontent.add(dto);
		}
		
		Page<BaseAPIDTO> result = new PageImpl<BaseAPIDTO>(ncontent,new PageRequest(list.getNumber(),list.getSize()),list.getTotalElements());
		return result;
	}
}
