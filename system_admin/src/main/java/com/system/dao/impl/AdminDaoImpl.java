package com.system.dao.impl;

import com.system.bean.Admin;
import com.system.common.util.Page;
import com.system.dao.BaseDao;
import com.system.dao.IAdminDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class AdminDaoImpl extends BaseDao<Admin> implements IAdminDao {
    @Override
    public boolean save(Admin entity) {
        return super.insertEntity(entity);
    }

    @Override
    public boolean delete(Admin entity) {
        return super.deleteEntity(entity);
    }

    @Override
    public boolean delById(Serializable [] ids) {
        return super.deleteById(ids);
    }

    @Override
    public boolean update(Admin entity) {
        return super.updateEntity(entity);
    }

    @Override
    public Admin findById(Serializable id) {
        return super.findEntityById(id);
    }

    @Override
    public Page<Admin> findPage(int page, int pageSize, Object... args) {
        String sql = "order by create_date desc";

        /*for(int i = 0;i < args.length; i++){

            if(i == 0){
                sql += " "
            }
        }*/






        return super.paginate(page,pageSize,sql,args);
    }

    @Override
    public Admin getByAccAndPwd(String account, String password) {
        String sql = "select * from sys_admin where account = ? and password = ?";
        return super.findFirstEntity(sql,account,password);
    }

    @Override
    public Admin getByAccAndId(String account, Integer id) {
        String sql = "select * from sys_admin where account = ?";
        Object [] args = null;

        if(null != id){
            sql += " and id != ?";
            args = new Object[]{account,id};

        }else{
            args = new Object[]{account};
        }

        return super.findFirstEntity(sql,args);
    }
}
