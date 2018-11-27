package com.system.service.ipml;

import com.system.bean.Role;
import com.system.common.util.Page;
import com.system.dao.IRoleDao;
import com.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zzy
 * @Date 2018/7/16 上午11:30
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    IRoleDao roleDao;

    @Override
    public boolean save(Role role) {
        return roleDao.save(role);
    }

    @Override
    public boolean deleteByIds(Integer[] ids) {
        return roleDao.delById(ids);
    }

    @Override
    public Role getById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public boolean update(Role role) {
        return roleDao.update(role);
    }

    @Override
    public Page<Role> getPage(int page, int pageSize) {
        return roleDao.findPage(page,pageSize);
    }

    @Override
    public List<Role> getList() {
        return roleDao.findList();
    }
}
