package me.alexng.gns.env;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.FunctionToken;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.Token;

public abstract class NativeFunction extends FunctionToken {

	public NativeFunction(String name) {
		super(new IdentifierToken(name, FileIndex.INTERNAL_INDEX), null, null, FileIndex.INTERNAL_INDEX);
	}

	public abstract Value executeFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException;
}
