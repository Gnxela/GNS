package me.alexng.gns.bridge;

import me.alexng.gns.env.scope.VariableProvider;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.IdentifiedToken;

import java.lang.reflect.Field;
import java.util.Map;

public class BridgeVariableProvider extends VariableProvider {

	private Object bridgeInstance;
	private Map<String, Field> variables;

	public BridgeVariableProvider(Object bridgeInstance, Map<String, Field> variables) {
		super(null);
		this.bridgeInstance = bridgeInstance;
		this.variables = variables;
	}

	@Override
	public Value getLocal(IdentifiedToken identifiedToken) {
		try {
			return (Value) variables.get(identifiedToken.getIdentifier().getName()).get(bridgeInstance);
		} catch (IllegalAccessException e) {
			// TODO: This is very bad. We want to throw an exception here. Silently failing is terrible.
			return null;
		}
	}

	@Override
	public void setLocal(IdentifiedToken identifiedToken, Value value) {
		super.setLocal(identifiedToken, value);
	}
}
