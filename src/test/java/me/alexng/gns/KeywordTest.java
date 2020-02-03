package me.alexng.gns;

import me.alexng.gns.tokens.KeywordToken;
import me.alexng.gns.util.MockToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KeywordTest {

	@Test
	public void testMatches() {
		assertTrue(Keyword.FUNC.matches(new KeywordToken(Keyword.FUNC, FileIndex.NULL_INDEX)));
		assertFalse(Keyword.FUNC.matches(new KeywordToken(Keyword.TRUE, FileIndex.NULL_INDEX)));
		assertFalse(Keyword.FUNC.matches(new MockToken()));
	}

	@Test
	public void testGetKeyword() {
		assertEquals("true", Keyword.TRUE.getKeyword());
	}
}
