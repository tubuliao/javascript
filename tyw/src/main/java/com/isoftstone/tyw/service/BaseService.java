package com.isoftstone.tyw.service;
 import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.ApprovalModule;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.BaseExcelModule;
import com.isoftstone.tyw.entity.info.BaseModule;
import com.isoftstone.tyw.entity.info.ClickCount;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.repository.info.BaseDao;
import com.isoftstone.tyw.repository.info.BaseModuleDtoDao;
import com.isoftstone.tyw.repository.info.ClickCountDao;
import com.isoftstone.tyw.repository.info.TagDao;
@Component
@Transactional(readOnly = true)
public class BaseService {
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private BaseModuleDtoDao baseModuleDtoDao;
    @Autowired
    private ClickCountDao clickCountDao;
    @PersistenceContext
	public EntityManager entityManager;
    @Autowired
	private TagDao tagDao;
	
	

    @Transactional(readOnly=false)
    public void updateBaseById(String Id,Integer appleDetele){
        baseDao.updateById(Id,appleDetele);
     }
    
    @Transactional(readOnly=false)
    public void deteleBaseById(String Id){
    	baseDao.delete(Id);
     }
    
    public Base findBaseById(String Id){
       return baseDao.findBaseById(Id);
    }
    
    public Base findBaseModuleById(String Id){
        return baseDao.findBaseModuleById(Id);
     }
     
//     public List<Base> searchBase(String code){
//         return baseDao.searchBase(code);
//      }
      
     /**
      * 根据某个知识id查询 该知识的分部分项下的其余5条知识
     * @param id
     * @return
     */
    public List<BaseModule> findBaseListById(String id){
    	 List<Tag> taglist =  tagDao.findTagListByBaseId(id);
    	 if(taglist!=null&&taglist.size()>0){
    		 Tag tag = taglist.get(0);
    		 List<BaseModule>  baselist = baseModuleDtoDao.findBaseListByTag(tag.getCode());
    		 return baselist;
    	 }else{
    		 return new ArrayList<BaseModule>();
    	 }
      }
      
     
     public List<BaseModule> searchBase(Long codestart,Long codeend,Integer pageNum,Integer pageSize){
         return baseModuleDtoDao.searchBase(codestart,codeend,pageNum,pageSize);
      }
     
      public List<BaseModule> searchBaseModule(Long codestart,Long codeend,Integer pageNum,Integer pageSize){
          return baseModuleDtoDao.searchBaseModule(codestart,codeend,pageNum,pageSize);
       }
      
      public List<BaseModule> searchBaseModule(String id,String id1,Integer pageNum,Integer pageSize){
          return baseModuleDtoDao.searchBaseModule(id,id,pageNum,pageSize);
       }
      
      
      public BigInteger searchBaseModuleCount(String id,String id1){
          return baseModuleDtoDao.searchBaseModuleCount(id,id1);
       }
      
       public BigInteger searchBaseModuleCount(Long codestart,Long codeend){
           return baseModuleDtoDao.searchBaseModuleCount(codestart,codeend);
        }
       
      public BigInteger searchBaseCount(String codestart,String codeend,String tagid){
          return baseModuleDtoDao.searchTagBaseCount(Long.valueOf(codestart),Long.valueOf(codeend),tagid);
       }
      
      public void modifyclickCount(String id){
          clickCountDao.modifyclickCount(id);
      }
      public Page<Base> findByRecommend(int recommend,Pageable pageable){
          return baseDao.findByRecommendOrderByModifyDateDesc(recommend,pageable);
      }
      
      public Base getOne(String id) {
    	  return baseDao.findOneById(id);
      }
      
      public void saveAll(String baseId, String tagId, Long tagCode, String tagName, String title, String source) {
    	  baseDao.saveBaseAndTag(baseId, tagId, tagCode, tagName, title, source);
      }
      
      public void setTitleWeighing(String id, int wVal) {
    	  baseDao.updateTitleWeighing(id, wVal);
      }
      
