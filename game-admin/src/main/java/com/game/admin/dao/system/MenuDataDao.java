package com.game.admin.dao.system;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;
import net.paoding.rose.jade.core.Identity;

import com.game.admin.pojo.system.MenuData;

/**
 * 菜单dao层
 * @author huangchunjian
 *
 */
@DAO
public interface MenuDataDao {

	@SQL("insert into t_menu(pid,name,sort,url,type) values(:data.pid,:data.name,:data.sort,:data.url,:data.type);")
	public Identity save(@SQLParam("data") MenuData data);

	@SQL("select id,pid,sort,url,type,name  from t_menu t where t.id=:id")
	public MenuData get(@SQLParam("id") int id);

	@SQL("select id,pid,sort,url,type,name from t_menu")
	public List<MenuData> getMenuAll();

	@SQL("delete from t_menu where id=:data.id")
	public void delete(@SQLParam("data") MenuData data);

	@SQL("update t_menu set name=:data.name,url=:data.url,sort=:data.sort,pid=:data.pid,type=:data.type where id=:data.id;")
	public MenuData update(@SQLParam("data") MenuData data);

}
