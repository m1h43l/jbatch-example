package rpgnextgen.batch.jdbi;

import javax.sql.DataSource;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
public class IbmiDatabaseProvider {

	@Inject
	@Named("ibmiDataSource")
	Instance<DataSource> dataSources;

	public Jdbi createJdbi() {
		if (dataSources.isResolvable()) {
			Jdbi jdbi = Jdbi.create(dataSources.get());
			jdbi.installPlugin(new SqlObjectPlugin());
			return jdbi;
		} else
			return null;
	}

	@Produces
	public Jdbi provideJdbi() {
		return createJdbi();
	}

	@Produces
	public Handle provideDatabaseHandle() {
		return createJdbi().open();
	}
}
