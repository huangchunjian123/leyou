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
@Table(name="sys_dictionary")
public class Dictionary implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @Getter
    @Setter
    @Column(name="id",isPK = true)
    private Integer id;

    /**
     *
     */
    @Getter
    @Setter
    @Column(name="dic_parent_id")
    private Integer dicParentId;

    /**
     *
     */
    @Getter
    @Setter
    @Column(name="dic_name")
    private String dicName;

    /**
     *
     */
    @Getter
    @Setter
    @Column(name="dic_code")
    private String dicCode;

    /**
     *
     */
    @Getter
    @Setter
    @Column(name="dic_value")
    private String dicValue;

    /**
     *
     */
    @Getter
    @Setter
    @Column(name="create_name")
    private String createName;

    /**
     *
     */
    @Getter
    @Setter
    @Column(name="create_date")
    private java.util.Date createDate;

    /**
     *
     */
    @Getter
    @Setter
    @Column(name="update_name")
    private String updateName;

    /**
     *
     */
    @Getter
    @Setter
    @Column(name="update_date")
    private java.util.Date updateDate;

}
