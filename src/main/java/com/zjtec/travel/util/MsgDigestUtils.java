package com.zjtec.travel.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class MsgDigestUtils {
    /**
     * SHA256 加密
     * @param str 明文
     * @return 密文
     */
    public static String encodeSHA256(String str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = byte2Hex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }

    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    // ========== 新增方法：生成随机盐 ==========
    /**
     * 生成指定长度的随机盐
     * @param length 盐的长度（建议8位）
     * @return 随机盐字符串
     */
    public static String generateSalt(int length) {
        // 盐值包含大小写字母+数字，提高复杂度
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom(); // 安全随机数生成器
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < length; i++) {
            salt.append(chars.charAt(random.nextInt(chars.length())));
        }
        return salt.toString();
    }

    // ========== 新增方法：带盐的循环加密 ==========
    /**
     * 加盐并循环3次SHA256加密
     * @param password 明文密码
     * @param salt 盐值
     * @return 最终加密后的密码
     */
    public static String encryptPassword(String password, String salt) {
        String temp = salt + password; // 盐值前置拼接（也可后置，保持规则统一即可）
        // 循环加密3次
        for (int i = 0; i < 3; i++) {
            temp = encodeSHA256(temp);
        }
        return temp;
    }

}