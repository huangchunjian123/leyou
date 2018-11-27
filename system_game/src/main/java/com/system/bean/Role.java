package com.system.bean;

import com.system.common.anotation.Column;
import com.system.common.anotation.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
* @author auto create
* @Date 2018/55/26 15:55:32
*/
@Table(name="sys_role")
public class Role implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     *主键id
     */
    @Getter
    @Setter
    @Column(name="id",isPK = true)
    private Integer id;

    /**
     *角色名
     */
    @Getter
    @Setter
    @Column(name="role_name")
    private String roleName;

    /**
     *描述
     */
    @Getter
    @Setter
    @Column(name="description")
    private String description;

    /**
     *创建时间
     */
    @Getter
    @Setter
    @Column(name="create_date")
    private java.util.Date createDate;

}
