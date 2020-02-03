package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.Keyword;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeywordTokenTest {

	@Test
	public void testConstructor() {
		KeywordToken keywordToken = new KeywordToken(Keyword.FUNC, FileIndex.NULL_INDEX);
		assertEquals(Keyword.FUNC, keywordToken.getKeyword());
	}
}
