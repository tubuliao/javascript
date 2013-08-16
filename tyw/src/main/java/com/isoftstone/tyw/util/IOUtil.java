package com.isoftstone.tyw.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class IOUtil {
	
	private TreeSet<File> allFiles = new TreeSet<File>();
	
	/**
	 * HTTP下载
	 * @param request
	 * @param response
	 * @param fileName
	 * @param downLoadPath
	 * @throws Exception
	 */
	public void httpDownload(HttpServletRequest request,HttpServletResponse response,String fileName,String downLoadPath)throws Exception{
		response.setContentType("text/html;charset=utf-8");   
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bufferInputStream = null;   
		BufferedOutputStream bufferOutPutStream = null;    
		try {    
			long fileLength = new File(downLoadPath).length();    
			response.setContentType("application/x-msdownload;");    
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));    
			response.setHeader("Content-Length", String.valueOf(fileLength));    
			bufferInputStream = new BufferedInputStream(new FileInputStream(downLoadPath));    
			bufferOutPutStream = new BufferedOutputStream(response.getOutputStream());    
			byte[] buff = new byte[2048];    
			int bytesRead;    
			while (-1 != (bytesRead = bufferInputStream.read(buff, 0, buff.length))) {     
				bufferOutPutStream.write(buff, 0, bytesRead);    
			}   
		} catch (Exception e) {    
			e.printStackTrace();   
		} finally {    
			if (bufferInputStream != null)     
				bufferInputStream.close();    
			if (bufferOutPutStream != null)     
				bufferOutPutStream.close();   
		}   
	}
	
	
	   public static void httpDownloadURL(HttpServletRequest request,HttpServletResponse response,String fileName,String downLoadPath)throws Exception{
	        response.setContentType("text/html;charset=utf-8");   
	        request.setCharacterEncoding("UTF-8");
	        BufferedInputStream bufferInputStream = null;   
	        BufferedOutputStream bufferOutPutStream = null;    
	        try {    
	            URL url = new URL(downLoadPath);
	   	        response.setContentType("application/x-msdownload;");    
	            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));    
	            bufferInputStream = new BufferedInputStream(url.openStream());    
	            bufferOutPutStream = new BufferedOutputStream(response.getOutputStream());    
	            byte[] buff = new byte[2048];    
	            int bytesRead;    
	            while (-1 != (bytesRead = bufferInputStream.read(buff, 0, buff.length))) {     
	                bufferOutPutStream.write(buff, 0, bytesRead);    
	            }   
	        } catch (Exception e) {    
	            e.printStackTrace();   
	        } finally {    
	            if (bufferInputStream != null)     
	                bufferInputStream.close();    
	            if (bufferOutPutStream != null)     
	                bufferOutPutStream.close();   
	        }   
	    }
	
	   public static void httpDownloadfile(HttpServletRequest request,HttpServletResponse response,String downLoadPath)throws Exception{
         //  response.setContentType("text/html;charset=utf-8");   
         //  request.setCharacterEncoding("UTF-8");
           BufferedInputStream bufferInputStream = null;   
           BufferedOutputStream bufferOutPutStream = null;    
           try {    
               URL url = new URL(downLoadPath);
              // response.setContentType("application/x-msdownload;");    
              // response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));    
               bufferInputStream = new BufferedInputStream(url.openStream());    
               bufferOutPutStream = new BufferedOutputStream(response.getOutputStream());    
               byte[] buff = new byte[2048];    
               int bytesRead;    
               while (-1 != (bytesRead = bufferInputStream.read(buff, 0, buff.length))) {     
                   bufferOutPutStream.write(buff, 0, bytesRead);    
               }   
           } catch (Exception e) {    
               e.printStackTrace();   
           } finally {    
               if (bufferInputStream != null)     
                   bufferInputStream.close();    
               if (bufferOutPutStream != null)     
                   bufferOutPutStream.close();   
           }   
       }
	/**
	 * 将输入流写入文件
	 * @param inputStream
	 * @param file
	 * @param isCreate 是否
	 * @throws Exception
	 */
	public void streamToFile(InputStream inputStream,File file,boolean isCreate) throws Exception{
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs() ;   // 创建文件的目录
		}
		if(isCreate&&!file.exists()){
			file.createNewFile();
		}
		BufferedInputStream bufferInputStream=new BufferedInputStream(inputStream);
		BufferedOutputStream bufferOutPutStream = new BufferedOutputStream(new FileOutputStream(file));
		byte[] buff = new byte[2048];   
		int bytesRead;    
		while (-1 != (bytesRead = bufferInputStream.read(buff, 0, buff.length))) {     
			bufferOutPutStream.write(buff, 0, bytesRead);    
		}   
		if (bufferInputStream != null)     
			bufferInputStream.close();    
			inputStream.close();
		if (bufferOutPutStream != null)     
			bufferOutPutStream.close();   

	}
	
	/**
	 * 遍历文件目录
	 * @param path
	 */
	public void traversalFile(File path) throws Exception {
    	if(path != null && path.isDirectory()) {
    		File f[] = path.listFiles();
    		if(f != null) {
    			for(int i = 0 ; i < f.length ; i++) {
    				traversalFile(f[i]) ;
    			}
    		}
    	} else if(path.isFile()) {
    		allFiles.add(path) ;
    		//System.out.println("5555 traversalFile path : " + path) ;
    	}
    }

	public TreeSet<File> getAllFiles() {
		return allFiles;
	}

	public void setAllFiles(TreeSet<File> allFiles) {
		this.allFiles = allFiles;
	}

}
