package me.alexng.gns.env.scope;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.FunctionToken;
import me.alexng.gns.tokens.IdentifiedToken;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionProvider extends IdentifiedScopeProvider<FunctionToken> {

	private List<FunctionToken> functions;

	public FunctionProvider(FunctionProvider parent) {
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
	public void setLocal(IdentifiedToken identifiedToken, FunctionToken functionToken) throws RuntimeException {
		if (getLocal(identifiedToken) != null) {
			throw new RuntimeException(functionToken.getFileIndex(), "Function name already defined");
		}
		functions.add(functionToken);
	}


	@Override
	public String toString() {
		return functions.stream().map(f -> f.getIdentifier().getName()).collect(Collectors.toList()).toString();
	}
}
