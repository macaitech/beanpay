/**
 * 
 */
package com.macaitech.beanpay.util;

import java.util.UUID;

/**
 * @author sz
 *
 */
public class UuidUtil {
	
	public static String getUUID() {
		String uuid =  UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
}
