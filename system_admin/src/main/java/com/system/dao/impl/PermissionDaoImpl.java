package com.system.dao.impl;

import com.system.bean.Permission;
import com.system.common.util.Page;
import com.system.dao.BaseDao;
import com.system.dao.IPermissionDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zzy
 * @Date 2018/7/10 下午4:33
 */
@Repository
public class PermissionDaoImpl extends BaseDao<Permission> implements IPermissionDao {
    @Override
    public boolean save(Permission permission) {
        return super.insertEntity(permission);
    }

    @Override
    public boolean delete(Permission permission) {
        return super.deleteEntity(permission);
    }

    @Override
    public boolean delById(Serializable[] ids) {
        return super.deleteById(ids);
    }

    @Override
    public boolean update(Permission permission) {
        return super.updateEntity(permission);
    }

    @Override
    public Permission findById(Serializable id) {
        return super.findEntityById(id);
    }

    @Override
    public Page<Permission> findPage(int page, int pageSize, Object... args) {
        return null;
    }

    @Override
    public int findMaxSort(Integer parentId){
        StringBuffer sb = new StringBuffer("select max(sort) from sys_permission");

        if(null == parentId){
            sb.append(" where parent_id is NULL");
        }else{
            sb.append(" where parent_id = "+parentId+"");
        }

        Integer result = super.jdbcTemplate.queryForObject(sb.toString(),Integer.class);

        return result == null?0:++result;
    }

    @Override
    public List<Permission> findByParentId(Integer id) {
        return super.findEntity("select * from sys_permission where parent_id = ?",id);
    }

    @Override
    public List<Permission> findFunctionByRole(Integer roleId) {
        return super.findEntity("select p.* from sys_permission p left join sys_role_per r on p.id = r.per_id where r.role_id = ? and p.permission_type = 1 ",roleId);
    }

    @Override
    public boolean findByPath(String path, Integer parentId, Integer id) {
        StringBuffer sb = new StringBuffer("select count(*) from sys_permission where visit_url = '"+path+"'");
        if(null != parentId){
            sb.append(" and parent_id = "+parentId+"");
        }
        if(null != id){
            sb.append(" and id != "+id+"");
        }


        return super.jdbcTemplate.queryForObject(sb.toString(),Integer.class) > 0?false:true;
    }

    @Override
    public List<Map<String,Object>> findTreeList(Integer id, Integer roleId){
        List<Map<String,Object>> results = null;
        String sql = null;

        if (null == id) {
            sql = "select * from sys_permission p where p.parent_id is NULL order by p.sort";

            if (null != roleId) {
                sql = "select p.* from sys_permission p left join sys_role_per r on p.id = r.per_id where p.parent_id is NULL and r.role_id="+roleId+" order by p.sort";
            }
            results = super.jdbcTemplate.queryForList(sql);
        } else{

            sql = "select * from sys_permission p where p.parent_id = ? order by p.sort";

            if (null != roleId) {
                sql = "select p.* from sys_permission p left join sys_role_per r on p.id = r.per_id where p.parent_id = ? and r.role_id="+roleId+" order by p.sort";
            }
            results = super.jdbcTemplate.queryForList(sql,id);
        }

        if(results.size() > 0){

            for(Map<String,Object>  result: results){
                List<Map<String,Object>> childrenResult =  this.findTreeList((int)result.get("id"),roleId);
               if(childrenResult.size() > 0){
                   result.put("children",childrenResult);
               }
            }
        }
        return results;
    }
}
