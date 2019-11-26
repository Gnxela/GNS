package me.alexng.gns.env;

import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.lexer.Lexer;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.EOLToken;

import java.util.Iterator;
import java.util.LinkedList;

public class Script {

	private String source;
	private LinkedList<Token> tokens;

	public Script(String source) {
		this.source = source;
	}

	/**
	 * Parses {@link #source}. Must be run before {@link #run()}.
	 *
	 * @throws ParsingException thrown if {@link #source} is invalid.
	 */
	public void parse() throws ParsingException {
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
	public void run() throws RuntimeException {
		Scope globalScope = new Scope();
		Iterator<Token> iterator = tokens.iterator();
		while (iterator.hasNext()) {
			Token token = iterator.next();
			if (token instanceof EOLToken) {
				continue;
			}
			token.execute(globalScope);
		}
	}

	public String getSource() {
		return source;
	}
}
