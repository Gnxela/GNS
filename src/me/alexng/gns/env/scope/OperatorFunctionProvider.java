package me.alexng.gns.env.scope;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.IdentifiedToken;
import me.alexng.gns.tokens.OperatorFunctionToken;

import java.util.LinkedList;
import java.util.List;

public class OperatorFunctionProvider extends IdentifiedScopeProvider<OperatorFunctionToken> {

	private List<OperatorFunctionToken> functions;

	public OperatorFunctionProvider(OperatorFunctionProvider parent) {
		super(parent);
		this.functions = new LinkedList<>();
	}

	@Override
	public OperatorFunctionToken get(IdentifiedToken identifiedToken) throws RuntimeException {
		OperatorFunctionToken functionToken = getLocal(identifiedToken);
		if (functionToken != null) {
			return functionToken;
		}
		if (parent != null) {
			return parent.get(identifiedToken);
		}
		throw new RuntimeException(identifiedToken, "Undefined function: " + identifiedToken.getIdentifier().getName());
	}

	@Override
	public OperatorFunctionToken getLocal(IdentifiedToken identifiedToken) {
		String identifier = identifiedToken.getIdentifier().getName();
		for (OperatorFunctionToken functionToken : functions) {
			if (functionToken.getName().contentEquals(identifier)) {
				return functionToken;
			}
		}
		return null;
	}

	@Override
	public void set(IdentifiedToken identifiedToken, OperatorFunctionToken functionToken) throws RuntimeException {
		setLocal(identifiedToken, functionToken);
	}

	@Override
	public void setLocal(IdentifiedToken identifiedToken, OperatorFunctionToken functionToken) {
		// TODO: CHeck for previous functions?
		functions.add(functionToken);
	}


	@Override
	public String toString() {
		return functions.toString();
	}
}
