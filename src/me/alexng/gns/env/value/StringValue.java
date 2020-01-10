package me.alexng.gns.env.value;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.NativeFunction;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.tokens.Token;

public class StringValue extends ObjectValue {

	private static final NativeFunction ADD_FUNC = new NativeFunction("+") {
		@Override
		public Value executeFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
			// TODO: Use the ExceptionUtil class to make this easier. See BuiltInFunctions for more examples.
			if (values.length != 1) {
				throw new RuntimeException(caller, "Invalid number of arguments. Expected: 1. Got: " + values.length);
			}
			// TODO: Add a way to access the calling object from native function.
			//  Or change how operator functions work.
			Value value = values[0];
			if (value instanceof StringValue) {
				StringValue stringValue = (StringValue) value;
				return new ReturnedValue(new StringValue(stringValue.value + "ASD", parentScope));
			} else {
				// TODO: Add toString function
				return null;
			}
		}
	};

	private String value;

	public StringValue(String value, Scope callingScope) throws RuntimeException {
		super(null, callingScope.createObjectScope());
		this.value = value;
		addBuiltIns();
	}

	private void addBuiltIns() throws RuntimeException {
		getObjectScope().operatorFunctionProvider.set(ADD_FUNC);
	}

	@Override
	public Object getJavaValue() {
		return value;
	}
}
