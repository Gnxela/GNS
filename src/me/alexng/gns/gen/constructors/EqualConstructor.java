package me.alexng.gns.gen.constructors;

import me.alexng.gns.gen.Constructor;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.AssignToken;
import me.alexng.gns.lexer.tokens.EqualToken;

import java.util.ListIterator;

public class EqualConstructor implements Constructor {

	@Override
	public boolean accepts(Token token) {
		return token instanceof EqualToken && !((EqualToken) token).isBound();
	}

	@Override
	public void construct(ListIterator<Token> tokens) {
		EqualToken assignToken = (EqualToken) tokens.next();
		tokens.previous();
		Token left = tokens.previous();
		tokens.remove();
		tokens.next(); // Skip over the assign token
		Token right = tokens.next();
		tokens.remove();
		assignToken.bind(left, right);
	}
}
