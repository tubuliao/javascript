package com.isoftstone.tyw.service;

import org.apache.commons.pool.PoolableObjectFactory;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.isoftstone.tyw.util.PropertiesReader;


/**
 * @author JQZ
 *
 */
public class EsClientPoolFactory implements PoolableObjectFactory{
	
	/** 
	 *这里，在业务中第一次借对象的时候，这里要初始化一个，如果初始的都被借了，那就要继续初始 
	 *这里创建的对象将被存到pool里面 
	 */  
	@Override
	public Object makeObject() throws Exception {
		PropertiesReader preader = PropertiesReader.getInstance();
		String addr=preader.getProperties("elasticsearch.InetSocketTransportAddress");
		Integer port=Integer.parseInt(preader.getProperties("elasticsearch.InetSocketPort"));
		Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("192.168.0.150",9300));
		return client;
	}
	
	/** 
	 *这里我们销毁这个对象。不用这个对象了，这个方法得在业务使用过程中主动调用。 
	 *在调用完这个方法过后，对象已经被销毁，但是在Objectpool里面还是存在这个对象的 
	 *只是这个对象是null而已。所以在调用完destroyObject过后，要记得把pool清空一下。 
	 */  
	@Override
	public void destroyObject(Object obj) throws Exception {
		Client client = (Client) obj;
		client.close();
	}
	
	/** 
	*这个方法是验证对象，这里我们不做处理，这里在借对象和还对象的时候都要验证下的。 
	*/ 
	@Override
	public boolean validateObject(Object obj) {
		
		return true;
	}
	
	/** 
	 * 这里，我们在使用对象的时候，需要首先激活这个对象，但是在多线程情况下， 
	 * 这个对象已经被别的线程借去用了，那我们就要再建立一个对象。 
	 */
	@Override
	public void activateObject(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/** 
	*这里，在我们使用完每个对象过后，要做的就是归还，在归还后就要调用这个方法。 
	*简单的说，在还款凭证上面签个大名 
	*/ 
	@Override
	public void passivateObject(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
