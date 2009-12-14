package minesweeper.client;

import java.util.Iterator;

public class CollectionIterator implements Iterator<Field> {
	private Collection collection;
	private int col = -1;
	private int row = 0;

	public CollectionIterator(Collection collection) {
		this.collection = collection;
	}

	@Override
	public boolean hasNext() {
		return col + 1 < collection.getCols() || row + 1 < collection.getRows();
	}

	@Override
	public Field next() {
		if (++col == collection.getCols()) {
			col = 0;
			++row;
		}
		return collection.get(col, row);
	}

	@Override
	public void remove() {
	}
}
