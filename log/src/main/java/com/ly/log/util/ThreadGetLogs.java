package com.ly.log.util;

import com.aliyun.openservices.log.common.LogContent;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.common.QueriedLog;
import com.aliyun.openservices.log.exception.LogException;
import com.aliyun.openservices.log.request.GetLogsRequest;
import com.aliyun.openservices.log.response.GetLogsResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class ThreadGetLogs implements Runnable{
    private static final Logger logger = (Logger) LoggerFactory.getLogger(LogUtil.class);
    private ALiYun aLiYun = new ALiYun();
    private List<QueriedLog> queriedLogs;
    private CountDownLatch countDownLatch;
    private String tableName;

    public ALiYun getaLiYun() {
        return aLiYun;
    }
    public ThreadGetLogs(ALiYun aLiYun,List<QueriedLog> queriedLogs,CountDownLatch countDownLatch,String tableName){
        this.aLiYun = aLiYun;
        this.queriedLogs = queriedLogs;
        this.countDownLatch = countDownLatch;
        this.tableName = tableName;
    }

    public void setaLiYun(ALiYun aLiYun) {
        this.aLiYun = aLiYun;
    }

    public List<QueriedLog> getQueriedLogs() {
        return queriedLogs;
    }

    public void setQueriedLogs(List<QueriedLog> queriedLogs) {
        this.queriedLogs = queriedLogs;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // 对于每个 log offset,一次读取 10 行 log，如果读取失败，最多重复读取 3 次。
        GetLogsResponse res4 = null;
        for (int retry_time = 0; retry_time < 3; retry_time++) {

            GetLogsRequest req4 = new GetLogsRequest(aLiYun.getProject(), aLiYun.getLogstore(), aLiYun.getBeginSecond(), aLiYun.getEndSecond(), "", "", aLiYun.getLogOffset(),
                    aLiYun.getLogLine(), false);
            try {
                res4 = aLiYun.getClient().GetLogs(req4);
            } catch (LogException e) {
                //e.printStackTrace();
                System.out.println("============================================请求失败============================================");
                logger.error("请求失败");
                aLiYun.clientConfig();
            }

            if (res4 != null && res4.IsCompleted()) {
                StringBuffer sb = new StringBuffer();

               for(QueriedLog log:res4.GetLogs()){
                    LogItem item = log.GetLogItem();
                    String [] params = new String[29];


                   int index = 10;
                   for(LogContent content : item.GetLogContents()){
                       switch (content.GetKey()){
                           case "program_type":
                               params[0] = content.GetValue();
                               break;
                           case "log_type":
                               params[1] = content.GetValue();
                               break;
                           case "event_type":
                               params[2] = content.GetValue();
                               break;
                           case "logname":
                               params[3] = content.GetValue();
                               break;
                           case "roleid":
                               params[4] = content.GetValue();
                               break;
                           case "rolename":
                               params[5] = content.GetValue();
                               break;
                           case "userid":
                               params[6] = content.GetValue();
                               break;
                           case "account":
                               params[7] = content.GetValue();
                               break;
                           case "serverid":
                               params[8] = content.GetValue();
                               break;
                           case "logtime":
                               params[9] = content.GetValue();
                               break;
                           case "__tag__:__pack_id__":
                               break;
                           case "__tag__:__path__":
                               break;
                           case "__tag__:__hostname__":
                               break;
                           case "__topic__":
                               break;
                           case "__source__":
                               break;
                           case "__tag__:__client_ip__":
                               break;
                           case "__tag__:__receive_time__":
                               break;
                           default:
                               params[index] =  content.GetKey()+":"+content.GetValue();
                               ++index;
                               break;
                       }
                   }

                    sb.append("(");

                    for (int i = 0;i<params.length;i++){
                        if(null != params[i]){
                            sb.append("'"+params[i]+"'");
                        }else{
                            sb.append(params[i]);
                        }

                        if(i != params.length-1){
                            sb.append(",");
                        }

                   }
                    sb.append("),");

                }

            DBController dbController = new DBController();
            if(sb.toString().length() > 1){
                dbController.insertData(sb.substring(0,sb.length()-1).toString(),tableName);
            }
            /*synchronized (queriedLogs){

                queriedLogs.addAll(res4.GetLogs())
            }*/
            //queriedLogs.addAll(res4.GetLogs());
            countDownLatch.countDown();
            break;
            }
            //Thread.sleep(200);
        }
        System.out.println(countDownLatch+" | Read log count:" + String.valueOf(res4.GetCount()));
    }
}
