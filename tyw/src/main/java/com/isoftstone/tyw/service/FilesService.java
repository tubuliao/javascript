package com.isoftstone.tyw.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.isoftstone.tyw.entity.info.FileModule;
import com.isoftstone.tyw.entity.info.Files;
import com.isoftstone.tyw.entity.info.FilesPageCount;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.repository.info.BaseBaseDao;
import com.isoftstone.tyw.repository.info.BaseDao;
import com.isoftstone.tyw.repository.info.FilesDao;
import com.isoftstone.tyw.repository.info.FilesPageDao;
import com.isoftstone.tyw.util.PropertiesReader;

@Component
@Transactional(readOnly = true)
public class FilesService {
	
	@Autowired
	private FilesDao filesDao;
	@Autowired
    private FilesPageDao filesPageDao;
	@Autowired
	private BaseBaseDao baseBaseDao;
	@Autowired
	private BaseDao baseDao;
	@PersistenceContext
	public EntityManager entityManager;
	@Autowired
	JpaTransactionManager txManager;
	@Autowired
	private FdfsService fdfsService;
	
	private static final String xmlConfig = "/META-INF/resource/file_mapping.xml" ;
	
	@Transactional(readOnly = false)
	public Files saveFile(Files files){
		return filesDao.save(files);
	}
	
	@Transactional(readOnly = false)
	public void deleteFile(String id){
		baseDao.deleteBaseTag(id);
		baseDao.deleteBase(id);
		filesDao.deleteFile(id);
	}
	
	public Page<Files> listFiles(Pageable pageable){
		return filesDao.findAll(pageable);
	}
	
	public Files getFileById(String id){
		return filesDao.findFile(id);
	}
	
	
	
