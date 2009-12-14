package minesweeper.client;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public abstract class FieldState {
	protected Field field;

	public FieldState(Field field) {
		this.field = field;
	}

	public abstract Widget getWidget();

	public void onClick(Event event) {
	}

	public void open() {
		field.forceOpen();
	}

	public void forceOpen() {
	};
}
