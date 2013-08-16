package com.isoftstone.tyw.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.isoftstone.tyw.entity.info.Form;
import com.isoftstone.tyw.entity.info.FormView;
import com.isoftstone.tyw.repository.info.FormDao;
import com.isoftstone.tyw.repository.info.FormViewDao;
import com.isoftstone.tyw.util.PropertiesReader;

@Component
public class FormService {
	
	@Autowired
	private FormDao formDao;
	@Autowired
	private FormViewDao  formViewDao;
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	JpaTransactionManager txManager;
	
	@Autowired
	private FdfsService fdfsService;

	
	private static final String xmlConfig = "/META-INF/resource/form_mapping.xml" ;
	
	@Transactional(readOnly = false)
	public Form saveForm(Form form){
		return formDao.save(form);
	}
	
	@Transactional(readOnly = false)
	public void deleteForm(String id){
		//删除关系
		//baseBaseDao.deleteBaseBaseByParimaryId(id);
		//删除主表
		formDao.delete(id);
		//删除样表和填写说明
		/*for(FormExt formExt :formExtDao.findByPrimary(id)){
			formExtDao.delete(formExt);
		}*/
		
	}
	
	public Page<Form> listForm(Pageable pageable,String insertId,String syncDate){
		try{
			if(StringUtils.isNotBlank(syncDate)){
				Date date = DateUtils.parseDate(syncDate, "yyyy-MM-dd hh:mm:ss");
  				return formDao.findByCreateDateGreaterThanAndInsertId(date, insertId, pageable);
 			}else{
				return formDao.findByInsertId(insertId, pageable);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public Page<Form> listForm(Pageable pageable){
		return formDao.findAll(pageable);
	}
	
	public Form getFormById(String id){
		return formDao.findOne(id);
	}
	
	
	/**
	 * 读取Excle数据
	 * @param dataXls
	 * @return List<Form>
	 * @throws Exception
	 */
	public List<Form> readXls(String dataXls) throws Exception {
		Map<String, List<Form>> beans = new HashMap<String, List<Form>>() ;
		InputStream inputXML = new BufferedInputStream(this.getClass().getResourceAsStream(xmlConfig)) ;
		XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML) ;
		InputStream inputXLS = new BufferedInputStream(new FileInputStream(new File(dataXls))) ;
		List<Form> formList = new ArrayList<Form>() ;
		beans.put("formList", formList) ;
		mainReader.read(inputXLS, beans) ;
		return formList ;
		
	}
	
	
	public Specification<Form> getWhereClause(final String insertId,final String createDate){
		return new Specification<Form>(){
			@Override
			public Predicate toPredicate(Root<Form> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				// 创建时间倒叙
				query.orderBy(cb.desc(root.get("createDate")));

				
				//录入人
				if(StringUtils.isNotBlank(insertId)){
					predicate.getExpressions().add(cb.equal(root.<String>get("insertId"), insertId.trim()));
				}
				//同步时间
				if(StringUtils.isNotBlank(createDate)){
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.<String>get("createDate"), createDate));
				}
				return predicate;
			}
		};
	}
	
	/**
	 * 保存切片List
	 */
	@Transactional(readOnly=false)
	public void saveFormList(List<Form> formList) {
		for(Form form : formList) {
			formDao.save(form) ;
		}
	}
	
	@Transactional(readOnly=false) 
	public Form getFormByName(String title, String areaTag, String source) {
		return formDao.findOneByName(title, areaTag, source) ;
	} 
	
