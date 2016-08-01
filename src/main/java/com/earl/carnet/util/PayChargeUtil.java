/**
 * Copyright (c) 2014 Wteamfly.  All rights reserved. 网飞公司 版权所有.
 * 请勿修改或删除版权声明及文件头部.
 */
package com.earl.carnet.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;



/**
 *	支付工具类
 */
public class PayChargeUtil {
	
	/**
	 * pingpp 管理平台对应的 API key
	 */
//	sk_test_T04yj18Kezv1LG408S0C0mrT
	
//	public static String apiKey = "sk_test_CWj5mHjTy5qH5qL800PunP8K";
	
	/**
	 * pingpp 管理平台对应的应用 ID
	 */
	public static final String appId = "app_e9a5m9rnPWz50arv";
	
	static{
		Pingpp.apiKey = "sk_test_CWj5mHjTy5qH5qL800PunP8K";
	}
	
//	/**
//	 * pingpp 管理平台对应的应用 ID
//	 */
//	public static final String appId = "app_fT8aTCzvnr1OLyT0";
//	
//	static{
//		Pingpp.apiKey = "sk_test_CWj5mHjTy5qH5qL800PunP8K";
//	}
	
	static Logger logger = LogManager.getLogger(PayChargeUtil.class);

	/**
	     * 创建 Charge
	     * 
	     * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
	     * map 里面参数的具体说明请参考：https://pingxx.com/document/api#api-c-new
	 * @param ordersId TODO
	 * @param totalPrice TODO
	 * @param payWay TODO
	 * @param goodsName TODO
	 * @param describe TODO
	 * @return
	 * @throws ChannelException 
	 * @throws APIException 
	 * @throws APIConnectionException 
	 * @throws InvalidRequestException 
	 * @throws AuthenticationException 
	     */
	    public static Charge charge(Long ordersId, Long totalPrice, String payWay, String goodsName, String describe) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, ChannelException {
	    	
	        Charge charge = null;
	        Map<String, Object> chargeParams = new HashMap<String, Object>();
	        chargeParams.put("amount", totalPrice);//required 订单总金额, 单位为对应币种的最小货币单位，例如：人民币为分（如订单总金额为 1 元，此处请填 100）。
	        chargeParams.put("currency", "cny");//required 三位 ISO 货币代码，目前仅支持人民币 cny。
	        chargeParams.put("subject", goodsName);//required 商品的标题，该参数最长为 32 个 Unicode 字符，银联全渠道（upacp/upacp_wap）限制在 32 个字节。
	        chargeParams.put("body", describe);//required 商品的描述信息，该参数最长为 128 个 Unicode 字符，yeepay_wap 对于该参数长度限制为 100 个 Unicode 字符
	        chargeParams.put("order_no", ordersId);//required 商户订单号，适配每个渠道对此参数的要求，必须在商户系统内唯一。
	//        											(alipay: 1-64 位， wx: 1-32 位，bfb: 1-20 位，
	//        											upacp: 8-40 位，yeepay_wap:1-50 位，jdpay_wap:1-30 位，
	//        											cnp_u:8-20 位，cnp_f:8-20 位，推荐使用 8-20 位，要求数字或字母，不允许特殊字符)。
	        chargeParams.put("channel", payWay);//required 支付使用的第三方支付渠道，

	        chargeParams.put("client_ip", "127.0.0.1");//required 发起支付请求终端的 IP 地址，格式为 IPV4，如: 127.0.0.1。
	        Map<String, String> app = new HashMap<String, String>();
	        app.put("id",appId);
	        chargeParams.put("app", app);
	            //发起交易请求
	            charge = Charge.create(chargeParams);
	            System.out.println(charge);
	        return charge;
	    }

	
}


