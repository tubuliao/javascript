package com.isoftstone.tyw.controller.rest;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.pub.Version;
import com.isoftstone.tyw.service.VersionService;

@Controller
public class VersionRestController {
	
	@Autowired
	private VersionService VersionService;
	
	@RequestMapping(value = "/api/version", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> versionGET(@RequestParam(value = "tool", required = false ,defaultValue="2") Integer tool,@RequestParam(value="version",required=true)String version) {
		Version versionObj=VersionService.getVersionForTools(tool.intValue());
		StringBuffer sb = new StringBuffer();
		if(versionObj!=null){
			sb.append(versionObj.getVersionFirst());
			sb.append(".");
			sb.append(versionObj.getVersionSecond());
		}
		Map<String,String> msg=null;
		
		if("".equals(version)||version==null){
			msg=new HashMap<String,String>();
			msg.put("msg", "传入版本号为空!");
		}
		double dbVersion=Double.parseDouble(sb.toString());
		double clientVersion=Double.parseDouble(version);
		
		if(dbVersion>clientVersion){
			msg=new HashMap<String,String>();
			msg.put("msg", "软件版本过低,请及时升级,最新版本为:Ver"+sb.toString()+"!");
		}
		return msg;
	}
	
	@RequestMapping(value = "/version", method = RequestMethod.GET)
	@ResponseBody
	public String webversion(@RequestParam(value = "version", required = false ,defaultValue="1") Integer versionCode) {
		Version version=VersionService.getVersionForTools(versionCode.intValue());
		StringBuffer sb = new StringBuffer();
		if(version!=null){
			sb.append(version.getVersionFirst());
			sb.append(".");
			sb.append(version.getVersionSecond());
		}
		return sb.toString();
	}
	
}
