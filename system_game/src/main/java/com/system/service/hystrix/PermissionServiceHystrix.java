package com.system.service.hystrix;

import com.system.bean.Permission;
import com.system.service.PermissionService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zzy
 * @Date 2018/8/25 上午10:04
 */
@Component
public class PermissionServiceHystrix implements PermissionService{
    @Override
    public String addPermission(Permission permission) {
        return null;
    }

    @Override
    public String updatePermission(Permission permission) {
        return null;
    }

    @Override
    public String getPermissionById(Integer id) {
        return null;
    }

    @Override
    public String deletePermission(Integer id) {
        return null;
    }

    @Override
    public String getTreeList() {
        return null;
    }

    @Override
    public List<Permission> getRoleFunction(int roleId) {
        return null;
    }

    @Override
    public String checkPath(String path, Integer parentId, Integer id) {
        return null;
    }

    @Override
    public String changeSort(String flag, Integer parentId, Integer sort) {
        return null;
    }
}
