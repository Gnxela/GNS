package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.ReturnedValue;
import me.alexng.gns.tokens.value.Value;

public class ReturnToken extends Token {

	private Token returnedToken;

	public ReturnToken(Token returnedToken, FileIndex fileIndex) {
		super(fileIndex);
		this.returnedToken = returnedToken;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		return new ReturnedValue(returnedToken.execute(scope));
	}

	@Override
	public String toString() {
		return "<Return " + returnedToken.toString() + " >";
	}
}