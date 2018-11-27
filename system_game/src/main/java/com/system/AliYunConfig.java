package com.system;

import com.aliyun.odps.Odps;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliYunConfig {
    private String endpoint = "http://service.odps.aliyun.com/api";
    @Value("${aliyun.maxproject}")
    private String projectName;
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;

    @Bean
    public Odps getOdps(){
        Account account = new AliyunAccount(this.accessKeyId, this.accessKeySecret);
        Odps odps = new Odps(account);
        odps.setEndpoint(this.endpoint);
        odps.setDefaultProject(this.projectName);
        return odps;
    }
}
