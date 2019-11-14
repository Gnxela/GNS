package me.alexng.gns.env;

import me.alexng.gns.ParsingException;
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
		ArrayList<LinkedList<Token>> tokenizedLines = Lexer.tokenize(source);
		for (int i = 0; i < tokenizedLines.size(); i++) {
			LinkedList<Token> tokens = tokenizedLines.get(i);
			System.out.print(i + ":");
			for (Token token : tokens) {
				System.out.println("\t" + token);
			}
		}
	}

	public void run() {

	}

	public String getSource() {
		return source;
	}
}
