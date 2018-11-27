package com.system.service;

import com.system.bean.RolePer;

import java.util.List;

/**
 * @author zzy
 * @Date 2018/7/17 下午3:41
 */
public interface IRolePerService {

    boolean updateRolePer(Integer[] roleIds, Integer roleId);

    List<RolePer> getRolePerList(Integer roleId);
}
