package com.isoftstone.tyw.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.LicensePaymentModule;
import com.isoftstone.tyw.entity.pub.LicensePayment;
import com.isoftstone.tyw.repository.auths.UserDao;
import com.isoftstone.tyw.repository.pub.LicensePaymentDao;


@Component
@Transactional(readOnly = true)
public class LicensePaymentService {
	
	
	@Autowired
	private LicensePaymentDao licensePaymentDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UserDao userdao;

	
	public Page<LicensePayment> listLicensePayment(Specification<LicensePayment> specification, Pageable pageable) {
		return licensePaymentDao.findAll(specification, pageable) ;
	}
	 
	/**
	 * 订单列表查询
	 * @param vOid
	 * @param payStatus
	 * @param sAgentId
	 * @param sBatchCode
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> listLicensePaymentBySql(String vOid, String payStatus, String sAgentId, String sBatchCode, Pageable pageable) {
		Map<String, Object> page = new HashMap<String, Object>();
		// sql
		StringBuffer sb = new StringBuffer(" SELECT DISTINCT p.id, a.name, b.batch_code, u.aliasname, p.pay_status, p.pay_amount, p.pay_date, p.v_oid, p.v_pmode, p.v_pstring, p.v_moneytype FROM pub_license_payment p LEFT OUTER JOIN pub_license c ON c.id = p.license_id LEFT OUTER JOIN pub_license_batch b ON b.batch_code = c.batch_code LEFT OUTER JOIN auths_agent a ON a.id = b.agent_id LEFT OUTER JOIN auths_user u ON u.id = p.user_id WHERE 1 = 1");
		StringBuffer sbc = new StringBuffer(" SELECT COUNT(DISTINCT p.id) FROM pub_license_payment p LEFT OUTER JOIN pub_license c ON c.id = p.license_id LEFT OUTER JOIN pub_license_batch b ON b.batch_code = c.batch_code LEFT OUTER JOIN auths_agent a ON a.id = b.agent_id LEFT OUTER JOIN auths_user u ON u.id = p.user_id WHERE 1 = 1");
		
		// 订单编号
		if(vOid != null && !"".equals(vOid)) {
			sb.append(" AND p.v_oid = '" + vOid + "'");
			
			sbc.append(" AND p.v_oid = '" + vOid + "'");
		}
		// 交费状态
		if(payStatus != null && !"".equals(payStatus) && !"0".equals(payStatus)) {
			if("2".equals(payStatus)) {	// 未交费
				sb.append(" AND p.pay_status IS NULL");
				
				sbc.append(" AND p.pay_status IS NULL");
			} else {
				sb.append(" AND p.pay_status = " + payStatus);
				
				sbc.append(" AND p.pay_status = " + payStatus);
			}
			
		}
		// 渠道商
		if(sAgentId != null && !"".equals(sAgentId)) {
			sb.append(" AND b.agent_id = '" + sAgentId + "'");
			
			sbc.append(" AND b.agent_id = '" + sAgentId + "'");
		}
		// 批次号
		if(sBatchCode != null && !"".equals(sBatchCode)) {
			sb.append(" AND c.batch_code = " + sBatchCode);
			
			sbc.append(" AND c.batch_code = " + sBatchCode);
		}
		// 排序
		sb.append(" ORDER BY p.pay_date DESC");
		// 分页
		sb.append(" LIMIT " + pageable.getPageNumber()*pageable.getPageSize() + ", " + pageable.getPageSize());
		// 查询
		Query q = entityManager.createNativeQuery(sb.toString(), LicensePaymentModule.class);
		Query qc = entityManager.createNativeQuery(sbc.toString());
		
		List<LicensePaymentModule> result = q.getResultList();
		BigInteger resultc = (BigInteger)qc.getResultList().get(0);
		
		page.put("rows", result);
		page.put("total", resultc);
		
		entityManager.close();
		
		return page;
	}
	
	/**
	 * 获取所有经销商
	 * @return
	 */
	public List<User> getAgentNameList() {
		return userdao.findAgentNameList();
	}
}

