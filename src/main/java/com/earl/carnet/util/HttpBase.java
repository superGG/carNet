package com.earl.carnet.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Part;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * @author 黄祥谦.
 * @date:2015-11-24 下午12:26:17
 * @version :
 */
public class HttpBase {

	//	String basePath = "http://www.earltech.cn:8080/fishshop/";
	String basePath = "http://localhost:8080/fishshop/";
//	String basePath = "http://localhost:8082/carnettcp/";

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static final MediaType MEDIA_TYPE_MARKDOWN  = MediaType.parse("text/x-markdown; charset=utf-8");
	private static final String IMGUR_CLIENT_ID = "...";
	private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
	OkHttpClient client = new OkHttpClient();

	public static void main(String [] arg){
		new HttpBase().get("http:localhost:8080/users/getAll",null);
	}

	public String get(String targetURl, Part[] parts) {
		String string = null;
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("multipart/form-data; boundary=---011000010111000001101001");
//		RequestBody body = RequestBody.create(mediaType, "-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"userName\"\r\n\r\nsdfsdf\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"userSex\"\r\n\r\n男\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"userBirthday\"\r\n\r\n1994-11-19\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"userSchool\"\r\n\r\n广东海洋大学\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"userSign\"\r\n\r\nsdfsdfsdf\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"userPassword\"\r\n\r\n 123\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"userAccount\"\r\n\r\n114422\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"keyCore\"\r\n\r\n1234\r\n-----011000010111000001101001--");
		Request request = new Request.Builder()
		  .url("http://localhost:8080/users/getAll")
		  .get()
		  .addHeader("content-type", "multipart/form-data; boundary=---011000010111000001101001")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "00ba3c53-9ddd-538e-e672-47f8fbf69b80")
		  .build();
		try {
			Response response = client.newCall(request).execute();
			response.body().string();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * post 方式提交json数据
	 * @param url
	 * @param json
	 * @return
	 * @throws IOException
     */
	public String post(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}


	/**
	 * post 方式提交纯字符串数据
	 * @param url
	 * @param data
	 * @return
	 * @throws IOException
     */
	String post(String url, Map<String,String> data,Map<String,String> header) throws IOException {

		RequestBody formBody = new FormEncodingBuilder()
				.add("platform", "android")
				.add("name", "bug")
				.add("subject", "XXXXXXXXXXXXXXX")
				.build();

		Request request = new Request.Builder()
				.url(url)
//				.header()
				.post(formBody)
				.build();

		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}

	/**
	 * post 方式提交单文件
	 * @throws Exception
     */
	public void postSignalFile() throws Exception {
		File file = new File("README.md");

		Request request = new Request.Builder()
				.url("https://api.github.com/markdown/raw")
				.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
				.build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

		System.out.println(response.body().string());
	}

	/**
	 * 混合表单提交
	 * @throws Exception
     */
	public void mulitPart() throws Exception {
		// Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
		RequestBody requestBody = new MultipartBuilder()
				.type(MultipartBuilder.FORM)
				.addPart(
						Headers.of("Content-Disposition", "form-data; name=\"title\""),
						RequestBody.create(null, "Square Logo"))
				.addPart(
						Headers.of("Content-Disposition", "form-data; name=\"image\""),
						RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
				.build();

		Request request = new Request.Builder()
				.header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
				.url("https://api.imgur.com/3/image")
				.post(requestBody)
				.build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

		System.out.println(response.body().string());
	}
}
