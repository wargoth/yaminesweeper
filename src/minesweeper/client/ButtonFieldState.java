package minesweeper.client;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class ButtonFieldState extends FieldState {
	private Button widget;

	public ButtonFieldState(Field field) {
		super(field);
	}

	@Override
	public Widget getWidget() {
		if (widget == null) {
			widget = new Button();
			widget.setStyleName("field-button");
		}

		return widget;
	}

	@Override
	public void onClick(Event event) {
		switch (event.getTypeInt()) {
		case Event.ONMOUSEUP:
			onMouseUp(event);
			break;

		case Event.ONMOUSEDOWN:
			onMouseDown(event);
			break;
		}
	}

	@Override
	public void open() {
		if (!field.isFlaged()) {
			field.forceOpen();
		}
	}

	@Override
	public void forceOpen() {
		if (field.isMine()) {
			field.setState(new MineFieldState(field));
		} else if (field.isFlaged()) {
			field.setState(new InvalidFieldState(field));
		} else {
			field.setState(new OpenedFieldState(field));
		}

		field.open();
	}

	private void onMouseUp(Event event) {
		switch (event.getButton()) {
		case Event.BUTTON_LEFT:
			Minefield.getTimer().start();
			field.open();
			break;
		}
	}

	private void onMouseDown(Event event) {
		switch (event.getButton()) {
		case Event.BUTTON_RIGHT:
			toggleFlag();
			break;
		}
	}

	private void toggleFlag() {
		if (field.isFlaged()) {
			field.setFlaged(false);
			((Button) getWidget()).setText("");
			field.getMinefield().getMinesLeft().increment();
		} else {
			field.setFlaged(true);
			((Button) getWidget()).setText("!");
			field.getMinefield().getMinesLeft().decrement();
		}
	}
}
