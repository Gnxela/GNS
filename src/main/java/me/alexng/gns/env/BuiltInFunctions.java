package me.alexng.gns.env;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.ObjectValue;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.util.ExceptionUtil;

public class BuiltInFunctions {

	private static final NativeFunction print = new NativeFunction("print") {
		@Override
		public Value executeFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
			if (values.length != 1) {
				throw ExceptionUtil.createRuntimeExpected("Invalid number of arguments", caller, 1, values.length);
			}
			Value value = values[0];
			String output = null;
			switch (value.getType()) {
				case NULL:
					output = "null";
					break;
				case BOOLEAN:
				case NUMBER:
					output = value.getJavaValue().toString();
					break;
				case OBJECT:
					ObjectValue rawObject = (ObjectValue) value;
					output = rawObject.getObjectScope().nameProvider.getName() + "#" + rawObject.getObjectId();
					// TODO: Call a toString function in the object
					break;
			}
			// TODO: Fix this
			// parentScope.getEnvironment().stdout.write(output.getBytes());
			return null;
		}
	};

	public static final NativeFunction[] functions = new NativeFunction[]{print};
}
