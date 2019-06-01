package com.framework.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class CommonHelper {

    /**
     * MD5加密
     * @param target
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5(String target) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(target.getBytes("utf-8"));
        byte[] md5Bytes = digest.digest();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < md5Bytes.length; i++) {
            sb.append(Character.forDigit(md5Bytes[i] >>> 4 & 0xf, 16));
            sb.append(Character.forDigit(md5Bytes[i] & 0xf, 16));
        }

        return sb.toString();
    }

    /**
     * 生成随机码
     *
     * @param length
     *            生成多长的随机码
     * @return
     */
    public static String generateRandomNumber(int length) {
        Random random = new Random();
        String val = "";
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

    /**
     * 验证手机号 格式027-82609562 或 82696523 或 13476565335 都正确
      * @param mobile 电话号码
     * @return boolean 是否是一个正确的电话号格式
     */
    public static boolean isMobileNumber(String mobile) {

       return mobile.matches("\\d{8}|\\d{11}|(\\d{3}-\\d{8})");

    }

}
