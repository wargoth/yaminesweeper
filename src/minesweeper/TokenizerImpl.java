package minesweeper;

import minesweeper.client.Level;

public class TokenizerImpl implements Tokenizer {
	private static final String SALT = "s-T9m$v1T%^{Y%I|jy^].-vl1/JiBL>~;<5Af@U";

	@Override
	public String getToken(Level level, int ticks, String userName) {
		String token = ticks * 2 + "" + level.getLevel() + SALT + userName;

		return token;
	}
}