	/**
	 * 读取Excle数据
	 * @param dataXls
	 * @return List<Files>
	 * @throws Exception
	 */
	public List<Files> readXls(String dataXls) throws Exception {
		Map<String, List<Files>> beans = new HashMap<String, List<Files>>() ;
		InputStream inputXML = new BufferedInputStream(this.getClass().getResourceAsStream(xmlConfig)) ;
		XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML) ;
		InputStream inputXLS = new BufferedInputStream(new FileInputStream(new File(dataXls))) ;
		List<Files> fileList = new ArrayList<Files>() ;
		beans.put("fileList", fileList) ;
		mainReader.read(inputXLS, beans) ;
		return fileList ;
	}
	 
	 /**
     * 大文本分页
     * @param fileId
     */
    public FilesPageCount findContent(int pageStart,int pageEnd,String id){
        return filesPageDao.findContent( pageStart, pageEnd, id);
    }
   public String findContentPage(String id){
       return filesPageDao.findContentPage(id);
   }
	public Page<Files> listFiles(Pageable pageable,String insertId,String syncDate){
		try{
			if(StringUtils.isNotBlank(syncDate)){
				Date date = DateUtils.parseDate(syncDate, "yyyy-MM-dd hh:mm:ss");
				return filesDao.findByCreateDateGreaterThanAndInsertId(date, insertId, pageable);
			}else{
				return filesDao.findByInsertId(insertId, pageable);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存切片List
	 */
	@Transactional(readOnly=false)
	public void saveFileList(List<Files> fileList) {
		for(Files file : fileList) {
			filesDao.save(file) ;
		}
	}
	
	@Transactional(readOnly=false)
	public Files getFileByName(String name) {
		return filesDao.findByName(name) ;
	}

	/**
	 * 大文件接口service保存文件
	 * @param file
	 * @return
	 */
	@Transactional(readOnly=false)
	public int saveFileAndBase(Files file){
		System.out.println("FilesId_length:"+file.getId().length());
		System.out.println("FilesId:"+file.getId());
		System.out.println("FilesId_trim_length:"+file.getId().trim().length());
		if(file.getBegincreateDate()!=null){
			baseDao.insertBase(file.getId(), file.getTitle(), file.getInfoType(), file.getInsertId(), file.getInsertName(), file.getSource(),file.getShortTitle(),file.getBegincreateDate());
		}else{
			baseDao.insertBase(file.getId(), file.getTitle(), file.getInfoType(), file.getInsertId(), file.getInsertName(), file.getSource(),file.getShortTitle());
		}
		for(Tag tag:file.getTags()){
			baseDao.insertBaseTag(file.getId(),tag.getId());
		}
		int result=0;
		if(file.getTotalPages()>0){
			result=filesDao.insertFile(file.getId(),file.getUrl(), file.getCatalog(),file.getTotalPages());
		}else{
			result=filesDao.insertFile(file.getId(),file.getUrl(), file.getCatalog());
		}
		return result;
	}
	

	/**
	 * 同步附件，更新url字段
	 * @param id
	 * @param url
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUrl(String id, String url) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    def.setName("SomeTxName");
	    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	    TransactionStatus status = txManager.getTransaction(def);
		try {
			txManager.commit(status);
			filesDao.modifyUrl(id, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 同步附件，更新url字段(original)
	 * @param id
	 * @param url
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUrlOriginal(String id, String url) {
		try {
			filesDao.modifyUrl(id, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 大文件列表查询
	 * @param tagId
	 * @param title
	 * @param source
	 * @param insertName
	 * @param state
	 * @param infoType
	 * @param synStatus
	 * @param pageable
	 * @return
	 */
	public List<FileModule> listFileBaseRows(String tagId, String title, String source, String insertName, String state, Integer infoType, String synStatus, String level, String begincreateDate,Pageable pageable) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT DISTINCT b.id, b.title, b.create_date, s.url, b.source, b.insert_name, b.modify_date, b.state,b.weighing FROM info_files s LEFT OUTER JOIN info_base b ON b.id = s.id");
		
		if(tagId == null || "".equals(tagId)) {
			sb.append(" WHERE 1 = 1");
		}
		// 标签
		if(tagId != null && !"".equals(tagId)) {
			sb.append(" LEFT OUTER JOIN info_base_tag bt ON bt.base_id = b.id WHERE 1 = 1 AND bt.tag_id = '" + tagId + "'");
		}
		// 数据类型
		if(infoType != null && !"".equals(infoType)) {
			sb.append(" AND b.info_type =" + infoType);
		}
		// 标题
		if(title != null && !"".equals(title)) {
			sb.append(" AND b.title LIKE '%" + title + "%' ");
		}
		// 来源
		if(source != null && !"".equals(source)) {
			sb.append(" AND b.source LIKE '%" + source + "%' ");
		}
		// 录入人
		if(insertName != null && !"".equals(insertName)) {
			sb.append(" AND b.insert_name LIKE '%" + insertName + "%' ");
		}
		// 审批状态
		if(state != null && !"".equals(state) && !"10000".equals(state)) {
			sb.append(" AND b.state =" + state);
		}
		//同步状态（已同步）
		if(synStatus != null && !"".equals(synStatus) && synStatus.equals("1")){
			sb.append(" AND s.url != '' ");
		}
		//同步状态（未同步）
		if(synStatus != null && !"".equals(synStatus) && synStatus.equals("2")){
			sb.append(" AND s.url = '' ");
		}
		// 发布日期
		if(StringUtils.isNotBlank(begincreateDate)) {
			sb.append(" AND b.begincreate_date = '" + begincreateDate + "'");
			
		}
		// 知识等级
		if(level != null && !"10000".equals(level)) {
			sb.append(" AND b.weighing = " + level);
			
		}
		// 分页
		sb.append(" limit " + pageable.getPageNumber()*pageable.getPageSize() + "," + pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), FileModule.class);
		
		List<FileModule> result = query.getResultList();   
		
		entityManager.close();
        return result;
	}
	
	/**
	 * 大文件列表查询（count）
	 * @param tagId
	 * @param title
	 * @param source
	 * @param insertName
	 * @param state
	 * @param synStatus
	 * @param infoType
	 * @return
	 */
	public BigInteger listFileBaseTotal(String tagId, String title, String source, String insertName, String state, String synStatus, String level, String begincreateDate,Integer infoType) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT DISTINCT COUNT(s.id) FROM info_files s LEFT OUTER JOIN info_base b ON b.id = s.id");
		
		if(tagId == null || "".equals(tagId)) {
			sb.append(" WHERE 1 = 1");
		}
		// 标签
		if(tagId != null && !"".equals(tagId)) {
			sb.append(" LEFT OUTER JOIN info_base_tag bt ON bt.base_id = b.id WHERE 1 = 1 AND bt.tag_id = '" + tagId + "'");
		}
		// 数据类型
		if(infoType != null && !"".equals(infoType)) {
			sb.append(" AND b.info_type =" + infoType);
		}
		// 标题
		if(title != null && !"".equals(title)) {
			sb.append(" AND b.title LIKE '%" + title + "%' ");
		}
		// 来源
		if(source != null && !"".equals(source)) {
			sb.append(" AND b.source LIKE '%" + source + "%' ");
		}
		// 录入人
		if(insertName != null && !"".equals(insertName)) {
			sb.append(" AND b.insert_name LIKE '%" + insertName + "%' ");
		}
		// 审批状态
		if(state != null && !"".equals(state) && !"10000".equals(state)) {
			sb.append(" AND b.state =" + state);
		}
		//同步状态（已同步）
		if(synStatus != null && !"".equals(synStatus) && synStatus.equals("1")){
			sb.append(" AND s.url != '' ");
		}
		//同步状态（未同步）
		if(synStatus != null && !"".equals(synStatus) && synStatus.equals("2")){
			sb.append(" AND s.url = '' ");
		}
		
		// 发布日期
		if(StringUtils.isNotBlank(begincreateDate)) {
			sb.append(" AND b.begincreate_date = '" + begincreateDate + "'");
			
		}
		// 知识等级
		if(level != null && !"10000".equals(level)) {
			sb.append(" AND b.weighing = " + level);
			
		}
		
		Query query = entityManager.createNativeQuery(sb.toString());
		
		BigInteger total = (BigInteger)query.getResultList().get(0);   
		
		entityManager.close();
        return total;
	}
	
	
	public Map<String, Object> modifyUrl(TreeSet<File> allFiles) {
		 Map<String, Object> result = new HashMap<String, Object>();
		 Files files = new Files() ;	// 根据附件名称获取的Files对象
		 Iterator<File> ite = allFiles.iterator() ;
		 while(ite.hasNext()) {
			 File f = ite.next() ;
   		 // 大小
   		 Long fileLength = f.length() ;
   		 // 绝对路径
   		 String fileAbsultePath = f.getAbsolutePath() ;
   		 // 文件名
//   		 String fileName = fileAbsultePath.substring(fileAbsultePath.lastIndexOf("\\")+1, fileAbsultePath.lastIndexOf(".")) ;	// Windows 环境
   		 String fileName = fileAbsultePath.substring(fileAbsultePath.lastIndexOf("/")+1, fileAbsultePath.lastIndexOf(".")) ;	// Linux 环境
   		 // 文件后缀
   		 String fileExtName =  fileAbsultePath.substring(fileAbsultePath.lastIndexOf(".")+1) ;
   		
   		 // 存数据库
   		 String path = "" ;
   		 if(fileName.contains("_")) {
   			 // 多附件，附件名称：标题_数字
   			 files = filesDao.findByName(fileName.substring(0, fileName.indexOf("_"))) ;
   			 if(files != null) {
        			// 上传到文件系统 fdfs
    	    		 PropertiesReader pu=PropertiesReader.getInstance();
    	    		 path = pu.getProperties("fdfs.HttpAddress");
    	    		 if("xls".equals(fileExtName) || "jpg".equals(fileExtName) || "doc".equals(fileExtName) ||
    	    				 "xlsx".equals(fileExtName) || "docx".equals(fileExtName) || "pdf".equals(fileExtName) || "rar".equals(fileExtName)){
    	    		 	 InputStream inputStream = null;
						try {
							inputStream = new FileInputStream(f);
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
    	        		  try {
    	                 	 path += fdfsService.upload(fileName, fileExtName, inputStream, fileLength);
						 	 if(path.equals(pu.getProperties("fdfs.HttpAddress"))) {	// 文件服务器返回地址为空
						 		 result.put("fail", true);
								 result.put("msg", "文件服务器fdfs未正确的返回附件地址！") ;
								 return result;
							 }
    	     			  } catch (Exception e) {
    	     				 result.put("fail", true);
    	     				 result.put("msg", "上传文件系统出错！") ;
    	     				 e.printStackTrace();
    	     			  } 
    	    		  }	else {
	    				  result.put("fail", true);
	    				  result.put("msg", "同步失败，暂不支持该类型《" + fileName + "." + fileExtName + "》的附件同步！");
	    				  return result;
    	    		  }
    	    		 try {
    	    			// 更新url字段，继续拼接url,通过“;”分隔
						try {
							filesDao.modifyUrl(files.getId(), path);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						result.put("fail", true);
						result.put("msg", "更新数据库时出错！") ;
						e.printStackTrace();
					}
        		 }
   		 	}
		 }
		 return result;
	}
}
