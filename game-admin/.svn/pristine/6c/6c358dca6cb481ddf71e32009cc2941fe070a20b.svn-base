package com.xg.admin.dao.data;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.xg.admin.pojo.Npc;

/**
 * npc dao层
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface NpcDao {

	public final static String TABLE = "t_npc";

	@SQL("select f_id as npcId,f_name as name from " + TABLE
			+ " where 1=1 #if(:npcName != ''){ and F_name like :npcName} limit :offset,:pagesize")
	public List<Npc> getNpcByName(@SQLParam("npcName") String npcName, @SQLParam("offset") int offset,
			@SQLParam("pagesize") int pagesize);

	@SQL("select f_id as npcId,f_name as name from " + TABLE)
	public List<Npc> getAll();

}
