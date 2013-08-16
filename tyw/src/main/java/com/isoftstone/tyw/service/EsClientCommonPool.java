package com.isoftstone.tyw.service;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.elasticsearch.client.Client;

/**
 * 
 *@author JQZ
 *@version 1.0
 *ES客户端公共对象池  
 */
public class EsClientCommonPool {
	private ObjectPool pool = null;
	private static EsClientCommonPool escp=null;
	
	//创建构造器
	private EsClientCommonPool(){
		pool = new GenericObjectPool(new EsClientPoolFactory());
	}
	
	
	public static EsClientCommonPool getPoolInstance(){
		if(escp==null){
			escp = new EsClientCommonPool();
		}
		return escp;
	}
	
	/**
	 * 这里我们要借个对象用一下。 
	 * @return ES连接客户端
	 */
	public Client borrowClient(){
		try {
			return (Client) pool.borrowObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 归还ES连接客户端
	 * @param client ES连接客户端
	 */
	public void returnClient(Client client){
		try {
			pool.returnObject(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取活跃的对象数
	 * @return
	 */
	public int getNumberActive(){
		return pool.getNumActive();
	}
	
	/**
	 * 获取空闲的对象
	 * @return
	 */
	public int getNumberIdle(){
		return pool.getNumIdle();
	}
	
	
}
