package me.alexng.gns.env;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.Token;

public class BuiltInFunctions {

    private static final NativeFunction print = new NativeFunction("print") {
        @Override
        public Value executeFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
            if (values.length != 1) {
                throw new RuntimeException(caller, "Invalid number of arguments. Expected: 1. Got: " + values.length);
            }
            Value value = values[0];
            String output = null;
            switch (value.getType()) {
                case NULL:
                    output = "null";
                    break;
                case BOOLEAN:
                case NUMBER:
                    output = value.getValue().toString();
                    break;
                case OBJECT:
                    ObjectValue object = (ObjectValue) value;
                    output = object.toString(); // TODO: Call a toString function in the object
                    break;
            }
            System.out.println(output);
            return null;
        }
    };

    public static final NativeFunction[] functions = new NativeFunction[]{print};
}
