package minesweeper.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class InvalidFieldState extends FieldState {

	private Label widget;

	public InvalidFieldState(Field field) {
		super(field);
	}

	@Override
	public Widget getWidget() {
		if (widget == null) {
			widget = new Label();
			widget.setText("@");
			widget.setStyleName("invalid field mine");
		}

		return widget;
	}

}
