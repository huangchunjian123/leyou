package com.system.common.util;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SignUtils {
    private static Logger logger = LoggerFactory.getLogger(SignUtils.class);
    public static final String Format = "JSON";
    public static final String VERSION = "2016-11-01";
    public static final String AccessKeyId = "LTAIrxm1EQVIMKye";//填写你的key
    public static final String AccessKeySecret = "za0R6kIUbvQGytSCM6upmeqnE0yv6S";//填写你的Secret
    public static final String SignatureMethod = "HMAC-SHA1";
    public static final String Timestamp = getUTCTimeStr();
    public static final String SignatureVersion = "1.0";
    public static final String SignatureNonce = String.valueOf(RandomUtils.nextLong());

    public static String getSignature(Map parameters) throws UnsupportedEncodingException {
        String signature = "";
        try {
            // 对参数进行排序
            String[] sortedKeys = (String[]) parameters.keySet().toArray(new String[]{});
            Arrays.sort(sortedKeys);
            final String SEPARATOR = "&";
            // 生成stringToSign字符串
            StringBuilder stringToSign = new StringBuilder();

            stringToSign.append("GET").append(SEPARATOR);

            stringToSign.append(percentEncode("/")).append(SEPARATOR);

            StringBuilder canonicalizedQueryString = new StringBuilder();

            for (String key : sortedKeys) {
                // 这里注意对key和value进行编码
                canonicalizedQueryString.append("&").append(percentEncode(key))
                        .append("=").append(percentEncode((String) parameters.get(key)));
            }
            // 这里注意对canonicalizedQueryString进行编码
            stringToSign.append(percentEncode(canonicalizedQueryString.toString()
                    .substring(1)));
            logger.debug("----------stringToSign:{}", stringToSign.toString());
            final String ALGORITHM = "HmacSHA1";
            final String ENCODING = "UTF-8";
            String key = AccessKeySecret + "&";
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1"));
            byte[] signData = mac.doFinal(stringToSign.toString().getBytes("UTF-8"));
            signature = DatatypeConverter.printBase64Binary(signData);
            logger.debug("----------signature:{}", signature);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return signature;
    }

    private static String percentEncode(String value) throws UnsupportedEncodingException {
        return value != null ? URLEncoder.encode(value, "utf-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~") : null;

    }

    private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static String getUTCTimeStr() {
        //1、取得本地时间：
        final java.util.Calendar cal = java.util.Calendar.getInstance();
        System.out.println(cal.getTime());
        //2、取得时间偏移量：
        final int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        System.out.println(zoneOffset);
        //3、取得夏令时差：
        final int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        System.out.println(dstOffset);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        return df.format(cal.getTime());
    }

    public static void main(String [] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Map parameters = new HashMap();
        //公共参数
        parameters.put("Version", VERSION);
        parameters.put("AccessKeyId", AccessKeyId);
        parameters.put("SignatureMethod", SignatureMethod);
        parameters.put("Timestamp", Timestamp);
        parameters.put("SignatureVersion", SignatureVersion);
        parameters.put("SignatureNonce", SignatureNonce);
        parameters.put("ServiceCode", "live");
        parameters.put("Format", Format);
        parameters.put("RegionId", "cn-hangzhou");

        parameters.put("Action", "DescribeLiveStreamsOnlineList");
        parameters.put("DomainName", "hjxxavlup.loyin.com.cn");
        parameters.put("AppName", "hjxx");
        parameters.put("PageNum", "1");
        parameters.put("PageSize", "10");
        parameters.put("StreamType", "all");

        parameters.put("Signature", SignUtils.getSignature(parameters));

        try{
            System.out.println(HttpKit.get("http://live.aliyuncs.com/",parameters));
        }catch (Exception e){
            e.printStackTrace();
        }


        /*String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        String ENCODING = "UTF-8";
        String encryptKey = "testsecret&";
        String encryptText = "GET&%2F&AccessKeyId%3Dtestid&Action%3DDescribeLiveSnapshotConfig&AppName%3Dtest&DomainName%3Dtest.com&Format%3DXML&RegionId%3Dcn-shanghai&ServiceCode%3Dlive&SignatureMethod%3DHMAC-SHA1&SignatureNonce%3Dc2fe8fbb-2977-4414-8d39-348d02419c1c&SignatureVersion%3D1.0&Timestamp%3D2017-06-14T09%253A51%253A14Z&Version%3D2016-11-01";
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKeySpec signinKey = new SecretKeySpec(encryptKey.getBytes(ENCODING), HMAC_SHA1_ALGORITHM);
        //生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        //用给定密钥初始化 Mac 对象
        mac.init(signinKey);
        //完成 Mac 操作
        byte[] rawHmac = mac.doFinal(encryptText.getBytes(ENCODING));
        System.out.println(DatatypeConverter.printBase64Binary(rawHmac));*/
    }

}
