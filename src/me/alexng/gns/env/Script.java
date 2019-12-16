package me.alexng.gns.env;

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
		LinkedList<Token> tokens = Lexer.tokenize(source);
		for (Token token : tokens) {
			System.out.println(token);
		}
		Assembler.assemble(tokens);
		System.out.println();
		for (Token token : tokens) {
			System.out.println(token);
		}

		this.tokens = tokens;
	}

	/**
	 * Runs the script. {@link #parse()} must be called before this.
	 */
	public void run() throws GNSException {
		BlockToken globalBlock = new BlockToken(tokens, tokens.getFirst().getStartIndex(), tokens.getLast().getEndIndex());
		Scope globalScope = Scope.createGlobalScope();
		globalBlock.executeBlockWithScope(globalScope);
		System.out.println(globalScope);
	}

	public String getSource() {
		return source;
	}
}
