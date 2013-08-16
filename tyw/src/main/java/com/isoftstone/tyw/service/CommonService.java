package com.isoftstone.tyw.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@Transactional(readOnly=true)
public class CommonService {
	public void responseJsonBody(HttpServletResponse response,String resultJson){
		try {
			PrintWriter out=response.getWriter();
			out.write(resultJson);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
