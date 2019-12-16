package me.alexng.gns.env;

import me.alexng.gns.GNSException;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.lexer.Lexer;
import me.alexng.gns.tokens.BlockToken;
import me.alexng.gns.tokens.Token;

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
