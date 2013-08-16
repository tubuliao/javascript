package com.isoftstone.tyw.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.pub.ReceiptMoudle;
import com.isoftstone.tyw.repository.pub.ReceiptDao;

@Component
@Transactional(readOnly=true)
public class ReceiptService {
	
	@Autowired
	private ReceiptDao receiptDao;
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 发票列表查询
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> listReceiptBySql(String licenseNumber, String status, String sAgentId, String sBatchCode, Pageable pageable) {
		Map<String, Object> page = new HashMap<String, Object>();
		// sql
		StringBuffer sb = new StringBuffer(" SELECT r.id, a.name AS agent_name, e.batch_code, r.receipt_title, r.receipt_person, r.post_code, r.receipt_phone, r.receipt_address, r.receipt_amount, p.v_moneytype, r.license_number, r.status FROM pub_license_receipt r LEFT OUTER JOIN pub_license e ON e.license_num = r.license_number LEFT OUTER JOIN pub_license_batch b ON b.batch_code = e.batch_code LEFT OUTER JOIN pub_license_payment p ON p.license_id = e.id LEFT OUTER JOIN auths_agent a ON a.id = b.agent_id WHERE 1 = 1");
		StringBuffer sbc = new StringBuffer(" SELECT COUNT(*) FROM pub_license_receipt r LEFT OUTER JOIN pub_license e ON e.license_num = r.license_number LEFT OUTER JOIN pub_license_batch b ON b.batch_code = e.batch_code LEFT OUTER JOIN pub_license_payment p ON p.license_id = e.id LEFT OUTER JOIN auths_agent a ON a.id = b.agent_id WHERE 1 = 1");
		// 序列号
		if(licenseNumber != null && !"".equals(licenseNumber)) {
			sb.append(" AND r.license_number LIKE '%" + licenseNumber + "%'");
			
			sbc.append(" AND r.license_number LIKE '%" + licenseNumber + "%'");
		}
		// 发票状态
		if(status != null && !"".equals(status)) {
			sb.append(" AND r.status = " + status);
			
			sbc.append(" AND r.status = " + status);
		}
		// 渠道商
		if(sAgentId != null && !"".equals(sAgentId)) {
			sb.append(" AND b.agent_id = '" + sAgentId + "'");
			
			sbc.append(" AND b.agent_id = '" + sAgentId + "'");
		}
		// 批次号
		if(sBatchCode != null && !"".equals(sBatchCode)) {
			sb.append(" AND b.batch_code LIKE '%" + sBatchCode + "%'");
			
			sbc.append(" AND b.batch_code LIKE '%" + sBatchCode + "%'");
		}
		// 排序
		sb.append(" ORDER BY p.pay_date DESC");
		// 分页
		sb.append(" LIMIT " + pageable.getPageNumber() * pageable.getPageSize() + ", " + pageable.getPageSize());
		
		// 查询
		Query q = entityManager.createNativeQuery(sb.toString(), ReceiptMoudle.class);
		Query qc = entityManager.createNativeQuery(sbc.toString());
		
		page.put("rows", q.getResultList());
		page.put("total", (BigInteger)qc.getResultList().get(0));
		
		return page;
	}
	
	/**
	 * 批量状态
	 * @param idArr
	 * @param status
	 */
	@Transactional(readOnly=false)
	public void modifyAllStatus(String idArr[], String status) {
		int i = 0;
		int j = idArr.length;
		for(; i < j; i++) {
			if(idArr[i] != null && !"".equals(idArr[i])) {
				receiptDao.updateStatus(idArr[i], status);
			}
		}
	}
}
