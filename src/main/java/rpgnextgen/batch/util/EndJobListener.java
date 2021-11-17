package rpgnextgen.batch.util;

import java.util.logging.Logger;

import jakarta.batch.api.listener.AbstractJobListener;

public class EndJobListener extends AbstractJobListener {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public void afterJob() throws Exception {
		logger.fine("Ending application");

		BatchExecutorServiceProvider.shutdown();

		super.afterJob();
	}
}
