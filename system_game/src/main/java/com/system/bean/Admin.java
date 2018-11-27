package com.system.bean;



import lombok.Getter;
import lombok.Setter;

import java.io.*;

/**
* @author auto create
* @Date 2018/55/26 15:55:32
*/
public class Admin implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     *用户id
     */
    @Getter
    @Setter
    private Integer id;

    /**
     *头像
     */
    @Getter
    @Setter
    private String headPortrait;

    /**
     *管理员帐号
     */
    @Getter
    @Setter
    private String account;

    /**
     *管理员姓名
     */
    @Getter
    @Setter
    private String name;

    /**
     *管理员密码
     */
    @Getter
    @Setter
    private String password;

    /**
     *性别
     */
    @Getter
    @Setter
    private String gender;

    /**
     *手机号
     */
    @Getter
    @Setter
    private String phone;

    /**
     *邮箱
     */
    @Getter
    @Setter
    private String email;

    /**
     *是否启用
     */
    @Getter
    @Setter
    private String isEnable;

    /**
     *管理员类型
     */
    @Getter
    @Setter
    private Integer adminType;

    /**
     *描述
     */
    @Getter
    @Setter
    private String description;

    /**
     *登录次数
     */
    @Getter
    @Setter
    private Integer loginNum;

    /**
     *最后登录时间
     */
    @Getter
    @Setter
    private java.util.Date lastLogin;

    /**
     *最后登录IP
     */
    @Getter
    @Setter
    private String lastLoginIp;

    /**
     *创建时间
     */
    @Getter
    @Setter
    private java.util.Date createDate;

    /**深复制
     * 将一个对象复制后，不论是基本数据类型还有引用类型，都是重新创建的。简单来说，就是深复制进行了完全彻底的复制，而浅复制不彻底。
     */
    public Admin deepClone() throws IOException, ClassNotFoundException {

        /* 写入当前对象的二进制流 */
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);

        /* 读出二进制流产生的新对象 */
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (Admin)ois.readObject();
    }

}
