package me.alexng.gns.env;

import me.alexng.gns.FileIndex;
import me.alexng.gns.Options;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * A runtime environment for scripts.
 */
public class Environment {

    private static final FileIndex ENVIRONMENT_INDEX = FileIndex.unindexedFile("<ENV>");

    private final Options options;
    private Scope globalScope;
    private LinkedList<Script> loadedScripts;
    private boolean isSetup = false;

    public Environment(Options options) {
        this.options = options;
        this.loadedScripts = new LinkedList<>();
        this.globalScope = Scope.createGlobalScope();
    }

    public void setup() throws ParsingException, IOException {
        if (isSetup) {
            return;
        }
        isSetup = true;
        if (options.isUsingStandardLib()) {
            // TODO: We shouldn't need to compile the standard library every time.
            //  We should serialize the library and just read it.
            String path = ClassLoader.getSystemClassLoader().getResource("me/alexng/gns/lib").getFile();
            File libPackage = new File(path);
            for (File file : libPackage.listFiles()) {
                Script script = new Script(file);
                script.parse();
                loadedScripts.add(script);
            }
        }
    }

    /**
     * Adds a script to the {@link Environment}, parsing it if it was not already parsed.
     *
     * @param script
     * @throws ParsingException
     */
    public void loadScript(Script script) throws ParsingException {
        if (!isSetup) {
            throw new ParsingException(FileIndex.unindexedFile(script.getFile()), "Environment not set up");
        }
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
        if (!isSetup) {
            throw new RuntimeException(ENVIRONMENT_INDEX, "Environment not set up");
        }
        for (Script script : loadedScripts) {
            script.run(globalScope);
        }
    }

    public Options getOptions() {
        return options;
    }

    public Scope getGlobalScope() {
        return globalScope;
    }
}
