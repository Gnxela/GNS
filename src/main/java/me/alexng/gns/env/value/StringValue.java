package me.alexng.gns.env.value;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.tokens.IdentifierToken;

public class StringValue extends ObjectValue {

	private static final String STRING_NAME = "String";
	private static final IdentifierToken LENGTH_ID = new IdentifierToken("length", FileIndex.INTERNAL_INDEX);

	private String value;

	public StringValue(int objectId, String value, Scope callingScope) throws RuntimeException {
		super(objectId, null, callingScope.createObjectScope(STRING_NAME));
		this.value = value;
		addBuiltIns();
	}

	public static StringValue createString(String string, Scope callingScope) throws RuntimeException {
		return new StringValue(callingScope.getEnvironment().incrementObjectId(), string, callingScope);
	}

	private void addBuiltIns() throws RuntimeException {
		// TODO: Make immutable
		getObjectScope().variableProvider.setLocal(LENGTH_ID, new NumberValue(value.length()));
	}

	@Override
	public String getJavaValue() {
		return value;
	}
}
