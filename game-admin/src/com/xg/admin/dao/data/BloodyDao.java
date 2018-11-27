package com.xg.admin.dao.data;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.xg.admin.pojo.Bloodyplace;

/**
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface BloodyDao {

	public final static String TABLE = "t_bloody_palace";

	@SQL("select F_id as id from " + TABLE)
	public List<Bloodyplace> getAll();

}
