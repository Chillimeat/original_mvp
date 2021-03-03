package indi.ayun.mingwork_all.utils.java;


import indi.ayun.mingwork_all.mlog.MLog;

@SuppressWarnings("deprecation")
public class ThreadUtils {
	public static final int DEFAULT_THREAD_POOL_SIZE = getSysDefaultThreadPoolSize();

	/**
	 * 获得系统配置相符的线程池大小
	 * @return Integer 返回系统配置相符合线程大小
	 */
	public static Integer getSysDefaultThreadPoolSize() {
		Integer availableProcessors = 2 * Runtime.getRuntime().availableProcessors() + 1;
		availableProcessors = availableProcessors > 8 ? 8 : availableProcessors;
		MLog.i("SysInfoUtils-->>getSysDefaultThreadPoolSize",  availableProcessors + "");
		return availableProcessors;
	}









}
