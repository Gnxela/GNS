package me.alexng.gns.env.scope;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Value;
import me.alexng.gns.tokens.IdentifiedToken;

import java.util.HashMap;
import java.util.Map;

public class VariableProvider extends ScopeProvider<IdentifiedToken, Value> {

	private Map<String, Value> variables;

	public VariableProvider(ScopeProvider<IdentifiedToken, Value> parent) {
		super(parent);
		this.variables = new HashMap<>();
	}

	@Override
	public Value get(IdentifiedToken identifiedToken) throws RuntimeException {
		Value value = getLocal(identifiedToken);
		if (value != null) {
			return value;
		}
		if (parent != null) {
			return parent.get(identifiedToken);
		}
		throw new RuntimeException(identifiedToken, "Undefined variable: " + identifiedToken.getIdentifier().getName());
	}

	@Override
	public Value getLocal(IdentifiedToken identifiedToken) {
		return variables.get(identifiedToken.getIdentifier().getName());
	}

	@Override
	public void set(IdentifiedToken identifiedToken, Value value) throws RuntimeException {
		VariableProvider provider = findProviderWithVariable(identifiedToken);
		if (provider == null) {
			provider = this;
		}
		provider.setLocal(identifiedToken, value);
	}

	@Override
	public void setLocal(IdentifiedToken identifiedToken, Value value) {
		variables.put(identifiedToken.getIdentifier().getName(), value);
	}

	private VariableProvider findProviderWithVariable(IdentifiedToken identifiedToken) {
		VariableProvider variableProvider = this;
		while (variableProvider != null && variableProvider.getLocal(identifiedToken) == null) {
			variableProvider = (VariableProvider) variableProvider.parent;
		}
		return variableProvider;
	}

	@Override
	public String toString() {
		return variables.toString();
	}
}
