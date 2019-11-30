package me.alexng.gns.lexer;

import me.alexng.gns.GNSException;

public interface TokenGenerator {

	/**
	 * Decides whether the generator accepts the input string (from the index).
	 *
	 * @param input The input string.
	 * @param index The index to start parsing from.
	 * @return If accepted, returns the start index of the next token. If rejected, returns the original index (or anything less than it).
	 */
	int accepts(String input, int index);

	/**
	 * Takes the accepted string and converts it into a token.
	 *
	 * @return A token generated from {@code input}[{@code index}:{@code endIndex})
	 */
	Token generate(String input, int startIndex, int endIndex) throws GNSException;

}
