package indi.ayun.original_mvp.utils.storage;


import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import indi.ayun.original_mvp.mlog.MLog;


public class PropertiesUtils {

	
	//声明Properties对象
	private static Properties properties = null;
	
	//声明InputStream对象
	private static InputStream in = null;
	
	private static Class<PropertiesUtils> cls = PropertiesUtils.class;
	
	private static String fileName = "/App.properties";

	/**
	 * 初始化配置文件
	 * @param fileName 文件名称
	 * @return Properties
	 */
	public static Properties init(String fileName) {
		try {
			if (null == properties) {
				properties = new Properties();
			}
			in = (InputStream) cls.getResourceAsStream(fileName);
			//in = new BufferedInputStream(new FileInputStream(fileName));
			properties.load(in);
			return properties;
		} catch (IOException e) {
			e.printStackTrace();
			MLog.e("AppPropertiesUtils-->>initProperties", "初始化配置文件错误！"  + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 读取配置文件Key获取Value
	 * @param key Key值
	 * @return String  返回值
	 */
	public static String getPropertiesByKey(String key) {
		String value = null;
		try {
			init(fileName);
			value = properties.getProperty(key);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			MLog.e("AppPropertiesUtils-->>getPropertiesByKey", "根据配置文件Key获取Value错误！"  + e.getMessage());
		}
			return value;
	}
	

	/**
	 * 读取所有Key和Value
	 * @return map Map集合
	 */
	public static Map<String, String> getPropertiesAll(){
		Map<String, String> map = null;
		try {
			init(fileName);
			Enumeration<?> enumeration = properties.propertyNames();
			map = new HashMap<String, String>();
			while(enumeration.hasMoreElements()) {
				 String key = (String) enumeration.nextElement();
				 String value = properties.getProperty(key);
				 map.put(key, value);
		     }
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			MLog.e("AppPropertiesUtils-->>getPropertiesAll", "读取所有KEY和VALUES"  + e.getMessage());
		}
			return map;
	}
	
	
	
	
}