	@Transactional(propagation = Propagation.REQUIRED)
	public int modifySourceUrl(String formTitle, String formSort, String path, String areaTag, String source) {
		int reRows = 0;		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    def.setName("SomeTxName");
	    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	    TransactionStatus status = txManager.getTransaction(def);
		
		try{
			if("emp".equals(formSort)) {
				txManager.commit(status);
				reRows = formDao.updateEmpSourceUrl(formTitle, path, areaTag, source) ;
			} else if("samp".equals(formSort)) {
				txManager.commit(status);
				reRows = formDao.updateEmpSourceUrl(formTitle, path, areaTag, source) ;
			} else if("desc".equals(formSort)) {
				txManager.commit(status);
				reRows = formDao.updateDescSourceUrl(formTitle, path, areaTag, source) ;
			} else {
				reRows = 0 ;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return reRows;
	}
	
	@Transactional(readOnly=false)
	public int modifySourceUrlOriginal(String formTitle, String formSort, String path, String areaTag, String source) {
		int reRows = 0;		
		try{
			if("emp".equals(formSort)) {
				reRows = formDao.updateEmpSourceUrl(formTitle, path, areaTag, source) ;
			} else if("samp".equals(formSort)) {
				reRows = formDao.updateEmpSourceUrl(formTitle, path, areaTag, source) ;
			} else if("desc".equals(formSort)) {
				reRows = formDao.updateDescSourceUrl(formTitle, path, areaTag, source) ;
			} else {
				reRows = 0 ;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return reRows;
	}
	
	@Transactional(readOnly=false)
	public Map<String, Object> modifyUrl(TreeSet<File> allFiles) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		// 存放错误信息
		String msg = "" ;
		try {
			Iterator<File> ite = allFiles.iterator();
			while (ite.hasNext()) {
				File f = ite.next();
				// 大小
				Long fileLength = f.length();
				// 绝对路径
				String fileAbsultePath = f.getAbsolutePath();
				
				// 文件名
//				String fileName = fileAbsultePath.substring(
//						fileAbsultePath.lastIndexOf("\\") + 1,
//						fileAbsultePath.lastIndexOf(".")); // Windows 环境
				
				 String fileName =
				 fileAbsultePath.substring(fileAbsultePath.lastIndexOf("/")+1,
				 fileAbsultePath.lastIndexOf(".")) ; // linux 环境

				msg = fileName ;
				
				// 文件后缀
				String fileExtName = fileAbsultePath.substring(fileAbsultePath
						.lastIndexOf(".") + 1);
				// 标题
				String fileTitle = fileName.substring(0, fileName.indexOf("_"));
				
				// 地区标签
				// windows
//	    		String fileAreaTag = fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).substring(0, fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).lastIndexOf("\\")).substring(fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).substring(0, fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).lastIndexOf("\\")).lastIndexOf("\\")+1) ;
	    		// linux
	    		String fileAreaTag = fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).substring(0, fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).lastIndexOf("/")).substring(fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).substring(0, fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).lastIndexOf("/")).lastIndexOf("/")+1) ;
	    		
	    		// 来源标记
	    		// windows
//	    		String fileSource = fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).substring(fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).lastIndexOf("\\")+1) ;
	    		// linux
	    		String fileSource = fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).substring(fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).lastIndexOf("/")+1) ;
	    		// 模糊查询
	    		fileSource = "%" + fileSource + "%" ;
	    		
