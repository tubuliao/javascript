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
package com.isoftstone.tyw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.BaseSourceModule;
import com.isoftstone.tyw.entity.info.BaseTitleModule;
import com.isoftstone.tyw.entity.info.BookDirModule;
import com.isoftstone.tyw.entity.info.PdTag;
import com.isoftstone.tyw.entity.info.Segment;
import com.isoftstone.tyw.entity.info.SegmentDir;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.repository.info.BaseDao;
import com.isoftstone.tyw.repository.info.BaseTagDao;
import com.isoftstone.tyw.repository.info.SegmentDao;
import com.isoftstone.tyw.repository.info.SourceDao;
import com.isoftstone.tyw.repository.info.TagDao;

/**
 * 专题展示类
 * 
 * @author Rommel
 */
@Component
@Transactional(readOnly=true)
public class SpecialService {
	
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private SourceDao sourceDao;
	
	@Autowired
	private SegmentDao segmentDao;
	
	@Autowired
	private BaseTagDao baseTagDao;
	
	/**
	 * 强制性条文--国标来源列表
	 * @param pageable
	 * @return
	 */
	public Page<Base> listBase(Pageable pageable){
		Tag tag = tagDao.findTagByName("强制性条文");
		return baseDao.findByTag(tag.getId(),pageable);
	}
	
	public List<Tag> listTag(){
		return  tagDao.findTagForCode();
	}
	
	public List<Tag> listTag(String scode,String ecode){
		return  tagDao.findTagForCode(scode,ecode);
	}
	
	/**
	 * 根据一个标签的code作为parent_code作为查询条件，取出此标签的下一层所有标签
	 * @param code
	 * @return
	 */
	public List<Tag> getListAllTagByParentCode(Long code){
		return tagDao.findAllTagByParentCode(code);
	}
	
	/**
	 * 根据知识ID取出此知识所在的知识性质标签的路径
	 * 在各个知识详情中显示
	 * 例如：首页-》知识性质-》....
	 * @param id
	 * @return
	 */
	public String getTagPathById(String id){
		return tagDao.findTagPathById(id);
	}
	
	/**
	 * 根据一个标签的code作为parent_code,頻道code,层级,作为查询条件，取出此标签的下一层所有标签
	 * @param code
	 * @return
	 */
	public List<PdTag> getListAllTagByParentCodeAndPdCodeAndPLevel(Long code,Long pdCode,Integer pLevel){
		return tagDao.findAllTagByParentCodeAndPdCodeAndPLevel(code, pdCode, pLevel);
	}
	
	/**
	 * 通过来源名称从数据库中取出知识列表
	 * @param source
	 * @param pageable
	 * @return
	 */
	public List<Segment> getListBaseBySource(String source){
		return segmentDao.findBySource(source);
	}
	
	/**
	 * 通过来源名称从数据库中取出知识列表,不走hibernate的orm映射
	 * @param source
	 * @param pageable
	 * @return
	 */
	public List<SegmentDir> getListBaseBySourceN(String source){
		return segmentDao.findBySourceN(source);
	}
	
	/**
	 * 根据分部分项
	 * 查询核心条文下的来源信息---不分页
	 * @param start
	 * @param end
	 * @return
	 */
	public List<BaseSourceModule> getBaseListByTags(String start,String end){
		return baseDao.findBaseForTags(start, end);
	}
	
	/**
	 * 根据来源或标准
	 * 查询核心条文下的来源信息---不分页
	 * @param start
	 * @param end
	 * @param source
	 * @return
	 */
	public List<BaseSourceModule> getBaseListByTagsAndSource(String start,String end,String source){
		return baseDao.findBaseForTagsAndSource(start, end, source);
	}
	
	/**
	 * 根据标题
	 * 查询核心条文下的来源信息---不分页
	 * @param start
	 * @param end
	 * @param title
	 * @return
	 */
	public List<BaseSourceModule> getBaseListByTagsAndTitle(String start,String end,String title){
		return baseDao.findBaseForTagsAndTitle(start, end, title);
	}
	
	public List<BookDirModule> getTitleByTagsAndTitle(String start,String end,String title){
		return baseDao.findTitleForTagsAndTitle(start, end, title);
	}
	
	/**
	 * 根据标题、来源（标准）
	 * 查询核心条文下的来源信息---不分页
	 * @param start
	 * @param end
	 * @param title
	 * @param source
	 * @return
	 */
	public List<BaseSourceModule> getBaseListByTagsAndTitleAndSource(String start,String end,String title,String source){
		return baseDao.findBaseForTagsAndTitleAndSource(start, end, title, source);
	}
	
	public List<BaseTitleModule> getTitleByTagsAndTitleAndSource(String start,String end,String title,String source){
		return baseDao.findBaseForTitleAndTitleAndSource(start, end, title, source);
	}
	
	/**
	 * 根据标准、来源
	 * 查询核心条文下的来源信息---不分页
	 * @param start
	 * @param end
	 * @param standard
	 * @param source
	 * @return
	 */
	public List<BaseSourceModule> getBaseListByTagsAndStandardAndSource(String start,String end,String standard,String source){
		return baseDao.findBaseForTagsAndStandardAndSource(start, end, standard, source);
	}
	
	/**
	 * 根据标准、来源、标题
	 * 查询核心条文下的来源信息---不分页
	 * @param start
	 * @param end
	 * @param title
	 * @param standard
	 * @param source
	 * @return
	 */
	public List<BaseSourceModule> getBaseListByTagsAndTitleAndStandardAndSource(String start,String end,String title,String standard,String source){
		return baseDao.findBaseForTagsAndTitleAndStandardAndSource(start, end, title,standard, source);
	}
	
	public List<BaseTitleModule> getTitleByTagsAndTitleAndStandardAndSource(String start,String end,String title,String standard,String source){
		return baseDao.findBaseForTitleAndTitleAndStandardAndSource(start, end, title,standard, source);
	}
	
	/**
	 * 通过标签NAME取出标签信息
	 * @param name
	 * @return
	 */
	public Tag getTagByName(String name){
		return tagDao.findByName(name);
	}
	
	/**
	 * 通过baseId取出base信息
	 * @param id
	 * @return
	 */
	public Base getBaseById(String id){
		return baseDao.findBaseById(id);
	}
	
	/**
	 * 通过标签code取出标签的名称
	 * @param code
	 * @return
	 */
	public String getTagNameByCode(String code){
		return tagDao.getTagNameByCode(code);
	}
}
