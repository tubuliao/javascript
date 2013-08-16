package com.isoftstone.tyw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.KnowledgeSort;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.entity.info.TagCount;
import com.isoftstone.tyw.repository.info.KnowledgeSortDao;
import com.isoftstone.tyw.repository.info.TagCodeDao;
import com.isoftstone.tyw.repository.info.TagDao;

@Component
@Transactional(readOnly=true)
public class KnowledgeSortService {

		
	@Autowired
	private KnowledgeSortDao knowledgeSortDao ;
	
	@Autowired
    private TagCodeDao tagCodeDao ;
	
	
	@Autowired
    private TagDao tagDao ;
	/**
	 * 知识分类统计List
	 * @param tagCode
	 * @param tagLevel
	 * @return
	 */
	@Transactional(readOnly=false)
	public List<KnowledgeSort> getKnowledgeSortList(String tagCode, Integer tagLevel) {
		StringBuffer sb = new StringBuffer() ;
		String codeBetween = tagCode;
		String codeAnd = "99999999999999999".replaceFirst("9", tagCode.substring(0,1));
		String codeLike = "" ;
		String codeLast = "99999999999999".substring( (tagLevel - 1) * 2 );
		sb.append(tagCode.substring(0, tagLevel*2-1)) ;
		sb.append("__") ;
		for(int i = 0; i < 16-tagLevel*2; i++) {
			sb.append("0") ;
		}
		codeLike = sb.toString();
		return knowledgeSortDao.knowledgeList(Long.parseLong(codeBetween), codeLike, Long.parseLong(codeLast), Long.parseLong(codeAnd));
	}
	
	   @Transactional(readOnly=false)
	    public List<TagCount> knowledgeList(String tagCode, Integer tagLevel) {
	        StringBuffer sb = new StringBuffer() ;
	        String codeBetween = tagCode;
	        String codeAnd = "99999999999999999".replaceFirst("9", tagCode.substring(0,1));
	        String codeLike = "" ;
	        String codeLast = "99999999999999".substring( (tagLevel - 1) * 2 );
	        sb.append(tagCode.substring(0, tagLevel*2-1)) ;
	        sb.append("__") ;
	        for(int i = 0; i < 16-tagLevel*2; i++) {
	            sb.append("0") ;
	        }
	        codeLike = sb.toString();
	        return tagCodeDao.knowledgeList(Long.parseLong(codeBetween), codeLike, Long.parseLong(codeLast), Long.parseLong(codeAnd));
	    }
	   
	   @Transactional(readOnly=false)
       public List<TagCount> knowledgeList(String id) {
           return tagCodeDao.knowledgeList(id);
       }
	   
	   @Transactional(readOnly=false)
       public  TagCount tagCount(String id) {
           return tagCodeDao.tagCount(id);
       }
	   
	   public List<TagCount>  tagCountIndex(){
	         return  tagCodeDao.tagCountIndex();
	     }
	     public TagCount tagCountfbfx(){
             return  tagCodeDao.tagCountfbfx();
         }
}
