package me.alexng.gns.env;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
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
	private boolean isParsed = false;

	private Script(String source, String file) {
		this.source = source;
		this.file = file;
	}

	public Script(String source) {
		this(source, "<NULL>");
	}

	public Script(File sourceFile) throws IOException {
		this(FileUtil.readFile(sourceFile), sourceFile.getPath());
	}

	/**
	 * Parses {@link #source}. Must be run before {@link #run}.
	 *
	 * @throws ParsingException thrown if {@link #source} is invalid.
	 */
	public void parse() throws ParsingException {
		isParsed = true;
		LinkedList<Token> tokens = Lexer.tokenize(source, file);
		Assembler.assemble(tokens);
		this.tokens = tokens;
	}

	/**
	 * Runs the script. {@link #parse()} must be called before this.
	 */
	public void run(Scope globalScope) throws RuntimeException {
		if (!isParsed) {
			throw new RuntimeException(new FileIndex(file, 0, 0), "Script executed before parsed");
		}
		BlockToken globalBlock = new BlockToken(tokens, FileIndex.wrap(tokens));
		globalBlock.executeBlockWithScope(globalScope);
	}

	public String getSource() {
		return source;
	}

	public String getFile() {
		return file;
	}

	public boolean isParsed() {
		return isParsed;
	}
}
