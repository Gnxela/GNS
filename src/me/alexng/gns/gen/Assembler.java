package me.alexng.gns.gen;

import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.BlockToken;
import me.alexng.gns.lexer.tokens.BracketToken;
import me.alexng.gns.lexer.tokens.IfToken;
import me.alexng.gns.lexer.tokens.KeywordToken;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Takes tokenized input and assembles "secondary" tokens; tokens that are not directly parsed from the input text
 * but instead inferred from a sequence of tokens
 */
public class Assembler {

	public static void assemble(LinkedList<Token> tokens) throws ParsingException {
		ListIterator<Token> iterator = tokens.listIterator();
		while (iterator.hasNext()) {
			Token token = iterator.next();
			if (token.equals(BracketToken.CURLEY_OPEN)) {
				assembleBlock(iterator);
			}
			iterator.next();
		}
		iterator = tokens.listIterator();
		while (iterator.hasNext()) {
			Token token = iterator.next();
			if (token instanceof KeywordToken && ((KeywordToken) token).getKeyword() == Keyword.IF) {
				assembleIfStatement(iterator);
			}
			iterator.next();
		}
	}

	private static void assembleBlock(ListIterator<Token> tokens) throws ParsingException {
		tokens.remove();

		List<Token> blockTokens = matchTokens(tokens, BracketToken.CURLEY_OPEN, BracketToken.CURLEY_CLOSED);
		tokens.add(new BlockToken(blockTokens));
	}

	private static void assembleIfStatement(ListIterator<Token> tokens) throws ParsingException {
		tokens.remove();

		if (!tokens.next().equals(BracketToken.ROUND_OPEN)) {
			// TODO: Better exception
			throw new ParsingException(0, "Expected open bracket");
		}
		tokens.remove();

		List<Token> condition = collectUntil(tokens, BracketToken.ROUND_CLOSED);
		if (!tokens.next().equals(BracketToken.ROUND_CLOSED)) {
			// TODO: Better exception
			throw new ParsingException(0, "Expected closed bracket");
		}
		tokens.remove();

		Token expectedBlock = tokens.next();
		if (!(expectedBlock instanceof BlockToken)) {
			// TODO: Better exception
			throw new ParsingException(0, "Expected block");
		}
		tokens.remove();

		BlockToken block = (BlockToken) expectedBlock;
		tokens.add(new IfToken(condition, block));
	}

	/**
	 * Collects all tokens in between two tokens, matching opening and closing tokens to each other.
	 * @return A list of tokens in between the matched tokens. Not including the open and close tokens (middle open and close tokens are included)
	 * @throws ParsingException
	 */
	private static LinkedList<Token> matchTokens(ListIterator<Token> tokens, Token increment, Token decrement) throws ParsingException {
		int depth = 1;
		LinkedList<Token> bucket = new LinkedList<>();
		while (tokens.hasNext()) {
			Token token = tokens.next();
			tokens.remove();
			if (token.equals(increment)) {
				depth++;
			} else if (token.equals(decrement)) {
				if (depth == 1) {
					return bucket;
				} else {
					depth--;
				}
			} else {
				bucket.add(token);
			}
		}
		// TODO: Better exception
		throw new ParsingException(0, "Matching not found");
	}

	/**
	 * Collects all tokens until {@code end} is met (end is not included, and not consumed) and removes them from {@code tokens}.
	 * @return All collected tokens.
	 */
	private static LinkedList<Token> collectUntil(ListIterator<Token> tokens, Token end) throws ParsingException {
		LinkedList<Token> bucket = new LinkedList<>();
		while (tokens.hasNext()) {
			Token token = tokens.next();
			if (token.equals(end)) {
				tokens.previous();
				return bucket;
			}
			tokens.remove();
			bucket.add(token);
		}
		// TODO: Better message
		throw new ParsingException(0, "Didn't see end");
	}
}
