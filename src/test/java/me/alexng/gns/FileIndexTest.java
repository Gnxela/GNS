package me.alexng.gns;

import me.alexng.gns.tokens.Token;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileIndexTest {

	@Test
	public void testConstructor() {
		FileIndex fileIndex = new FileIndex("dir/fileName.extension", 10, 20);
		assertFileIndex(fileIndex, "dir/fileName.extension", 10, 20);
	}

	@Test
	public void testUnindexedFile() {
		assertFileIndex(FileIndex.unindexedFile("file1"), "file1", 0, 0);
	}

	@Test
	public void testOpenClose() throws ParsingException {
		FileIndex newFileIndex = FileIndex.openClose(new MockToken("file1", 1, 2), new MockToken("file1", 2, 7));
		assertFileIndex(newFileIndex, "file1", 1, 7);
	}

	@Test
	public void testOpenClose_throws() {
		assertThrows(ParsingException.class, () -> {
			FileIndex.openClose(new MockToken("file1", 1, 2), new MockToken("file2", 2, 7));
		});
	}

	@Test
	public void testWrap() throws ParsingException {
		LinkedList<Token> linkedList = new LinkedList<>();
		linkedList.add(new MockToken("file1", 1, 2));
		linkedList.add(new MockToken("file1", 10, 12));
		linkedList.add(new MockToken("file1", 15, 20));
		FileIndex newFileIndex = FileIndex.wrap(linkedList);
		assertFileIndex(newFileIndex, "file1", 1, 20);
	}

	@Test
	public void testWrap_throws() {
		LinkedList<Token> linkedList = new LinkedList<>();
		linkedList.add(new MockToken("file1", 1, 2));
		linkedList.add(new MockToken("file1", 10, 12));
		linkedList.add(new MockToken("file2", 15, 20));
		assertThrows(ParsingException.class, () -> {
			FileIndex.wrap(linkedList);
		});
	}

	@Test
	public void testMorph() {
		FileIndex fileIndex = new FileIndex("file1", 10, 20);
		assertFileIndex(fileIndex.morph(1, 1), "file1", 11, 21);
		assertFileIndex(fileIndex.morph(-1, 1), "file1", 9, 21);
	}

	private void assertFileIndex(FileIndex receivedFileIndex, String expectedFile, int expectedStartIndex, int expectedEndIndex) {
		assertEquals(expectedFile, receivedFileIndex.getSourceFile());
		assertEquals(expectedStartIndex, receivedFileIndex.getStartIndex());
		assertEquals(expectedEndIndex, receivedFileIndex.getEndIndex());
	}

	@Test
	public void testSubstring() {
		FileIndex fileIndex = new FileIndex("file1", 3, 6);
		assertEquals("456", fileIndex.substring("1234567890"));
		fileIndex = new FileIndex("file1", 0, 6);
		assertEquals("123456", fileIndex.substring("1234567890"));
	}
}
