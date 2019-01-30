package com.system.service.impl;

import java.util.Comparator;

import com.system.bean.Log;

public class LogComparator implements Comparator<Log> {

	@Override
	public int compare(Log o1, Log o2) {
		if (o1.getRoleid().hashCode()>o2.getRoleid().hashCode()) {
			return -1;
		}
		if (o1.getRoleid().hashCode()<o2.getRoleid().hashCode()) {
			return 1;
		}
		return o1.getLogtime().compareTo(o2.getLogtime());
	}


}
