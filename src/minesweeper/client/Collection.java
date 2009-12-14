package minesweeper.client;

import java.util.Iterator;

public class Collection implements Iterable<Field> {
	private int cols = 0;
	private int rows = 0;
	private Field[][] collection;

	public Collection(int cols, int rows) {
		resize(cols, rows);
	}

	@Override
	public Iterator<Field> iterator() {
		return new CollectionIterator(this);
	}

	public Iterator<Field> aroundFieldIterator(int col, int row) {
		return new AroundFieldIterator(col, row, this);
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public void resize(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;
		collection = new Field[cols][rows];
	}

	public Field get(int col, int row) {
		return collection[col][row];
	}

	public void set(Field field) {
		collection[field.getCol()][field.getRow()] = field;
	}
}