      public void setSourceWeighing(String source, int wVal) {
    	  baseDao.updateSourceWeighing(source, wVal);
      }
      
      public void saveOne(Base b) {
    	 Base base= baseDao.save(b);
    	 ClickCount cc=new ClickCount();
    	 cc.setId(base.getId());
    	 cc.setClickcount(0);
    	 clickCountDao.save(cc);
      }
      
      public void modifyModifyInfo(String id, Date modifyDate, String modifyId, String modifyName) {
    	  baseDao.updateModifyInfo(id, modifyDate, modifyId, modifyName);
      }
      
      public void modifyApprovalState(String id, Integer state, Date checkDate, String checkId, String checkName) {
    	  baseDao.updateApprovalState(id, state, checkDate, checkId, checkName);
      }
      
      /**
       * 批量标签列表查询
       * @param tagId
       * @param infoType
       * @param insertName
       * @param dateFrom
       * @param dateTo
       * @param title
       * @param state
       * @param pageable
       * @return
       */
      public Map<String, Object> listBaseBySql(String tagId, String infoType, String insertName, String dateFrom, String dateTo, String title, String state, Integer applyDetele, String titleRepeat, String source, Pageable pageable) {
    	  
    	  Map<String, Object> page = new HashMap<String, Object>();
    	  
    	  // sql
    	  StringBuffer sb = new StringBuffer(" SELECT DISTINCT b.id, b.info_type, b.title, b.source, b.create_date, b.insert_name, b.modify_date, b.modify_name, b.state ,b.apply_detele FROM info_base b ");
    	  StringBuffer sbc = new StringBuffer(" SELECT COUNT(DISTINCT b.id) FROM info_base b ");
    	  if(tagId == null || "".equals(tagId)) {
    		  sb.append("WHERE 1 = 1");
    		  
    		  sbc.append("WHERE 1 = 1");
    	  }
    	  // 标签
    	  if(tagId != null && !"".equals(tagId)) {
    		  sb.append(" LEFT OUTER JOIN info_base_tag bt ON bt.base_id = b.id WHERE 1 = 1 AND bt.tag_id = '" + tagId + "'");
    		  
    		  sbc.append(" LEFT OUTER JOIN info_base_tag bt ON bt.base_id = b.id WHERE 1 = 1 AND bt.tag_id = '" + tagId + "'");
    	  }
    	  // 知识类型
    	  if(infoType != null && !"".equals(infoType) && !"0".equals(infoType)) {
    		  sb.append(" AND b.info_type = " + infoType);
    		  
    		  sbc.append(" AND b.info_type = " + infoType);
    	  }
    	  // 录入人
    	  if(insertName != null && !"".equals(insertName)) {
    		  sb.append(" AND b.insert_name LIKE '%" + insertName + "%'");
    		  
    		  sbc.append(" AND b.insert_name LIKE '%" + insertName + "%'");
    	  }
    	  // 录入时间from
    	  if(dateFrom != null && !"".equals(dateFrom)) {
    		  sb.append(" AND b.create_date >= '" + dateFrom + "'");
    		  
    		  sbc.append(" AND b.create_date >= '" + dateFrom + "'");
    	  }
    	  // 录入时间to
    	  if(dateTo != null && !"".equals(dateTo)) {
    		  sb.append(" AND b.create_date <= '" + dateTo + "'");
    		  
    		  sbc.append(" AND b.create_date <= '" + dateTo + "'");
    	  }
    	  // 审批状态
    	  if(state != null && !"".equals(state)) {
    		  sb.append(" AND b.state = " + state);
    		  
    		  sbc.append(" AND b.state = " + state);
    	  }
    	  //申请删除
    	  if(applyDetele !=null&& !"".equals(applyDetele)){
    		  sb.append(" AND b.apply_detele = " + applyDetele);
    		  
    		  sbc.append(" AND b.apply_detele = " + applyDetele);
    	  }
    	  // 标题
    	  if(title != null && !"".equals(title)) {
    		  sb.append(" AND b.title LIKE '%" + title + "%'");
    		  
    		  sbc.append(" AND b.title LIKE '%" + title + "%'");
    	  }
    	  // 来源
    	  if(source != null && !"".equals(source)) {
    		sb.append(" AND b.source LIKE '%" + source + "%'");
    		
    		sbc.append(" AND b.source LIKE '%" + source + "%'");
    	  }
    	  
    	  // 标题重复(分组查询)
    	  if(titleRepeat != null && "1".equals(titleRepeat)) {
    		  sb.append(" GROUP BY b.title HAVING COUNT(b.title) > 1");
    		  
    		  sbc.append(" GROUP BY b.title HAVING COUNT(b.title) > 1");
    	  }
    	  
    	  // 排序
    	  //sb.append(" ORDER BY b.modify_date DESC"); 太慢
    	  // 分页
    	  sb.append(" LIMIT " + pageable.getPageSize()*pageable.getPageNumber() + ", " + pageable.getPageSize());
    	  // 查询
    	  Query q = entityManager.createNativeQuery(sb.toString(), ApprovalModule.class);
    	  List<ApprovalModule> rows = q.getResultList();
    	  page.put("rows", rows);
    	  
    	  Query qc = entityManager.createNativeQuery(sbc.toString());
    	  BigInteger total = null;
    	  if(titleRepeat != null && "1".equals(titleRepeat)) {	// 查询标题重复
    		  total = BigInteger.valueOf(qc.getResultList().size());
    	  } else {	// 一般查询
    		  total = (BigInteger)qc.getResultList().get(0);
    	  }
    	  page.put("total", total);
    	  
    	  entityManager.close();
    	  
    	  return page;
      }
      
      
      public BigInteger listBaseBySqlCount(String tagId, String infoType, String insertName, String dateFrom, String dateTo, String title, String state,Integer applyDetele) {
    	  // sql
    	  StringBuffer sb = new StringBuffer(" SELECT COUNT(DISTINCT b.id) FROM info_base b ");
    	  if(tagId == null || "".equals(tagId)) {
    		  sb.append("WHERE 1 = 1");
    	  }
    	  // 标签
    	  if(tagId != null && !"".equals(tagId)) {
    		  sb.append(" LEFT OUTER JOIN info_base_tag bt ON bt.base_id = b.id WHERE 1 = 1 AND bt.tag_id = '" + tagId + "'");
    	  }
    	  // 知识类型
    	  if(infoType != null && !"".equals(infoType) && !"0".equals(infoType)) {
    		  sb.append(" AND b.info_type = " + infoType);
    	  }
    	  // 录入人
    	  if(insertName != null && !"".equals(insertName)) {
    		  sb.append(" AND b.insert_name LIKE '%" + insertName + "%'");
    	  }
    	  // 录入时间from
    	  if(dateFrom != null && !"".equals(dateFrom)) {
    		  sb.append(" AND b.create_date >= '" + dateFrom + "'");
    	  }
    	  // 录入时间to
    	  if(dateTo != null && !"".equals(dateTo)) {
    		  sb.append(" AND b.create_date <= '" + dateTo + "'");
    	  }
    	  // 审批状态
    	  if(state != null && !"".equals(state)) {
    		  sb.append(" AND b.state = " + state);
    	  }
    	  //申请删除
    	  if(applyDetele !=null&& !"".equals(applyDetele)){
    		  sb.append(" AND b.apply_detele = " + applyDetele);
    	  }
    	  // 标题
    	  if(title != null && !"".equals(title)) {
    		  sb.append(" AND b.title LIKE '%" + title + "%'");
    	  }
    	  // 查询
    	  Query q = entityManager.createNativeQuery(sb.toString());
    	  BigInteger total = (BigInteger)q.getResultList().get(0);
    	  entityManager.close();
    	  return total;
      }
      
      
      /**
       * 批量修改来源
       * @param idArr
       * @param source
       * @param checkDate
       * @param checkId
       * @param checkName
       */
      @Transactional(readOnly=false)
      public void modifyApprovalSource(String idArr[], String source, Date modifyDate, String modifyId, String modifyName) {
    	 int i = 0;
    	 int j = idArr.length;
    	 for( ; i < j; i++) {
    		 if(idArr[i] != null && !"".equals(idArr[i])) {
    			 baseDao.updateApprovalSource(idArr[i], source, modifyDate, modifyId, modifyName);
    		 }
    	 }
      }
      
