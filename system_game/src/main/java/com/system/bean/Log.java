package com.system.bean;

import com.system.common.anotation.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author auto create
 * @Date 2018/31/16 15:31:40
 */
public class Log implements Serializable{
        private static final long serialVersionUID = 1L;
        /**
         *程序类型
         */
        @Getter
        @Setter
        @Column(name="program_type")
        private String programType;

        /**
         *日志类型
         */
        @Getter
        @Setter
        @Column(name="log_type")
        private String logType;

        /**
         *事件类型
         */
        @Getter
        @Setter
        @Column(name="event_type")
        private String eventType;

        /**
         *日志名称
         */
        @Getter
        @Setter
        @Column(name="logname")
        private String logname;

        /**
         *角色id
         */
        @Getter
        @Setter
        @Column(name="roleid")
        private String roleid;

        /**
         *用户名
         */
        @Getter
        @Setter
        @Column(name="rolename")
        private String rolename;

        /**
         *用户id
         */
        @Getter
        @Setter
        @Column(name="userid")
        private String userid;

        /**
         *用户账号
         */
        @Getter
        @Setter
        @Column(name="account")
        private String account;

        /**
         *用户服务
         */
        @Getter
        @Setter
        @Column(name="serverid")
        private String serverid;

        /**
         *日志时间
         */
        @Getter
        @Setter
        @Column(name="logtime")
        private java.util.Date logtime;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params1")
        private String params1;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params2")
        private String params2;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params3")
        private String params3;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params4")
        private String params4;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params5")
        private String params5;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params6")
        private String params6;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params7")
        private String params7;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params8")
        private String params8;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params9")
        private String params9;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params10")
        private String params10;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params11")
        private String params11;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params12")
        private String params12;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params13")
        private String params13;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params14")
        private String params14;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params15")
        private String params15;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params16")
        private String params16;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params17")
        private String params17;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params18")
        private String params18;

        /**
         *
         */
        @Getter
        @Setter
        @Column(name="params19")
        private String params19;

        }
