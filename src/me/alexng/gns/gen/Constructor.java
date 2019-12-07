package me.alexng.gns.gen;

import me.alexng.gns.ParsingException;
import me.alexng.gns.tokens.Token;

import java.util.ListIterator;

public interface Constructor {

	/**
	 * Decides whether this constructor accepts {@code token}.
	 *
	 * @return True if this constructor accepts.
	 */
	boolean accepts(Token token);

	/**
	 * Constructs the new token(s) and modifies {@code tokens} in place.
	 *
	 * @param tokens an iterator of tokens, with {@link ListIterator#next()} as the tokens that was passed to {@link #accepts(Token)}.
	 * @throws ParsingException thrown when the input is invalid.
	 */
	void construct(ListIterator<Token> tokens) throws ParsingException;

}
