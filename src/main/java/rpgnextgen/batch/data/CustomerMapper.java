package rpgnextgen.batch.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class CustomerMapper implements RowMapper<Customer> {

	@Override
	public Customer map(ResultSet rs, StatementContext ctx) throws SQLException {
		Customer c = new Customer();
		c.active = rs.getInt("active") == 1;
		c.city = rs.getString("city");
		c.country = rs.getString("country");
		c.discount = rs.getBigDecimal("discount");
		c.id = rs.getInt("id");
		c.company = rs.getString("company");
		c.street = rs.getString("street");

		return c;
	}

}
