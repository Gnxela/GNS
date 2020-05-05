package me.alexng.gns.env;

import me.alexng.gns.Options;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.bridge.BridgeMapper;
import me.alexng.gns.tokens.TemplateToken;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

/**
 * A runtime environment for scripts.
 */
public class Environment {

	private final Options options;
	public final OutputStream stdout;
	public final InputStream stdin;
	private Scope globalScope;
	private LinkedList<Script> loadedScripts;

	public Environment(Options options) {
		this.options = options;
		this.stdout = options.getStdout();
		this.stdin = options.getStdin();
		this.loadedScripts = new LinkedList<>();
		this.globalScope = Scope.createGlobalScope(this);
		setup();
	}

	private void setup() {
		if (options.isUsingSys()) {
			try {
				addBridge(Sys.class);
			} catch (RuntimeException | ParsingException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	/**
	 * Creates a bridge, using {@code bridgeClass} as the mapping.
	 *
	 * @param bridgeClass The class to be mapped as a bridge.
	 */
	public void addBridge(Class<?> bridgeClass) throws RuntimeException, ParsingException {
		TemplateToken bridgedClass = BridgeMapper.mapBridge(bridgeClass);
		globalScope.set(bridgedClass, bridgedClass);
	}

	/**
	 * Adds a script to the {@link Environment}, parsing it if it was not already parsed.
	 *
	 * @param script
	 * @throws ParsingException
	 */
	public void loadScript(Script script) throws ParsingException {
		if (!script.isParsed()) {
			script.parse();
		}
		loadedScripts.add(script);
	}

	/**
	 * Runs all the loaded scripts.
	 * The order in which they are executed is the order in which they were added.
	 * TODO: We need to change above, should be executed in order added unless dependency requires otherwise
	 */
	public void runScripts() throws RuntimeException {
		for (Script script : loadedScripts) {
			script.run(globalScope);
		}
	}

	public Scope getGlobalScope() {
		return globalScope;
	}
}
