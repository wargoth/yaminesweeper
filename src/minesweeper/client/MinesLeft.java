package minesweeper.client;

import com.google.gwt.user.client.ui.Label;

public class MinesLeft {
	private int minesNum;
	private Label widget = new Label();

	public void setNum(int num) {
		minesNum = num;
		updateWidget();
	}

	public int getNum() {
		return minesNum;
	}

	public Label getWidget() {
		return widget;
	}

	public void increment() {
		minesNum++;
		updateWidget();
	}

	public void decrement() {
		minesNum--;
		updateWidget();
	}

	private void updateWidget() {
		widget.setText(minesNum + "");
	}
}