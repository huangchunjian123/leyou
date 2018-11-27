package com.system.dao.impl;

import com.system.bean.Role;
import com.system.common.util.Page;
import com.system.dao.BaseDao;
import com.system.dao.IRoleDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author zzy
 * @Date 2018/7/16 上午10:54
 */
@Repository
public class RoleDaoImpl extends BaseDao<Role> implements IRoleDao {

    @Override
    public boolean save(Role role) {
        return super.insertEntity(role);
    }

    @Override
    public boolean delete(Role role) {
        return super.deleteEntity(role);
    }

    @Override
    public boolean delById(Serializable... ids) {
        return super.deleteById(ids);
    }

    @Override
    public boolean update(Role role) {
        return super.updateEntity(role);
    }

    @Override
    public Role findById(Serializable id) {
        return super.findEntityById(id);
    }

    @Override
    public Page<Role> findPage(int page, int pageSize, Object... args) {
        String sql = "order by create_date desc";
        return super.paginate(page,pageSize,sql,args);
    }

    @Override
    public List<Role> findList() {
        return super.findEntity("select * from sys_role order by create_date desc");
    }
}
