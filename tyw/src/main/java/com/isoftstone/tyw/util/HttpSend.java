package com.isoftstone.tyw.util;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


/**
 * 短信接口公用类
 * @author zhaowenli
 *
 */
public class HttpSend {
	
	public static String strReg = "101100-WEB-HUAX-176006";
	public static String strPwd = "ZKMRMUUM";
	public static String strSourceAdd = "";
	
	public static String postSend(String strUrl,String param){
		
		URL url = null;
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();

			//POST方法时使用
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(param);
			out.flush();
			out.close();
			
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		
		
	}
	/**
	 * 转为16进制方法
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String paraTo16(String str) throws UnsupportedEncodingException {
			String hs = "";
			
			byte[] byStr = str.getBytes("UTF-8");
			for(int i=0;i<byStr.length;i++)
			{
				String temp = "";
				temp = (Integer.toHexString(byStr[i]&0xFF));
				if(temp.length()==1) temp = "%0"+temp;
				else temp = "%"+temp;
				hs = hs+temp;
			}
			return hs.toUpperCase();
	
	}
	
	/**
	 * @param phone 发送短信的手机号
	 * @param content 发送的内容
	 * @return result=0&message=成功发送 只有等于0的时候才是成功发送
	 */
	public static String sendMessage(String phone,String content){
		 String strRes  = new String();
		try {
			String strContent = paraTo16(content);
			String strMobile = "13391750223";            //手机号，不可为空
			String strUname = paraTo16("软通动力信息技术有限公司"); //用户名，不可为空
	        String strFax = "+861058749001";             //座机，不可为空
	        String strRegPhone = "+861058749000";               //传真，不可为空
	        String strEmail = "contact@isoftstone.com";       //电子邮件，不可为空
	        String strPostcode = "100193";               //邮编，不可为空
	        String strCompany = paraTo16("软通动力信息技术有限公司");    //公司名称，不可为空
	        String strAddress = paraTo16("北京市海淀区东北旺西路8号 中关村软件园9号楼");//公司地址，不可为空
	       //以下参数为服务器URL,以及发到服务器的参数，不用修改
	        String strSmsUrl = "http://www.stongnet.com/sdkhttp/sendsms.aspx";
	        String strSmsParam = "reg=" + strReg + "&pwd=" + strPwd + "&sourceadd=" + strSourceAdd + "&phone=" + phone + "&content=" + strContent;;
	        //发送短信
	        strRes = postSend(strSmsUrl, strSmsParam);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return strRes;
	}
	
	
	
	
	public static void main(String[] args){
		String strRes = sendMessage("18618185023","nimei");
        //System.out.println(strRes);
	}

}

