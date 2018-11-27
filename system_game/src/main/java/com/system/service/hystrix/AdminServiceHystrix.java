package com.system.service.hystrix;

import com.system.bean.Admin;
import com.system.common.util.Ret;
import com.system.controller.AdminController;
import com.system.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author zzy
 * @Date 2018/8/25 上午9:28
 */
@Component
public class AdminServiceHystrix implements AdminService{

    @Override
    public Admin login(String account, String password) {
        return null;
    }

    @Override
    public String addAdmin(Admin admin) {
        return null;
    }

    @Override
    public String getAdminById(Integer id) {
        return null;
    }

    @Override
    public String update(Admin admin) {
        return null;
    }

    @Override
    public String getAdminPage(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public String checkAccount(String account, Integer id) {
        return null;
    }

    @Override
    public String delAdmin(Integer[] id) {
        return null;
    }
}
