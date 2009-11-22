package minesweeper;

import minesweeper.client.Level;

public interface Tokenizer {
	public String getToken(Level level, int ticks, String userName);
}
