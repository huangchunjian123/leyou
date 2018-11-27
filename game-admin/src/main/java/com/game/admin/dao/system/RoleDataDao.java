package com.game.admin.dao.system;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;
import net.paoding.rose.jade.core.Identity;

import com.game.admin.pojo.system.RoleData;
/**
 * 角色dao层
 * @author huangchunjian
 *
 */
@DAO
public interface RoleDataDao {

	@SQL("insert into t_role(role_name,role_desc) values(:data.roleName,:data.roleDesc);")
	public Identity save(@SQLParam("data") RoleData data);

	@SQL("update t_role set role_name=:data.roleName,role_desc=:data.roleDesc,auth_id=:data.authId where id=:data.id;")
	public void update(@SQLParam("data") RoleData data);

	@SQL("select  id,role_desc as roleDesc,role_name as roleName,auth_id as authId  from t_role where id=:id ")
	public RoleData get(@SQLParam("id") int id);
	
	@SQL("select id,role_desc as roleDesc,role_name as roleName,auth_id as authId from t_role")
	public List<RoleData> getAll();
	
	@SQL("delete from t_role where id=:data.id ")
	public RoleData delete(@SQLParam("data") RoleData data);

}
