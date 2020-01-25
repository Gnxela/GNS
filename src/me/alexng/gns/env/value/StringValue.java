package me.alexng.gns.env.value;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.NativeFunction;
import me.alexng.gns.env.NativeFunctionObjectWrapper;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.util.ExceptionUtil;

public class StringValue extends RawObjectValue {

	private static final IdentifierToken LENGTH_ID = new IdentifierToken("length", FileIndex.INTERNAL_INDEX);

	private static final NativeFunction OP_ADD_FUNC = new NativeFunction("+") {
		@Override
		public Value executeFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
			if (values.length != 2) {
				throw ExceptionUtil.createRuntimeExpected("Invalid number of arguments", caller, 2, values.length);
			}
			StringValue stringValueA = (StringValue) values[0];
			Value valueB = values[1];
			if (valueB instanceof StringValue) {
				StringValue stringValueB = (StringValue) valueB;
				return new ReturnedValue(StringValue.createString(stringValueA.value + stringValueB.value, parentScope));
			} else {
				// TODO: Add toString function
				return null;
			}
		}
	};

	private String value;

	public StringValue(int objectId, String value, Scope callingScope) throws RuntimeException {
		super(objectId, null, callingScope.createObjectScope("String"));
		this.value = value;
		addBuiltIns();
	}

	public static StringValue createString(String string, Scope callingScope) throws RuntimeException {
		return new StringValue(callingScope.getEnvironment().incrementObjectId(), string, callingScope);
	}

	private void addBuiltIns() throws RuntimeException {
		// TODO: Make immutable
		getObjectScope().variableProvider.setLocal(LENGTH_ID, new NumberValue(value.length()));
		getObjectScope().operatorFunctionProvider.set(new NativeFunctionObjectWrapper<>(this, OP_ADD_FUNC));
	}

	@Override
	public Object getJavaValue() {
		return value;
	}
}
