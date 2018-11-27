package com.system.service;

import com.system.bean.Admin;
import com.system.common.util.Page;

/**
 * @author zzy
 */
public interface IAdminService {
    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    Admin login(String account, String password);

    /**
     * 保存
     * @param admin
     * @return
     */
    boolean save(Admin admin);

    /**
     * 根据id获取详情
     * @param admin
     * @return
     */
    Admin getById(int admin);

    /**
     * 删除
     * @param ids
     * @return
     */
    boolean delete(Integer[] ids);

    /**
     * 更新
     * @param admin
     * @return
     */
    boolean update(Admin admin);

    /**
     * 获取分页
     * @param page
     * @param pageSize
     * @return
     */
    Page<Admin> getPage(int page, int pageSize);

    /**
     * 帐号验证
     * @param account
     * @param id
     * @return
     */
    boolean checkAcc(String account, Integer id);

}
