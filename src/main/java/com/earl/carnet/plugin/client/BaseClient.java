package com.earl.carnet.plugin.client;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.model.internal.CommonConfig;

import com.google.gson.JsonObject;

public class BaseClient { //Jersery默认不支持multiform表单类型，需要通过配置文件配置上才行

	public static final Random random = new Random();

	public static final int bound = 2048; // 随机数边界

	// public static final String baseRongYun = "https://api.cn.ronghub.com";
	public static final String baseRongYun = "http://localhost:8080/weishi";

	public static final String APPKEY = "uwd1c0sxdlx2";
	public static final String SIGNATURE = "890b422b75c1c5cb706e4f7921df1d94e69c17f4";

	public static final String JSON = "json";
	public static final String XML = "xml";

	/**
	 * <warn> 不支持文件上传</warn> 得到融云token
	 * 
	 * @param paramMap
	 *            传入参数集合
	 * @param format
	 *            TODO
	 * @param strings
	 *            strings,传入地址集合
	 * @return 返回json 格式数据
	 */
	public JsonObject doPost(Map<String, String> paramMap, String format, String... strings) {
		Client client = JerseyClientBuilder.createClient();
		// URI uri = new URI("https://api.cn.ronghub.com/user/refresh.json");
		StringBuilder builder = new StringBuilder();
		builder.append(baseRongYun);
		for (String string : strings) {
			builder.append('/').append(string);
		}

		builder.append('.').append(format);

		WebTarget target = client.target(builder.toString());
		Builder request = target.request();
		request.accept(MediaType.TEXT_HTML);

		random.setSeed(System.currentTimeMillis());
		request.header("App-Key", APPKEY);
		request.header("Timestamp", System.currentTimeMillis());
		request.header("Nonce", random.nextInt(bound));
		request.header("Signature", SIGNATURE);
		request.header("Content-Type", "application/x-www-form-urlencoded");

		// request.header(name, value);//设置请求桐

		MultivaluedMap<String, String> dodo = new MultivaluedStringMap();

		for (Entry<String, String> entry : paramMap.entrySet()) {
			dodo.putSingle(entry.getKey(), entry.getValue());
		}
		return request.post(Entity.form(dodo), JsonObject.class);
	}

	/**
	 * <warn> 不支持文件上传</warn> 得到融云token
	 * 
	 * @param paramMap
	 *            传入参数集合
	 * @param format
	 *            TODO
	 * @param strings
	 *            strings,传入地址集合
	 * @return 返回json 格式数据
	 */
	public JsonObject doGet(Map<String, String> paramMap, String format, String... strings) {
		Client client = new JerseyClientBuilder().build();
		// URI uri = new URI("https://api.cn.ronghub.com/user/refresh.json");
		StringBuilder builder = new StringBuilder();
		builder.append(baseRongYun);
		for (String string : strings) {
			builder.append('/').append(string);
		}

		builder.append('.').append(format);

		WebTarget target = client.target(builder.toString());
		for (Entry<String, String> entry : paramMap.entrySet()) {
			target.queryParam(entry.getKey(), entry.getValue());
		}

		Builder request = target.request();
		request.accept(MediaType.TEXT_HTML);

		random.setSeed(System.currentTimeMillis());
		request.header("App-Key", APPKEY);
		request.header("Timestamp", System.currentTimeMillis());
		request.header("Nonce", random.nextInt(bound));
		request.header("Signature", SIGNATURE);
		request.header("Content-Type", "application/x-www-form-urlencoded");

		return request.get(JsonObject.class);
	}

	/**
	 * <warn> 不支持文件上传</warn> 得到融云token
	 * 
	 * @param paramMap
	 *            传入参数集合
	 * @param format
	 *            TODO
	 * @param strings
	 *            strings,传入地址集合
	 * @return 返回json 格式数据
	 */
	public JsonObject doPostWithFile(Map<String, String> paramMap, String format, String... strings) {
		
		//TODO 这里是重点
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(MultiPartFeature.class);
		
		Client client = new JerseyClientBuilder().withConfig(clientConfig).build();
		// URI uri = new URI("https://api.cn.ronghub.com/user/refresh.json");
		StringBuilder builder = new StringBuilder();
		builder.append(baseRongYun);
		for (String string : strings) {
			builder.append('/').append(string);
		}

		if(format != null){
			builder.append('.').append(format);
		}
		System.out.println(builder.toString());
		WebTarget target = client.target(builder.toString());
		Builder request = target.request();
		request.accept(MediaType.TEXT_HTML);

		random.setSeed(System.currentTimeMillis());
//		request.header("Signature", SIGNATURE);

		// request.header(name, value);//设置请求桐

		MultivaluedMap<String, String> dodo = new MultivaluedStringMap();

		if (paramMap != null) {

			for (Entry<String, String> entry : paramMap.entrySet()) {
				dodo.putSingle(entry.getKey(), entry.getValue());
			}
		}
		
		
		File f = new File("D:/aa1470475453360.sql");
		//构建一个form data体
		FileDataBodyPart fdp = new FileDataBodyPart("file", f, MediaType.APPLICATION_OCTET_STREAM_TYPE);
		MultiPart multiPart = new MultiPart();
		multiPart.bodyPart(fdp);
		
		//构建一个form data体
		Entity<Object> entity = Entity.entity(multiPart, MediaType.MULTIPART_FORM_DATA);
		String post = request.post(entity,String.class);
		return null;
	}

	public static void main(String[] args) {

		BaseClient baseClient = new BaseClient();
		baseClient.doPostWithFile(null, null, "appMyMassage", "updateImg");

	}

}
