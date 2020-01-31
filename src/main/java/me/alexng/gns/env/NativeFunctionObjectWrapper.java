package me.alexng.gns.env;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.FunctionToken;
import me.alexng.gns.tokens.Token;

/**
 * Wraps a {@link me.alexng.gns.env.NativeFunction} and will pass the calling object as a parameter.
 */
public class NativeFunctionObjectWrapper<T extends Value> extends FunctionToken {

	private NativeFunction nativeFunction;
	private T object;

	public NativeFunctionObjectWrapper(T object, NativeFunction nativeFunction) {
		super(nativeFunction.getIdentifier(), null, null, nativeFunction.getFileIndex());
		this.nativeFunction = nativeFunction;
		this.object = object;
	}

	@Override
	public Value executeFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
		Value[] newValues = new Value[values.length + 1];
		newValues[0] = object;
		System.arraycopy(values, 0, newValues, 1, values.length);
		return nativeFunction.executeFunction(caller, parentScope, newValues);
	}
}
