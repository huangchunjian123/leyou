package com.system.dao.impl;

import com.system.bean.Dictionary;
import com.system.common.util.Page;
import com.system.dao.BaseDao;
import com.system.dao.IDictionaryDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zzy
 * @Date 2018/7/26 下午4:13
 */
@Repository
public class DictionaryDaoImpl extends BaseDao<Dictionary> implements IDictionaryDao {
    @Override
    public boolean save(Dictionary dictionary) {
        return super.insertEntity(dictionary);
    }

    @Override
    public boolean delete(Dictionary dictionary) {
        return false;
    }

    @Override
    public boolean delById(Serializable... ids) {
        return super.deleteById(ids);
    }

    @Override
    public boolean update(Dictionary dictionary) {
        return super.updateEntity(dictionary);
    }

    @Override
    public Dictionary findById(Serializable id) {
        return super.findEntityById(id);
    }

    @Override
    public Page<Dictionary> findPage(int page, int pageSize, Object... args) {
        return null;
    }

    @Override
    public Page<Map<String,Object>> findMapPage(int page, int pageSize, Object... args) {
        Page<Map<String,Object>> pages = super.paginate(page, pageSize,"select * ","from sys_dictionary where dic_parent_id is null order by create_date");
        pages.getList().forEach((item) -> {
            List<Map<String,Object>> child= super.select("select * from sys_dictionary where dic_parent_id = ? order by dic_value,create_date",item.get("id"));
            if (child.size() > 0) {
                item.put("children",child);
            }
        });

        return pages;
    }

    @Override
    public String findName(String code, String value) {
        List<Map<String,Object>>  result = super.jdbcTemplate.queryForList("select dic_name from sys_dictionary where dic_code = ? and dic_value = ?",code,value);
        return result.size() > 0 ? result.get(0).get("dic_name").toString() : "";
    }

    @Override
    public List<Dictionary> findByParentId(Integer id) {
        return super.findEntity("select * from sys_dictionary where dic_parent_id = ? order by create_date desc",id);
    }

    @Override
    public List<Dictionary> findByCode(String code) {
        return super.findEntity("select * from sys_dictionary where dic_code = ? and dic_parent_id is not null order by create_date desc",code);
    }

    @Override
    public Dictionary findByCode(String code, Integer id) {
        String sql = "select * from sys_dictionary where dic_parent_id is null and dic_code = ?";
        if (null != id) {
            sql += " and id != "+id+"";
        }
        return super.findFirstEntity(sql, code);
    }

    @Override
    public Dictionary findByValue(String value, Integer parentId, Integer id) {
        String sql = "select * from sys_dictionary where dic_value = ? and dic_parent_id = ? ";
        if (null != id) {
            sql += " and id != "+id+"";
        }
        return super.findFirstEntity(sql,value,parentId);
    }
}
