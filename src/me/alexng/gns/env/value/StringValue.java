package me.alexng.gns.env.value;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;

public class StringValue extends ObjectValue {

	private String value;

	public StringValue(String value, Scope objectScope) throws RuntimeException {
		super(objectScope.getGlobalScope().classProvider.getLocal("String"), objectScope);
		if (!objectScope.getEnvironment().getOptions().isUsingStandardLib()) {
			throw new RuntimeException(FileIndex.INTERNAL_INDEX, "Can not create strings without stdlib");
		}
		this.value = value;
	}
}
