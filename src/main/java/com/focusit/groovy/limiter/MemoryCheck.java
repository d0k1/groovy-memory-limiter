package com.focusit.groovy.limiter;

import java.lang.management.ManagementFactory;
import com.sun.management.ThreadMXBean;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCheck {
	private static final ThreadMXBean threadMXBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
	private static final Map<Long, Long> threadMemoryUsage = new ConcurrentHashMap<>();

	public static void saveThreadMemoryUsage() {
		long threadId = Thread.currentThread().getId();
	
		Long usage = threadMXBean.getThreadAllocatedBytes(threadId);
		threadMemoryUsage.put(threadId, usage);
	}

	public static Long getThreadMemoryUsage(long threadId) {
		return threadMemoryUsage.get(threadId);
	}
	
	public static boolean check() {
		long threadId = Thread.currentThread().getId();
		Long usage = threadMemoryUsage.get(threadId);
		Long current  = threadMXBean.getThreadAllocatedBytes(threadId);
		
		long limit = 20*1024*1024;
		
		if(current-usage>limit) {
			System.err.println("Allocated bytes: "+(current-usage)+". Memory allocation limit("+limit+") is exceeded, terminating");
			return true;
		}
		
		return false;
	}
}
