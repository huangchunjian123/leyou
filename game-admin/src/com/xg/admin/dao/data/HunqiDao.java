package com.xg.admin.dao.data;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.xg.admin.pojo.Hunqi;

/**
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface HunqiDao {

	public final static String TABLE = "t_hunqi";

	@SQL("select F_hunqi_id as id,F_job as job from " + TABLE)
	public List<Hunqi> getAll();

}
