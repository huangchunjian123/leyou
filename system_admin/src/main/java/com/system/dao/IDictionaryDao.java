package com.system.dao;

import com.system.bean.Dictionary;
import com.system.common.util.Page;

import java.util.List;
import java.util.Map;

/**
 * @author zzy
 * @Date 2018/7/26 下午4:12
 */
public interface IDictionaryDao extends IBaseDao<Dictionary>{

    List<Dictionary> findByParentId(Integer id);

    List<Dictionary> findByCode(String code);

    Dictionary findByCode(String code, Integer id);

    Dictionary findByValue(String value, Integer parentId, Integer id);

    Page<Map<String,Object>> findMapPage(int page, int pageSize, Object... args);

    String findName(String code, String value);




}
