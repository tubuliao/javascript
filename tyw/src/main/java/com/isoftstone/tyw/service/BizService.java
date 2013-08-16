package com.isoftstone.tyw.service;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.biz.Card;
import com.isoftstone.tyw.entity.biz.CardBiz;
import com.isoftstone.tyw.entity.biz.CardLog;
import com.isoftstone.tyw.entity.biz.CardType;
import com.isoftstone.tyw.entity.biz.Discount;
import com.isoftstone.tyw.entity.biz.Payment;
import com.isoftstone.tyw.entity.biz.PaymentItem;
import com.isoftstone.tyw.entity.pub.Announce;
import com.isoftstone.tyw.repository.biz.CardBizDao;
import com.isoftstone.tyw.repository.biz.CardDao;
import com.isoftstone.tyw.repository.biz.CardLogDao;
import com.isoftstone.tyw.repository.biz.CardTypeDao;
import com.isoftstone.tyw.repository.biz.DiscountDao;
import com.isoftstone.tyw.repository.biz.PaymentDao;
import com.isoftstone.tyw.repository.biz.PaymentItemDao;

@Component
@Transactional(readOnly=true)
public class BizService {
	
	@Autowired
	private CardTypeDao cardTypeDao;
	
	@Autowired
	private CardDao cardDao;
	
	@Autowired
	private CardBizDao cardBizDao;
	
	@Autowired
	private CardLogDao cardLogDao;
	
	@Autowired
	private DiscountDao discountDao;
	
	@Autowired
	private PaymentDao paymentDao;
	
	@Autowired
	private PaymentItemDao paymentItemDao;

	/**
	 * 卡类型操作---列出所有卡类型
	 * @param 
	 * @return
	 */
	
	public Page<CardType> listAllCardType(String title,String createName,Pageable pageable){

		return cardTypeDao.findAll(this.getCardTypeWhereClause(title, createName),pageable);
	}
	/**
	 * 产品类型查询条件
	 * @param title
	 * @param createName
	 * @return
	 */
	
	public Specification<CardType> getCardTypeWhereClause(final String title,final String createName){
		return new Specification<CardType>(){
			@Override
			public Predicate toPredicate(Root<CardType> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(title!=null&&!"".equals(title)){
					predicate.getExpressions().add(cb.like(root.<String>get("title"), "%"+title.trim()+"%"));
				}
				if(createName!=null&&!"".equals(createName)){
					predicate.getExpressions().add(cb.like(root.<String>get("createName"), "%"+createName.trim()+"%"));
				}
				return predicate;
			}
		};
	}
	/**
	 * 卡类型操作---根据卡类型ID删除卡类型
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void deleteCardTypeById(String id){
		cardTypeDao.delete(id);
		
	}
	/**
	 * 卡类型操作---保存卡类型
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void saveCardType(CardType cardType){
		cardTypeDao.save(cardType);
		
	}
	/**
	 * 卡类型操作---根据卡类型ID查找该卡类型
	 * @param 
	 * @return
	 */
	public CardType getCardTypeById(String id){
		return cardTypeDao.findOne(id);
		
	}
	
	/**
	 * 优惠通道操作---列出所有优惠通道
	 * @param 
	 * @return
	 */
	
	public Page<Discount> listAllDiscount(String discountName,String discountCode,Pageable pageable){

		return discountDao.findAll(this.getWhereClause(discountName, discountCode),pageable);
	}
	
	/**
	 * 优惠通道操作---根据优惠通道ID删除优惠通道
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void deleteDiscountId(String id){
		discountDao.delete(id);
		
	}
	/**
	 * 优惠通道操作---保存优惠通道
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void saveDiscount(Discount discount){
		discountDao.save(discount);
		
	}
	/**
	 * 优惠通道操作---根据优惠通道ID查找该优惠通道
	 * @param 
	 * @return
	 */
	public Discount getDiscountById(String id){
		return discountDao.findOne(id);
		
	}
	
	/**
	 * 根据渠道商ID查找优惠列表
	 * @param 
	 * @return
	 */
	public List<Discount> getDiscountByAgentId(String id){
		return discountDao.findDiscountByAgentId(id);
	}
	
	/**
	 * 优惠通道查询条件
	 * @param title
	 * @param issuedBy
	 * @return
	 */
	
	public Specification<Discount> getWhereClause(final String discountName,final String discountCode){
		return new Specification<Discount>(){
			@Override
			public Predicate toPredicate(Root<Discount> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(discountName!=null&&!"".equals(discountName)){
					predicate.getExpressions().add(cb.like(root.<String>get("discountName"), "%"+discountName.trim()+"%"));
				}
				if(discountCode!=null&&!"".equals(discountCode)){
					predicate.getExpressions().add(cb.like(root.<String>get("discountCode"), "%"+discountCode.trim()+"%"));
				}
				return predicate;
			}
		};
	}
	
	
	/**
	 * 卡操作---列出所有卡
	 * @param 
	 * @return
	 */
	
