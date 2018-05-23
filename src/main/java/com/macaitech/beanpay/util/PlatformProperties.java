package com.macaitech.beanpay.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源文件工具类
 * @author xiexiaogang
 *
 */
public class PlatformProperties {
	private final static Logger logger = LoggerFactory.getLogger(PlatformProperties.class);
	private static List<ResourceBundle> platforms = new ArrayList<ResourceBundle>();

	/**
	 * 构造方法
	 * @param paths
	 */
	private PlatformProperties(List<String> paths) {
		try {
			logger.info("开始加载系统参数信息");
			for(int i = 0 ; i < paths.size() ; i++){
				String path = paths.get(i);
				if(path != null ){
					loadProperties(path,i);
				}
			}
			logger.info("完成加载系统参数信息");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 从给定的配置文件中加载参数配置
	 * @param file
	 * @throws Exception
	 */
	private static void loadProperties(String file,int i) throws Exception {
		platforms.add(ResourceBundle.getBundle(file, Locale.getDefault()));
		printResouceBundle(platforms.get(i));
		
	}

	/**
	 * 输出资源参数文件的内容
	 * @param rb
	 */
	private static void printResouceBundle(ResourceBundle rb) {
		if (rb != null) {
			Enumeration<String> keys = rb.getKeys();
			while (keys.hasMoreElements()) {
				String k = keys.nextElement();
				logger.info(k + " = " + rb.getString(k));
			}
		}
	}

	/**
	 * 从配置文件取信息
	 * @param key
	 * @return String
	 */
	public static String getProperty(Object key) {
		if (platforms == null || platforms.size() < 1) {
			return "";
		}
		return getProperty(platforms.get(0), String.valueOf(key), 0);
	}
	
	private static String getProperty(ResourceBundle rb, String key, int i) {
		try {
			return rb.getString(key);
		} catch (MissingResourceException e) {
			i++;
			if (platforms.size() > i) {
				return getProperty(platforms.get(i), key, i);
			}
		}
		return "";
	}
		
}
