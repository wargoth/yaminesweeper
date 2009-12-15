package minesweeper.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class CongratsDialog {
	private DialogBox dialogBox = new DialogBox();
	private Minefield parent;

	public CongratsDialog(Minefield parent) {
		this.parent = parent;

		dialogBox.setText("Congratulations, you win!");

		Button okButton = new Button("OK");
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});

		Grid layout = new Grid(3, 2);

		if (parent.getLevel().getLevel() != Level.CUSTOM) {
			if (Minesweeper.User.isLoggedIn()) {
				layout.setWidget(0, 0, new Label("User name:"));
				layout.setWidget(0, 1, new Label(Minesweeper.User.getEmail()));
			} else {
				layout.setWidget(0, 0, new HTML("<a href=\""
						+ Minesweeper.User.getLoginUrl()
						+ "\">Login</a> to save your result"));
			}
		}

		layout.setWidget(1, 0, new Label("Time:"));
		layout.setWidget(1, 1, new Label(GameTimer.getInstance().getText()));
		layout.setWidget(2, 1, okButton);

		dialogBox.setWidget(layout);
		dialogBox.center();

		sendStats();
	}

	private void sendStats() {
		if (parent.getLevel().getLevel() != Level.CUSTOM
				&& Minesweeper.User.isLoggedIn()) {
			StatsServiceAsync statsService = GWT.create(StatsService.class);
			statsService.saveStats(parent.getLevel().getLevel(), GameTimer
					.getInstance().getTime(), new AsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
				}

				@Override
				public void onFailure(Throwable caught) {
				}
			});
		}
	}
}
