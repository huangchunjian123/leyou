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
@Table(name="sys_permission")
public class Permission implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     *主键
     */
    @Getter
    @Setter
    @Column(name="id",isPK = true)
    private Integer id;

    /**
     *权限名
     */
    @Getter
    @Setter
    @Column(name="permission_name")
    private String permissionName;

    /**
     *权限类型0.菜单，1.功能
     */
    @Getter
    @Setter
    @Column(name="permission_type")
    private Integer permissionType;

    /**
     *访问路径
     */
    @Getter
    @Setter
    @Column(name="visit_url")
    private String visitUrl;

    /**
     *图标
     */
    @Getter
    @Setter
    @Column(name="icon")
    private String icon;

    /**
     *排序
     */
    @Getter
    @Setter
    @Column(name="sort")
    private Integer sort;

    /**
     *父级id
     */
    @Getter
    @Setter
    @Column(name="parent_id")
    private Integer parentId;

    /**
     *创建人
     */
    @Getter
    @Setter
    @Column(name="create_name")
    private String createName;

    /**
     *创建时间
     */
    @Getter
    @Setter
    @Column(name="create_date")
    private java.util.Date createDate;

    /**
     *更新人
     */
    @Getter
    @Setter
    @Column(name="update_name")
    private String updateName;

}
