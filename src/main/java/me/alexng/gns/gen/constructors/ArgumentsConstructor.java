package me.alexng.gns.gen.constructors;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.tokens.ArgumentsToken;
import me.alexng.gns.tokens.BracketToken;
import me.alexng.gns.tokens.CommaToken;
import me.alexng.gns.tokens.Token;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ArgumentsConstructor {

	public static ArgumentsToken construct(ListIterator<Token> tokens) throws ParsingException {
		LinkedList<Token> parameterTokens = Assembler.matchTokens(tokens, BracketToken.ROUND_OPEN, BracketToken.ROUND_CLOSED);
		FileIndex fileIndex = FileIndex.wrap(parameterTokens);
		parameterTokens.removeFirst();
		parameterTokens.removeLast();
		if (parameterTokens.size() > 0) {
			Assembler.assemble(parameterTokens);
		}
		checkFormat(parameterTokens);
		Token[] arguments = grabValues(parameterTokens);
		return new ArgumentsToken(arguments, fileIndex);
	}

	private static Token[] grabValues(LinkedList<Token> parameterTokens) {
		List<Token> identifiers = new LinkedList<>();
		boolean isExpectingComma = false;
		for (Token token : parameterTokens) {
			if (!isExpectingComma) {
				identifiers.add(token);
			}
			isExpectingComma = !isExpectingComma;
		}
		return identifiers.toArray(new Token[0]);
	}

	private static void checkFormat(LinkedList<Token> parameterTokens) throws ParsingException {
		boolean isExpectingComma = false;
		for (Token token : parameterTokens) {
			if (isExpectingComma) {
				if (!(token instanceof CommaToken)) {
					throw new ParsingException(token, "Expected comma");
				}
			}
			isExpectingComma = !isExpectingComma;
		}
	}
}
