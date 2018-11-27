package com.system.service;

import com.system.bean.Dictionary;
import com.system.common.util.Page;

import java.util.List;
import java.util.Map;

/**
 * @author zzy
 * @Date 2018/7/27 上午10:25
 */
public interface IDictionaryService {
    Page<Map<String,Object>> getPage(int page, int pageSize);

    boolean save(Dictionary dictionary);

    boolean update(Dictionary dictionary);

    boolean delete(Integer[] ids);

    Dictionary getById(Integer id);

    List<Dictionary> getByCode(String code);

    Dictionary getByCode(String code, Integer id);

    Dictionary getByValue(String value, Integer parentId, Integer id);

    String getName(String code, String value);
}
