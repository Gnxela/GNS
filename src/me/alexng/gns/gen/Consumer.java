package me.alexng.gns.gen;

import me.alexng.gns.lexer.Token;

import java.util.List;
import java.util.ListIterator;

public interface Consumer {

	List<Token> consume(ListIterator<Token> tokens);

}