				if (formDao.findOneByName(fileTitle, fileAreaTag, fileSource) != null) { // 已通过Excel导入数据
					// 上传到文件系统
					PropertiesReader pu = PropertiesReader.getInstance();
					String path = pu.getProperties("fdfs.HttpAddress");
					// 上传附件的格式校验
					if ("xls".equals(fileExtName) || "jpg".equals(fileExtName)
							|| "doc".equals(fileExtName)
							|| "pdf".equals(fileExtName)
							|| "xlsx".equals(fileExtName)
							|| "docx".equals(fileExtName)) {
						InputStream inputStream = new FileInputStream(f);
						try {
							path += fdfsService.upload(fileName, fileExtName,
									inputStream, fileLength);
							if(path.equals(pu.getProperties("fdfs.HttpAddress"))) {	// 文件服务器返回地址为空
						 		 result.put("fail", true);
								 result.put("msg", "文件服务器fdfs未正确的返回附件地址！") ;
								 return result;
							 }
						} catch (Exception e) {
							result.put("success", false);
							result.put("msg", "同步失败，附件 《 " + fileName + " 》 上传文件系统出错！" ) ;
							e.printStackTrace();
						}
						// 存数据库
						// 标题
						String formTitle = "";
						// 类型 emp/samp
						String formSort = "";
						// 大小 WEB/Mobile
						String formSize = "";
						// 顺序（多图片）
						String formOrder = "";
						try {
							String arr[];
							arr = fileName.split("_");
							if (arr.length == 2) { // 源文件
								formTitle = arr[0];
								formSort = arr[1];
								try{
									if("emp".equals(formSort)) {
										formDao.updateEmpSourceUrl(formTitle, path, fileAreaTag, fileSource) ;
									} else if("samp".equals(formSort)) {
										formDao.updateEmpSourceUrl(formTitle, path, fileAreaTag, fileSource) ;
									} else if("desc".equals(formSort)) {
										formDao.updateDescSourceUrl(formTitle, path, fileAreaTag, fileSource) ;
									}
								}catch(Exception e){
									e.printStackTrace();
								}

							} else if (arr.length == 4) { // 图片文件
								formTitle = arr[0];
								formSort = arr[1];
								formSize = arr[2];
								formOrder = arr[3];
								try {
									if("emp".equals(formSort) || "samp".equals(formSort)) {	// 空表或样表
										if("hi".equals(formSize)) {		// WEB展现
											formDao.updateEmpHiNoFirstPicUrl(formTitle, path, fileAreaTag, fileSource) ;
										} else if("low".equals(formSize)) {		// 移动展现
											formDao.updateEmpLowNoFirstPicUrl(formTitle, path, fileAreaTag) ;
										}
									} 
								} catch (Exception e) {
									e.printStackTrace();
								}
							} 
						} catch (Exception e) {
							result.put("success", false);
							result.put("msg", "同步失败, 附件 《" + fileName + "》 更新数据库时出错，请检查！");
							e.printStackTrace();
						}
					} else {
	    				  result.put("fail", true);
	    				  result.put("msg", "同步失败，暂不支持该类型《" + fileName + "." + fileExtName + "》的附件同步！");
	    				  return result;
   	    		  	}
				}

			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "同步失败, 附件 《" + msg + "》 名称格式有误，请检查！");
			e.printStackTrace();
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int modifyPicUrl(String formTitle, String formSort, String formSize, String formOrder, String path, String areaTag, String source) {
		int reRows = 0;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    def.setName("SomeTxName");
	    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	    TransactionStatus status = txManager.getTransaction(def);
		try {
			if("emp".equals(formSort) || "samp".equals(formSort)) {	// 空表或样表
				if("hi".equals(formSize)) {		// WEB展现
					txManager.commit(status);
					reRows = formDao.updateEmpHiNoFirstPicUrl(formTitle, path, areaTag, source) ;
				} else if("low".equals(formSize)) {		// 移动展现
					txManager.commit(status);
					reRows = formDao.updateEmpLowNoFirstPicUrl(formTitle, path, areaTag) ;
				} else {
					reRows = 0 ;
				}
			} else {
				reRows = 0 ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reRows;
	}
	
	@Transactional(readOnly=false)
	public int modifyPicUrlOriginal(String formTitle, String formSort, String formSize, String formOrder, String path, String areaTag, String source) {
		int reRows = 0;
		try {
			if("emp".equals(formSort) || "samp".equals(formSort)) {	// 空表或样表
				if("hi".equals(formSize)) {		// WEB展现
					reRows = formDao.updateEmpHiNoFirstPicUrl(formTitle, path, areaTag, source) ;
				} else if("low".equals(formSize)) {		// 移动展现
					reRows = formDao.updateEmpLowNoFirstPicUrl(formTitle, path, areaTag) ;
				} else {
					reRows = 0 ;
				}
			} else {
				reRows = 0 ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reRows;
	}
	
	@Transactional(readOnly=false)
	public void deletePic(String id, String url, String type) {
		if("hi".equals(type)) {
			formDao.deleteHiPic(id, url) ;
		} else if("low".equals(type)) {
			formDao.deleteLowPic(id, url) ;
		} 
	}
	
	@Transactional(readOnly = false)
	public Page<FormView> listFormView(String name,Pageable pageable){
		return formViewDao.findAll(this.getWhereClause(name),pageable);
	}
	
	
	public Specification<FormView> getWhereClause
	(final String name){
		return new Specification<FormView>(){
			@Override
			public Predicate toPredicate(Root<FormView> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				
				if(name!=null&&!"".equals(name)){
					predicate.getExpressions().add(cb.like(root.<String>get("name"), "%"+name+"%"));
				}
				return predicate;
			}
		};
	}
	
	@Transactional(readOnly=false)
	public String getAreaTagById(String id) {
		return formDao.findAreaTagById(id);
	}
	
	@Transactional(readOnly=false)
	public List<Form> getExampleFormList(String formTitle, String formSource, String formAreaTag, Integer pageIndex, Integer itemsPerPage) {
		return formDao.findExampleFormList(formTitle, formSource, formAreaTag, pageIndex, itemsPerPage);
	}
	
	@Transactional(readOnly=false)
	public BigInteger getExampleFormListCount(String formTitle, String formSource, String formAreaTag) {
		return formDao.findExampleFormListCount(formTitle, formSource, formAreaTag);
	}
	
	@Transactional(readOnly=false)
	public List<Form> getOtherExampleFormList(String formTitle, Integer pageIndex, Integer itemsPerPage) {
		return formDao.findOtherExampleFormList(formTitle, pageIndex, itemsPerPage);
	}
	
	@Transactional(readOnly=false)
	public BigInteger getOtherExampleFormListCount(String formTitle) {
		return formDao.findOtherExampleFormListCount(formTitle);
	}
	
	/**
	 * 表格同步统计列表查询
	 * @param name
	 * @param pageable
	 * @return
	 */
	public List<FormView> listFormViewBaseRows(String name, Pageable pageable) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT * FROM view_report_form t WHERE 1 = 1 ");
		// 地区名称
		if(name != null && !"".equals(name)) {
			sb.append(" AND t.name like '%" + name + "%' ");
		}
		// 分页
		sb.append(" limit " + pageable.getPageNumber()*pageable.getPageSize() + "," + pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), FormView.class);
		
		List<FormView> result = query.getResultList();   
		entityManager.close();
        return result;
	}
	
	/**
	 * 表格同步统计列表查询(count)
	 * @param name
	 * @return
	 */
	public BigInteger listFormViewBaseTotal(String name) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT count(*) FROM view_report_form t WHERE 1 = 1 ");
		// 地区名称
		if(name != null && !"".equals(name)) {
			sb.append(" AND t.name like '%" + name + "%' ");
		}
		Query query = entityManager.createNativeQuery(sb.toString());
		
		BigInteger total = (BigInteger)query.getResultList().get(0);   
		entityManager.close();
        return total;
	}

	
}
