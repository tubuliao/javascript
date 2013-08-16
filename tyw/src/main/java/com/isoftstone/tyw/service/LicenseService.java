package com.isoftstone.tyw.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.entity.pub.License;
import com.isoftstone.tyw.entity.pub.LicenseBatch;
import com.isoftstone.tyw.entity.pub.LicensePayment;
import com.isoftstone.tyw.entity.pub.LicenseProject;
import com.isoftstone.tyw.entity.pub.LicenseUser;
import com.isoftstone.tyw.entity.pub.Receipt;
import com.isoftstone.tyw.repository.pub.LicenseBatchDao;
import com.isoftstone.tyw.repository.pub.LicenseDao;
import com.isoftstone.tyw.repository.pub.LicensePaymentDao;
import com.isoftstone.tyw.repository.pub.LicenseProjectDao;
import com.isoftstone.tyw.repository.pub.LicenseUserDao;
import com.isoftstone.tyw.repository.pub.ReceiptDao;


/**
 * 序列号管理类
 * 
 * @author wangrui
 */
@Component
@Transactional(readOnly=true)
public class LicenseService {
	@Autowired
	LicenseDao licenseDao;
	@Autowired
	LicenseProjectDao licenseProjectDao;
	@Autowired
	LicenseUserDao licenseUserDao;
	@Autowired
	LicensePaymentDao licensePaymentDao;
	@Autowired
	LicenseBatchDao licenseBatchDao;
	@Autowired
	ReceiptDao receiptDao;
	@PersistenceContext
	public EntityManager entityManager;
	
	@Transactional(readOnly = false)
	public Receipt saveReceipt(Receipt receipt){
		return receiptDao.save(receipt);
	}
	
	@Transactional(readOnly = false)
	public Receipt getReceipt(String licenseNumber ){
		return receiptDao.findBylicenseNumber(licenseNumber);
	}
	
	@Transactional(readOnly = false)
	public List<License> findLicense(String licenseNum){
		List<License> list = new ArrayList<License>();
		if(StringUtils.isNotBlank(licenseNum)){
			list = licenseDao.findByLicenseNum(licenseNum);
		}
		return list;
	}
	
	@Transactional(readOnly = false)
	public LicenseProject save(LicenseProject licenseProject){
		return licenseProjectDao.save(licenseProject);
	}
	
	@Transactional(readOnly = false)
	public LicenseBatch save(LicenseBatch batch){
		return licenseBatchDao.save(batch);
	}
	
	@Transactional(readOnly = false)
	public LicensePayment save(LicensePayment pay){
		return licensePaymentDao.save(pay);
	}
	
	@Transactional(readOnly = false)
	public LicensePayment findLicensePaymentByvOid(String vOid){
		return licensePaymentDao.findLicensePaymentByvOid(vOid);
	}
	
	@Transactional(readOnly = false)
	public List<LicensePayment> listLicensePayment(String licenseId){
		return licensePaymentDao.findAll(getWhereClausePayment(licenseId));
	}
	
	@Transactional(readOnly = false)
	public List<LicenseBatch> listLicenseBatch(){
		return licenseBatchDao.findAll(getWhereClauseBatch());
	}
	
	@Transactional(readOnly = false)
	public Page<License> listLicense(String batchCode,String licenseNum,String status,Pageable pageable){
		return licenseDao.findAll(this.getWhereClause(batchCode,licenseNum,status),pageable);
	}
	
	@Transactional(readOnly = false)
	public Page<LicenseBatch> listLicenseBatch(String agentId,String batchCode,Pageable pageable){
		return licenseBatchDao.findAll(this.getWhereClauseBatch(agentId,batchCode),pageable);
	}

	@Transactional(readOnly = false)
	public void saveOne(License b) {
		licenseDao.save(b) ;
	}
	
	@Transactional(readOnly = false)
	public void saveAll(List<License> list) {
	    licenseDao.save(list);
	}
	
	@Transactional(readOnly = false)
	public List<LicenseUser> findLicenseUserList(String licenseNumber) {
		List<LicenseUser> list = new ArrayList<LicenseUser>();
		if(StringUtils.isNotBlank(licenseNumber)){
			List<License> licenselist = licenseDao.findByLicenseNum(licenseNumber);
			list = licenseUserDao.findAll(this.getWhereClauseLicenseUser(licenselist.get(0).getId()));
		}
		return list;
	}
	
	
 
	
	public Specification<LicenseUser> getWhereClauseLicenseUser
	(final String licenseId){
		return new Specification<LicenseUser>(){
			@Override
			public Predicate toPredicate(Root<LicenseUser> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(StringUtils.isNotBlank(licenseId)){
					predicate.getExpressions().add(cb.equal(root.<String>get("licenseId"), licenseId));
				}
				return predicate;
			}
		};
	}
	
	
	
