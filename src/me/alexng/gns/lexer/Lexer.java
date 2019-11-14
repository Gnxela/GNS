package me.alexng.gns.lexer;

import me.alexng.gns.AmbiguousParsingException;
import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.tokens.*;
import me.alexng.gns.util.ArrayUtil;

import java.util.ArrayList;
import java.util.LinkedList;

public class Lexer {

	private static final String NEW_LINE = "\n";
	private static final Character[] WHITESPACE_CHARS = new Character[]{' ', '\t'};
	private static final TokenGenerator[] generators = new TokenGenerator[] {
			new CommaToken.Generator(),
			new BracketToken.Generator(),
			new AssignToken.Generator(),
			new EqualToken.Generator(),
			new KeywordToken.Generator(),
			new NumberToken.Generator(),
			new IdentifierToken.Generator()
	};

	public static ArrayList<LinkedList<Token>> tokenize(String input) throws ParsingException {
		ArrayList<LinkedList<Token>> tokenizedLines = new ArrayList<>();
		String[] lines = input.split(NEW_LINE);
		for (int index = 0; index < lines.length; index++) {
			String line = lines[index];
			try {
				tokenizedLines.add(tokenizeLine(line));
			} catch (AmbiguousParsingException e) {
				throw new ParsingException(index + 1, e.getMessage());
			}
		}
		return tokenizedLines;
	}

	private static LinkedList<Token> tokenizeLine(String line) throws AmbiguousParsingException {
		LinkedList<Token> tokens = new LinkedList<>();
		line = trimWhitespace(line);
		while (!line.isEmpty()) {
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
				throw new AmbiguousParsingException("Did not generate");
			}
			line = trimWhitespace(line);
		}
		return tokens;
	}

	private static String trimWhitespace(String string) {
		int i = 0;
		while (i < string.length() && ArrayUtil.arrayContains(WHITESPACE_CHARS, string.charAt(i))) {
			i++;
		}
		return string.substring(i);
	}
}
