package com.macaitech.beanpay.signature;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;



/**
 * 加密验签
 * @author 首证投顾
 */
public class SignatureUtil {
    public static final String FIELD_SIGN = "sign";
    public static final String Sign_Exclude_String="uuid";
    public enum SignatureType {
        MD5, HMACSHA256
    }
    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, Object> data, String key) throws Exception {
        return isSignatureValid(data, key, SignatureType.MD5);
    }
    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @param signatureType 签名方式
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, Object> data, String key, SignatureType signatureType) throws Exception {
        if (!data.containsKey(FIELD_SIGN) ) {
            return false;
        }
        String sign = data.get(FIELD_SIGN).toString();
        return generateSignature(data, key, signatureType).equals(sign);
    }

    /**
     * 生成签名
     *
     * @param data 待签名数据
     * @param key API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, Object> data, String key) throws Exception {
        return generateSignature(data, key, SignatureType.MD5);
    }
    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signatureType参数保持一致。
     *
     * @param data 待签名数据
     * @param key API密钥
     * @param signatureType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, Object> data, String key, SignatureType signatureType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(FIELD_SIGN) || k.equals(Sign_Exclude_String)) {
                continue;
            }
            if(data.get(k)==null) continue;
            String value = data.get(k).toString();
            if (value!=null && value.trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(value).append("&");
        }
        sb.append("key=").append(key);
        if (SignatureType.MD5.equals(signatureType)) {
            return MD5(sb.toString()).toUpperCase();
        }
        else if (SignatureType.HMACSHA256.equals(signatureType)) {
            return HMACSHA256(sb.toString(), key);
        }
        else {
            throw new Exception(String.format("Invalid sign_type: %s", signatureType));
        }
    }
    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        java.security.MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
    
    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
}
