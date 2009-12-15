package minesweeper.client;

import java.util.ArrayList;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;

public class GameTimer {
	private ArrayList<Label> listeners = new ArrayList<Label>();
	private int ticks = 0;
	private boolean is_started = false;
	private static GameTimer instance;

	private Timer timer = new Timer() {
		@Override
		public void run() {
			ticks++;
			setUpdated();
		}
	};

	private GameTimer() {
	}

	public static GameTimer getInstance() {
		if(instance == null) {
			instance = new GameTimer();
		}
		return instance;
	}

	public void init() {
		stop();
		ticks = 0;
		setUpdated();
	}

	public void start() {
		if (!is_started) {
			is_started = true;
			timer.scheduleRepeating(1000);
		}
	}

	public void stop() {
		is_started = false;
		timer.cancel();
	}

	public String getText() {
		int mins = (int) Math.floor((double) ticks / 60);
		int secs = ticks % 60;

		NumberFormat fmt = NumberFormat.getFormat("00");
		return fmt.format(mins) + ":" + fmt.format(secs);
	}

	public int getTime() {
		return ticks;
	}

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
}