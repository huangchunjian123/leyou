package com.system.service.hystrix;

import com.system.bean.Role;
import com.system.service.RoleService;
import org.springframework.stereotype.Component;

/**
 * @author zzy
 * @Date 2018/8/25 上午10:05
 */
@Component
public class RoleServiceHystrix implements RoleService{
    @Override
    public String addRole(Role role) {
        return null;
    }

    @Override
    public String updateRole(Role role) {
        return null;
    }

    @Override
    public String getPage(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public String getList() {
        return null;
    }

    @Override
    public String getRole(Integer id) {
        return null;
    }

    @Override
    public String deleteRole(Integer[] ids) {
        return null;
    }

    @Override
    public String addRolePer(Integer role, String permissions) {
        return null;
    }

    @Override
    public String getRolePer(Integer role) {
        return null;
    }

    @Override
    public String getTreeList(Integer role) {
        return null;
    }
}
