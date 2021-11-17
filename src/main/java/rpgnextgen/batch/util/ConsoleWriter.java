package rpgnextgen.batch.util;

import java.util.List;
import java.util.logging.Logger;

import jakarta.batch.api.chunk.AbstractItemWriter;

public class ConsoleWriter extends AbstractItemWriter {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private int count;

	@Override
	public void writeItems(List<Object> items) throws Exception {
		items.forEach((c) -> {
			System.out.println(c);
			count++;
		});
	}

	@Override
	public void close() throws Exception {
		logger.info("Processed item count: " + count);

		super.close();
	}
}
