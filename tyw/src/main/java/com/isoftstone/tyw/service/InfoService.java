package com.isoftstone.tyw.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.BaseTagModule;
import com.isoftstone.tyw.entity.info.BaseUtilModule;
import com.isoftstone.tyw.entity.info.Files;
import com.isoftstone.tyw.entity.info.FormModule;
import com.isoftstone.tyw.entity.info.Powerpoint;
import com.isoftstone.tyw.entity.info.Question;
import com.isoftstone.tyw.entity.info.Segment;
import com.isoftstone.tyw.entity.info.SegmentModule;
import com.isoftstone.tyw.repository.info.BaseDao;
import com.isoftstone.tyw.repository.info.BaseTagModuleDao;
import com.isoftstone.tyw.repository.info.BaseUtilModuleDao;
import com.isoftstone.tyw.repository.info.FilesDao;
import com.isoftstone.tyw.repository.info.FormDao;
import com.isoftstone.tyw.repository.info.PowerpointDao;
import com.isoftstone.tyw.repository.info.QuestionDao;
import com.isoftstone.tyw.repository.info.SegmentDao;
import com.isoftstone.tyw.repository.info.VideoDao;

@Component
@Transactional(readOnly=true)
public class InfoService {
	
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private BaseUtilModuleDao baseUtilModuleDao;
	
	@Autowired
	private BaseTagModuleDao baseTagModuleDao;
	
	@Autowired
	private SegmentDao segmentDao;
	
	@Autowired
	private VideoDao videoDao;
	
	@Autowired
	private FormDao formDao ;
	
	@Autowired
	private FilesDao filesDao ;
	
	@Autowired
	private PowerpointDao powerpointDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	 @PersistenceContext
	 public EntityManager entityManager;
	 
 
	 
	private static final String xmlConfig = "/META-INF/resource/segment_mapping.xml" ;
	
	
	/**
	 * 公共分页操作
	 */
	public Page<Base> listBySyncDate(Pageable pageable, int infoType, String syncDate){
		
		try{
			if(syncDate != null){
				Date date = DateUtils.parseDate(syncDate, "yyyy-MM-dd hh:mm:ss");
				return baseDao.findByModifyDateGreaterThanAndInfoType(date, infoType, pageable);
			}else{
				return baseDao.findByInfoType(infoType, pageable);
			}
		}catch (ParseException e) {
			throw new ServiceException(e);
		}
		
	}
	
	
	
	
	public Page<Base> listByStateAndInfoTypeAndInsertId(Integer state,int infoType,String insertId, Pageable pageable){
		return baseDao.findByStateAndInfoTypeAndInsertId(state,infoType,insertId, pageable);
	}
	
	/*
	 * 切片操作
	 */
	
	/**
	 * 保存切片
	 * 
	 * @param segment
	 * @return
	 */
	@Transactional(readOnly=false)
	public Segment saveSegment(Segment segment){
		return segmentDao.save(segment);
	}
	
	/**
	 * 保存切片List
	 */
	@Transactional(readOnly=false)
	public void saveSegmentList(List<Segment> segmentList) {
		for(Segment seg : segmentList) {
			segmentDao.save(seg) ;
		}
	}
	/**
	 * 通过id获取切片
	 * 
	 * @param id
	 * @return
	 */
	public Segment getSegmentById(String id){
		return segmentDao.findSegment(id);
	}
	
	/**
	 * 通过id删除切片
	 * @param id
	 */
	@Transactional(readOnly=false)
	public void deleteSegmentById(String id){
		segmentDao.delete(id);
	}
	
	
	public Page<Base> listBase(Specification<Base> specification,Pageable pageable){
		return baseDao.findAll(specification,pageable);
	}
	
