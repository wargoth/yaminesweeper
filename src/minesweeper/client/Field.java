package minesweeper.client;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Field {
	public static final int MINE = -1;
	private int minesNum;

	private int col;
	private int row;
	private boolean is_opened = false;
	private Minefield parent;
	private Button button;
	private boolean is_flaged;
	private Widget currentWidget;
	private boolean trigger_left;
	private boolean trigger_right;

	public Field(Minefield parent, int col, int row, int minesNum) {
		this.minesNum = minesNum;
		this.col = col;
		this.row = row;
		this.parent = parent;
	}

	public void open() {
		if (is_flaged) {
			return;
		}
		forceOpen();
	}

	public void forceOpen() {
		if (is_opened) {
			return;
		}

		is_opened = true;

		if (isMine()) {
			parent.boom();
		} else {
			calculateMinesNum();
			parent.open(this);
		}
	}

	public void setOpened(boolean is_opened) {
		this.is_opened = is_opened;
	}

	private void calculateMinesNum() {
		for (AroundFieldIterator iterator = parent.getCollection()
				.aroundFieldIterator(col, row); iterator.hasNext();) {
			Field field = iterator.next();

			if (field.isMine()) {
				incrementMinesNum();
			}
		}
	}

	public int getMinesNumExpected() {
		int minesExpected = 0;

		for (AroundFieldIterator iterator = parent.getCollection()
				.aroundFieldIterator(col, row); iterator.hasNext();) {
			Field field = iterator.next();

			if (field.isFlaged()) {
				minesExpected++;
			}
		}

		return minesExpected;
	}

	public int getMinesNum() {
		return minesNum;
	}

	public void incrementMinesNum() {
		minesNum++;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public Widget getWidget() {
		if (!is_opened) {
			currentWidget = getButtonWidget();
		} else if (isMine()) {
			currentWidget = getMineWidget();
		} else if (!isEmpty()) {
			currentWidget = getFieldWidget();
		} else if (isFlaged()) {
			currentWidget = getInvalidFlagedWidget();
		} else {
			currentWidget = getEmptyWidget();
		}

		return currentWidget;
	}

	private Widget getInvalidFlagedWidget() {
		Widget widget = getMineWidget();
		widget.addStyleName("invalid");

		return widget;
	}

	private Widget getEmptyWidget() {
		return new Label();
	}

	private Widget getButtonWidget() {
		button = new Button();
		button.setStyleName("field-button");

		return button;
	}

	private Widget getMineWidget() {
		Label widget = new Label();
		widget.setText("@");
		widget.setStyleName("field mine");

		return widget;
	}

	private Widget getFieldWidget() {
		Label widget = new Label();
		widget.setText(getMinesNum() + "");
		widget.setStyleName("field num-" + getMinesNum());

		return widget;
	}

	protected void openAround() {
		if (minesNum != getMinesNumExpected()) {
			return;
		}

		for (AroundFieldIterator iterator = parent.getCollection()
				.aroundFieldIterator(col, row); iterator.hasNext();) {
			Field field = iterator.next();
			field.open();
		}
	}

	public void toggleFlag() {
		if (is_flaged) {
			is_flaged = false;
			button.setText("");
			parent.getMinesLeft().increment();
		} else {
			is_flaged = true;
			button.setText("!");
			parent.getMinesLeft().decrement();
		}
	}

	public boolean isFlaged() {
		return is_flaged;
	}

	public boolean isMine() {
		return minesNum == MINE;
	}

	public boolean isEmpty() {
		return minesNum == 0;
	}

	public Widget getCurrentWidget() {
		return currentWidget;
	}

	public void clicked(Event event) {
		switch (event.getTypeInt()) {
		case Event.ONMOUSEUP:
			onMouseUp(event);
			break;

		case Event.ONMOUSEDOWN:
			onMouseDown(event);
			break;
		}
	}

	private void onMouseDown(Event event) {
		if (!is_opened) {
			switch (event.getButton()) {
			case NativeEvent.BUTTON_RIGHT:
				toggleFlag();
				break;
			}
		} else if (!isMine() && !isEmpty()) {
			switch (event.getButton()) {
			case NativeEvent.BUTTON_LEFT:
				trigger_left = true;
				break;
			case NativeEvent.BUTTON_RIGHT:
				trigger_right = true;
				break;
			}
		}
	}

	private void onMouseUp(Event event) {
		if (!is_opened) {
			switch (event.getButton()) {
			case NativeEvent.BUTTON_LEFT:
				GameTimer.getInstance().start();
				open();
				break;
			}
		} else if (!isMine() && !isEmpty()) {
			if (trigger_left && trigger_right) {
				openAround();
			}

			trigger_left = false;
			trigger_right = false;
		}
	}
}
