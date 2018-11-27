package com.xg.admin.dao.data;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.xg.admin.pojo.NicknameData;

/**
 * 称呼dao
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface NickNameDao {

	public final static String TABLE = "t_nickname";

	@SQL("select F_id as id,F_nickname as nickname from " + TABLE)
	public List<NicknameData> getAll();

}
