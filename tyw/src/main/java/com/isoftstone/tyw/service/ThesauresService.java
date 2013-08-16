package com.isoftstone.tyw.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.Thesaures;
import com.isoftstone.tyw.entity.info.ThesauresModule;
import com.isoftstone.tyw.repository.info.ThesauresDao;

/**
 * 
 * @author liuwei
 *
 */
@Component
@Transactional(readOnly=true)
public class ThesauresService {

	private static final String xmlConfig = "/META-INF/resource/thesaures_mapping.xml" ;
	
	@Autowired
	private ThesauresDao thesauresDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Page<Thesaures> listThesaures(Specification<Thesaures> specification,Pageable pageable){
		return thesauresDao.findAll(specification,pageable);
	}
	
	@Transactional(readOnly=false)
	public void saveOne(Thesaures t) {
		thesauresDao.save(t);
	}
	
	@Transactional(readOnly=true) 
	public Thesaures getOne(String id) {
		return thesauresDao.findOne(id);
	}
	
	@Transactional(readOnly=false)
	public void delOne(String id) {
		thesauresDao.delete(id);
	}
	
	/**
	 * 读取Excle数据
	 * @param dataXls
	 * @return List<Segment>
	 * @throws Exception
	 */
	public List<Thesaures> readXls(String dataXls) throws Exception {
		Map<String, List<Thesaures>> beans = new HashMap<String, List<Thesaures>>() ;
		InputStream inputXML = new BufferedInputStream(this.getClass().getResourceAsStream(xmlConfig)) ;
		XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML) ;
		InputStream inputXLS = new BufferedInputStream(new FileInputStream(new File(dataXls))) ;
		List<Thesaures> thesauresList = new ArrayList<Thesaures>() ;
		beans.put("thesauresList", thesauresList) ;
		mainReader.read(inputXLS, beans) ;
		return thesauresList ;
	}
	
	/**
	 * 检查名称唯一性
	 * @param name
	 * @return
	 */
	public Thesaures getOneByName(String name) {
		return thesauresDao.findOneByName(name);
	}
	
	/**
	 * excel导入保存
	 * @param tList
	 */
	@Transactional(readOnly=false)
	public void saveAll(List<Thesaures> tList) {
		for(Thesaures t: tList) {
			thesauresDao.save(t);
		}
	}
	
	/**
	 * 词库列表查询
	 * @param name
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> listThesauresBySql(String name, Pageable pageable) {
		// 结果
		Map<String, Object> result = new HashMap<String, Object>();
		// 类容
		StringBuffer sb = new StringBuffer(" SELECT t.id, t.name FROM info_thesaures t WHERE 1 = 1");
		// 数量
		StringBuffer sbc = new StringBuffer(" SELECT COUNT(t.id) FROM info_thesaures t WHERE 1 = 1");
		
		// 名称
		if(name != null && !"".equals(name)) {
			sb.append(" AND t.name LIKE '%" + name + "%'");
			
			sbc.append(" AND t.name LIKE '%" + name + "%'");
		}
		
		// 排序
		sb.append(" ORDER BY t.len");
		// 分页
		sb.append(" LIMIT " + pageable.getPageNumber() * pageable.getPageSize() + ", " + pageable.getPageSize());
		
		// 查询
		Query q = entityManager.createNativeQuery(sb.toString(), ThesauresModule.class);
		Query qc = entityManager.createNativeQuery(sbc.toString());
		
		List<ThesauresModule> rows = q.getResultList();
		BigInteger total = (BigInteger)qc.getResultList().get(0);
		
		entityManager.close();
		result.put("rows", rows);
		result.put("total", total);
		
		return result;
	}
	
}
