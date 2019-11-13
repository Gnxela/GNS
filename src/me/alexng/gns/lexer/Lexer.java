package me.alexng.gns.lexer;

import me.alexng.gns.AmbiguousParsingException;
import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.tokens.BracketToken;
import me.alexng.gns.lexer.tokens.IdentifierToken;
import me.alexng.gns.lexer.tokens.KeywordToken;
import me.alexng.gns.util.ArrayUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Lexer {

	private static final String NEW_LINE = "\n";
	private static final Character[] WHITESPACE_CHARS = new Character[]{' ', '\t'};
	private static final TokenGenerator[] generators = new TokenGenerator[]{
			new BracketToken.BracketGenerator(),
			new KeywordToken.KeywordGenerator(),
			new IdentifierToken.IdentifierGenerator()
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
				throw new AmbiguousParsingException("Did not generate");
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
