package rpgnextgen.batch.data;

import java.time.LocalDate;

import org.jdbi.v3.core.result.ResultIterator;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface CustomerDao {

	@SqlQuery("SELECT id, company, street, city, country, discount, active FROM customer "
			+ "WHERE active = 1 AND id > :offset AND changeDate >= :changeDate")
	ResultIterator<Customer> listActiveCustomers(@Bind("offset") int offset, @Bind("changeDate") LocalDate changeDate);

	@SqlQuery("SELECT id, company, street, city, country, discount, active FROM customer WHERE id = :id")
	ResultIterator<Customer> getCustomer(@Bind("id") int id);
}
