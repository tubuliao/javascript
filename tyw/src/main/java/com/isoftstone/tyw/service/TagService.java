package com.isoftstone.tyw.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.ReaderConfig;
import net.sf.jxls.reader.XLSReadStatus;
import net.sf.jxls.reader.XLSReader;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import com.isoftstone.tyw.entity.info.BaseTag;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.entity.info.TagCount;
import com.isoftstone.tyw.entity.info.VideoModule;
import com.isoftstone.tyw.repository.info.BaseTagDao;
import com.isoftstone.tyw.repository.info.TagDao;

@Component
@Transactional(readOnly = true)
public class TagService {
    private final static String xmlConfig = "/META-INF/resource/tag_mapping.xml";

    @Autowired
    private TagDao tagDao;

    @Autowired
    BaseTagDao baseTagDao;
    @PersistenceContext
	public EntityManager entityManager;
    /**
     * 根据信息类型获取所有标签分页列表
     * 
     * @param pageable
     * @return
     */
    public Page<Tag> listBySyncDate(Integer infoType, String syncDate, Pageable pageable) {

        try {
            if (syncDate != null) {
                Date date = DateUtils.parseDate(syncDate, "yyyy-MM-dd HH:mm:ss");
                return tagDao.findByDataDateGreaterThanAndInfoType(date, pageable);
            } else {
                return tagDao.findByInfoType(pageable);
            }
        } catch (ParseException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = false)
    public Tag saveTag(Tag tag) {
        return tagDao.save(tag);
    }

       public List<BaseTag> findRelatedWords(String id) {
        return baseTagDao.findRelatedWords(id);
     }

    @Transactional(readOnly = false)
    public void deleteTag(String id, boolean flag) {
        if (flag) {
            tagDao.delete(id);
        } else {
            tagDao.modifyStatus(id);
        }
    }

    public Tag getTagById(String id) {
        return tagDao.findOne(id);
    }

    public Tag getTagByCode(Long code) {
        return tagDao.findByCode(code);
    }

    public BigInteger searchBaseCount(String code) {
        return tagDao.searchBaseCount(code);
    }

    public Page<Tag> tagNext(String code, Pageable pageable) {
        return tagDao.findAllTagByParentId(code, pageable);
    }

    // 推荐板块
    public List<Tag> findByRecommend() {
        return tagDao.searchTagByrecommend();
    }

    public List<Tag> tagRoot() {
        return tagDao.tagRoot();
    }

    public List<Tag> searchTagByCode(String code, Pageable pageable) {
        return tagDao.searchTagByCode(code);
    }

    public List<Tag> getTagByParentId1(String parentId) {
        return tagDao.findTagForCount1(parentId);
    }

    // public List<Tag> getTagByParentId2(String parentId){
    // return tagDao.findTagForCount2(parentId);
    // }

    @Transactional(readOnly = false)
    public void saveTag(List<Tag> tags) {
        for (Tag tag : tags) {
            if (tag.getId() != null && tag.getName() != null) {
                this.saveTag(tag);
            }
        }
    }

    public boolean findUseingTagById(String id) {
        List<Tag> tagList = tagDao.findUseingTagById(id);
        if (tagList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取全部标签
     * 
     * @param pageable
     * @return
     */
    public Page<Tag> getAll(Pageable pageable) {
        return tagDao.findAll(pageable);
    }

    public Tag getTagByParentId8(String parentId) {
        return tagDao.findTagByParentId8(parentId);
    }

    public Page<Tag> listTag(Specification<Tag> specification, Pageable pageable) {
        return tagDao.findAll(specification, pageable);
    }

    /**
     * 读取Excel到数组
     * 
     * @param dataXls
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws InvalidFormatException
     */
    public List<Tag> readExcel(String dataXls) throws IOException, SAXException, InvalidFormatException {
        Map<String, List<Tag>> beans = new HashMap<String, List<Tag>>();
        InputStream inputXML = new BufferedInputStream(getClass().getResourceAsStream(xmlConfig));
        XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
        InputStream inputXLS = new BufferedInputStream(new FileInputStream(dataXls));
        List<Tag> tagList = new ArrayList<Tag>();
        beans.put("tagList", tagList);
        ReaderConfig.getInstance().setSkipErrors(true);
        XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
        if (readStatus.isStatusOK()) {
            return tagList;
        }
        return tagList;

    }

    /**
     * 写入Excel文件
     * 
     * @param rowList
     * @param templateName
     * @param destName
     * @throws ParsePropertyException
     * @throws InvalidFormatException
     * @throws IOException
     */
    public void writeExcel(List<Tag> rowList, String templateName, String destName) throws ParsePropertyException, InvalidFormatException,
            IOException {
        XLSTransformer transformer = new XLSTransformer();
        Map<String, List<Tag>> beans = new HashMap<String, List<Tag>>();
        beans.put("tagList", rowList);
        File file = new File(destName);
        if (!file.exists())
            file.createNewFile();
        transformer.transformXLS(templateName, beans, destName);
    }

    public void modifyLeaf(String id, Integer flag) {
        tagDao.modifyLeaf(id, flag);
    }

    public List<Tag> getTagRoot() {
        return tagDao.findTagRootForCount();
    }

    public List<Tag> getTagByParentId(String parentId) {
        return tagDao.findTagForCount(parentId);
    }

    public List<Tag> getSegmentTagRoot() {
        return tagDao.findSegmentTagRootForCount();
    }

    public List<Tag> getSegmentTagByParentId(String parentId) {
        return tagDao.findSegmentTagForCount(parentId);
    }

    public List<Tag> getAllTagRoot() {
        return tagDao.findRootTags();
    }

    public List<Tag> getAllTagByParentId(String parentId) {
        return tagDao.findTagByParentId(parentId);
    }

    public Tag findTagByTagName(String name) {
        return tagDao.findTagByName(name);
    }

    public List<Tag> findTagByCodeLevel(Long codestart, Long codeend) {
        return tagDao.findTagByCodeLevel(codestart, codeend);
    }

    /**
     * 此方法仅支持parentCode为17位的查询
     * 
     * @param parentCode
     * @param level
     * @return
     */
    public List<Tag> getAllTagsByParentCode(String parentCode, int level) {
        int maxLevel = 17;// 17位的parentCode
        StringBuffer sb = new StringBuffer();
        int endLevel = maxLevel - level - 1;
        for (int i = 0; i < endLevel; i++) {
            sb.append("0");
        }
        String strCode = parentCode.substring(level + 1, maxLevel);
        String pcCode = parentCode.replace(strCode, sb);
        List<Tag> tags = tagDao.findTagByparentCode(Long.valueOf(pcCode));
        return tags;
    }

    public List<Tag> findsearchhotword() {
        List<Tag> tags = tagDao.findsearchhotword();
        return tags;
    }

    public Tag findTagByMaxCode(String parentId) {
        return tagDao.findTagByMaxCode(parentId);

    }

    public Tag findMaxSortNoByRoot() {
        return tagDao.findMaxSortNoByRoot();
    }

    public Tag findMaxSortNoByParentId(String parentId) {
        return tagDao.findMaxSortNoByParentId(parentId);
    }

    /* 查询子的数量 */
    public int findCountByParentId(String parentId, Pageable pageable) {
        return tagDao.findAllTagByParentId(parentId, pageable).getContent().size();
    }

    /**
     * 根据标签name查询
     */
    public Tag findTagByName(String tagName) {
        return tagDao.findTagByName(tagName);
    }

    /**
     * 根标签的统计查询
     * 
     * @return List<Tag>
     */
    public List<Tag> getTagList() {
        return tagDao.findTagList();
    }

    /**
     * 标签的统计查询
     * 
     * @param parentId
     * @return List<Tag>
     */
    public List<Tag> getTagListByParentId(String parentId) {
        return tagDao.findTagListByParentId(parentId);
    }

    /**
     * 删除知识的单个标签
     * 
     * @param baseId
     * @param tagId
     */
    public void removeBaseAndTag(String baseId, String tagId) {
        tagDao.deleteSingleTag(baseId, tagId);
    }
    
    /**
     * 标签列表查询
     * @param name
     * @param tagNo
     * @param pageable
     * @return
     */
	public List<Tag> listTagBaseRows( String name, String tagNo, String parentId, Pageable pageable) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT * FROM info_tag t WHERE status = '1' ");
		// 标签名称
		if(name != null && !"".equals(name)) {
			sb.append(" AND t.name like '%" + name + "%' ");
		}
		// 首字母
		if(tagNo != null && !"".equals(tagNo)) {
			sb.append(" AND t.tag_no LIKE '%" + tagNo + "%' ");
		}
		// 标签父id
		if(parentId != null && !"".equals(parentId)) {
			sb.append(" AND t.parent_id = '"+parentId+"' ");
		}
		// 分页
		sb.append(" order by sort_no desc limit " + pageable.getPageNumber()*pageable.getPageSize() + "," + pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), Tag.class);
		
		List<Tag> result = query.getResultList();   
		
		entityManager.close();
        return result;
	}
	
	/**
	 * 标签列表查询（count）
	 * @param name
	 * @param tagNo
	 * @return
	 */
	public BigInteger listTagBaseTotal(String name, String tagNo, String parentId) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT count(*) FROM info_tag t WHERE status = '1' ");
		// 标签名称
		if(name != null && !"".equals(name)) {
			sb.append(" AND t.name like '%" + name + "%' ");
		}
		// 首字母
		if(tagNo != null && !"".equals(tagNo)) {
			sb.append(" AND t.tag_no LIKE '%" + tagNo + "%' ");
		}
		// 标签父id
		if(parentId != null && !"".equals(parentId)) {
			sb.append(" AND t.parent_id = '"+parentId+"' ");
		}
		Query query = entityManager.createNativeQuery(sb.toString());
		
		BigInteger total = (BigInteger)query.getResultList().get(0);   
		
		entityManager.close();
        return total;
	}
}
