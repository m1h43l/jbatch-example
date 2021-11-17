package rpgnextgen.batch.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ibm.jbatch.spi.ExecutorServiceProvider;

public class BatchExecutorServiceProvider implements ExecutorServiceProvider {

	private static ExecutorService executorService;

	public static void shutdown() {
		if (executorService != null)
			executorService.shutdown();
	}

	@Override
	public ExecutorService getExecutorService() {
		executorService = Executors.newFixedThreadPool(5);
		return executorService;
	}

}
