package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.Value;

// TODO: Do we need this class? This is used to help reduce mainly maths expressions "x * (y + x)  / 2"
//   Why noy just store {@code #expression}?
public class ExpressionToken extends Token {

	private Token expression;

	public ExpressionToken(Token expression, FileIndex fileIndex) {
		super(fileIndex);
		this.expression = expression;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		return expression.execute(scope);
	}

	@Override
	public String toString() {
		return "<Expression " + expression.toString() + ">";
	}
}
