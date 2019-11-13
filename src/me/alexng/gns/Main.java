package me.alexng.gns;

import me.alexng.gns.lexer.Lexer;
import me.alexng.gns.lexer.Token;

import java.util.LinkedList;

public class Main {

	public static void main(String[] args) throws Exception {
		LinkedList<Token> tokens = Lexer.tokenizeLine("   test_keyword    			test_keywordtest_keyword2");
		for (Token token : tokens) {
			System.out.println(token);
		}
	}
}
