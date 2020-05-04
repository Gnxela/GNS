package me.alexng.gns.env;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.FunctionToken;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.Token;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NativeFunction extends FunctionToken {

	private Object bridgeInstance;
	private Method method;

	public NativeFunction(Object bridgeInstance, Method method) {
		super(new IdentifierToken(method.getName(), FileIndex.INTERNAL_INDEX), null, null, FileIndex.INTERNAL_INDEX);
		this.bridgeInstance = bridgeInstance;
		this.method = method;
	}

	public Value executeFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
		try {
			Object[] arguments = new Object[values.length + 1];
			arguments[0] = parentScope.getEnvironment();
			System.arraycopy(values, 0, arguments, 1, values.length);
			return (Value) method.invoke(bridgeInstance, arguments);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(FileIndex.INTERNAL_INDEX, "Failed to invoke bridge method:" + e.getClass() + " " + e.getMessage());
		}
	}
}