      /**
       * 获取知识等级
       * @param infoType
       * @return
       */
      public List<String> getKnowledgeLevelSort(String infoType) {
    	  return baseDao.findKnowledgeLevelSort(infoType);
      }
      
      public Map<String, Object> listBaseForExcel(String infoType, String tagId, String insertName, String title, String state, String source, String begincreateDate, String level) {
    	  // 查询结果
    	  Map<String, Object> page = new HashMap<String, Object>();
    	  // sql
    	  StringBuffer sb = new StringBuffer(" SELECT a.id, a.info_type, a.title, a.source, a.create_date, a.insert_name, a.modify_date, a.modify_name, a.state, a.check_date, a.check_name FROM info_base a");
    	  
    	  StringBuffer sbc = new StringBuffer(" SELECT COUNT(a.id) FROM info_base a");
    	  
    	  //标签
    	  if(StringUtils.isNotBlank(tagId)) {
    		  sb.append(" LEFT JOIN info_base_tag b ON b.base_id = a.id WHERE b.tag_id = '" + tagId + "'");
    		  
    		  sbc.append(" LEFT JOIN info_base_tag b ON b.base_id = a.id WHERE b.tag_id = '" + tagId + "'");
    	  } else {
    		  sb.append(" WHERE 1 = 1");
    		  
    		  sbc.append(" WHERE 1 = 1");
    	  }
    	  
    	  // 知识类型
    	  if(infoType != null && !"".equals(infoType) && !"0".equals(infoType)) {
    		  sb.append(" AND a.info_type = " + infoType);
    		  
    		  sbc.append(" AND a.info_type = " + infoType);
    	  }
    	  
    	  // 录入人
    	  if(StringUtils.isNotBlank(insertName)) {
    		  sb.append(" AND a.insert_name LIKE '%" + insertName + "%'");
    		  
    		  sbc.append(" AND a.insert_name LIKE '%" + insertName + "%'");
    	  }
    	  
    	  // 标题
    	  if(StringUtils.isNotBlank(title)) {
    		  sb.append(" AND a.title LIKE '%" + title + "%'");
    		  
    		  sbc.append(" AND a.title LIKE '%" + title + "%'");
    	  }
    	  
    	  // 审批状态
    	  if(state != null && !"10000".equals(state)) {
    		  sb.append(" AND a.state = " + state);
    		  
    		  sbc.append(" AND a.state = " + state);
    	  }
    	  
    	  // 来源
    	  if(StringUtils.isNotBlank(source)) {
    		  sb.append(" AND a.source LIKE '%" + source + "%'");
    		  
    		  sbc.append(" AND a.source LIKE '%" + source + "%'");
    	  }
    	  
    	  // 发布时间
    	  if(StringUtils.isNotBlank(begincreateDate)) {
    		  sb.append(" AND a.begincreate_date = '" + begincreateDate + "'" );
    		  
    		  sbc.append(" AND a.begincreate_date = '" + begincreateDate + "'" );
    	  }
    	  
    	  // 知识等级
    	  if(StringUtils.isNotBlank(level) && !"10000".equals(level)) {
    		  sb.append(" AND a.weighing = " + level);
    		  
    		  sbc.append(" AND a.weighing = " + level);
    	  }
    	  
    	  // 查询
    	  Query q = entityManager.createNativeQuery(sb.toString(), BaseExcelModule.class);
    	  List<BaseExcelModule> rows = q.getResultList();
    	  page.put("rows", rows);
    	  
    	  Query qc = entityManager.createNativeQuery(sbc.toString());
    	  BigInteger total = (BigInteger)qc.getResultList().get(0);
    	  page.put("total", total);
    	  
    	  entityManager.close();
    	  
    	  return page;
      }
}
