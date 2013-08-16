package com.isoftstone.tyw.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.csource.fastdfs.UploadCallback;

import com.isoftstone.tyw.util.PropertiesReader;

public class FdfsService {

	private List<StorageClient1> connList = Collections
			.synchronizedList(new ArrayList<StorageClient1>(1));

	// 连接超时的时限，单位为毫秒
	private int connectTimeout = 2000;

	// 网络超时的时限，单位为毫秒
	private int networkTimeout = 30000;

	// 防盗链Token
	private boolean antiStealToken = false;

	// 字符集
	private String charset = "UTF-8";

	private String secretKey = "";

	// HTTP访问服务的端口号
	private int trackerHttpPort = 8080;
	private TrackerServer trackerServer = null;
	private StorageServer storageServer = null;

	private void createDFSConn() {
		ClientGlobal.setG_connect_timeout(connectTimeout);
		ClientGlobal.setG_network_timeout(networkTimeout);
		ClientGlobal.setG_anti_steal_token(antiStealToken);
		ClientGlobal.setG_charset(charset);
		ClientGlobal.setG_secret_key(secretKey);
		ClientGlobal.setG_tracker_http_port(trackerHttpPort);
		InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
		PropertiesReader pu = PropertiesReader.getInstance();
		tracker_servers[0] = new InetSocketAddress(
				pu.getProperties("fdfs.InetSocketAddress"), 22122);
		ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));
		try {
			TrackerClient tracker = new TrackerClient();
			trackerServer = tracker.getConnection();
			storageServer = tracker.getStoreStorage(trackerServer);
			StorageClient1 client = new StorageClient1(trackerServer,
					storageServer);
			if (client != null) {
				connList.add(client);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 确保获取有效通信
	 * 
	 * @return
	 */
	public StorageClient1 getDfsClient() {
		StorageClient1 client = null;
		if (connList.size() == 0)
			createDFSConn();
		while (client == null) {
			synchronized (connList) {
				client = connList.get(0);
				if (client == null)
					createDFSConn();
			}
		}
		return client;
	}

	/**
	 * 回收通信
	 * 
	 * @throws Exception
	 */
	public void destroy() {
		try {
			ProtoCommon.activeTest(storageServer.getSocket());
			storageServer.close();
			ProtoCommon.activeTest(trackerServer.getSocket());
			trackerServer.close();
		} catch (Exception e) {

		}

	}

	/**
	 * 写文件到DFS
	 * 
	 * @param fileName
	 * @param fileExtName
	 * @param inputStream
	 * @param fileLength
	 * @return
	 */
	public String upload(String fileName, String fileExtName,
			InputStream inputStream, long fileLength) {
		String result = "";
		StorageClient1 client = null;
		// 设置元信息
		NameValuePair[] metaList = new NameValuePair[3];
		metaList[0] = new NameValuePair("fileName", fileName);
		metaList[1] = new NameValuePair("fileExtName", fileExtName);
		metaList[2] = new NameValuePair("fileLength",
				String.valueOf(fileLength));
		try {
			byte[] fileBuff = getFileBuffer(inputStream, fileLength);
			client = getDfsClient();
			result = client.upload_file1(fileBuff, fileExtName, metaList);
			System.out.println(result);
		} catch (Exception e) {
			connList.remove(client);
			destroy();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 从DFS删除指定文件
	 * 
	 * @param fileName
	 */
	public void delete(String fileName) {
		StorageClient1 client = null;
		try {
			client = getDfsClient();
			client.delete_file1(fileName);
		} catch (Exception e) {
			connList.remove(client);
			destroy();
		}
	}

	/**
	 * 获取文件信息
	 * 
	 * @param groupName
	 * @param remoteFilename
	 * @return
	 */
	public String getFileInfo(String groupName, String remoteFilename) {
		String result = "";
		StorageClient1 client = null;
		try {
			client = getDfsClient();
			// 根据文件的路径和名称获取信息
			FileInfo fi = client.get_file_info(groupName, remoteFilename);
			// ip地址
			String ip = fi.getSourceIpAddr();
			// 文件大小
			long size = fi.getFileSize();
			// 实例化一个StringBuffer
			StringBuffer sb = new StringBuffer();
			sb = sb.append("ip:" + ip).append(",").append("filesize:" + size);
			result = sb.toString();
		} catch (Exception e) {
			connList.remove(client);
			destroy();
		}
		return result;
	}

	/**
	 * 下载文件
	 * 
	 * @param fileId
	 * @return
	 */
	public String downloadFile(String fileId) {
		String downFile = "";
		StorageClient1 client = null;
		try {
			client = getDfsClient();
			// 从服务器端下载文件
			byte[] bFlie = client.download_file1(fileId);

			downFile = new String(bFlie, "UTF-8");
		} catch (Exception e) {
			connList.remove(client);
			destroy();
		}
		return downFile;

	}

	/**
	 * 根据byte数组，生成文件
	 * 
	 * @param bfile
	 * @param filePath
	 * @param fileName
	 */
	public static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "\\" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private byte[] getFileBuffer(InputStream inStream, long fileLength)
			throws IOException {

		byte[] buffer = new byte[10 * 1024];
		byte[] fileBuffer = new byte[(int) fileLength];

		int count = 0;
		int length = 0;

		while ((length = inStream.read(buffer)) != -1) {
			for (int i = 0; i < length; ++i) {
				fileBuffer[count + i] = buffer[i];
			}
			count += length;
		}
		return fileBuffer;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getNetworkTimeout() {
		return networkTimeout;
	}

	public void setNetworkTimeout(int networkTimeout) {
		this.networkTimeout = networkTimeout;
	}

	public boolean isAntiStealToken() {
		return antiStealToken;
	}

	public void setAntiStealToken(boolean antiStealToken) {
		this.antiStealToken = antiStealToken;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public int getTrackerHttpPort() {
		return trackerHttpPort;
	}

	public void setTrackerHttpPort(int trackerHttpPort) {
		this.trackerHttpPort = trackerHttpPort;
	}

	private static class UploadFileSender implements UploadCallback {

		private InputStream inStream;

		public UploadFileSender(InputStream inStream) {
			this.inStream = inStream;
		}

		public int send(OutputStream out) throws IOException {
			int readBytes;
			while ((readBytes = inStream.read()) > 0) {
				out.write(readBytes);
			}
			return 0;
		}
	}
}
