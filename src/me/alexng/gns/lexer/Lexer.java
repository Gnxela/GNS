package me.alexng.gns.lexer;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.generators.*;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.util.ArrayUtil;

import java.util.LinkedList;

public class Lexer {

	private static final Character[] WHITESPACE_CHARS = new Character[]{' ', '\t'};
	private static final TokenGenerator[] generators = new TokenGenerator[]{
			new InlineCommentGenerator(),
			new MultilineCommentGenerator(),
			new EOLGenerator(),
			new CommaGenerator(),
			new BracketGenerator(),
			new NumberGenerator(),
			new AdditionGenerator(),
			new LessThanGenerator(),
			new EqualGenerator(),
			new AssignGenerator(),
			new KeywordGenerator(),
			new IdentifierGenerator()
	};

	/**
     * Takes an input string and returns a list of tokens that represent that string.
     * Output from this function must be sent to {@link me.alexng.gns.gen.Assembler#assemble(LinkedList)} before being executed.
     *
     * @param file the file from which the input was loaded
     * @throws ParsingException Thrown when the input is invalid.
     */
    public static LinkedList<Token> tokenize(String input, String file) throws ParsingException {
        LinkedList<Token> tokens = new LinkedList<>();
        int index = trimWhitespace(input, 0);
        while (index < input.length()) {
            boolean generated = false;
            for (TokenGenerator generator : generators) {
				int newIndex = generator.accepts(input, index);
				if (newIndex <= index) {
					continue;
				}
				Token token = generator.generate(input, new FileIndex(file, index, newIndex));
				if (token != null) {
					tokens.add(token);
				}
				index = newIndex;
				generated = true;
				break;
			}
			index = trimWhitespace(input, index);
			if (!generated) {
				throw new ParsingException(new FileIndex(file, index, index + 1), "Unexpected character");
			}
		}
		return tokens;
	}

	/**
	 * Skips all whitespace as defined in {@link #WHITESPACE_CHARS}
	 *
	 * @return A new index that has skipped all whitespace.
	 */
	private static int trimWhitespace(String string, int index) {
		while (index < string.length() && ArrayUtil.arrayContains(WHITESPACE_CHARS, string.charAt(index))) {
			index++;
		}
		return index;
	}
}
