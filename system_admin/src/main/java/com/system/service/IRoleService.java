package com.system.service;

import com.system.bean.Role;
import com.system.common.util.Page;

import java.util.List;

/**
 * @author zzy
 * @Date 2018/7/16 上午11:25
 */
public interface IRoleService {

    boolean save(Role role);

    boolean deleteByIds(Integer[] id);

    Role getById(Integer id);

    boolean update(Role role);

    Page<Role> getPage(int page, int pageSize);

    List<Role> getList();
}
