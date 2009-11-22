package minesweeper.client;

import java.util.Iterator;

public class AroundFieldIterator implements Iterator<Field> {

	private int col = -2;
	private int row = -1;
	private int start_col = -1;
	private int start_row = -1;
	private int curent_col;
	private int curent_row;
	private int cols;
	private int rows;
	private Collection collection;

	public AroundFieldIterator(int col, int row, Collection collection) {
		curent_col = col;
		curent_row = row;
		cols = Math.min(collection.getCols(), col + 2) - Math.max(0, col - 1);
		rows = Math.min(collection.getRows(), row + 2) - Math.max(0, row - 1);

		if (col == 0) {
			start_col = 0;
			this.col = -1;
		}

		if (row == 0) {
			this.row = start_row = 0;
		}

		this.collection = collection;
	}

	@Override
	public boolean hasNext() {
		return col - start_col + 1 < cols || row - start_row + 1 < rows;
	}

	@Override
	public Field next() {
		if (++col - start_col == cols) {
			col = start_col;
			++row;
		}
		return collection.get(col + curent_col, row + curent_row);
	}

	@Override
	public void remove() {
	}

	public int getCol() {
		return col + curent_col;
	}

	public int getRow() {
		return row + curent_row;
	}
}
