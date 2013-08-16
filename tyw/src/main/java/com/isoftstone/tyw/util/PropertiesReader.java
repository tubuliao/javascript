package com.isoftstone.tyw.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	
	private static PropertiesReader sgProperties=null;
	private static String proUrl="/META-INF/resource/config.properties";
	
	public static PropertiesReader getInstance(){
		if(sgProperties==null){
			try {
				sgProperties = new PropertiesReader();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sgProperties;
	}
	
	public Properties pro = new Properties();
	
	
	
	private PropertiesReader() throws IOException{
		InputStream inputStream = getClass().getResourceAsStream(proUrl);
		pro.load(inputStream);
	}
	
	public String getProperties(String key){
		return pro.getProperty(key);
	}
	
}