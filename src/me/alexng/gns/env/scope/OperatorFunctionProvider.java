package me.alexng.gns.env.scope;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.FunctionToken;
import me.alexng.gns.tokens.IdentifiedToken;

import java.util.LinkedList;
import java.util.List;

public class OperatorFunctionProvider extends FunctionProvider {

	// TODO: This class is now the exact same as FunctionProvider. 

	private List<FunctionToken> functions;

	public OperatorFunctionProvider(OperatorFunctionProvider parent) {
		super(parent);
		this.functions = new LinkedList<>();
	}

	@Override
	public FunctionToken get(IdentifiedToken identifiedToken) throws RuntimeException {
		FunctionToken functionToken = getLocal(identifiedToken);
		if (functionToken != null) {
			return functionToken;
		}
		if (parent != null) {
			return parent.get(identifiedToken);
		}
		throw new RuntimeException(identifiedToken, "Undefined function: " + identifiedToken.getIdentifier().getName());
	}

	@Override
	public FunctionToken getLocal(IdentifiedToken identifiedToken) {
		String identifier = identifiedToken.getIdentifier().getName();
		for (FunctionToken functionToken : functions) {
			if (functionToken.getName().contentEquals(identifier)) {
				return functionToken;
			}
		}
		return null;
	}

	@Override
	public void set(IdentifiedToken identifiedToken, FunctionToken functionToken) throws RuntimeException {
		setLocal(identifiedToken, functionToken);
	}

	@Override
	public void setLocal(IdentifiedToken identifiedToken, FunctionToken functionToken) {
		// TODO: CHeck for previous functions?
		functions.add(functionToken);
	}


	@Override
	public String toString() {
		return functions.toString();
	}
}