	public Specification<LicensePayment> getWhereClausePayment(final String licenseId){
		return new Specification<LicensePayment>(){
			@Override
			public Predicate toPredicate(Root<LicensePayment> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				query.orderBy(cb.desc(root.get("payDate")));	
				
				if(StringUtils.isNotBlank(licenseId)){
					predicate.getExpressions().add(cb.equal(root.<String>get("licenseId"), licenseId));
				}
				
				return predicate;
			}
		};
	}
	public Specification<LicenseBatch> getWhereClauseBatch(){
		return new Specification<LicenseBatch>(){
			@Override
			public Predicate toPredicate(Root<LicenseBatch> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				query.orderBy(cb.desc(root.get("createDate")));	
				return predicate;
			}
		};
	}
	
	public Specification<License> getWhereClause
	(final String batchCode,final String licenseNum,final String status){
		return new Specification<License>(){
			@Override
			public Predicate toPredicate(Root<License> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				 
				List<Order> ol = new ArrayList<Order>();
				ol.add(cb.desc(root.get("createDate")));
				ol.add(cb.desc(root.get("licenseType")));
				query.orderBy(ol);	
				if(licenseNum!=null&&!"".equals(licenseNum)){
					predicate.getExpressions().add(cb.equal(root.<String>get("licenseNum"), licenseNum));
				}
				if(batchCode!=null&&!"".equals(batchCode)){
					predicate.getExpressions().add(cb.equal(root.<String>get("batchCode"), batchCode));
				}
				if(status!=null&&!"".equals(status)){
					predicate.getExpressions().add(cb.equal(root.<String>get("licenseStatus"), status));
				}
				return predicate;
			}
		};
	}
	
	
	public Specification<LicenseBatch> getWhereClauseBatch
	(final String agentId,final String batchCode){
		return new Specification<LicenseBatch>(){
			@Override
			public Predicate toPredicate(Root<LicenseBatch> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				query.orderBy(cb.desc(root.get("createDate")));	
				if(StringUtils.isNotBlank(batchCode)){
					predicate.getExpressions().add(cb.equal(root.<String>get("batchCode"), batchCode));
				}
				if(agentId!=null&&!"".equals(agentId)){
					//使用子查询查询获得批次
					Subquery<User> sq = query.subquery(User.class);
					Root<User> ruser = sq.from(User.class);
					sq.where(cb.equal(ruser.get("id"), agentId)).select(ruser);
					predicate.getExpressions().add(cb.equal(root.<User>get("agent"), sq));
				}
				return predicate;
			}
		};
	}
	
	/**
	 * 保存分配用户
	 * @param licenseUser
	 * @return
	 */
	 
	@Transactional(readOnly = false)
	public LicenseUser saveLicenseUser(LicenseUser licenseUser) {
		
		return licenseUserDao.save(licenseUser);
	}
	
	/**
	 * 通过licenseId获取项目组信息
	 * @param licenseId
	 * @return
	 */
	public LicenseProject getProInfoByLicenseId(String licenseId) {
		return licenseProjectDao.findByLicenseId(licenseId);
	}
	/**
	 * 更改pub_license表中use_id的值
	 */
	@Transactional(readOnly = false)
	public boolean modifyUserId(String userId, String licenseId){
		return licenseDao.modifyUserId(userId, licenseId)>0;
	}
	
	/**
	 * 更改pub_license表中license_status的值
	 */
	@Transactional(readOnly = false)
	public boolean modifyLicenseStatus(String licenseStatus, String licenseId){
		return licenseDao.modifyLicenseStatus(licenseStatus, licenseId)>0;
	}
	
	/**
	 * 查询当前许可号白金用户的信息
	 */
	public LicenseUser getUserByLicenseId(String licenseId){
		return licenseUserDao.findUserByLicenseId(licenseId);
	}
	
	/**
	 * 根据许可号id查询对应该许可号下所有创建的用户
	 */
	@Transactional(readOnly = false)
	public List<LicenseUser> findUser(String licenseId){
		return licenseUserDao.findByLicenseId(licenseId);
	}
	
