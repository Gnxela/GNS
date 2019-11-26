package me.alexng.gns.gen.constructors;

import me.alexng.gns.ParsingException;
import me.alexng.gns.gen.Constructor;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.AssignToken;
import me.alexng.gns.lexer.tokens.IdentifierToken;

import java.util.ListIterator;

public class AssignConstructor implements Constructor {

	@Override
	public boolean accepts(Token token) {
		return token instanceof AssignToken && !((AssignToken) token).isBound();
	}

	@Override
	public void construct(ListIterator<Token> tokens) throws ParsingException {
		AssignToken assignToken = (AssignToken) tokens.next();
		tokens.previous();
		Token variable = tokens.previous();
		if (!(variable instanceof IdentifierToken)) {
			throw new ParsingException(variable.getStartIndex(), "Invalid assignment");
		}
		tokens.remove();
		tokens.next(); // Skip over the assign token
		Token value = tokens.next();
		tokens.remove();
		assignToken.bind((IdentifierToken) variable, value);
	}
}
