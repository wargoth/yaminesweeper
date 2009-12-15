package minesweeper.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OptionsDialog {
	private DialogBox dialogBox = new DialogBox();
	private TextBox rowsTextBox;
	private TextBox colsTextBox;
	private TextBox minesTextBox;

	public OptionsDialog(final Minefield parent) {
		dialogBox.setText("Options");

		final RadioButton easyRadio = new RadioButton("sizes", "Easy");
		final RadioButton mediumRadio = new RadioButton("sizes", "Medium");
		final RadioButton hardRadio = new RadioButton("sizes", "Hard");
		final RadioButton customRadio = new RadioButton("sizes", "Custom");

		ValueChangeHandler<Boolean> handler = new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				updateState(customRadio.getValue());
			}
		};

		easyRadio.addValueChangeHandler(handler);
		mediumRadio.addValueChangeHandler(handler);
		hardRadio.addValueChangeHandler(handler);
		customRadio.addValueChangeHandler(handler);

		switch (parent.getLevel().getLevel()) {
		case Level.EASY:
			easyRadio.setValue(true);
			break;

		case Level.MEDIUM:
			mediumRadio.setValue(true);
			break;

		case Level.HARD:
			hardRadio.setValue(true);
			break;

		default:
			customRadio.setValue(true);
			break;
		}

		VerticalPanel sizesLayout = new VerticalPanel();
		sizesLayout.add(easyRadio);
		sizesLayout.add(mediumRadio);
		sizesLayout.add(hardRadio);
		sizesLayout.add(customRadio);

		rowsTextBox = new TextBox();
		rowsTextBox.setValue(parent.getRows() + "");

		colsTextBox = new TextBox();
		colsTextBox.setValue(parent.getCols() + "");

		minesTextBox = new TextBox();
		minesTextBox.setValue(parent.getMinesNum() + "");

		Button cancelButton = new Button("Cancel");
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});

		Button applyButton = new Button("Apply");
		applyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (easyRadio.getValue()) {
					parent.getLevel().setLevel(Level.EASY);
				} else if (mediumRadio.getValue()) {
					parent.getLevel().setLevel(Level.MEDIUM);
				} else if (hardRadio.getValue()) {
					parent.getLevel().setLevel(Level.HARD);
				} else if (customRadio.getValue()) {
					parent.getLevel().setParams(colsTextBox.getValue(),
							rowsTextBox.getValue(), minesTextBox.getValue());
				}

				parent.init();
				dialogBox.hide();
			}
		});

		Grid layout = new Grid(6, 2);
		layout.setWidget(0, 0, new Label("Difficulty:"));
		layout.setWidget(0, 1, sizesLayout);
		layout.setWidget(1, 0, new Label("Custom:"));
		layout.setWidget(2, 0, new Label("Cols:"));
		layout.setWidget(2, 1, colsTextBox);
		layout.setWidget(3, 0, new Label("Rows:"));
		layout.setWidget(3, 1, rowsTextBox);
		layout.setWidget(4, 0, new Label("Mines:"));
		layout.setWidget(4, 1, minesTextBox);
		layout.setWidget(5, 0, cancelButton);
		layout.setWidget(5, 1, applyButton);

		updateState(parent.getLevel().getLevel() == Level.CUSTOM);

		dialogBox.setWidget(layout);
		dialogBox.center();
	}

	private void updateState(boolean enabled) {
		rowsTextBox.setEnabled(enabled);
		colsTextBox.setEnabled(enabled);
		minesTextBox.setEnabled(enabled);
	}
}
