package com.system.dao;

import com.system.bean.Admin;

public interface IAdminDao extends IBaseDao<Admin>{

    Admin getByAccAndPwd(String account, String password);

    Admin getByAccAndId(String account, Integer id);


}
