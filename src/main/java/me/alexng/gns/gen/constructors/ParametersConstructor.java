package me.alexng.gns.gen.constructors;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Assembler;
import me.alexng.gns.tokens.*;

import java.util.*;

public class ParametersConstructor {

	public static ParametersToken construct(ListIterator<Token> tokens) throws ParsingException {
		LinkedList<Token> parameterTokens = Assembler.matchTokens(tokens, BracketToken.ROUND_OPEN, BracketToken.ROUND_CLOSED);
		FileIndex fileIndex = FileIndex.wrap(parameterTokens);
		parameterTokens.removeFirst();
		parameterTokens.removeLast();
		checkFormat(parameterTokens);
		IdentifierToken[] parameters = grabIdentifiers(parameterTokens);
		checkCollision(parameters);

		return new ParametersToken(parameters, fileIndex);
	}

	private static void checkCollision(IdentifierToken[] parameters) throws ParsingException {
		Set<String> nameSet = new HashSet<>();
		for (IdentifierToken identifierToken : parameters) {
			if (!nameSet.add(identifierToken.getName())) {
				throw new ParsingException(identifierToken, "Identifier repeated");
			}
		}
	}

	private static IdentifierToken[] grabIdentifiers(LinkedList<Token> parameterTokens) throws ParsingException {
		List<IdentifierToken> identifiers = new LinkedList<>();
		boolean isExpectingComma = false;
		for (Token token : parameterTokens) {
			if (!isExpectingComma) {
				identifiers.add(Assembler.castTo(IdentifierToken.class, token));
			}
			isExpectingComma = !isExpectingComma;
		}
		return identifiers.toArray(new IdentifierToken[0]);
	}

	private static void checkFormat(LinkedList<Token> parameterTokens) throws ParsingException {
		boolean isExpectingComma = false;
		for (Token token : parameterTokens) {
			if (isExpectingComma) {
				if (!(token instanceof CommaToken)) {
					throw new ParsingException(token, "Expected comma");
				}
			} else {
				if (!(token instanceof IdentifierToken)) {
					throw new ParsingException(token, "Expected identifier");
				}
			}
			isExpectingComma = !isExpectingComma;
		}
	}
}
