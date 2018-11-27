package com.xg.admin.dao.data;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.xg.admin.pojo.Goods;

/**
 * 普通道具dao层
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
@DAO
public interface GoodsDao {

	public final static String TABLE = "t_goodmodel";

	@SQL("select F_id as goodsId,F_name as name from " + TABLE + " where F_id=:goodsId")
	public Goods get(@SQLParam("goodsId") int goodsId);

	@SQL("select F_id as goodsId,F_name as name from " + TABLE
			+ " where 1=1 #if(:goodsName != ''){ and F_name like :goodsName} limit :offset,:pagesize")
	public List<Goods> getGoodsByname(@SQLParam("goodsName") String goodsName, @SQLParam("offset") int offset,
			@SQLParam("pagesize") int pagesize);

	@SQL("select F_id as goodsId,F_name as name from " + TABLE)
	public List<Goods> getAll();

}
