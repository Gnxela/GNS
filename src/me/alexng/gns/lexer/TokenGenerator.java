package me.alexng.gns.lexer;

import me.alexng.gns.AmbiguousParsingException;

public interface TokenGenerator {

	/**
	 * TODO: Docs
	 * @param input The input string. All preceding whitespace should be trimmed.
	 * @return The length of the accepted string. Or zero if the string is not accepting.
	 */
	int accepts(String input);

	/**
	 *
	 * @param input A string contain exactly the text that the token will consume.
	 * @return The generated token.
	 */
	Token generate(String input) throws AmbiguousParsingException;

}
