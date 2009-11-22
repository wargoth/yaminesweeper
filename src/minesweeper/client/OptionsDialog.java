package minesweeper.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OptionsDialog {
	private DialogBox dialogBox;

	public OptionsDialog(final Minefield parent) {
		dialogBox = new DialogBox();
		dialogBox.setText("Options");

		final RadioButton easyRadio = new RadioButton("sizes", "Easy");
		final RadioButton mediumRadio = new RadioButton("sizes", "Medium");
		final RadioButton hardRadio = new RadioButton("sizes", "Hard");
		final RadioButton customRadio = new RadioButton("sizes", "Custom");

		VerticalPanel sizesLayout = new VerticalPanel();
		sizesLayout.add(easyRadio);
		sizesLayout.add(mediumRadio);
		sizesLayout.add(hardRadio);
		sizesLayout.add(customRadio);

		final TextBox rowsTextBox = new TextBox();
		rowsTextBox.setValue(parent.getRows() + "");

		final TextBox colsTextBox = new TextBox();
		colsTextBox.setValue(parent.getCols() + "");

		final TextBox minesTextBox = new TextBox();
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
					parent.setCols(8);
					parent.setRows(8);
					parent.setMinesNum(10);
				} else if (mediumRadio.getValue()) {
					parent.setCols(16);
					parent.setRows(16);
					parent.setMinesNum(40);
				} else if (hardRadio.getValue()) {
					parent.setCols(31);
					parent.setRows(16);
					parent.setMinesNum(99);
				} else if (customRadio.getValue()) {
					int cols = Math.min(100, Math.max(3, Integer
							.parseInt(colsTextBox.getValue())));
					int rows = Math.min(100, Math.max(3, Integer
							.parseInt(rowsTextBox.getValue())));
					int maxMines = cols * rows / 4;
					int minesNum = Math.min(maxMines, Math.max(1, Integer
							.parseInt(minesTextBox.getValue())));

					parent.setCols(cols);
					parent.setRows(rows);
					parent.setMinesNum(minesNum);
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

		dialogBox.setWidget(layout);
		dialogBox.center();
	}
}
