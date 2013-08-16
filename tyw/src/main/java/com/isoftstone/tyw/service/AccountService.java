/**
 * Copyright 2008 - 2012 Simcore.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.auths.Educational;
import com.isoftstone.tyw.entity.auths.Occupational;
import com.isoftstone.tyw.entity.auths.Project;
import com.isoftstone.tyw.entity.auths.Role;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.repository.auths.EducationalDao;
import com.isoftstone.tyw.repository.auths.OccupationalDao;
import com.isoftstone.tyw.repository.auths.ProjectDao;
import com.isoftstone.tyw.repository.auths.RoleDao;
import com.isoftstone.tyw.repository.auths.UserDao;
import com.isoftstone.tyw.util.Pager;

/**
 * 账户管理类
 * 
 * @author ray
 */
@Component
@Transactional(readOnly=true)
public class AccountService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EducationalDao educationalDao;
	
	@Autowired
	private OccupationalDao occupationalDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	private static final String xmlConfig = "/META-INF/resource/user_mapping.xml" ;
	
	@Autowired
	private RoleDao roleDao;
	
	 @PersistenceContext
	 public EntityManager entityManager;
	 
	/**
	 * 保存用户
	 * 
	 * @param user
	 * @return
	 */
	 
	@Transactional(readOnly = false)
	public User saveUser(User user) {
		
		Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
		
		user.setPassword(md5.encodePassword(user.getPassword(), user.getUsername()));
		
		return userDao.save(user);
	}
	
	/**
	 * 保存用户
	 * 
	 * @param user
	 * @return
	 */
	 
	@Transactional(readOnly = false)
	public User updateUserNoPassword(User user) {
		return userDao.save(user);
	}
	
	
	/**
	 * 删除用户
	 * 
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void deleteUser(String id){
		userDao.delete(id);
	}
	
	
	/**
	 * 删除教育信息
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteEducationInfo(String id){
		educationalDao.delete(id);
	}
	
	/**
	 * 删除工作经历
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteOccupInfo(String id){
		occupationalDao.delete(id);
	}
	
	/**
	 * 删除项目经验
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteProject(String id){
		projectDao.delete(id);
	}
	
	/**
	 * 分页查询用户
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<User> listUser(Pageable pageable){
		return userDao.findAll(pageable);
	}
	
	
	public Pager listUser(String username,String aliasname,Integer userType,Boolean enable,Boolean nonLocked,Pageable pageable){
		StringBuffer sb = new StringBuffer("select * from auths_user u where 1=1 ");
		StringBuffer sbc = new StringBuffer("select count(1) from auths_user u where 1=1 ");
		if(StringUtils.isNotBlank(username)){
			sb.append(" and u.username like '%");
			sb.append(username);
			sb.append("%' ");
			
			sbc.append(" and u.username like '%");
			sbc.append(username);
			sbc.append("%' ");
		}
		if(StringUtils.isNotBlank(aliasname)){
			sb.append(" and u.aliasname like '%");
			sb.append(aliasname);
			sb.append("%' ");
			
			sbc.append(" and u.aliasname like '%");
			sbc.append(aliasname);
			sbc.append("%' ");
		}
		if(userType!=null){
			sb.append(" and u.user_type =");
			sb.append(userType);
			
			sbc.append(" and u.user_type =");
			sbc.append(userType);
		}
		if(enable!=null&&!"".equals(enable)){
			sb.append(" and u.enable =");
			if(enable){
				sb.append("1");
			}else{
				sb.append("0");
			} 
			
			sbc.append(" and u.enable =");
			if(enable){
				sbc.append("1");
			}else{
				sbc.append("0");
			} 
		}
		if(nonLocked!=null&&!"".equals(nonLocked)){
			sb.append(" and u.non_locked =");
			if(nonLocked){
				sb.append("1");
			}else{
				sb.append("0");
			} 
			
			sbc.append(" and u.non_locked =");
			if(nonLocked){
				sbc.append("1");
			}else{
				sbc.append("0");
			} 
		}
		sb.append(" limit ");
		sb.append(pageable.getPageNumber()*pageable.getPageSize());
		sb.append(",");
		sb.append(pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), User.class);
		List<User> userlist = query.getResultList();
		Query queryCount = entityManager.createNativeQuery(sbc.toString());
		BigInteger totalcount =	(BigInteger)queryCount.getResultList().get(0);
		Pager pager =  new Pager(pageable.getPageSize(),pageable.getPageNumber(),totalcount.longValue(),userlist);
		entityManager.close();
		return pager;
	}
	
	/**
	 * 通过id获取USER
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(String id){
		return userDao.findOne(id);
		
	}
	/**
	 * 通过用户名获取用户
	 * 
	 * @param username
	 * @return
	 */
	public User loadUserByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	/**
	 * 通过邮箱用户
	 * 
	 * @param mail
	 * @return
	 */
	public User loadUserByMail(String mail) {
		return userDao.findByMail(mail);
	}
	
	/**
	 * 通过电话用户
	 * 
	 * @param mail
	 * @return
	 */
	public User loadUserByPhone(String phone) {
		return userDao.findByPhone(phone);
	}
	
	
	
	@Transactional(readOnly = false)
	public boolean modifyPassword(User user){
		Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
		user.setPassword(md5.encodePassword(user.getPassword(), user.getUsername()));
		return userDao.modifyPassword(user.getPassword(), user.getId())>0;
	}
	
	/**
	 * 保存用户教育信息
	 * 
	 * @param educational
	 * @return
	 */
	 
	@Transactional(readOnly = false)
	public Educational saveUserEduInfo(Educational educational) {
		return educationalDao.save(educational);
	}
	
	/**
	 * 保存用户工作经历信息
	 * 
	 * @param occupational
	 * @return
	 */
	 
	@Transactional(readOnly = false)
	public Occupational saveUserOccupInfo(Occupational occupational) {
		return occupationalDao.save(occupational);
	}
	
	/**
	 * 保存用户项目经历信息
	 * 
	 * @param project
	 * @return
	 */
	 
	@Transactional(readOnly = false)
	public Project saveUserProjectInfo(Project project) {
		return projectDao.save(project);
	}
	
	/**
	 * 通过id获取用户教育信息
	 * 
	 * @param id
	 * @return List<Educational>
	 */
	public List<Educational> getEduByAdditionalId(String id){
		return educationalDao.findByAdditionalId(id);
	}
	
	public Educational getEduByAdditionalId1(String id){
		return educationalDao.findByAdditionalId1(id);
	}
	
	/**
	 * 通过id获取用户工作信息
	 * 
	 * @param id
	 * @return List<Occupational>
	 */
	public List<Occupational> getOccupByAdditionalId(String id){
		return occupationalDao.findByAdditionalId(id);
	}
	
	public Occupational getOccupByAdditionalId1(String id){
		return occupationalDao.findByAdditionalId1(id);
	}
	/**
	 * 更改用户状态
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean modifyStatus(String status, String uid){
		return userDao.modifyStatus(status, uid)>0;
	}
	
	/**
     * 保存用户列表
     * @param sources
     */
    @Transactional(readOnly=false)
    public void saveUser(List<User> users){
        for(User user:users){
        	Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
    		user.setPassword(md5.encodePassword(user.getPassword(), user.getUsername()));
        	userDao.save(user);
        }
    }
    
    /**
	 * 读取Excle数据
	 * @param dataXls
	 * @return List<Firm>
	 * @throws Exception
	 */
	public List<User> readXls(String dataXls) throws Exception {
		Map<String, List<User>> beans = new HashMap<String, List<User>>() ;
		InputStream inputXML = new BufferedInputStream(this.getClass().getResourceAsStream(xmlConfig)) ;
		XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML) ;
		InputStream inputXLS = new BufferedInputStream(new FileInputStream(new File(dataXls))) ;
		List<User> userList = new ArrayList<User>() ;
		beans.put("userList", userList) ;
		mainReader.read(inputXLS, beans) ;
		return userList ;
	}
	
	/**
	 * 通过id获取用户项目信息
	 * 
	 * @param id
	 * @return List<Project>
	 */
	public List<Project> getProjectByAdditionalId(String id){
		return projectDao.findByAdditionalId(id);
	}
	
	public Project getProjectByAdditionalId1(String id){
		return projectDao.findByAdditionalId1(id);
	}
	
	public Role getById(String id){
		return roleDao.findOne(id);
	}
	/*************************************非验证用户登陆************************************************************/
	public User getByUsername(String username){
		return userDao.findByUsername(username);
	}
	public User getByMail(String username){
		return userDao.findByMail(username);
	}
	public User getByPhone(String username){
		return userDao.findByPhone(username);
	}
	/**
	 * 计算当前用户资料完整度
	 * @param userId
	 * @return
	 */
	public int getProgressPercent(String userId){
		
		int progressSum1 = 0;
		int progressSum2 = 0;
		int progressSum3 = 0;
		int progressSum4 = 0;
		int progressSum5 = 0;
		int progressSum6 = 0;
		int progressSum7 = 0;
		int progressSum8 = 0;
		int progressSum9 = 0;
		int progressSum10 = 0;
		int progressSum11 = 0;
		int progressSum12 = 0;
		int progressSum13 = 0;
		int progressSum14 = 0;
		int progressSum15 = 0;
		int progressSum16 = 0;
		int sumTotal = 0;
		
		
		//获取用户基础信息
		User u = this.getUserById(userId);
		//获取教育背景信息
		Educational edu = this.getEduByAdditionalId1(userId);
		//获取工作经历信息
		Occupational occ = this.getOccupByAdditionalId1(userId);
		//获取项目经验信息
		Project pro = this.getProjectByAdditionalId1(userId);
		if(u != null){
			if(u.getUsername()!= null && !u.getUsername().equals("")){
				progressSum1 = progressSum1+3;
			}
			if(u.getMail()!= null && !u.getMail().equals("")){
				progressSum2 = progressSum2+3;
			}
			if(u.getAliasname()!= null && !u.getAliasname().equals("")){
				progressSum3 = progressSum3+4;
			}
			if(u.getAdditional().getSex()== 0 || u.getAdditional().getSex()== 1){
				progressSum4 = progressSum4+3;
			}
			if(u.getAdditional().getBirthday()!= null && !u.getAdditional().getBirthday().equals("")){
				progressSum5 = progressSum5+3;
			}
			if(u.getAdditional().getOriginalFamilyHome()!= null && !u.getAdditional().getOriginalFamilyHome().equals("")){
				progressSum6 = progressSum6+3;
			}
			if(u.getAdditional().getProfession()!= null && !u.getAdditional().getProfession().equals("")){
				progressSum7 = progressSum7+3;
			}
			if(u.getAdditional().getJobNature()!= null && !u.getAdditional().getJobNature().equals("")){
				progressSum8 = progressSum8+3;
			}
			if(u.getAdditional().getCompanyNature()!= null && !u.getAdditional().getCompanyNature().equals("")){
				progressSum9 = progressSum9+3;
			}
			if(u.getPhone()!= null && !u.getPhone().equals("")){
				progressSum10 = progressSum10+3;
			}
			if(u.getAdditional().getQq()!= null && !u.getAdditional().getQq().equals("")){
				progressSum11 = progressSum11+3;
			}
			if(u.getAdditional().getBrief()!= null && !u.getAdditional().getBrief().equals("")){
				progressSum12 = progressSum12+3;
			}
			if(u.getHeadUrl()!= null && !u.getHeadUrl().equals("")){
				progressSum13 = progressSum13+3;
			}
		}
		if(edu != null){
			if(edu.getSchoolName() != null && !edu.getSchoolName().equals("")){
				progressSum14 = progressSum14+20;
			}
		}
		if(occ != null){
			if(occ.getUnitName() != null && !occ.getUnitName().equals("")){
				progressSum15 = progressSum15+20;	
			}
		}
		if(pro != null){
			if(pro.getName() != null && !pro.getName().equals("")){
				progressSum16 = progressSum16+20;
			}
		}
		
		
		sumTotal = progressSum1+progressSum2+progressSum3+progressSum4+progressSum5+progressSum6+progressSum7
				   +progressSum8+progressSum9+progressSum10+progressSum11+progressSum12+progressSum13+progressSum14
				   +progressSum15+progressSum16;
		return sumTotal;
	}
	
	
	
	/**
	 * 更改auths_user表中grade的值
	 */
	@Transactional(readOnly = false)
	public boolean modifyGrade(int grade, String phone){
		return userDao.modifyGrade(grade, phone)>0;
	}
	
	/**
	 * 根据电话号码和用户id查询电话号是否存在
	 */
	public List<User> checkPhoneExist(String phone, String userId){
		return userDao.checkPhoneExist(phone, userId);
	}
	
	public User getUserByUsername(String username) {
		return userDao.findUserByUsername(username);
	}
	
}
