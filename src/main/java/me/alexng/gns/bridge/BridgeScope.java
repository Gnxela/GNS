package me.alexng.gns.bridge;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.NativeFunction;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.IdentifiedToken;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.ValueToken;
import me.alexng.gns.tokens.value.Value;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BridgeScope extends Scope {

	private Object bridgeInstance;
	private Map<String, Field> variables;
	private List<NativeFunction> functions;

	private BridgeScope(Object bridgeInstance, Map<String, Field> variables, List<Method> functions, Environment environment, Scope parentScope, boolean globalScope, boolean objectScope) {
		super(environment, parentScope, globalScope, objectScope);
		this.bridgeInstance = bridgeInstance;
		this.variables = variables;
		this.functions = functions.stream().map(method -> new NativeFunction(bridgeInstance, method)).collect(Collectors.toList());
	}

	public static BridgeScope create(Scope globalScope, Object bridgeInstance, Map<String, Field> variables, List<Method> functions) {
		return new BridgeScope(bridgeInstance, variables, functions, globalScope.getEnvironment(), globalScope, false, true);
	}

	@Override
	public Token getLocal(IdentifierToken identifier) throws RuntimeException {
		String name = identifier.getName();
		Field field = variables.get(name);
		if (field != null) {
			try {
				Value value = (Value) field.get(bridgeInstance);
				if (value != null) {
					return value.wrap();
				} else {
					return ValueToken.NULL;
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(FileIndex.INTERNAL_INDEX, "Failed to access bridge variable: " + name + " " + e.getMessage());
			}
		}
		for (NativeFunction method : functions) {
			if (name.equals(method.getName())) {
				return method;
			}
		}
		return null;
	}

	@Override
	public void setLocal(IdentifiedToken identifiedToken, Token value) throws RuntimeException {
		String name = identifiedToken.getIdentifier().getName();
		Field field = variables.get(name);
		if (field != null) {
			try {
				ValueToken valueToken = (ValueToken) value;
				field.set(bridgeInstance, valueToken.getValue());
				return;
			} catch (IllegalAccessException e) {
				throw new RuntimeException(FileIndex.INTERNAL_INDEX, "Failed to access bridge variable: " + name + " " + e.getMessage());
			}
		}
		for (NativeFunction method : functions) {
			if (name.equals(method.getName())) {
				throw new RuntimeException(FileIndex.INTERNAL_INDEX, "Illegal set of bridge method: " + name);
			}
		}
		throw new RuntimeException(FileIndex.INTERNAL_INDEX, "Identifier not found: " + name);
	}
}