	public Page<Card> listAllCard(String cardNo,String discountCode, Pageable pageable){

		return cardDao.findAll(this.getCardWhereClause(cardNo, discountCode),pageable);
	}
	/**
	 * 天佑卡查询条件
	 * @param title
	 * @param issuedBy
	 * @return
	 */
	
	public Specification<Card> getCardWhereClause(final String cardNo,final String discountCode){
		return new Specification<Card>(){
			@Override
			public Predicate toPredicate(Root<Card> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(cardNo!=null&&!"".equals(cardNo)){
					predicate.getExpressions().add(cb.like(root.<String>get("cardNo"), "%"+cardNo.trim()+"%"));
				}
				if(discountCode!=null&&!"".equals(discountCode)){
					predicate.getExpressions().add(cb.like(root.<String>get("discountCode"), "%"+discountCode.trim()+"%"));
				}
				return predicate;
			}
		};
	}
	/**
	 * 卡操作---根据卡ID删除卡类型
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void deleteCardById(String id){
		cardDao.delete(id);
		
	}
	/**
	 * 卡操作---保存卡类型
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void saveCard(Card card){
		cardDao.save(card);
		
	}
	/**
	 * 卡操作---根据卡ID查找该卡类型
	 * @param 
	 * @return
	 */
	public Card getCardById(String id){
		return cardDao.findOne(id);
		
	}
/**********************************************产品功能明细****************************************/
	/**
	 * 产品功能明细操作---列出所有产品功能明细
	 * @param 
	 * @return
	 */
	
	public Page<CardBiz> listAllCardBiz(Pageable pageable){

		return cardBizDao.findAll(pageable);
	}
	
	/**
	 * 产品功能明细操作--根据产品功能ID删除产品功能
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void deleteCardBizById(String id){
		cardBizDao.delete(id);
		
	}
	/**
	 * 产品功能操作---保存产品功能
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void saveCardBiz(CardBiz cardBiz){
		cardBizDao.save(cardBiz);
		
	}
	/**
	 * 产品功能操作---根据产品功能ID查找该产品功能
	 * @param 
	 * @return
	 */
	public CardBiz getCardBizById(String id){
		return cardBizDao.findOne(id);
		
	}
	
	/***************************************用户充值消费台账************************************/
	/**
	 * 用户充值消费台账操作---列出所有用户充值消费台账
	 * @param 
	 * @return
	 */
	
	public Page<CardLog> listAllCardLog(Pageable pageable){

		return cardLogDao.findAll(pageable);
	}
	
	/**
	 * 用户充值消费台账操作---根据用户充值消费台账ID删除用户充值消费台账
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void deleteCardLogById(String id){
		cardLogDao.delete(id);
		
	}
	/**
	 * 用户充值消费台账操作---保存用户充值消费台账
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void saveCardLog(CardLog cardLog){
		cardLogDao.save(cardLog);
		
	}
	/**
	 * 用户充值消费台账操作---根据用户充值消费台账ID查找该用户充值消费台账
	 * @param 
	 * @return
	 */
	public CardLog getCardLogById(String id){
		return cardLogDao.findOne(id);
		
	}
	/***************************************缴费记录*********************************/
	/**
	 * 缴费记录操作---列出所有缴费记录
	 * @param 
	 * @return
	 */
	
	public Page<Payment> listAllPayment(String orderCode, String discountCode,Pageable pageable){

		return paymentDao.findAll(this.getPaymentWhereClause(orderCode, discountCode),pageable);
	}
	/**
	 * 天佑卡查询条件
	 * @param title
	 * @param issuedBy
	 * @return
	 */
	
	public Specification<Payment> getPaymentWhereClause(final String orderCode,final String discountCode){
		return new Specification<Payment>(){
			@Override
			public Predicate toPredicate(Root<Payment> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(orderCode!=null&&!"".equals(orderCode)){
					predicate.getExpressions().add(cb.like(root.<String>get("orderCode"), "%"+orderCode.trim()+"%"));
				}
				if(discountCode!=null&&!"".equals(discountCode)){
					predicate.getExpressions().add(cb.like(root.<String>get("discountCode"), "%"+discountCode.trim()+"%"));
				}
				return predicate;
			}
		};
	}
	/**
	 * 缴费记录操作---根据缴费记录ID删除缴费记录
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void deletePaymentById(String id){
		paymentDao.delete(id);
		
	}
	/**
	 * 缴费记录操作---保存缴费记录
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void savePayment(Payment payment){
		paymentDao.save(payment);
		
	}
	/**
	 * 缴费记录操作---根据缴费记录ID查找该缴费记录
	 * @param 
	 * @return
	 */
	public Payment getPaymentById(String id){
		return paymentDao.findOne(id);
		
	}
	/******************************************购物车********************************************/
	/**
	 * 购物车操作---列出所有购物车内容
	 * @param 
	 * @return
	 */
	
