package com.xg.admin.dao.data;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.xg.admin.pojo.GemstoneData;

/**
 * 宝石dao层
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface GemstoneDao {

	public final static String TABLE = "t_gemstone";

	@SQL("select F_id as id,F_name as name from " + TABLE)
	public List<GemstoneData> getAll();

}
