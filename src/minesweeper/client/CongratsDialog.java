package minesweeper.client;

import com.google.gwt.user.client.ui.DialogBox;

public class CongratsDialog {
	private DialogBox dialogBox;
	
	public CongratsDialog() {
		dialogBox.setText("Congratulations, you win!");

		dialogBox.center();
	}
}
