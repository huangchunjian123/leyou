package com.system.common.util;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptUtil {
    //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
    private static String KEY = "xib5eii68fqy8lsu";
    // 向量
    private static String IV = "xib5eii68fqy8lsu";
    // 不容易主动攻击，安全性好于ECB，是SSL、IPSec的标准 不利于并行计算 误差传递 需要初始化向量IV
    private static String CBC = "AES/CBC/PKCS5Padding";
    // 简单 有利于并行计算 误差不会被传递 不能隐藏明文的模式 可能对明文进行主动攻击
    private static String ECB = "AES/ECB/PKCS5Padding";


    /**
     * 加密方法
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(CBC);

            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes("utf-8");
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            //用密匙初始化Cipher对象,ENCRYPT_MODE用于将 Cipher 初始化为加密模式的常量
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            //获取数据并加密，正式执行加密操作
            byte[] encrypted = cipher.doFinal(data.getBytes("utf-8"));

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
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = new Base64().decode(data);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(CBC);
            // 转换成SecretKey对象
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            // 真正开始解密操作
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(data, KEY, IV);
    }

    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data) throws Exception {
        return desEncrypt(data, KEY, IV);
    }



    /**
     * 测试
     */
    public static void main(String args[]) throws Exception {

        String test = "18729990   11 0";

        String data = null;
        String key = "xib5eii68fqy8lsu";
        String iv = "xib5eii68fqy8lsu";

        data = encrypt(test, key, iv);

        System.out.println(data);
        System.out.println(desEncrypt("s8KKbWaCwoaT8cb99bQBKw==", key, iv));
    }
}
