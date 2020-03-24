package me.alexng.gns.env;

import me.alexng.gns.Options;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;

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
		// TODO: This should be done only once when we create the global scope.
		addBuiltInFunctions(globalScope);
		for (Script script : loadedScripts) {
			script.run(globalScope);
		}
	}

	private void addBuiltInFunctions(Scope globalScope) throws RuntimeException {
		for (NativeFunction nativeFunction : BuiltInFunctions.functions) {
			globalScope.functionProvider.set(nativeFunction);
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
