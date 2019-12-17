package me.alexng.gns.gen.constructors;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.tokens.*;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class FunctionCallConstructor implements Constructor {

	@Override
	public boolean accepts(Token token) {
		return token instanceof IdentifierToken;
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		IdentifierToken functionIdentifier = (IdentifierToken) tokens.next();

		if (!tokens.hasNext()) {
			return;
		}
		Token expectedOpenBracket = tokens.next();
		if (!BracketToken.ROUND_OPEN.matches(expectedOpenBracket)) {
			tokens.previous();
			return;
		}
		tokens.previous(); // Go back to the open bracket
		tokens.previous(); // Go back to the identifier
		tokens.remove(); // Remove the identifier token

		ArgumentsToken arguments = ArgumentsConstructor.construct(tokens);

		tokens.add(new FunctionCallToken(functionIdentifier, arguments, FileIndex.openClose(functionIdentifier, arguments)));
	}

	private Token[] grabValues(LinkedList<Token> parameterTokens) {
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

	private void checkFormat(LinkedList<Token> parameterTokens) throws ParsingException {
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
