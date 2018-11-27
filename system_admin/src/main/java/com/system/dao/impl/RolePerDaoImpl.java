package com.system.dao.impl;

import com.system.bean.RolePer;
import com.system.dao.BaseDao;
import com.system.dao.IRolePerDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zzy
 * @Date 2018/7/17 下午3:31
 */
@Repository
public class RolePerDaoImpl extends BaseDao<RolePer> implements IRolePerDao {
    @Override
    public List<RolePer> findByRoleId(Integer roleId) {
        return super.findEntity("select * from sys_role_per where role_id = ?",roleId);
    }

    @Override
    public boolean save(RolePer rolePer) {
        return super.insertEntity(rolePer);
    }

    @Override
    public boolean deleteByRoleId(Integer roleId) {
        return super.jdbcTemplate.update("delete from sys_role_per where role_id = ?",roleId) >0?true:false;
    }
}
