package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.KeywordToken;
import me.alexng.gns.tokens.Token;

public class KeywordGenerator implements TokenGenerator {
	@Override
	public int accepts(String input, int index) {
		String trimmedInput = input.substring(index);
		for (Keyword keyword : Keyword.values()) {
			if (trimmedInput.startsWith(keyword.getKeyword())) {
				return index + keyword.getKeyword().length();
			}
		}
		return 0;
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) throws ParsingException {
		String key = fileIndex.substring(input);
		for (Keyword keyword : Keyword.values()) {
			if (keyword.getKeyword().equals(key)) {
				return new KeywordToken(keyword, fileIndex);
			}
		}
		throw new ParsingException(fileIndex, "Keyword unrecognised");
	}
}