package com.system.service;

import com.system.bean.Permission;

import java.util.List;
import java.util.Map;

public interface IPermissionService {
    boolean save(Permission permission);

    boolean update(Permission permission);

    boolean deleteById(Integer id);

    Permission getById(Integer id);

    List<Map<String,Object>> getTreeList(Integer roleId);

    List<Permission> getRoleFunction(Integer roleId);

    boolean checkPath(String path, Integer parentId, Integer id);

}
