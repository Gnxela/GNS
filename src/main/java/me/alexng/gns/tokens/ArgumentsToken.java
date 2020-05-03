package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.util.StringUtil;

public class ArgumentsToken extends Token {

	private Token[] arguments;

	public ArgumentsToken(Token[] arguments, FileIndex fileIndex) {
		super(fileIndex);
		this.arguments = arguments;
	}

	public Value[] grabValues(Scope scope) throws RuntimeException {
		Value[] values = new Value[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			values[i] = arguments[i].execute(scope);
		}
		return values;
	}

	public Token[] getArguments() {
		return arguments;
	}

	@Override
	public String toString() {
		return "<Arguments " + StringUtil.unrollArrayInline(arguments) + ">";
	}
}
