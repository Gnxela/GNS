package me.alexng.gns.lexer.tokens;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.lexer.BindableToken;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class AssignToken extends BindableToken {

	private IdentifierToken variable;
	private Token value;

	AssignToken(int startIndex, int endIndex) {
		super(startIndex, endIndex);
	}

	public void bind(IdentifierToken variable, Token value) {
		this.bind();
		this.variable = variable;
		this.value = value;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value returnedValue = value.execute(scope);
		scope.setVariable(variable.getName(), returnedValue);
		return returnedValue;
	}

	@Override
	public String toString() {
		return isBound() ? "<Assign " + variable.toString() + " = " + value.toString() + ">" : "<Assign UNBOUND>";
	}

	public static class Generator implements TokenGenerator {

		private final char CHAR = '=';

		@Override
		public int accepts(String input, int index) {
			return index + (input.charAt(index) == CHAR ? 1 : 0);
		}

		@Override
		public Token generate(String input, int startIndex, int endIndex) {
			return new AssignToken(startIndex, endIndex);
		}
	}
}