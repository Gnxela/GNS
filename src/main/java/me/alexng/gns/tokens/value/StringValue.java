package me.alexng.gns.tokens.value;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.IdentifierToken;

public class StringValue extends ObjectValue {

	private static final String STRING_NAME = "String";
	private static final IdentifierToken LENGTH_ID = new IdentifierToken("length", FileIndex.INTERNAL_INDEX);

	private String value;

	public StringValue(String value, Scope callingScope, FileIndex fileIndex) throws RuntimeException {
		super(null, Scope.createObjectScope(callingScope.getGlobalScope()), fileIndex);
		this.value = value;
		addBuiltIns();
	}

	public static StringValue createString(String string, Scope callingScope) throws RuntimeException {
		return new StringValue(string, callingScope, FileIndex.INTERNAL_INDEX);
	}

	private void addBuiltIns() throws RuntimeException {
		// TODO: Make immutable
		getObjectScope().set(LENGTH_ID, new NumberValue(value.length(), FileIndex.INTERNAL_INDEX));
	}

	@Override
	public String getJavaValue() {
		return value;
	}
}
