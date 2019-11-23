package me.alexng.gns.env;

import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.lexer.Lexer;
import me.alexng.gns.lexer.Token;

import java.util.ArrayList;
import java.util.LinkedList;

public class Script {

	private String source;

	public Script(String source) {
		this.source = source;
	}

	public void parse() throws ParsingException {
		LinkedList<Token> tokens = Lexer.tokenize(source);
		System.out.println(tokens.toString());
		Assembler.assemble(tokens);
		System.out.println(tokens.toString());
	}

	public void run() {

	}

	public String getSource() {
		return source;
	}
}
