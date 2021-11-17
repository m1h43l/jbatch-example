package rpgnextgen.batch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

public class Main {

	private final static Logger logger = Logger.getLogger(Main.class.getName());

	private Weld weld;
	public static String[] COMMAND_LINE_ARGS;

	public Main(String[] commandLineArgs) {
		Main.COMMAND_LINE_ARGS = commandLineArgs;

		weld = new Weld().containerId("rpgnextgen.batch");
	}

	public WeldContainer go() {
		return weld.initialize();
	}

	public static void main(String[] args) {
		System.setProperty("org.jboss.logging.provider", "jdk");

		try {
			InputStream stream = BatchStarter.class.getClassLoader().getResourceAsStream("logging.properties");
			LogManager.getLogManager().readConfiguration(stream);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		try {
			new Main(args).go();
		} catch (Throwable t) {
			logger.log(Level.SEVERE, "Application exited with an exception", t);
			System.exit(2);
		}
	}

	/**
	 * Shut down Weld immediately. Blocks until Weld is completely shut down.
	 */
	public void shutdownNow() {
		weld.shutdown();
	}
}

/**
 * Helper class to register the command line arguments as a bean.
 *
 */
class CommandLineArgumentsProvider {
	@Produces
	@Named("commandLineArguments")
	public List<String> get() {
		return Arrays.asList(Main.COMMAND_LINE_ARGS);
	}
}