	public Page<PaymentItem> listAllPaymentItem(Pageable pageable){

		return paymentItemDao.findAll(pageable);
	}
	
	/**
	 * 卡操作---根据卡ID删除卡类型
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void deletePaymentItemById(String id){
		paymentItemDao.delete(id);
		
	}
	/**
	 * 卡操作---保存卡类型
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void savePaymentItem(PaymentItem paymentItem){
		paymentItemDao.save(paymentItem);
		
	}
	/**
	 * 卡操作---根据卡ID查找该卡类型
	 * @param 
	 * @return
	 */
	public PaymentItem getPaymentItemById(String id){
		return paymentItemDao.findOne(id);
		
	}
	
	/*************************************前台账户信息关于天佑卡的模块****************************************************************/
	/**
	 * 卡操作---根据用户ID查找所有筑龙卡类型
	 * @param 
	 * @return
	 */
	public List<CardType> getAllCardType(){
		return cardTypeDao.findAll();
	}
	
	/**
	 * 卡操作---根据用户ID查找已购点卡
	 * @param 
	 * @return
	 */
	public List<Card> getCardByCreateId(String createId){
		return cardDao.findByCreateId(createId);
	}
	
	/**
	 * 卡操作---根据用户ID查找订单列表
	 * @param 
	 * @return
	 */
	public List<Payment> getPaymentByUserId(String id){
		return paymentDao.findPaymentByUserId(id);
	}
	
	/**
	 * 卡操作---根据用户ID和是否已经生成订单查找购物车列表
	 * @param 
	 * @return
	 */
	public List<PaymentItem> getPaymentItemByUserIdAndOrderStatus(String id,String orderStatus){
		return paymentItemDao.findPaymentItemByUserIdAndOrderStatus(id,orderStatus);
	}
	
	/**
	 * 卡操作---根据用户ID和卡类型和是否生产订单状态查找购物车列表
	 * @param 
	 * @return
	 */
	public PaymentItem getPaymentItemByUserIdAndCardTypeAndOrderStatus(String userId,CardType cardType,String orderStatus){
		return paymentItemDao.findPaymentItemByUserIdAndCardTypeAndOrderStatus(userId, cardType,orderStatus);
	}
	
	/**
	 * 缴费记录操作---保存缴费记录
	 * @param 
	 * @return	
	 */
	@Transactional(readOnly=false)
	public Payment saveWebPayment(Payment payment){
		return paymentDao.save(payment);
		
	}
	
	/**
	 * 卡操作---根据用户ID和是否已经生成订单查找购物车列表
	 * @param 
	 * @return
	 */
	public List<PaymentItem> getPaymentItemByOrderIdAndOrderStatus(String orderId,String orderStatus){
		return paymentItemDao.findPaymentItemByOrderIdAndOrderStatus(orderId, orderStatus);
	}
	
	/**
	 * web----通过卡类型ID得到对应卡类型对应的业务List
	 * @param cardTypeId
	 * @return
	 */
	public List<CardBiz> getCardBizByCardTypeId(String cardTypeId){
		return cardBizDao.findByCardTypeId(cardTypeId);
		
	}
	
	/**
	 * web---根据卡号查出卡信息
	 * @param cardNo
	 * @return
	 */
	public Card getCardByCardNo(String cardNo){
		return cardDao.findByCardNo(cardNo);
	}
	
	/**
	 * web---通过用户ID和业务类型查询出对应的台账信息
	 * @param authUserId
	 * @param bizType
	 * @return
	 */
	public List<CardLog> getCardLogByAuthsUserIdAndBizType(String authUserId,Integer bizType){
		return cardLogDao.findByAuthsUserIdAndBizType(authUserId, bizType);
	}
	
	public List<CardLog> getCardLogByAuthsUserIdAndBizTypeASC(String authUserId,Integer bizType){
		return cardLogDao.findAllByUserIdAndBizType(authUserId, bizType);
	}
	
	public String getCardTypeIdByCardId(String cardId){
		return cardBizDao.findCardTypeIdByCardId(cardId);
	}
	
	public String getCardTitleByCardTypeId(String cardTypeId){
		return cardBizDao.findCardTitleByCardTypeId(cardTypeId);
	}
	
	/**
	 * 更改biz_card表中price的值
	 */
	@Transactional(readOnly = false)
	public boolean modifyPrice(String bizCardId,double price){
		return cardDao.modifyPrice(bizCardId,price)>0;
	}
}
