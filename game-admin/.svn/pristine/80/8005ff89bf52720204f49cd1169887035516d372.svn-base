package com.xg.admin.dao.system;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;
import net.paoding.rose.jade.core.Identity;

import com.xg.admin.pojo.system.UserData;

/**
 * 用户dao层
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface UserDataDao {

	@SQL("insert into t_user(name,pwd,real_name,role_id,mobile,mail,create_date_time,modify_date_time)values(:t.name,:t.pwd,:t.realName,:t.roleId,:t.mobile,:t.mail,:t.createDateTime,:t.modifyDateTime);")
	public Identity save(@SQLParam("t") UserData t);

	@SQL("update t_user set real_name=:t.realName,name=:t.name,role_id=:t.roleId,mobile=:t.mobile,mail=:t.mail,create_date_time=:t.createDateTime,modify_date_time=:t.modifyDateTime where id=:t.id;")
	public int update(@SQLParam("t") UserData t);

	@SQL("select * from t_user t where t.id=:id")
	public UserData get(@SQLParam("id") int id);

	@SQL("select * from t_user")
	public List<UserData> getAll();

	@SQL("delete from t_user where id=:id")
	public UserData delete(@SQLParam("id") int id);

}
