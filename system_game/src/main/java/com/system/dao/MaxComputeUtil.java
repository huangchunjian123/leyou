package com.system.dao;

import com.aliyun.odps.Instance;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.Table;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordReader;
import com.aliyun.odps.task.SQLTask;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TunnelException;
import com.system.common.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MaxComputeUtil {

    @Autowired
    private Odps odps;

    public List<Record> run(String sql){
        Instance i;
        List<Record> records;
        try {
            i= SQLTask.run(this.odps,sql);
            i.waitForSuccess();
            records = SQLTask.getResultByInstanceTunnel(i);
        } catch (OdpsException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return records;
    }

    public List<Record> tunnel(String tableName){
        TableTunnel tableTunnel = new TableTunnel(this.odps);
        List<Record> list = new ArrayList<>();
        try {
            TableTunnel.DownloadSession downloadSession = tableTunnel.createDownloadSession(this.odps.getDefaultProject(),tableName);
            long count = downloadSession.getRecordCount();
            System.out.println("结果集总数为:"+count);
            RecordReader recordReader = downloadSession.openRecordReader(0,count);
            Record record;
            while ((record = recordReader.read()) != null) {
                list.add(record);
            }
            recordReader.close();

        } catch (TunnelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }


    public int executeForInt(String sql){
        List<Record> records = this.run(sql);

        return Integer.parseInt(records.get(0).get(0).toString());
    }

    public boolean deleteTable(String tableName){
        StringBuffer sb = new StringBuffer("DROP TABLE ").append(tableName).append(";");
        return this.execute(sb.toString());
    }

    public boolean execute(String sql){
        Instance i;
        try {
            i= SQLTask.run(this.odps,sql);
            i.waitForSuccess();
            return true;
        } catch (OdpsException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existTable(String tableName){
        for(Table t: this.odps.tables()){
            if(tableName.equals(t.getName())){
                return true;
            }
        }
        return false;
    }

    public List<String> findTables(String tableName){
        List<String> lists = new ArrayList<>();
        if(StrUtil.isEmpty(tableName)){
            for(Table t: this.odps.tables()){
                lists.add(t.getName());
            }
        }else{
            for(Table t: this.odps.tables()){
                if(t.getName().contains(tableName)){
                    lists.add(t.getName());
                }
            }
        }
        return lists;
    }

    public static void main(String [] args){
        MaxComputeUtil maxCompute = new MaxComputeUtil();
        String sql = "select * from log_1_2018_11_02_1 union all select * from log_1_2018_11_02_2;";
        long start = System.currentTimeMillis();
        List<Record> records = maxCompute.run(sql);
        long end = System.currentTimeMillis();

        System.out.println((end-start)/1000);
        for(Record r:records){
            System.out.println(r.get("logtime"));
        }

       /* for (Table t : maxCompute.odps.tables()) {
            System.out.println(t);
            System.out.println(t.getName());
        }

        for (Instance i : maxCompute.odps.instances()) {
            System.out.println(i);
            System.out.println(i);
        }*/
    }


}
