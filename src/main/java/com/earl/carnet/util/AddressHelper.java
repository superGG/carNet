package com.earl.carnet.util;

import com.earl.carnet.commons.util.JsonHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/15.
 */
public class AddressHelper {

    /**
     * 百度地图Geocoding API 密钥
     */
    private static String key = "Qb9S6LueqC6Por222wTyKaATi3cbljsd";

    /**
     * 根据经纬度获取详细地址
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return
     */
    public static String getAddress(double latitude, double longitude) {
        String addr = "";

        String url = String.format("http://api.map.baidu.com/geocoder/v2/?ak="
                + key
                + "&callback=renderReverse&location=%s,%s&output=json&pois=1", latitude, longitude);
        URL myURL = null;
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        try {
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(httpsConn
                        .getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {
                    System.out.println(data);
                    Map<String, Object> objectMap = JsonHelper.jsonToMapForSina(data);
                    Map<String, Object> result = (Map<String, Object>) objectMap.get("result");
                    String formatted_address = (String) result.get("formatted_address");
                    String sematic_description = (String) result.get("sematic_description");
                    if (sematic_description != null || !sematic_description.equals("")) {
                        addr = formatted_address + sematic_description;
                    } else  {
                        addr = formatted_address;
                    }
                }
                insr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return addr;
    }

    /**
     * 根据详细地址获取经纬度
     * @param address
     * @return
     */
    public static Map<String,Float> getLocation(String address) {
        Float lon = null; //经度
        Float lat = null; //纬度
        String url = String.format("http://api.map.baidu.com/geocoder/v2/?ak="
                + key
                + "&callback=renderOption&output=json&address=%s",address );
        URL myURL = null;
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        try {
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(httpsConn
                        .getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {
                    String json = data.substring(data.indexOf("(")+1,data.length()-1);
                    System.out.println(data);
                    System.out.println(json);
                    Map<String, Object> objectMap = JsonHelper.jsonToMapForSina(json);
                    System.out.println(objectMap.toString());
                    if (objectMap.get("status").toString().equals("0.0")) {
                        Map<String, Map<String, Object>> result = (Map<String, Map<String, Object>>) objectMap.get("result");
                        Map<String,  Object> location = result.get("location");
                        System.out.println(location.get("lng"));
                        lon =  Float.valueOf(location.get("lng").toString());
                        lat =  Float.valueOf(location.get("lat").toString());
                    }
                }
                insr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Map<String,Float> map = new HashMap<>();
        map.put("lon",lon);
        map.put("lat",lat);
        return map;
    }
}

