package com.earl.carnet.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * @author 黄祥谦.
 * @date:2015-11-24 下午12:26:17
 * @version :
 */
public class HttpBase {

	//	String basePath = "http://www.earltech.cn:8080/fishshop/";
	String basePath = "http://localhost:8080/fishshop/";
//	String basePath = "http://localhost:8082/carnettcp/";

	public String sendHttpRequest(String targetURl, Part[] parts) {
		String string = null;

		PostMethod filePost = new PostMethod(targetURl);
		try {
			HttpMethodParams params = filePost.getParams();
			params.setContentCharset("utf-8");
			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(50000);
			int status = client.executeMethod(filePost);
//			if (status == HttpStatus.SC_OK) {
//
//
//			} else {
//			}
			InputStream in = filePost.getResponseBodyAsStream();
			byte[] readStream = readStream(in);
			string = new String(readStream);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
		return string;
	}

	public String sendPostRequest(String targetURL, Map<String, String> params){

		String string = null;
		try {

		PostMethod postMethod = new PostMethod(targetURL);
			HttpMethodParams head = postMethod.getParams();
			head.setContentCharset("utf-8");

			NameValuePair[] data = { new NameValuePair("message", "youUserName"),
					new NameValuePair("passwd", "yourPwd") };
			postMethod.setRequestBody(data);

		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(50000);

			int status = client.executeMethod(postMethod);

		InputStream in = postMethod.getResponseBodyAsStream();
		byte[] readStream = readStream(in);
		string = new String(readStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}

	public String sendGetHttpRequest(String targetURL,Map<String,String> params){

		String string = null;
		try {
		GetMethod getMethod = new GetMethod(targetURL);

		for (Map.Entry<String,String> param:
				params.entrySet()) {
			getMethod.setParams(new HttpMethodParams());
		}


		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(50000);

			int status = client.executeMethod(getMethod);
		InputStream in = getMethod.getResponseBodyAsStream();
		byte[] readStream = readStream(in);
		string = new String(readStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}

	/**
	 * 读取流
	 *
	 * @param inStream
	 * @return 字节数组
	 * @throws Exception
	 */
	private byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}
}
