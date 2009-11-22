package minesweeper.client;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
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
				this.incrementMinesNum();
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
			return getButtonWidget();
		}

		if (isMine()) {
			return getMineWidget();
		} else if (!isEmpty()) {
			return getFieldWidget();
		}

		if (isFlaged()) {
			return getInvalidFlagedWidget();
		} else {
			return getEmptyWidget();
		}
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
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				parent.getTimer().start();
				open();
			}
		});
		button.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				switch (event.getNativeButton()) {
				case NativeEvent.BUTTON_RIGHT:
					event.stopPropagation();
					event.preventDefault();
					toggleFlag();
					break;
				}
			}
		});

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

		widget.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				switch (event.getNativeButton()) {
				case NativeEvent.BUTTON_MIDDLE:
					openAround();
					break;
				}
			}
		});

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

	public void deactivate() {
		button.setEnabled(false);
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
}
