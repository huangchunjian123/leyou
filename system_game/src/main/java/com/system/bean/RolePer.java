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
@Table(name="sys_role_per")
public class RolePer implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     *角色id
     */
    @Getter
    @Setter
    @Column(name="role_id")
    private Integer roleId;

    /**
     *根权id
     */
    @Getter
    @Setter
    @Column(name="per_id")
    private Integer perId;

}
