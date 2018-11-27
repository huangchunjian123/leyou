package com.system.common.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * @author zzy
 * @Date 2018/7/24 上午11:29
 */
public class DESEncryptUtil {

    /**
     * 加密方法
     * @param data  要加密的数据
     * @param key 加密key
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        try {
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes("UTF-8"));
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            //获取数据并加密，正式执行加密操作
            byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));

            return new Base64().encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @return 解密的结果
     * @throws Exception
     */
    public static String desEncrypt(String data, String key) throws Exception {
        byte[] encrypted1 = new Base64().decode(data);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 转换成SecretKey对象
        DESKeySpec desKey = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        // 真正开始解密操作
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString;
    }

    public static void main(String [] args) throws Exception {
        System.out.println(encrypt("123456","yzzadmin"));//JPfxYvlEZKU=
        System.out.println(desEncrypt("ODBtr+gxJCTPLwkfkrZdNA==","xib5eii68fqy8lsu"));
    }
}
