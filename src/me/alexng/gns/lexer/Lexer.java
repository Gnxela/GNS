package me.alexng.gns.lexer;

import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.tokens.*;
import me.alexng.gns.util.ArrayUtil;

import java.util.LinkedList;

public class Lexer {

	private static final String NEW_LINE = "\n";
	private static final Character[] WHITESPACE_CHARS = new Character[]{' ', '\t'};
	private static final TokenGenerator[] generators = new TokenGenerator[]{
			new EOLToken.Generator(),
			new CommaToken.Generator(),
			new BracketToken.Generator(),
			new EqualToken.Generator(),
			new AssignToken.Generator(),
			new KeywordToken.Generator(),
			new NumberToken.Generator(),
			new IdentifierToken.Generator()
	};

	public static LinkedList<Token> tokenize(String input) throws ParsingException {
		LinkedList<Token> tokens = new LinkedList<>();
		int index = trimWhitespace(input, 0);
		while (index < input.length()) {
			boolean generated = false;
			for (TokenGenerator generator : generators) {
				int newIndex = generator.accepts(input, index);
				if (newIndex <= index) {
					continue;
				}
				tokens.add(generator.generate(input, index, newIndex));
				index = newIndex;
				generated = true;
				break;
			}
			index = trimWhitespace(input, index);
			if (!generated) {
				throw new ParsingException(index, "Did not generate"); // TODO: Count the lines.
			}
		}
		return tokens;
	}

	private static int trimWhitespace(String string, int index) {
		while (index < string.length() && ArrayUtil.arrayContains(WHITESPACE_CHARS, string.charAt(index))) {
			index++;
		}
		return index;
	}
}
