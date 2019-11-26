package me.alexng.gns.gen;

import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.Token;

import java.util.ListIterator;

public interface Constructor {

	boolean accepts(Token token);

	void construct(ListIterator<Token> tokens) throws ParsingException;

}
