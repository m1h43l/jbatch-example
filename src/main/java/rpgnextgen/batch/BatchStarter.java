package rpgnextgen.batch;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.jboss.weld.environment.se.events.ContainerInitialized;

import com.ibm.jbatch.spi.BatchSPIManager;

import jakarta.batch.runtime.BatchRuntime;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import rpgnextgen.batch.util.BatchExecutorServiceProvider;

@Singleton
public class BatchStarter {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public void startImport(@Observes ContainerInitialized event,
			@Named("commandLineArguments") List<String> parameters) {
		logger.fine("Started with " + parameters.toString());

		if (parameters.size() <= 1) {
			logger.severe("Not enough arguments provided.");
			return;
		}

		Properties batchProperties;

		switch (parameters.get(0)) {
			case "start":
				String batchName = parameters.get(1);
				batchProperties = parseBatchProperties(parameters.subList(2, parameters.size()));
				runBatch(batchName, batchProperties);
				break;

			case "restart":
				long execId = Integer.valueOf(parameters.get(1));
				batchProperties = parseBatchProperties(parameters.subList(2, parameters.size()));
				restartBatch(execId, batchProperties);
				break;

			default:
				logger.info("No action parameter passed as an argument (start, restart, ...)");
				break;
		}
	}

	private void runBatch(String batchName, Properties batchProperties) {
		BatchExecutorServiceProvider executorServiceProvider = new BatchExecutorServiceProvider();

		BatchSPIManager batchSPIManager = BatchSPIManager.getInstance();
		batchSPIManager.registerPlatformMode(BatchSPIManager.PlatformMode.SE);
		batchSPIManager.registerExecutorServiceProvider(executorServiceProvider);

		long jobExecId = BatchRuntime.getJobOperator().start(batchName, batchProperties);
		logger.info("Starting batch job with id " + jobExecId);
	}

	private void restartBatch(long execId, Properties batchProperties) {
		BatchExecutorServiceProvider executorServiceProvider = new BatchExecutorServiceProvider();

		BatchSPIManager batchSPIManager = BatchSPIManager.getInstance();
		batchSPIManager.registerPlatformMode(BatchSPIManager.PlatformMode.SE);
		batchSPIManager.registerExecutorServiceProvider(executorServiceProvider);

		long jobExecId = BatchRuntime.getJobOperator().restart(execId, batchProperties);
		logger.info("Restarting batch job with id " + jobExecId);
	}

	private Properties parseBatchProperties(List<String> parameters) {
		Properties properties = new Properties();

		for (int i = 0; i < parameters.size(); i += 2) {
			String s = parameters.get(i);
			if (s.startsWith("--") && s.length() > 2 && i % 2 == 0 && parameters.size() > i) {
				logger.finer("Adding batch property " + s + " with value " + parameters.get(i + 1));
				properties.put(parameters.get(i).substring(2), parameters.get(i + 1));
			} else {
				logger.finer("Skipping argument " + parameters.get(i));
			}
		}

		return properties;
	}
}
