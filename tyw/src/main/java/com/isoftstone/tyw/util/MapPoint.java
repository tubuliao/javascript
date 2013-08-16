package com.isoftstone.tyw.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

/**
 * @author liuyulong
 */
public class MapPoint {

public static String getLocation(String x,String y){
    String str = getRequestByUrl("http://ditu.google.com/maps/api/geocode/json?latlng="+x+","+y+"&sensor=false&&language=zh-CN");  
     JSONObject jb=new JSONObject();
    JSONArray array=(JSONArray)jb.fromObject(str).get("results");
    JSONObject o = (JSONObject) array.get(0);
    return  null==o.get("formatted_address")?"":o.get("formatted_address").toString();
}
    
    
    public static String getRequestByUrl(String strurl){  
               String strjson = "";  
                try {  
                    URL url = new URL(strurl);  
                   URLConnection conn = url.openConnection();  
                  HttpURLConnection http = (HttpURLConnection)conn;  
                  http.setRequestMethod("GET");  
               http.setDoInput(true);  
                  http.setDoOutput(true);  
                   http.connect();  
                 InputStream in = http.getInputStream();  
                   BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));  
                   String s = null;  
                 while((s = br.readLine()) != null) {  
                       strjson+=s;  
                   }  
                 br.close();  
              } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                   e.printStackTrace();  
             }   
              return strjson;  
           }  

//    public static  String getGoogleAddressBylatlng(String latlng){  
//               String strAddress = "";  
//           HttpClient client = new  HttpClient();  
//           client.getHostConfiguration().setHost("ditu.google.com", 80, "http");  
//                
//            HttpMethod method = null;  
//            try {  
//                 method = getGetMethod(latlng);  
//            } catch (IOException e1) {  
//               // TODO Auto-generated catch block  
//                  e1.printStackTrace();  
//            }// 使用GET方式提交数据  
//             try {  
//              client.executeMethod(method);  
//         } catch (HttpException e1) {  
//                 // TODO Auto-generated catch block  
//                e1.printStackTrace();  
//                 } catch (IOException e1) {  
//                  // TODO Auto-generated catch block  
//                 e1.printStackTrace();  
//                return "获取经纬度地址异常";  
//           }  
//            // 打印服务器返回的状态  
//              int methodstatus = method.getStatusCode();  
//             StringBuffer sb = new StringBuffer();  
//              if(methodstatus == 200){  
//                   try {  
//                   BufferedReader rd = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),"UTF-8"));  
//                      String line;  
//                      while ((line = rd.readLine()) != null) {  
//                         sb.append(line);  
//       
//                       }  
//                       org.json.JSONObject jo;  
//                     try {  
//                         jo = new org.json.JSONObject(sb.toString());  
//                           org.json.JSONArray ja = jo.getJSONArray("results");  
//                        org.json.JSONObject jo1 = ja.getJSONObject(0);  
//                         //System.out.println(jo1.getString("formatted_address"));  
//                          strAddress = jo1.getString("formatted_address");  
//                     } catch (JSONException e) {  
//                          // TODO Auto-generated catch block  
//                            e.printStackTrace();  
//                      }  
//                     rd.close();  
//                }catch (IOException e) {  
//                     throw new RuntimeException("error", e);  
//                   }  
//              }  
//              method.releaseConnection();  
//             return strAddress;  
//          }  

    

}
