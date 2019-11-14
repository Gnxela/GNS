package me.alexng.gns;

import me.alexng.gns.lexer.Lexer;
import me.alexng.gns.lexer.Token;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) throws Exception {
		ArrayList<LinkedList<Token>> tokenizedLines = Lexer.tokenize("i = 0\nif (i == 0) {\n\ti = 1\n}\n");
		for (int i = 0; i < tokenizedLines.size(); i++) {
			LinkedList<Token> tokens = tokenizedLines.get(i);
			System.out.print(i + ":");
			for (Token token : tokens) {
				System.out.println("\t" + token);
			}
		}
	}
}
