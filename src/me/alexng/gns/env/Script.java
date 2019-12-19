package me.alexng.gns.env;

import me.alexng.gns.FileIndex;
import me.alexng.gns.GNSException;
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

	private String source;
	private String file;
	private LinkedList<Token> tokens;

	private Script(String source, String file) {
		this.source = source;
		this.file = file;
	}

	public Script(String source) {
		// TODO: BEtter name than null.
		this(source, "<NULL>");
	}

	public Script(File sourceFile) throws IOException {
		this(FileUtil.readFile(sourceFile), sourceFile.getPath());
	}

	/**
	 * Parses {@link #source}. Must be run before {@link #run()}.
	 *
	 * @throws ParsingException thrown if {@link #source} is invalid.
	 */
	public void parse() throws GNSException {
		LinkedList<Token> tokens = Lexer.tokenize(source, file);
		Assembler.assemble(tokens);
		this.tokens = tokens;
	}

	/**
	 * Runs the script. {@link #parse()} must be called before this.
	 */
	public void run() throws GNSException {
		BlockToken globalBlock = new BlockToken(tokens, FileIndex.wrap(tokens));
		Scope globalScope = Scope.createGlobalScope();
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
