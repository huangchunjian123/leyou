package com.system.service.ipml;

import com.system.bean.Permission;
import com.system.dao.IPermissionDao;
import com.system.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zzy
 * @Date 2018/7/11 下午12:25
 */
@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    IPermissionDao permissionDao;

    @Override
    public boolean save(Permission permission) {
        permission.setSort(permissionDao.findMaxSort(permission.getParentId()));
        return permissionDao.save(permission);
    }

    @Override
    public boolean update(Permission permission) {
        return permissionDao.update(permission);
    }

    @Override
    public boolean deleteById(Integer id) {
        List<Permission> childrenPer = permissionDao.findByParentId(id);
        Integer ids [] = new Integer[childrenPer.size()];
        for(int i = 0; i < ids.length; i++){
            ids[i] = childrenPer.get(i).getId();
        }

        if(ids.length > 0){
            if(permissionDao.delById(ids)){
               return permissionDao.delById(id);
            }
            return false;
        }
        return permissionDao.delById(id);
    }

    @Override
    public Permission getById(Integer id) {
        return permissionDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> getTreeList(Integer roleId) {
        return permissionDao.findTreeList(null,roleId);
    }

    @Override
    public List<Permission> getRoleFunction(Integer roleId) {
        return permissionDao.findFunctionByRole(roleId);
    }

    @Override
    public boolean checkPath(String path, Integer parentId, Integer id) {
        return permissionDao.findByPath(path,parentId,id);
    }

    public static void main(String [] args){
        Integer [] i = new Integer[0];
        System.out.println(i.length);
    }
}
