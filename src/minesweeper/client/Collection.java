package minesweeper.client;


public class Collection implements Iterable<Field> {
	private int cols = 0;
	private int rows = 0;
	private Field[][] collection;

	public Collection(int cols, int rows) {
		resize(cols, rows);
	}

	@Override
	public CollectionIterator iterator() {
		return new CollectionIterator(this);
	}
	
	public AroundFieldIterator aroundFieldIterator(int col, int row) {
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
	
	public void set(int col, int row, Field field) {
		collection[col][row] = field;
	}
}
