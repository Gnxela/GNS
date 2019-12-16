package me.alexng.gns.tokens;

import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;

public class AssignToken extends BinaryOperationToken<IdentifierToken, Token> {

	public AssignToken(int startIndex, int endIndex) {
		super(startIndex, endIndex);
	}

	@Override
	public void checkOperands(Token left, Token right) throws ParsingException {
		if (!(left instanceof IdentifierToken)) {
			throw new ParsingException(left, "Invalid type");
		}
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value returnedValue = getRight().execute(scope);
		scope.setVariable(getLeft(), returnedValue);
		return returnedValue;
	}

	@Override
	public String toString() {
		return isBound() ? "<Assign " + getLeft().toString() + " = " + getRight().toString() + ">" : "<Assign UNBOUND>";
	}
}