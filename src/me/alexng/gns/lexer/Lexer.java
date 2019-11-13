package me.alexng.gns.lexer;

import me.alexng.gns.lexer.tokens.KeywordToken;
import me.alexng.gns.util.ArrayUtil;

import java.util.LinkedList;

public class Lexer {

	private static final Character[] WHITESPACE_CHARS = new Character[]{' ', '\t'};
	private static final TokenGenerator[] generators = new TokenGenerator[]{
			new KeywordToken.KeywordGenerator()
	};

	public static LinkedList<Token> tokenizeLine(String line) throws Exception {
		LinkedList<Token> tokens = new LinkedList<>();
		while (!line.isEmpty()) {
			line = trimWhitespace(line);
			boolean generated = false;
			for (TokenGenerator generator : generators) {
				int index = generator.accepts(line);
				if (index == 0) {
					continue;
				}
				String tokenString = line.substring(0, index);
				line = line.substring(index);
				tokens.add(generator.generate(tokenString));
				generated = true;
				break;
			}
			if (!generated) {
				// TODO: Create an exception
				throw new Exception("Did not generate");
			}
		}
		return tokens;
	}

	private static String trimWhitespace(String string) {
		int i = 0;
		while (ArrayUtil.arrayContains(WHITESPACE_CHARS, string.charAt(i))) {
			i++;
		}
		return string.substring(i);
	}
}
