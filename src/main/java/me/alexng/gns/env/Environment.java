package me.alexng.gns.env;

import me.alexng.gns.Options;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.bridge.BridgeMapper;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.tokens.ClassToken;

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
	private int currentObjectId = 1;

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
			} catch (ParsingException e) {
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
	public void addBridge(Class<?> bridgeClass) throws ParsingException {
		ClassToken bridgedClass = BridgeMapper.mapBridge(bridgeClass);
		try {
			globalScope.classProvider.setLocal(bridgedClass);
		} catch (RuntimeException e) {
			// Our class provider will never throw this exception.
			e.printStackTrace();
		}
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

	/**
	 * Increments the object id counter and returns a unique object id.
	 */
	public int incrementObjectId() {
		synchronized (this) {
			return currentObjectId++;
		}
	}
}
