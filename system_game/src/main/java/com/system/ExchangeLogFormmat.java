package com.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.system.bean.Log;

public enum ExchangeLogFormmat {

	intance;
	
	private static final Map<String,String> currencyName = new HashMap<>();
	static {
		/**
		 * 金币	yuanbaotype	10200001
			元宝	yuanbaotype	10200002
			绑宝元宝	yuanbaotype	10200003
			精髓	yuanbaotype	10200004
			经验	yuanbaotype	10200005
			造化	yuanbaotype	10200006
			五行	yuanbaotype	10200007
			帮派	yuanbaotype	10200008
			师门	yuanbaotype	10200009
			战场	yuanbaotype	10200010
			竞技场积分	yuanbaotype	10200011
			伙伴积分	yuanbaotype	10200012
			法宝积分	yuanbaotype	10200013
			成就	yuanbaotype	10200014
			帮贡	yuanbaotype	10200015
			天赋	yuanbaotype	10200016
			体力	yuanbaotype	10200017
			充值积分	yuanbaotype	10200018
			抽奖积分	yuanbaotype	10200019
			会武积分	yuanbaotype	10200020
			灵石	yuanbaotype	10200021
			玄晶	yuanbaotype	10200022
			仙玉	yuanbaotype	10200023
			内丹	yuanbaotype	10200024
		 */
		currencyName.put("10200001", "金币");
		currencyName.put("10200002", "元宝");
		currencyName.put("10200003", "绑宝元宝");
		currencyName.put("10200004", "精髓");
		currencyName.put("10200005", "经验");
		currencyName.put("10200006", "造化");
		currencyName.put("10200007", "五行");
		currencyName.put("10200008", "帮派");
		currencyName.put("10200009", "师门");
		currencyName.put("10200010", "战场");
		currencyName.put("10200011", "竞技场积分");
		currencyName.put("10200012", "伙伴积分");
		currencyName.put("10200013", "法宝积分");
		currencyName.put("10200014", "成就");
		currencyName.put("10200015", "帮贡");
		currencyName.put("10200016", "天赋");
		currencyName.put("10200017", "体力");
		currencyName.put("10200018", "充值积分");
		currencyName.put("10200019", "抽奖积分");
		currencyName.put("10200020", "会武积分");
		currencyName.put("10200021", "灵石");
		currencyName.put("10200022", "玄晶");
		currencyName.put("10200023", "仙玉");
		currencyName.put("10200024", "内丹");
		
	}
	
public List<Log> change(List<Log> logs,Integer id,Integer status) {
	if (id == null || "".equals(String.valueOf(id)))
		id =-1 ;
	if (status == null || "".equals(String.valueOf(status)))
		status =-1;
	
	if (id == -1 && status == -1) {
		return logs;
	}
		
		List<Log> list = new ArrayList<>();
		for (Iterator iterator = logs.iterator(); iterator.hasNext();) {
			Log log = (Log) iterator.next();
			
			if (log.getEventType().equals("1005028")) {
				if (id>-1 && status>-1) {
					if (log.getParams5().contains(String.valueOf(id))) {
						if (log.getParams8().equals(String.valueOf(status))) {
							list.add(log);
						}
					}
				}else if (id > -1) {
					if (log.getParams5().contains(String.valueOf(id))) {
						list.add(log);
					}
				}else if (status > -1) {
					if (log.getParams8().equals(String.valueOf(status))) {
						list.add(log);
					}
				}
			}
			if (log.getEventType().equals("1005018")) {
				if (id>-1 && status>-1) {
					if (log.getParams5().contains(String.valueOf(id))) {
						if (log.getParams7().equals(String.valueOf(status))) {
							list.add(log);
						}
					}
				}else if (id > -1) {
					if (log.getParams5().contains(String.valueOf(id)) ) {
						list.add(log);
					}
				}else if (status > -1) {
					if (log.getParams7().equals(String.valueOf(status))) {
						list.add(log);
					}
				}
			}
			if (log.getEventType().equals("1005029")) {
				if (id>-1 && status>-1) {
					if (log.getParams6().contains(String.valueOf(id))) {
						if (log.getParams7().equals(String.valueOf(status))) {
							list.add(log);
						}
					}
				}else if (id > -1) {
					if (log.getParams6().contains(String.valueOf(id)) ) {
						list.add(log);
					}
				}else if (status > -1) {
					if (log.getParams7().equals(String.valueOf(status))) {
						list.add(log);
					}
				}
			}
			
			
			
			
		}
		
		return list;
	}
	public List<Log> change(List<Log> logs,String goodid) {
		
		if (goodid == null || "".equals(goodid))
			return logs;
		
		List<Log> list = new ArrayList<>();
		for (Iterator iterator = logs.iterator(); iterator.hasNext();) {
			Log log = (Log) iterator.next();
			if (log.getParams4().contains(goodid) ||log.getParams5().contains(goodid)) {
					list.add(log);
			}
		}
		
		return list;
		/*for (Iterator iterator = logs.iterator(); iterator.hasNext();) {
			Log log = (Log) iterator.next();
			if (currencyName.containsKey(log.getParams15())) {
				log.setParams15(currencyName.get(log.getParams15()));
			}
		}*/
	}
	
	public List<Log> change(List<Log> logs,int currencytype,int currencymin,int currencymax) {
		List<Log> list = new ArrayList<>();
		if (currencytype<=0)
			return logs;
		for (Iterator iterator = logs.iterator(); iterator.hasNext();) {
			Log log = (Log) iterator.next();
			if (Integer.parseInt(log.getParams15()) == currencytype) {
				if (currencymin>0 && currencymax>0 && currencymin<currencymax) {
					int num = Integer.parseInt(log.getParams13());
					if (num >= currencymin && num <=currencymax) {
						list.add(log);
					}
				} else {
					list.add(log);
				}
			}
		}
		return list;
		/*for (Iterator iterator = logs.iterator(); iterator.hasNext();) {
			Log log = (Log) iterator.next();
			if (currencyName.containsKey(log.getParams15())) {
				log.setParams15(currencyName.get(log.getParams15()));
			}
		}*/
	}
}
