package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.util.CountingExecutor;
import me.alexng.gns.util.ExecutableMockToken;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class BlockTokenTest {

	@Test
	public void testExecuteBlockWithScope() throws RuntimeException {
		CountingExecutor countingExecutor = new CountingExecutor();
		LinkedList<Token> tokens = new LinkedList<>();
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		tokens.add(new ExecutableMockToken(countingExecutor));
		BlockToken blockToken = new BlockToken(tokens, FileIndex.NULL_INDEX);
		blockToken.executeBlockWithScope(Scope.createGlobalScope(null));
		countingExecutor.assertCount(3);
	}

	// TODO: Test the scopes can be modified inside a block.
}
