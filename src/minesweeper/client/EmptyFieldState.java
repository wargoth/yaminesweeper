package minesweeper.client;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EmptyFieldState extends FieldState {
	private Label widget;
	private boolean is_opened;

	public EmptyFieldState(Field field) {
		super(field);
	}

	@Override
	public Widget getWidget() {
		if (widget == null) {
			widget = new Label();
		}
		return widget;
	}

	@Override
	public void forceOpen() {
		if (is_opened) {
			return;
		}

		is_opened = true;

		for (Iterator<Field> iterator = field.getMinefield().getCollection()
				.aroundFieldIterator(field.getCol(), field.getRow()); iterator
				.hasNext();) {
			Field field = iterator.next();

			field.forceOpen();
		}
	}
}
