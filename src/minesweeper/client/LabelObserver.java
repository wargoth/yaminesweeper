package minesweeper.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Label;

public abstract class LabelObserver {
	private ArrayList<Label> listeners = new ArrayList<Label>();

	public void addListener(Label label) {
		listeners.add(label);
	}

	public void removeListener(Label label) {
		listeners.remove(label);
	}

	public void setUpdated() {
		for (Label listener : listeners) {
			listener.setText(getText());
		}
	}

	public abstract String getText();
}
