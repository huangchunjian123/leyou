package com.system.service.ipml;

import com.system.bean.RolePer;
import com.system.dao.IRolePerDao;
import com.system.service.IRolePerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zzy
 * @Date 2018/7/17 下午3:43
 */
@Service
public class RolePerServiceImpl implements IRolePerService {
    @Autowired
    IRolePerDao rolePerDao;

    @Override
    @Transactional
    public boolean updateRolePer(Integer[] permissionIds,Integer roleId) {
        rolePerDao.deleteByRoleId(roleId);
        int count = 0;
        for(Integer id:permissionIds){
            RolePer rolePer = new RolePer();
            rolePer.setPerId(id);
            rolePer.setRoleId(roleId);
            if(rolePerDao.save(rolePer)){
                count++;
            }
        }
        return count == permissionIds.length?true:false;
    }

    @Override
    public List<RolePer> getRolePerList(Integer roleId) {
        return rolePerDao.findByRoleId(roleId);
    }
}
