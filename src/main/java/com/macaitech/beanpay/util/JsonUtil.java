package com.macaitech.beanpay.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json转换工具
 * 
 * @author meitao
 * @date 2014年7月15日 上午9:38:59
 */
public final class JsonUtil {
	static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

	static final void configure(ObjectMapper mapper) {
		// 转json时不转为null的属性
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		// 加引号兼容性比较好
		mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
		// 支持无引号属性名
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// 支持单引号
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}

	/**
	 * 把json串转化成list
	 * 
	 * @author xiaojuan
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final static <T> List<T> readJsonList(String jsonString, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonUtil.configure(mapper);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
			List<T> list = (List<T>) mapper.readValue(jsonString, javaType);
			return list;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	public final static Map<?, ?> readJsonMap(String jsonString) {
		return JsonUtil.toObject(jsonString, Map.class);
	}

	/**
	 * 将对象转换成json字符串格式
	 *
	 * @param value
	 *            需要转换的对象
	 * @param properties2Exclude
	 *            需要排除的属性
	 */
	// public static String toJsonStrWithExcludeProperties(Object value,
	// String[] properties2Exclude) {
	// try {
	// SimpleBeanPropertyFilter sbp =
	// SimpleBeanPropertyFilter.serializeAllExcept(properties2Exclude);
	// FilterProvider filterProvider = new
	// SimpleFilterProvider().addFilter("propertyFilterMixIn", sbp);
	// ObjectMapper mapper = new ObjectMapper();
	// JsonUtils.configure(mapper);
	// return mapper.writer(filterProvider).writeValueAsString(value);
	// } catch (Exception e) {
	// JsonUtils.log.error("Json转换失败", e);
	// throw new RuntimeException(e);
	// }
	// }
	/**
	 * 将Json字符串转换成指定类的实例，支持泛型
	 * 
	 * @author meitao
	 * @date Sep 4, 2014 7:05:29 AM
	 * @param jsonString
	 * @param Class
	 *            <T> clazz
	 * @return T 如果失败了会返回null
	 */
	@SuppressWarnings("unchecked")
	public final static <T> T toObject(String jsonString, Class<T> mainClass, Class<?>... parameterClasses) {
		ObjectMapper mapper = new ObjectMapper();
		T ret = null;
		try {
			JsonUtil.configure(mapper);
			if (parameterClasses != null && parameterClasses.length > 0) {
				JavaType javaType = mapper.getTypeFactory().constructParametricType(mainClass, parameterClasses);
				ret = (T) mapper.readValue(jsonString, javaType);
			} else {
				ret = mapper.readValue(jsonString, mainClass);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			JsonUtil.log.error("jsonString:" + jsonString);
		}
		return ret;
	}

	public final static <T> T tryObject(byte[] jsonByte, Class<T> mainClass, Class<?>... parameterClasses) {
		ObjectMapper mapper = new ObjectMapper();
		T ret = null;
		try {
			JsonUtil.configure(mapper);
			if (parameterClasses != null && parameterClasses.length > 0) {
				JavaType javaType = mapper.getTypeFactory().constructParametricType(mainClass, parameterClasses);
				ret = mapper.readValue(jsonByte, javaType);
			} else {
				ret = mapper.readValue(jsonByte, mainClass);
			}
		} catch (Throwable ignore) {
		}
		return ret;
	}

	/**
	 * 尝试将json转换成对象，支持泛型
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public final static <T> T tryObject(String jsonString, Class<T> mainClass, Class<?>... parameterClasses) {
		ObjectMapper mapper = new ObjectMapper();
		T ret = null;
		try {
			JsonUtil.configure(mapper);
			if (parameterClasses != null && parameterClasses.length > 0) {
				JavaType javaType = mapper.getTypeFactory().constructParametricType(mainClass, parameterClasses);
				ret = (T) mapper.readValue(jsonString, javaType);
			} else {
				ret = mapper.readValue(jsonString, mainClass);
			}
		} catch (Throwable ignore) {
		}
		return ret;
	}

	/**
	 * 枚举类型转换
	 * 
	 * @author meitao
	 * @date 2016年3月28日 上午11:26:23
	 * @param values
	 * @return String
	 */
	public final static String toString(Enum<?>[] values) {
		JSONArray ret = new JSONArray();
		if (values != null) {
			for (Enum<?> value : values) {
				ret.add(value.toString());
			}
		}
		return ret.toString();
	}

	/**
	 * 将Java对象转换成Json字符串
	 * 
	 * @author meitao
	 * @date Sep 4, 2014 7:04:29 AM
	 * @param Object
	 *            object
	 * @return String 如果失败了会返回null
	 */
	public final static String toString(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonUtil.configure(mapper);
			return mapper.writeValueAsString(object);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	public final static byte[] toBytes(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonUtil.configure(mapper);
			return mapper.writeValueAsBytes(object);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 验证是否是Json
	 * 
	 * @param s
	 * @return
	 */
	public final static boolean verify(String s) {
		return s.length() >= 2 && s.charAt(0) == '{' && s.endsWith("}");
	}
}
