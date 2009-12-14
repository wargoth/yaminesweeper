package minesweeper.client;

import java.util.Iterator;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Grid;

public class Minefield {
	private static GameTimer timer = new GameTimer();

	private int cols = 8; // 8,16,31
	private int rows = 8; // 8,16,16
	private int minesNum = 10; // 10,40,99

	private Collection collection;
	private Grid grid;

	private MinesLeft minesLeft = new MinesLeft();
	private int closedFieldsLeft;
	private Level level = new Level(this);
	private boolean is_active;

	public void setCols(int cols) {
		this.cols = cols;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setMinesNum(int minesNum) {
		this.minesNum = minesNum;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public int getMinesNum() {
		return minesNum;
	}

	public MinesLeft getMinesLeft() {
		return minesLeft;
	}

	public Level getLevel() {
		return level;
	}

	public void decrementClosedFieldsLeft() {
		closedFieldsLeft--;

		if (closedFieldsLeft - minesNum == 0) {
			getTimer().stop();
			showCongratsDialog();
		}
	}

	public void init() {
		closedFieldsLeft = cols * rows;
		minesLeft.setNum(minesNum);
		getTimer().init();
		is_active = true;

		initCollection();
		initWidget();
	}

	private void initCollection() {
		collection = new Collection(cols, rows);
		initFields();
		populateMines();
	}

	private void initFields() {
		for (int col = 0; col < getCols(); col++) {
			for (int row = 0; row < getRows(); row++) {
				collection.set(new Field(this, col, row));
			}
		}
	}

	private void populateMines() {
		for (int i = 0; i < minesNum; i++) {
			int col, row;
			do {
				col = (int) Math.round(Math.random() * (double) (cols - 1));
				row = (int) Math.round(Math.random() * (double) (rows - 1));
			} while (collection.get(col, row).isMine());
			collection.get(col, row).setIsMine();
		}
	}

	private void initWidget() {
		grid = getWidget();
		grid.clear();
		grid.resize(rows, cols);

		for (Iterator<Field> iterator = collection.iterator(); iterator
				.hasNext();) {
			Field field = iterator.next();
			grid.setWidget(field.getRow(), field.getCol(), field.getWidget());
		}
	}

	public Grid getWidget() {
		if (grid == null) {
			grid = new Grid() {
				@Override
				public void onBrowserEvent(Event event) {
					if (!is_active) {
						return;
					}

					switch (event.getTypeInt()) {
					case Event.ONMOUSEUP:
					case Event.ONMOUSEDOWN:
						if (DOM.eventGetCurrentTarget(event) == getElement()) {
							elementClicked(event);
						}
						break;
					}

					event.stopPropagation();
					event.preventDefault();
				}
			};
			grid.sinkEvents(Event.ONCONTEXTMENU | Event.ONMOUSEDOWN
					| Event.ONDBLCLICK | Event.ONMOUSEUP);
			grid.addStyleName("grid");
		}
		return grid;
	}

	protected void elementClicked(Event event) {
		Element element = DOM.eventGetTarget(event);

		for (Iterator<Field> iterator = collection.iterator(); iterator
				.hasNext();) {
			Field field = iterator.next();

			if (field.getWidget().getElement() == element) {
				field.clicked(event);
				break;
			}
		}
	}

	public void open(Field field) {
		grid.setWidget(field.getRow(), field.getCol(), field.getWidget());
	}

	public Collection getCollection() {
		return collection;
	}

	public void boom() {
		getTimer().stop();
		deactivate();

		for (Iterator<Field> iterator = collection.iterator(); iterator
				.hasNext();) {
			Field field = iterator.next();

			if (field.isMine() || field.isFlaged()) {
				field.forceOpen();
			}
		}
	}

	public static GameTimer getTimer() {
		return timer;
	}

	private void deactivate() {
		is_active = false;
	}

	public void showOptionsDialog() {
		new OptionsDialog(this);
	}

	private void showCongratsDialog() {
		new CongratsDialog(this);
	}
}
