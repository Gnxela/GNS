package me.alexng.gns.env;

import me.alexng.gns.FileIndex;
import me.alexng.gns.GNSException;
import me.alexng.gns.Options;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.lexer.Lexer;
import me.alexng.gns.tokens.BlockToken;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Script {

	private final Options options;
	private String source;
	private String file;

	private LinkedList<Script> requiredScripts;
	private LinkedList<Token> tokens;

	private Script(String source, String file, Options options) {
		this.source = source;
		this.file = file;
		this.options = options;
		this.requiredScripts = new LinkedList<>();
	}

	public Script(String source, Options options) {
		// TODO: Better name than null.
		this(source, "<NULL>", options);
	}

	public Script(File sourceFile, Options options) throws IOException {
		this(FileUtil.readFile(sourceFile), sourceFile.getPath(), options);
	}

	public Script(String source) {
		// TODO: Better name than null.
		this(source, "<NULL>", new Options());
	}

	public Script(File sourceFile) throws IOException {
		this(FileUtil.readFile(sourceFile), sourceFile.getPath(), new Options());
	}

	/**
	 * Parses {@link #source}. Must be run before {@link #run()}.
	 *
	 * @throws ParsingException thrown if {@link #source} is invalid.
	 */
	public void parse() throws GNSException, IOException {
		if (options.isUsingStandardLib()) {
			// TODO: We shouldn't need to compile the standard library every time.
			//  We should serialize the library and just read it.
			String path = ClassLoader.getSystemClassLoader().getResource("me/alexng/gns/lib").getFile();
			File libPackage = new File(path);
			Options stdLibOptions = new Options().setUsingStandardLib(false);
			for (File file : libPackage.listFiles()) {
				Script script = new Script(file, stdLibOptions);
				script.parse();
				requiredScripts.add(script);
			}
		}
		LinkedList<Token> tokens = Lexer.tokenize(source, file);
		Assembler.assemble(tokens);
		this.tokens = tokens;
	}

	/**
	 * Runs the script. {@link #parse()} must be called before this.
	 */
	public void run() throws GNSException {
		Scope globalScope = Scope.createGlobalScope();
		for (Script requiredScript : requiredScripts) {
			// TODO: We need some kind of import statement.
			//  Also need cyclic dependency checking.
			//  Also need to order the execution of dependencies depending on requirements.
			//  I remember writing a solution to this a while ago. Might dig it up.
			requiredScript.run(globalScope);
		}
		run(globalScope);
	}

	private void run(Scope globalScope) throws GNSException {
		BlockToken globalBlock = new BlockToken(tokens, FileIndex.wrap(tokens));
		addBuiltInFunctions(globalScope);
		globalBlock.executeBlockWithScope(globalScope);
	}

	private void addBuiltInFunctions(Scope globalScope) {
		for (NativeFunction nativeFunction : BuiltInFunctions.functions) {
			globalScope.addFunction(nativeFunction);
		}
	}

	public String getSource() {
		return source;
	}
}
