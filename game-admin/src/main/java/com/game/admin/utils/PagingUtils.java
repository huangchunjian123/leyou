package com.game.admin.utils;

import java.util.List;

import org.apache.poi.hssf.record.formula.functions.T;

import com.game.admin.pojo.system.OperateLogData;
import com.game.api.hessian.DataGrid;

/**
 * 分页工具
 * @author huangchunjian
 *
 */
public class PagingUtils {
	
public static  DataGrid getDataGrid(int totalNo,int pageNo,int pageCountNo,List<?> listAll){
		
		
//		if(listAll.size() == 0){
//			return null;
//		}
		
		DataGrid grid = new DataGrid();
		
		int totalPage= totalNo;//总条数
		
		grid.setRows(listAll);
		grid.setTotal(totalPage);
		return grid;
	}
	/**
	 * 分页封装 返回DataGrid
	 * @param pageNo   当前页
	 * @param pageCountNo 当前页显示条数
	 * @param listAll 存放数据的List集合
	 * @return
	 */
	public static  DataGrid getDataGrid(int pageNo,int pageCountNo,List<?> listAll){
		
		
		if(listAll.size() == 0){
			return null;
		}
		int end;//最后的取值
		
		DataGrid grid = new DataGrid();
		
		int totalPage= listAll.size();//总条数
		
		int pageCount = (totalPage+pageCountNo-1)/pageCountNo;//总页数
		
		if(pageNo >= pageCount){
			int overNum = pageNo*pageCountNo-totalPage;//最后页剩下的数量
			if(overNum > 0 ){
				int over = pageCountNo-overNum;
				pageNo = pageCount;
				int start =(pageNo-1)*pageCountNo;
				end= start+over;
				List<?> list =listAll.subList(start, end);
				grid.setRows(list);
				grid.setTotal(totalPage);
				return grid;
			}
		}
		//开始取
		int start =(pageNo-1)*pageCountNo;
		end = pageNo*pageCountNo;
		if(end >= totalPage){
			end = totalPage;
		}
		List<?> list =listAll.subList(start, end);
		grid.setRows(list);
		grid.setTotal(totalPage);
		return grid;
	}
	/**
	 * 分页封装 返回list集合
	 * @param pageNo  当前页
	 * @param pageCountNo 当前页显示记录数
	 * @param listAll 集合
	 * @return
	 */
	public static List<?> getObjectList(int pageNo,int pageCountNo,List<?> listAll){
		
		int end;//最后的取值
		
		int totalPage= listAll.size();//总条数
		
		int pageCount = (totalPage+pageCountNo-1)/pageCountNo;//总页数
		
		if(pageNo >= pageCount){
			int overNum = pageNo*pageCountNo-totalPage;//最后页剩下的数量
			if(overNum > 0 ){
				int over = pageCountNo-overNum;
				pageNo = pageCount;
				int start =(pageNo-1)*pageCountNo;
				end= start+over;
				List<?> list =listAll.subList(start, end);
				return list;
			}
		}
		//开始取
		int start =(pageNo-1)*pageCountNo;
		end = pageNo*pageCountNo;
		if(end >= totalPage){
			end = totalPage;
		}
		List<?> list= listAll.subList(start, end);
		return list;
	}
}
