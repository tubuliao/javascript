package com.isoftstone.tyw.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.biz.CardLog;
import com.isoftstone.tyw.entity.biz.CardType;
import com.isoftstone.tyw.repository.biz.CardLogDao;
import com.isoftstone.tyw.repository.biz.CardTypeDao;

@Component
@Transactional(readOnly = true)
public class CardLogService {

	@Autowired
	private CardLogDao cardLogDao ;
	@Autowired
	private CardTypeDao cardTypeDao;
	
	
	public CardLog getCardLogByUserId(String userId) {
		if(cardLogDao.findAllByUserId(userId) != null && !cardLogDao.findAllByUserId(userId).isEmpty() ) {
			return cardLogDao.findAllByUserId(userId).get(0) ;
		} else {
			return null ;
		}
	}

	/**
	 * 根据业务类型和用户编码来查询最新的一条消费记录 
	 * @param userId
	 * @param type
	 * @return
	 */
	public CardLog getCardLogByUserIdAndType(String userId,Integer type) {
		if(cardLogDao.findAllByUserId(userId) != null && !cardLogDao.findAllByUserId(userId).isEmpty() ) {
			//System.out.println(" user id is "+userId);
			List<CardLog> list =  cardLogDao.findAllByUserId(userId);
			CardLog result = null;
			for(int i = 0; i<list.size();i++){
				CardLog log = list.get(i);
				if(log.getBizType()!=type){
					list.remove(i);
					i=i-1;
				}else{
					result = list.get(i);
					break;
				}
			}
			return result ;
		} else {
			//System.out.println(" user log is null");
			return null ;
		}
	}
	
	public String getCardIdByUserId(String userId){
		return cardLogDao.findCardIdByUserId(userId);
	}
	
	public CardType getCardType(int months){
		return cardTypeDao.findOne(getWhereClause(months));
	}
	
	public Specification<CardType> getWhereClause
	(final int months){
		return new Specification<CardType>(){
			@Override
			public Predicate toPredicate(Root<CardType> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				predicate.getExpressions().add(cb.equal(root.<Integer>get("months"), months));
				return predicate;
			}
		};
	}
	
	/**
	 * 根据用户id去台帐表中查询当前用户最新的一条台帐信息
	 */
	public CardLog getCardInfoByUserId(String userId){
		return cardLogDao.findCardInfoByUserId(userId);
	}
	
	/**
	 * 保存台帐信息
	 * @param cardLog
	 */
	 
	@Transactional(readOnly = false)
	public CardLog saveCardLog(CardLog cardLog) {
		return cardLogDao.save(cardLog);
	}
	
	/**
	 * 更改biz_card_log表中lastTotal和overTotal的值
	 */
	@Transactional(readOnly = false)
	public boolean modifyLastTotalAndOverTotal(int lastTotal, int overTotal, String userId){
		return cardLogDao.modifyLastTotalAndOverTotal(lastTotal, overTotal, userId)>0;
	}
}