	/**
	 * 知识切片列表查询
	 * @param tagId
	 * @param title
	 * @param source
	 * @param insertName
	 * @param state
	 * @param infoType
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> listBaseRows(String tagId, String title, String source, String insertName, String state, Integer infoType, String level, String begincreateDate, Pageable pageable) {
		
		Map<String, Object> page = new HashMap<String, Object>();
		
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT DISTINCT b.id, b.title, s.seg_item, b.source, b.insert_name, b.modify_date, b.state, b.weighing FROM info_segment s LEFT OUTER JOIN info_base b ON b.id = s.id");
		StringBuffer sbc = new StringBuffer("SELECT DISTINCT COUNT(s.id) FROM info_segment s LEFT OUTER JOIN info_base b ON b.id = s.id");
		
		if(tagId == null || "".equals(tagId)) {
			sb.append(" WHERE 1 = 1");
			
			sbc.append(" WHERE 1 = 1");
		}
		// 标签
		if(tagId != null && !"".equals(tagId)) {
			sb.append(" LEFT OUTER JOIN info_base_tag bt ON bt.base_id = b.id WHERE 1 = 1 AND bt.tag_id = '" + tagId + "'");
			
			sbc.append(" LEFT OUTER JOIN info_base_tag bt ON bt.base_id = b.id WHERE 1 = 1 AND bt.tag_id = '" + tagId + "'");
		}
		// 数据类型
		if(infoType != null && !"".equals(infoType)) {
			sb.append(" AND b.info_type =" + infoType);
			
			sbc.append(" AND b.info_type =" + infoType);
		}
		// 标题
		if(title != null && !"".equals(title)) {
			sb.append(" AND b.title LIKE '%" + title + "%' ");
			
			sbc.append(" AND b.title LIKE '%" + title + "%' ");
		}
		// 来源
		if(source != null && !"".equals(source)) {
			sb.append(" AND b.source LIKE '%" + source + "%' ");
			
			sbc.append(" AND b.source LIKE '%" + source + "%' ");
		}
		// 录入人
		if(insertName != null && !"".equals(insertName)) {
			sb.append(" AND b.insert_name LIKE '%" + insertName + "%' ");
			
			sbc.append(" AND b.insert_name LIKE '%" + insertName + "%' ");
		}
		// 审批状态
		if(state != null && !"".equals(state) && !"10000".equals(state)) {
			sb.append(" AND b.state =" + state);
			
			sbc.append(" AND b.state =" + state);
		}
		// 知识等级
		if(level != null && !"".equals(level) && !"10000".equals(level)) {
			sb.append(" AND b.weighing = " + level);
			
			sbc.append(" AND b.weighing = " + level);
		}
		// 发布日期
		if(begincreateDate != null && !"".equals(begincreateDate)) {
			sb.append(" AND b.begincreate_date = '" + begincreateDate + "'");
			
			sbc.append(" AND b.begincreate_date = '" + begincreateDate + "'");
		}
		// 排序
		//sb.append(" ORDER BY b.modify_date DESC");太慢
		// 分页
		sb.append(" LIMIT " + pageable.getPageNumber()* pageable.getPageSize() + "," + pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), SegmentModule.class);
		List<SegmentModule> rows = query.getResultList();   
		page.put("rows", rows);
		
		Query queryc = entityManager.createNativeQuery(sbc.toString());
		BigInteger total = (BigInteger)queryc.getResultList().get(0);
		page.put("total", total);
		
		entityManager.close();
        return page;
	}
	
	
	public List<BaseTagModule> findAllBaseTagModule(String fbfxcode,String peoplecode ,String areacode){
		
		String fbfx = "";
		String fbfxcondition = "";
		if(!(StringUtils.isBlank(fbfxcode)||(StringUtils.isNotBlank(fbfxcode)&&StringUtils.equals("10000000000000000", fbfxcode)))){
			String fbfxcodeend = fbfxcode.replace("00", "99");
			fbfx = ", info_base_tag f ";
			fbfxcondition = " and f.tag_code between "+fbfxcode+" and "+fbfxcodeend+" and z.base_id = f.base_id ";
		}
		
		String people = "";
		String peoplecondition = "";
		if(!(StringUtils.isBlank(peoplecode)||(StringUtils.isNotBlank(peoplecode)&&StringUtils.equals("30000000000000000", peoplecode)))){
			String peoplecodecodeend = peoplecode.replace("00", "99");
			people = ", info_base_tag r ";
			peoplecondition = " and r.tag_code between "+peoplecode+" and "+peoplecodecodeend+" and z.base_id = r.base_id ";
		}
		
		String area = "";
		String areacondition = "";
		if(!(StringUtils.isBlank(areacode)||(StringUtils.isNotBlank(areacode)&&StringUtils.equals("23100000000000000", areacode)))){
			String areacodecodeend = areacode.replace("00", "99");
			area = ", info_base_tag d ";
			areacondition = " and (d.tag_code between "+areacode+" and "+areacodecodeend+" or d.tag_code = 23100000000000000) and z.base_id = d.base_id ";
		}
		
		String sql = "";
		sql+=" SELECT  UUID() AS id,it.code,IFNULL(tt.totalcount,0) totalcount FROM info_tag it ";
		sql+=" left join ( select t.code,count(distinct z.base_id) as totalcount from ";
		sql+=" info_base_tag z "+fbfx+" "+people+" "+area+" ,info_tag t ";
		sql+=" where z.tag_code BETWEEN 40000000000000000 AND 49999999999999999";
		sql+=" "+fbfxcondition;
		sql+=" "+peoplecondition;
		sql+=" "+areacondition;
		sql+=" and z.title NOT LIKE '%样表%' ";
		sql+=" AND t.code BETWEEN 40000000000000000 AND 49999999999999999 ";
		sql+=" and t.level = 3 ";
		sql+=" AND z.tag_code BETWEEN t.code AND (t.code + CAST((POW(100,9 - (t.level)) - 1)AS UNSIGNED)) GROUP BY t.code ) tt";
		sql+=" ON it.code = tt.code";
		sql+=" WHERE it.code BETWEEN 40000000000000000 AND 49999999999999999 and it.level = 3 ";
		
		Query query = entityManager.createNativeQuery(sql,BaseTagModule.class);
		
		List<BaseTagModule> result = query.getResultList();   
		entityManager.close();
		return result;
	}
	
	public List<BaseUtilModule> listBaseByfbfxAndzsxzAndpersonAndarea(boolean isSortByDate,String fbfxstart,String fbfxend,boolean isfbfx,String zsxzstart,String zsxzend,boolean isPeopleSelect,String personstart,String personend,String []areaCode,String title,String pageSize,String pageNo){
		int size = 100;
		int number = 1;
		if(StringUtils.isNotBlank(pageSize)&&StringUtils.isNotBlank(pageNo)){
			size = Integer.parseInt(pageSize);
			number = Integer.parseInt(pageNo);
		}
		int count = size*(number-1);
		
		String temp ="";
		String areaCondition = "";
		if(areaCode.length>0){
			temp =" AND d.tag_code in (";
			for(String area:areaCode){
				temp +=area+",";
			}
			temp = temp.substring(0,temp.lastIndexOf(","));
			temp +=" )";
			areaCondition = " AND d.tag_code>0 ";
		}
		String fbfxCondition = "";
		if(!StringUtils.equals(fbfxstart,"10000000000000000")){
			fbfxCondition = " AND f.tag_code>0 ";
		}
		if(StringUtils.equals(fbfxstart,"10000000000000000")&&isfbfx){
			fbfxCondition = " AND f.tag_code>0 ";
		}
		String liketitle = "";
		String ispeople ="";
		if(isPeopleSelect){
			ispeople += " AND r.tag_code>0 ";
		} 
		if(StringUtils.isNotBlank(title)){
			liketitle += " AND bt.title like '%"+title+"%' ";
		}
		String sortbydate = " bt.weighing  ";
		if(isSortByDate){
			sortbydate = " bt.begincreate_date ";
		}else{
			sortbydate = "  bt.weighing desc ,bt.begincreate_date   ";
		}
		String nativesql = "SELECT  DISTINCT bt.id,bt.title,bt.info_type,bt.begincreate_date,bt.source,bt.modify_date,ic.clickcount"+
				" FROM info_base AS bt "+
				" left join info_clickcount ic on bt.id = ic.id "+
				"LEFT JOIN info_base_tag AS z "+ 
				  "ON z.tag_code BETWEEN "+zsxzstart+" AND "+zsxzend +
				 " AND bt.id = z.base_id "
				  +(fbfxCondition.length()>0?"LEFT JOIN info_base_tag AS f  ON f.tag_code BETWEEN "+fbfxstart+" AND "+fbfxend +" AND bt.id = f.base_id ":"")
				  +(areaCondition.length()>0?"LEFT JOIN info_base_tag AS d ON bt.id = d.base_id AND d.tag_code BETWEEN 20000000000000000 and 29999999999999999 "+ temp :"")
				  +(ispeople.length()>0?"LEFT JOIN info_base_tag AS r ON r.tag_code BETWEEN "+personstart +" AND "+personend +" AND bt.id = r.base_id ":"")+
				"WHERE z.tag_code>0 "+ areaCondition+" "+fbfxCondition+"  "+ ispeople +
				" AND (bt.state = 1 OR bt.state = 3) "+
				" AND  bt.title NOT LIKE '%样表%' "+ liketitle +
				" ORDER BY "+sortbydate+" desc limit "+count+","+pageSize;
		Query query = entityManager.createNativeQuery(nativesql,BaseUtilModule.class);
		 
        List<BaseUtilModule> result = query.getResultList();   
        entityManager.close();
        return result;
	}
	
	
public  BigInteger listBaseByfbfxAndzsxzAndpersonAndareaCount(String fbfxstart,
		String fbfxend,boolean isfbfx,String zsxzstart,String zsxzend,boolean isPeopleSelect,String personstart,String personend,
		String []areaCode,String title){
		String temp ="";
		String areaCondition = "";
		if(areaCode.length>0){
			temp =" AND d.tag_code in (";
			for(String area:areaCode){
				temp +=area+",";
			}
			temp = temp.substring(0,temp.lastIndexOf(","));
			temp +=" )";
			areaCondition = " AND d.tag_code>0 ";
		}
		String fbfxCondition = "";
		if(!StringUtils.equals(fbfxstart,"10000000000000000")){
			fbfxCondition = " AND f.tag_code>0 ";
		}
		if(StringUtils.equals(fbfxstart,"10000000000000000")&&isfbfx){
			fbfxCondition = " AND f.tag_code>0 ";
		}
		
		//System.out.println("area:"+temp);
		String liketitle = "";
		String ispeople ="";
		if(isPeopleSelect){
			ispeople += " AND r.tag_code>0 ";
		} 
		if(StringUtils.isNotBlank(title)){
			liketitle += " AND bt.title like '%"+title+"%' ";
		}
		String nativesql = "SELECT  count(DISTINCT bt.id) "+
				"FROM info_base AS bt "+
				"LEFT JOIN info_base_tag AS z "+ 
				  "ON z.tag_code BETWEEN "+zsxzstart+" AND "+zsxzend +
				 " AND bt.id = z.base_id "
				  +(fbfxCondition.length()>0?"LEFT JOIN info_base_tag AS f  ON f.tag_code BETWEEN "+fbfxstart+" AND "+fbfxend +" AND bt.id = f.base_id ":"")
				  +(areaCondition.length()>0?"LEFT JOIN info_base_tag AS d ON bt.id = d.base_id AND d.tag_code BETWEEN 20000000000000000 and 29999999999999999 "+ temp :"")
				  +(ispeople.length()>0?"LEFT JOIN info_base_tag AS r ON r.tag_code BETWEEN "+personstart +" AND "+personend +" AND bt.id = r.base_id ":"")+
				"WHERE z.tag_code>0 "+ areaCondition+" "+fbfxCondition+"  "+ ispeople +
				" AND (bt.state = 1 OR bt.state = 3) "+
				" AND  bt.title NOT LIKE '%样表%' "+ liketitle +
				" ";
		Query query = entityManager.createNativeQuery(nativesql);
		 
		BigInteger result = (BigInteger)query.getResultList().get(0)  ;
		
		entityManager.close();
        return result;
	}


	
	public Map<String, Object>  listForm(  String tagId,  String title,  String source,  String insertName,   String state,
			  String empUrl,   String empHiPicUrl,   String empLowPicUrl,   String descUrl,   String descContent, 
			  Integer infoType, String level, String begincreateDate, Pageable pageable) {
		Map<String, Object> page = new HashMap<String, Object>();

		String sql = "select f.id,f.emp_url,f.emp_hi_pic_url,f.emp_low_pic_url,f.samp_url,f.samp_hi_pic_url,f.samp_low_pic_url,f.desc_url, f.desc_content, b.modify_name,b.modify_date,b.title,b.source,b.insert_name,b.state,b.create_date, b.weighing from info_form f inner join info_base b on f.id = b.id ";
		String sqlc = "select count(1) from info_form f inner join info_base b on f.id = b.id ";
		
		if(StringUtils.isNotBlank(tagId)){
			sql += " join (select t.base_id from info_base_tag t where t.tag_id='"+tagId+"') tt on tt.base_id = f.id";
			
			sqlc += " join (select t.base_id from info_base_tag t where t.tag_id='"+tagId+"') tt on tt.base_id = f.id";
		}
		sql+=" where 1=1 and b.info_type = "+infoType;
		sqlc+=" where 1=1 and b.info_type = "+infoType;
		if(StringUtils.isNotBlank(title)){
			 sql += " and b.title like '%"+title.trim()+"%'";
			 
			 sqlc += " and b.title like '%"+title.trim()+"%'";
		}
		if(StringUtils.isNotBlank(insertName)){
			 sql += " and b.insert_name like '%"+insertName.trim()+"%'";
			 
			 sqlc += " and b.insert_name like '%"+insertName.trim()+"%'";
		}
		if(StringUtils.isNotBlank(source)){
			 sql += " and b.source like '%"+source.trim()+"%'";
			 
			 sqlc += " and b.source like '%"+source.trim()+"%'";
		}
		if(StringUtils.isNotBlank(state)){
			int status = Integer.parseInt(state);
			if(status != 10000){
				 sql += " and b.state = "+status;
				 
				 sqlc += " and b.state = "+status;
			}
		}
		if(!"0".equals(empUrl)) {	// 全部
			if("1".equals(empUrl)) {	// 已同步
				sql+=" and (f.emp_Url !='' and f.emp_Url is not null)";
				
				sqlc+=" and (f.emp_Url !='' and f.emp_Url is not null)";
			} else if("2".equals(empUrl)) {	// 未同步
				sql+=" and (f.emp_Url ='' or f.emp_Url is null)";
				
				sqlc+=" and (f.emp_Url ='' or f.emp_Url is null)";
			}
		}
		if(!"0".equals(empHiPicUrl)) {	// 全部
			if("1".equals(empHiPicUrl)) {	// 已同步
				sql+=" and (f.emp_Hi_Pic_Url !='' and f.emp_Hi_Pic_Url is not null)";
				
				sqlc+=" and (f.emp_Hi_Pic_Url !='' and f.emp_Hi_Pic_Url is not null)";
			} else if("2".equals(empHiPicUrl)) {	// 未同步
				sql+=" and (f.emp_Hi_Pic_Url ='' or f.emp_Hi_Pic_Url is null)";
				
				sqlc+=" and (f.emp_Hi_Pic_Url ='' or f.emp_Hi_Pic_Url is null)";
			}
		}
		
		if(!"0".equals(empLowPicUrl)) {	// 全部
			if("1".equals(empLowPicUrl)) {	// 已同步
				sql+=" and (f.emp_Low_Pic_Url !='' and f.emp_Low_Pic_Url is not null)";
				
				sqlc+=" and (f.emp_Low_Pic_Url !='' and f.emp_Low_Pic_Url is not null)";
			} else if("2".equals(empLowPicUrl)) {	// 未同步
				sql+=" and (f.emp_Low_Pic_Url ='' or f.emp_Low_Pic_Url is null)";
				
				sqlc+=" and (f.emp_Low_Pic_Url ='' or f.emp_Low_Pic_Url is null)";
			}
		}
		
		if(!"0".equals(descUrl)) {	// 全部
			if("1".equals(descUrl)) {	// 已同步
				sql+=" and (f.desc_Url !='' and f.desc_Url is not null)";
				
				sqlc+=" and (f.desc_Url !='' and f.desc_Url is not null)";
			} else if("2".equals(descUrl)) {	// 未同步
				sql+=" and (f.desc_Url ='' or f.desc_Url is null)";
				
				sqlc+=" and (f.desc_Url ='' or f.desc_Url is null)";
			}
		}
		// 填报说明文本
		if(!"0".equals(descContent)) {	// 全部
			if("1".equals(descContent)) {	// 已同步
				sql+=" and (f.desc_Content !='' and f.desc_Content is not null)";
				
				sqlc+=" and (f.desc_Content !='' and f.desc_Content is not null)";
			} else if("2".equals(descContent)) {	// 未同步
				sql+=" and (f.desc_Content ='' or f.desc_Content is null)";
				
				sqlc+=" and (f.desc_Content ='' or f.desc_Content is null)";
			}
		}
		// 发布日期
		if(StringUtils.isNotBlank(begincreateDate)) {
			sql += " AND b.begincreate_date = '" + begincreateDate + "'";
			
			sqlc += " AND b.begincreate_date = '" + begincreateDate + "'";
		}
		// 知识等级
		if(level != null && !"10000".equals(level)) {
			sql += " AND b.weighing = " + level;
			
			sqlc += " AND b.weighing = " + level;
		}
		
		//sql += " order by b.modify_date desc "; 太慢
		sql += " limit "+pageable.getPageNumber()*pageable.getPageSize()+","+pageable.getPageSize();
		
		Query query = entityManager.createNativeQuery(sql,FormModule.class);
		List<FormModule> rows =  query.getResultList();   
		page.put("rows", rows);
		
		Query queryc = entityManager.createNativeQuery(sqlc);
		BigInteger total = (BigInteger)  queryc.getResultList().get(0);   
		page.put("total", total);
		
		entityManager.close();
		
		return page;   
	}
	
	
	public Page<Powerpoint> listPpt(Specification<Powerpoint> specification, Pageable pageable) {
		return powerpointDao.findAll(specification, pageable) ;
	}
	
	public Page<Question> listQuestion(Specification<Question> specification, Pageable pageable) {
		return questionDao.findAll(specification, pageable) ;
	}
	
	public void modifyStatus(Integer status,String checkId,String checkName,Date checkDate,String id){
		baseDao.modifyStatus(status, checkId, checkName, checkDate, id);
	} 
	public void modifyStatus(Integer status,String checkId,String checkName,Date checkDate,String auditInfo,String id){
		baseDao.modifyStatus(status, checkId, checkName, checkDate, auditInfo, id);
	} 
	
	
	/**
	 * 读取Excle数据
	 * @param dataXls
	 * @return List<Segment>
	 * @throws Exception
	 */
	public List<Segment> readXls(String dataXls) throws Exception {
		Map<String, List<Segment>> beans = new HashMap<String, List<Segment>>() ;
		InputStream inputXML = new BufferedInputStream(this.getClass().getResourceAsStream(xmlConfig)) ;
		XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML) ;
		InputStream inputXLS = new BufferedInputStream(new FileInputStream(new File(dataXls))) ;
		List<Segment> segmentList = new ArrayList<Segment>() ;
		beans.put("segmentList", segmentList) ;
		mainReader.read(inputXLS, beans) ;
		return segmentList ;
		
	}
}
