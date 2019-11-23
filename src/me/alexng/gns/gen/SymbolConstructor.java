package me.alexng.gns.gen;

import me.alexng.gns.lexer.Token;

import java.util.LinkedList;
import java.util.ListIterator;

public interface SymbolConstructor {

	int seekSymbol(LinkedList<Token> tokens);

	void construct(ListIterator<Token> tokens);

}
