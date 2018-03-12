package com.wanda.ffanad.crm.ds;

public class DataSourceContextHolder {
	private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

	public static void write() {
		contextHolder.set(DataSourceType.MASTER);
	}

	public static void read() {
		contextHolder.set(DataSourceType.SLAVE);
	}

	public static DataSourceType getType() {
		DataSourceType type = contextHolder.get();
		return type == null ? DataSourceType.MASTER : type;
	}
	
	public static boolean isEmpty() {
		DataSourceType type = contextHolder.get();
		return type == null;
	}

	public static void clear() {
		contextHolder.remove();
	}
}