	/**
	 * 查询当前许可号除白金用户以外的其他黄金用户的信息
	 */
	@Transactional(readOnly = false)
	public List<LicenseUser> getUserByLicenseId1(String licenseId){
		return licenseUserDao.findUserByLicenseId1(licenseId);
	}
	
	/**
	 * 根据创建用户id查询用户信息
	 */
	@Transactional(readOnly = false)
	public List<LicenseUser> getUserByLicenseUserId(String licenseUserId){
		return licenseUserDao.findUserByLicenseUserId(licenseUserId);
	}
	
	
	/**
	 * 根据手机号到许可号用户表里查询用户信息
	 */
	@Transactional(readOnly = false)
	public List<LicenseUser> getUserByPhone(String licenseId, String phone){
		return licenseUserDao.findUserByPhone(licenseId,phone);
	}
	
	/**
	 * 根据手机号和用户姓名到许可号用户表里查询用户信息
	 */
	@Transactional(readOnly = false)
	public List<LicenseUser> getUserByPhoneAndName(String licenseId, String name, String phone){
		return licenseUserDao.findUserByPhoneAndName(licenseId,name,phone);
	}
	
	/**
	 * 更改pub_license_user表中phone的值
	 */
	@Transactional(readOnly = false)
	public boolean modifyLicenseUserName(String name, String phone, String userId){
		return licenseUserDao.modifyLicenseUserName(name, phone, userId)>0;
	}
	
	/**
	 * 根据id删除pub_license_user表中的信息
	 */
	@Transactional(readOnly=false)
	public void deleteLicenseUserId(String id){
		licenseUserDao.delete(id);
		
	}
	
	/**
	 * 通过licenseId获取序列号信息
	 * @param licenseId
	 * @return
	 */
	public License getLicenseInfoByLicenseId(String licenseId) {
		return licenseDao.findById(licenseId);
	}
	
	/**
	 * 更改auths_user表中grade的值
	 */
	@Transactional(readOnly = false)
	public boolean modifyUserGrade(int grade, String userId){
		return licenseDao.modifyUserGrade(grade, userId)>0;
	}
	
	
	
	
	/**
	 * 当给序列号创建用户时，查询当前被分配人的手机号是否存在
	 */
	@Transactional(readOnly = false)
	public List<User> getUserExistByPhone(String phone){
		return licenseUserDao.findUserExistByPhone(phone);
	}

	/**
	 * 根据许可号用户表的id查询数据
	 */
	@Transactional(readOnly = false)
	public LicenseUser getById(String licenseUserId){
		return licenseUserDao.findById(licenseUserId);
	}
	
	
	/**
	 * 更改pub_license表中overplus_count的值
	 */
	@Transactional(readOnly = false)
	public boolean modifyOverPlusCount(int overPlusCount, String licenseId){
		return licenseDao.modifyOverPlusCount(overPlusCount, licenseId)>0;
	}
	
	/**
	 * 序列号管理列表查询
	 * @param batchCode
	 * @param agentId
	 * @param pageable
	 * @return
	 */
	public List<LicenseBatch> listLicenseBatchBaseRows(String batchCode, String agentId, Pageable pageable) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT * FROM pub_license_batch t WHERE 1 = 1 ");
		// 批次号
		if(batchCode != null && !"".equals(batchCode)) {
			sb.append(" AND t.batch_code = " + batchCode);
		}
		// 渠道商
		if(agentId != null && !"".equals(agentId)) {
			sb.append(" AND t.agent_id = (select id from auths_user where id = '" + agentId + "')");
		}
		// 分页
		sb.append(" limit " + pageable.getPageNumber()*pageable.getPageSize() + "," + pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), LicenseBatch.class);
		
		List<LicenseBatch> result = query.getResultList(); 
		
		entityManager.close();
        return result;
	}
	
	/**
	 * 序列号管理列表查询(count)
	 * @param batchCode
	 * @param agentId
	 * @return
	 */
	public BigInteger listLicenseBatchBaseTotal(String batchCode, String agentId) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT count(*) FROM pub_license_batch t WHERE 1 = 1 ");
		// 批次号
		if(batchCode != null && !"".equals(batchCode)) {
			sb.append(" AND t.batch_code = " + batchCode);
		}
		// 渠道商
		if(agentId != null && !"".equals(agentId)) {
			sb.append(" AND t.agent_id = (select id from auths_user where id = '" + agentId + "')");
		}
		Query query = entityManager.createNativeQuery(sb.toString());
		
		BigInteger total = (BigInteger)query.getResultList().get(0);   
		
		entityManager.close();
        return total;
	}
}
