package com.system.dao;

import com.system.bean.RolePer;

import java.util.List;

/**
 * @author zzy
 * @Date 2018/7/17 下午3:30
 */
public interface IRolePerDao {

    List<RolePer> findByRoleId(Integer roleId);

    boolean save(RolePer rolePer);

    boolean deleteByRoleId(Integer roleId);
}
