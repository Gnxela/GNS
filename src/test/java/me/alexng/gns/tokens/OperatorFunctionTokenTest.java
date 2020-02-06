package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.tokens.operators.AdditionToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperatorFunctionTokenTest {

	@Test
	public void testExecute() throws RuntimeException {
		AdditionToken additionToken = new AdditionToken(FileIndex.NULL_INDEX);
		// We are ONLY testing that the operator tokens are correctly stored here.
		OperatorFunctionToken operatorFunctionToken = new OperatorFunctionToken(additionToken, null, null, FileIndex.NULL_INDEX);
		Scope scope = Scope.createGlobalScope(null);
		operatorFunctionToken.execute(scope);
		assertEquals(operatorFunctionToken, scope.operatorFunctionProvider.get(additionToken));
	}
}
