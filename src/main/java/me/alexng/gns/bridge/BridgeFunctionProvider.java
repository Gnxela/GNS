package me.alexng.gns.bridge;

import me.alexng.gns.env.NativeFunction;
import me.alexng.gns.env.scope.FunctionProvider;
import me.alexng.gns.tokens.FunctionToken;
import me.alexng.gns.tokens.IdentifiedToken;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class BridgeFunctionProvider extends FunctionProvider {

	private Object bridgeInstance;
	private List<NativeFunction> functions;

	public BridgeFunctionProvider(Object bridgeInstance, List<Method> functions, FunctionProvider parent) {
		super(parent);
		this.bridgeInstance = bridgeInstance;
		this.functions = functions.stream().map(method -> new NativeFunction(bridgeInstance, method)).collect(Collectors.toList());
	}

	@Override
	public FunctionToken getLocal(IdentifiedToken identifiedToken) {
		for (NativeFunction nativeFunction : functions) {
			if (nativeFunction.getName().equals(identifiedToken.getIdentifier().getName())) {
				return nativeFunction;
			}
		}
		return null;
	}

	@Override
	public void setLocal(IdentifiedToken identifiedToken, FunctionToken value) {
		// NO OPERATION
	}
}
