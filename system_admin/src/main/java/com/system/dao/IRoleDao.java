package com.system.dao;

import com.system.bean.Role;

import java.util.List;

/**
 * @author zzy
 * @Date 2018/7/16 上午10:53
 */
public interface IRoleDao extends IBaseDao<Role> {
    List<Role> findList();
}
