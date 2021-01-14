package com.lw.apiTest.common;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUtils {

    Logger logger = Logger.getLogger(SignUtils.class.getName());
    private static final String KEY_MAC = "HmacMD5";

    /*签名认证*/
    public static String hexString(String key, String value) {
        try {
            return byteToHexString(encryptHMAC(key.getBytes(),value)).toString();
        } catch (Exception e) {
            /*throw new RuntimeException(e);*/
            e.printStackTrace();
            return "error";
        }
    }
    /**
     * Write a byte array as hexadecimal String.
     */
    private static String byteToHexString(byte[] bytes) {
        return String.valueOf(Hex.encodeHex(bytes));
    }
    /**
     * HMAC加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    /**
     * 查询项目信息Sign
     * @param plainText
     * @return
     */
    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
