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


import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.isoftstone.tyw.entity.auths.Role;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.repository.auths.RoleDao;
import com.isoftstone.tyw.repository.auths.UserDao;

/**
 * 权限管理类
 * 
 * @author ray
 */
@Component(value="authorityService")
@Transactional(readOnly=true)
public class AuthorityService implements UserDetailsService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = null;
				
		user = userDao.findByUsername(username);//用用户名登陆
		
		if (user == null) {
			user = userDao.findByMail(username);//用邮箱登陆
		}
		if(user == null){
			user = userDao.findByPhone(username);//用手机登陆
		}
		if(user == null){
			throw new UsernameNotFoundException(username);
		}
		Set<Role> roles = user.getRoles();
		if (roles == null){
			throw new ServiceException("user has no role.");
		}
		
		Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet();
		
		for(Role role : roles){
			String[] strs = StringUtils.split(role.getPermissions(), ",");
			for(String str : strs){
				grantedAuthorities.add(new SimpleGrantedAuthority(str));
			}
		}
		
		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.isEnabled(),user.isAccountNonExpired(),user.isCredentialsNonExpired(),user.isAccountNonLocked(),grantedAuthorities);
		
		return userDetails;
	}
	
}
