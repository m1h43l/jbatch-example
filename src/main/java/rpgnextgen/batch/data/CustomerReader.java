package rpgnextgen.batch.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.logging.Logger;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.result.ResultIterator;

import jakarta.batch.api.BatchProperty;
import jakarta.batch.api.chunk.AbstractItemReader;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;

public class CustomerReader extends AbstractItemReader {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Inject
	@BatchProperty
	private String customer;

	@Inject
	@BatchProperty
	private String date;

	private Handle handle;
	private ResultIterator<Customer> activeCustomers;
	private Customer lastReturnedCustomer;

	@Override
	public Customer readItem() throws Exception {
		lastReturnedCustomer = activeCustomers.hasNext() ? activeCustomers.next() : null;
		return lastReturnedCustomer;
	}

	@Override
	public void open(Serializable checkpoint) throws Exception {
		super.open(checkpoint);

		Integer lastCustomerId = 0;

		if (checkpoint != null) {
			lastCustomerId = (Integer) checkpoint;
			logger.info("Restarting after customer " + lastCustomerId);
		}

		handle = CDI.current().select(Handle.class).get();
		handle.registerRowMapper(new CustomerMapper());
		CustomerDao dao = handle.attach(CustomerDao.class);

		if (customer != null)
			activeCustomers = dao.getCustomer(Integer.valueOf(customer));
		else {
			LocalDate changeDate = date == null ? LocalDate.now() : LocalDate.parse(date);
			logger.info("Change date: " + changeDate.toString());
			activeCustomers = dao.listActiveCustomers(Integer.valueOf(lastCustomerId), changeDate);
		}
	}

	@Override
	public Serializable checkpointInfo() throws Exception {
		logger.finer("Saving reader checkpoint data: " + lastReturnedCustomer);
		return lastReturnedCustomer == null ? null : lastReturnedCustomer.id;
	}

	@Override
	public void close() throws Exception {
		if (handle != null)
			handle.close();

		super.close();
	}
}
