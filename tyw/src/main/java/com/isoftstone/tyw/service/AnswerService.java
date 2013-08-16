package com.isoftstone.tyw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.Answer;
import com.isoftstone.tyw.repository.info.AnswerDao;

@Component
@Transactional(readOnly=true)
public class AnswerService {
	
	@Autowired
	private AnswerDao answerDao;
	
	/**
	 * 根据问题id查询答案（在预览时显示一个问题的所有答案）
	 * @param id
	 * @return
	 */
	public List<Answer> getAnswerPageList(String id){
		return answerDao.getAnswerPageList(id);
	}
	
	/**
	 * 保存答案
	 * @param answer
	 * @return
	 */
	@Transactional(readOnly = false)
  	public Answer saveAnswer(Answer answer) {
  		return answerDao.save(answer);
  	}
	
	/**
	 * 答案的分页
	 * @param id
	 * @param pageable
	 * @return
	 */
	public Page<Answer> getAnswerByQid(String id,Pageable pageable){
		return answerDao.getAnswerByQid(id,pageable);
	}
}
