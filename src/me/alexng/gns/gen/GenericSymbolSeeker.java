package me.alexng.gns.gen;

import me.alexng.gns.lexer.Token;

import java.util.LinkedList;

public class GenericSymbolSeeker {

	private Class requiredToken;

	public GenericSymbolSeeker(Class requiredToken) {
		this.requiredToken = requiredToken;
	}

	public int seekSymbol(LinkedList<Token> tokens) {
		int i = 0;
		for (Token token : tokens) {
			if (token.getClass().equals(requiredToken)) {
				return i;
			}
			i++;
		}
		return -1;
	}
}
