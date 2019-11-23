package me.alexng.gns.gen;

import me.alexng.gns.gen.symbols.AssignSymbol;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.AssignToken;

import java.util.LinkedList;

/**
 * Takes tokenized input and assembles it into symbols which can be run.
 * Provides semantic error checking.
 */
public class Assembler {

	private static final SymbolConstructor[] CONSTRUCTORS = new SymbolConstructor[] {
			new AssignSymbol.Constructor()
	};

	public static void assemble(LinkedList<Token> tokens) {
		for (int i = 0; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			System.out.println(token);
		}
		for (SymbolConstructor constructor : CONSTRUCTORS) {
			int index = constructor.seekSymbol(tokens);
			if (index == -1) {
				continue;
			}
			constructor.construct(tokens.listIterator(index));
		}
		System.out.println();
		System.out.println();
		for (int i = 0; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			System.out.println(token);
		}
	}
}
