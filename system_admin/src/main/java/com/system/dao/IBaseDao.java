package com.system.dao;

import com.system.common.util.Page;

import java.io.Serializable;

/**
 * @author zzy
 */
public interface IBaseDao<T>{
    /**
     * 保存
     * @param t
     * @return
     */
    boolean save(T t);


    /**
     * 删除
     * @param t
     * @return
     */
    boolean delete(T t);

    /**
     * 根据id批量删除
     * @param ids
     * @return
     */
    boolean delById(Serializable... ids);

    /**
     * 更新
     * @param t
     * @return
     */
    boolean update(T t);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T findById(Serializable id);

    /**
     * 获取分页
     * @param page
     * @param pageSize
     * @param args
     * @return
     */
    Page<T> findPage(int page, int pageSize, Object... args);
}
