package com.system.service.ipml;

import com.system.bean.Admin;
import com.system.common.util.Page;
import com.system.dao.IAdminDao;
import com.system.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminerviceImpl implements IAdminService {
    @Autowired
    IAdminDao adminDao;

    @Override
    public Admin login(String account, String password) {
        return adminDao.getByAccAndPwd(account,password);
    }

    @Override
    public boolean save(Admin admin) {
        return adminDao.save(admin);
    }

    @Override
    public Admin getById(int id) {
        return adminDao.findById(id);
    }

    @Override
    public boolean delete(Integer[] ids) {
        return adminDao.delById(ids);
    }

    @Override
    public boolean update(Admin admin) {
        return adminDao.update(admin);
    }

    @Override
    public Page<Admin> getPage(int page, int pageSize) {
        return adminDao.findPage(page,pageSize);
    }

    @Override
    public boolean checkAcc(String account, Integer id) {
        if(null != adminDao.getByAccAndId(account,id)){
            return false;
        }
        return true;
    }
}
