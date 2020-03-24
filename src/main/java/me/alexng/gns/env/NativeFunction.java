package me.alexng.gns.env;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.FunctionToken;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.Token;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NativeFunction extends FunctionToken {

	private Object bridgeInstance;
	private Method method;

	public NativeFunction(Object bridgeIntance, Method method) {
		super(new IdentifierToken(method.getName(), FileIndex.INTERNAL_INDEX), null, null, FileIndex.INTERNAL_INDEX);
		this.bridgeInstance = bridgeIntance;
		this.method = method;
	}

	public Value executeFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
		try {
			return (Value) method.invoke(bridgeInstance, (Object[]) values);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(FileIndex.INTERNAL_INDEX, "Failed to invoke bridge method:" + e.getClass() + " " + e.getMessage());
		}
	}
}
