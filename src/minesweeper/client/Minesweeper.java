package minesweeper.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Minesweeper implements EntryPoint {
	public static class User {
		public static final native boolean isLoggedIn() /*-{
			return $wnd.is_logged_in;
		}-*/;

		public static final native String getEmail() /*-{
			return $wnd.user_email;
		}-*/;

		public static final native String getLoginUrl() /*-{
			return $wnd.login_url;
		}-*/;
	}

	public void onModuleLoad() {
		// set uncaught exception handler
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable throwable) {
				String text = "Uncaught exception: ";
				while (throwable != null) {
					StackTraceElement[] stackTraceElements = throwable
							.getStackTrace();
					text += throwable.toString() + "\n";
					for (int i = 0; i < stackTraceElements.length; i++) {
						text += "    at " + stackTraceElements[i] + "\n";
					}
					throwable = throwable.getCause();
					if (throwable != null) {
						text += "Caused by: ";
					}
				}
				DialogBox dialogBox = new DialogBox(true);
				DOM.setStyleAttribute(dialogBox.getElement(),
						"backgroundColor", "#ABCDEF");
				System.err.print(text);
				text = text.replaceAll(" ", " ");
				dialogBox.setHTML("<pre>" + text + "</pre>");
				dialogBox.center();
			}
		});

		// use a deferred command so that the handler catches onModuleLoad2()
		// exceptions
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				onModuleLoad2();
			}
		});
	}

	public void onModuleLoad2() {
		final Minefield minefield = new Minefield();
		minefield.init();

		Grid sidePanel = new Grid(2, 2);
		
		Label timer = new Label(GameTimer.getInstance().getText());
		GameTimer.getInstance().addListener(timer);

		sidePanel.setWidget(0, 0, new Label("Time:"));
		sidePanel.setWidget(0, 1, timer);
		sidePanel.setWidget(1, 0, new Label("Mines:"));
		sidePanel.setWidget(1, 1, minefield.getMinesLeft().getWidget());

		HorizontalPanel menuLayout = new HorizontalPanel();

		Button newGameButton = new Button("New game");
		newGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				minefield.init();
			}
		});

		menuLayout.add(newGameButton);

		Button optionsButton = new Button("Options");
		optionsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				minefield.showOptionsDialog();
			}
		});

		menuLayout.add(optionsButton);

		DockPanel layout = new DockPanel();

		layout.add(minefield.getWidget(), DockPanel.CENTER);
		layout.add(menuLayout, DockPanel.NORTH);
		layout.add(sidePanel, DockPanel.EAST);

		RootPanel.get("root").add(layout);
	}
}
