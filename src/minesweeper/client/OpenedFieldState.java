package minesweeper.client;

import java.util.Iterator;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class OpenedFieldState extends FieldState {
	private Label widget;

	public OpenedFieldState(Field field) {
		super(field);
		calculateMinesNum();
		field.getMinefield().decrementClosedFieldsLeft();
	}

	private void calculateMinesNum() {
		for (Iterator<Field> iterator = field.getMinefield().getCollection()
				.aroundFieldIterator(field.getCol(), field.getRow()); iterator
				.hasNext();) {
			Field field = iterator.next();

			if (field.isMine()) {
				this.field.incrementMinesNum();
			}
		}
	}

	@Override
	public void forceOpen() {
		if (isEmpty()) {
			field.setState(new EmptyFieldState(field));
			field.open();
		}
	}

	@Override
	public Widget getWidget() {
		if (widget == null) {
			widget = new Label();
			int minesNum = field.getMinesNum();
			widget.setText(minesNum + "");
			widget.setStyleName("field num-" + minesNum);
		}
		return widget;
	}

	@Override
	public void onClick(Event event) {
		switch (event.getTypeInt()) {
		case Event.ONMOUSEUP:
			onMouseUp(event);
			break;
		}
	}

	private void onMouseUp(Event event) {
		switch (event.getButton()) {
		case Event.BUTTON_MIDDLE:
			openAround();
			break;
		}
	}

	private int getMinesNumExpected() {
		int minesExpected = 0;

		for (Iterator<Field> iterator = field.getMinefield().getCollection()
				.aroundFieldIterator(field.getCol(), field.getRow()); iterator
				.hasNext();) {
			Field field = iterator.next();

			if (field.isFlaged()) {
				minesExpected++;
			}
		}

		return minesExpected;
	}

	private boolean isEmpty() {
		return field.getMinesNum() == 0;
	}

	private void openAround() {
		if (field.getMinesNum() != getMinesNumExpected()) {
			return;
		}

		for (Iterator<Field> iterator = field.getMinefield().getCollection()
				.aroundFieldIterator(field.getCol(), field.getRow()); iterator
				.hasNext();) {
			Field field = iterator.next();
			field.open();
		}
	}
}