package com.system.dao;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.live.model.v20161101.DescribeLiveSnapshotConfigRequest;
import com.aliyuncs.live.model.v20161101.DescribeLiveStreamsOnlineListRequest;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


public class LiveUtil {

    public static final String VERSION = "2016-11-01";
    public static final String AccessKeyId = "LTAIrxm1EQVIMKye";//填写你的key
    public static final String AccessKeySecret = "za0R6kIUbvQGytSCM6upmeqnE0yv6S";//填写你的Secret

    public static void main(String[] args) {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIrxm1EQVIMKye", "za0R6kIUbvQGytSCM6upmeqnE0yv6S");
        IAcsClient client = new DefaultAcsClient(profile);

        DescribeLiveStreamsOnlineListRequest describeLiveStreamsOnlineListRequest = new DescribeLiveStreamsOnlineListRequest();
        describeLiveStreamsOnlineListRequest.setProtocol(ProtocolType.HTTP);
        describeLiveStreamsOnlineListRequest.setAcceptFormat(FormatType.JSON);
        describeLiveStreamsOnlineListRequest.setDomainName("hjxxavldown.loyin.com.cn");
        describeLiveStreamsOnlineListRequest.setActionName("DescribeLivePullStreamConfig");
        describeLiveStreamsOnlineListRequest.putQueryParameter("ResourceOwnerAccount","acs:cdn:*:$accountid:domain/$domainName");

        try {
            HttpResponse httpResponse = client.doAction(describeLiveStreamsOnlineListRequest);
            System.out.println(httpResponse.getStatus());
            System.out.println(new String(httpResponse.getHttpContent()));
            System.out.println(httpResponse.isSuccess());
            //todo something.
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public static String getUTCTimeStr() {
        String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
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
}
