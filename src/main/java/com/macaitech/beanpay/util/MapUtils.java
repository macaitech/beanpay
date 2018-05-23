package com.macaitech.beanpay.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * 
 * @author wangkai
 * @2016年6月2日 下午8:24:56
 * @desc:对map的key进行ASCII排序
 */
public class MapUtils {

	/**
	 * 对map根据key进行排序 ASCII 顺序
	 * 
	 * @param 无序的map
	 * @return
	 */
	public static SortedMap<String, Object> sortMap(Map<String, Object> map) {

		List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
		// 排序前
		/*
		 * for (int i = 0; i < infoIds.size(); i++) {
		 * System.out.println(infoIds.get(i).toString()); }
		 */

		// 排序
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
			public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
				// return (o2.getValue() - o1.getValue());//value处理
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});
		// 排序后
		SortedMap<String, Object> sortmap = new TreeMap<String, Object>();
		for (int i = 0; i < infoIds.size(); i++) {
			String[] split = infoIds.get(i).toString().split("=");
			sortmap.put(split[0], split[1]);
		}
		return sortmap;
	}

	/**
	 * 
	 * Map to Json
	 * 
	 * @param map
	 * @return
	 */
	public static String toString(Map<String, Object> map) {
		StringBuffer buf = new StringBuffer();
		buf.append("{");
		Iterator<Entry<String, Object>> i = map.entrySet().iterator();
		boolean hasNext = i.hasNext();
		while (hasNext) {
			Entry<String, Object> e = i.next();
			Object key = e.getKey();
			Object value = e.getValue();
			if (value == null)
				continue;
			if (key == MapUtils.class)
				buf.append("(this Map)");
			else
				buf.append(key);
			buf.append("=");
			if (value == MapUtils.class)
				buf.append("(this Map)");
			else
				buf.append(value);
			hasNext = i.hasNext();
			if (hasNext)
				buf.append(", ");
		}
		buf.append("}");
		return buf.toString();
	}

	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null;

		Object obj = beanClass.newInstance();

		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			Method setter = property.getWriteMethod();
			if (setter != null) {
				setter.invoke(obj, map.get(property.getName()));
			}
		}

		return obj;
	}

	public static Map<String, Object> objectToMap(Object obj) {
		try {
			if (obj == null)
				return null;

			Map<String, Object> map = new HashMap<String, Object>();

			BeanInfo beanInfo;
			beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (key.compareToIgnoreCase("class") == 0) {
					continue;
				}
				Method getter = property.getReadMethod();
				Object value = getter != null ? getter.invoke(obj) : null;
				if(value!=null) {
					map.put(key, value);
				}
			}

			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param parametersMap
	 * @param isUrlEncoding
	 * @param charset
	 * @return
	 */
	public static String getQueryString(Map<String,Object> parametersMap,boolean isUrlEncoding,String charset) {  
        StringBuffer sbuffer = new StringBuffer();  
        for(Object obj:parametersMap.keySet()){  
        		if(parametersMap.get(obj)==null) continue;
            String value=parametersMap.get(obj).toString();  
            if(isUrlEncoding){  
                try {  
                    value = URLEncoder.encode(value,charset); 
                } catch (UnsupportedEncodingException e1) {  
                    //e1.printStackTrace();  
                }  
            }  
            sbuffer.append(obj).append("=").append(value).append("&");  
        }  
        return sbuffer.toString().replaceAll("&$", "");  

    }  

}
