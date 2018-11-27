package com.xg.admin.dao.server;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.xg.admin.pojo.server.ServerData;

/**
 * 服务器列表dao
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface ServerDao {

	public final static String TABLE = "t_vir_servers";

	@SQL("SELECT f_sid as serverId,f_server_ip as serverIp,f_port as serverPort FROM " + TABLE
			+ " WHERE f_is_main = 1 ORDER BY f_sid")
	public List<ServerData> getAll();

}
