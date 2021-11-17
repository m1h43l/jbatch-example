package rpgnextgen.batch.util;

import jakarta.batch.api.chunk.ItemProcessor;

public class PassThroughItemProcessor implements ItemProcessor {

	@Override
	public Object processItem(Object item) throws Exception {
		return item;
	}

}
