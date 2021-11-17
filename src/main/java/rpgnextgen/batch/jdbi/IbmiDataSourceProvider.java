package rpgnextgen.batch.jdbi;

import javax.sql.DataSource;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import com.ibm.as400.access.AS400JDBCXADataSource;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
public class IbmiDataSourceProvider {

	@Produces
	@Named("ibmiDataSource")
	public DataSource provideDataSource() {
		Config config = ConfigProvider.getConfig();
		String host = config.getValue("ibmi.host", String.class);
		String user = config.getValue("ibmi.user", String.class);
		String password = config.getValue("ibmi.password", String.class);
		String libraries = config.getValue("ibmi.libraries", String.class);

		AS400JDBCXADataSource ds = new AS400JDBCXADataSource(host, user, password);
		ds.setLibraries(libraries);
		ds.setBlockSize(config.getValue("ibmi.blocksize", Integer.class));
		return ds;

	}
}
