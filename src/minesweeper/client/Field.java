package minesweeper.client;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public class Field {
	public static final int MINE = -1;
	private int minesNum;

	private int col;
	private int row;
	private Minefield parent;
	private FieldState state;
	private boolean is_flaged = false;

	public Field(Minefield parent, int col, int row) {
		this.col = col;
		this.row = row;
		this.parent = parent;

		setState(new ButtonFieldState(this));
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

	public boolean isMine() {
		return minesNum == MINE;
	}

	public void setIsMine() {
		minesNum = MINE;
	}

	public boolean isFlaged() {
		return is_flaged;
	}

	public void setFlaged(boolean is_flaged) {
		this.is_flaged = is_flaged;
	}

	public Minefield getMinefield() {
		return parent;
	}

	public void setState(FieldState state) {
		this.state = state;
	}

	private FieldState getState() {
		return state;
	}

	public void open() {
		getState().open();
	}

	public void forceOpen() {
		getState().forceOpen();
		parent.open(this);
	}

	public Widget getWidget() {
		return getState().getWidget();
	}

	public void clicked(Event event) {
		getState().onClick(event);
	}

}
