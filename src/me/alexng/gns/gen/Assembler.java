package me.alexng.gns.gen;

import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.constructors.BinaryOperationConstructor;
import me.alexng.gns.gen.constructors.BlockConstructor;
import me.alexng.gns.gen.constructors.IfConstructor;
import me.alexng.gns.lexer.Token;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Takes tokenized input and assembles "secondary" tokens; tokens that are not directly parsed from the input text
 * but instead inferred from a sequence of tokens
 */
public class Assembler {

	private static final Constructor[] CONSTRUCTORS = new Constructor[]{
			// TODO: Some operations will need to be processed right to left.
			// TODO: Need an order in which binary operations are resolved.
			new BinaryOperationConstructor(),
			new BlockConstructor(),
			new IfConstructor()
	};

	public static void assemble(LinkedList<Token> tokens) throws ParsingException {
		for (Constructor constructor : CONSTRUCTORS) {
			ListIterator<Token> iterator = tokens.listIterator();
			while (iterator.hasNext()) {
				Token token = iterator.next();
				if (constructor.accepts(token)) {
					iterator.previous();
					constructor.construct(iterator);
				}
			}
		}
	}

	/**
	 * Collects all tokens in between two tokens, matching opening and closing tokens to each other.
	 *
	 * @return A list of tokens in between the matched tokens. Not including the open and close tokens (middle open and close tokens are included)
	 * @throws ParsingException Thrown when a matching end close token is not met.
	 */
	// TODO: Maybe make a util class.
	public static LinkedList<Token> matchTokens(ListIterator<Token> tokens, Token open, Token close) throws ParsingException {
		int depth = 1;
		LinkedList<Token> bucket = new LinkedList<>();
		while (tokens.hasNext()) {
			Token token = tokens.next();
			tokens.remove();
			if (token.equals(open)) {
				depth++;
			} else if (token.equals(close)) {
				if (depth == 1) {
					return bucket;
				} else {
					depth--;
				}
			}
			bucket.add(token);
		}
		// TODO: Better exception
		throw new ParsingException(0, "Matching not found");
	}
}
