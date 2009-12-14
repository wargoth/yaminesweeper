package minesweeper.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MineFieldState extends FieldState {
	private Label widget;

	public MineFieldState(Field field) {
		super(field);
	}

	@Override
	public void open() {
		field.getMinefield().boom();
	}

	@Override
	public Widget getWidget() {
		if (widget == null) {
			widget = new Label();
			widget.setText("@");
			widget.setStyleName("field mine");
		}

		return widget;
	}

}
