package minesweeper.client;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;

public class GameTimer extends LabelObserver {
	private int ticks = 0;
	private boolean is_started = false;
	private Timer timer = new Timer() {
		@Override
		public void run() {
			ticks++;
			setUpdated();
		}
	};

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

	@Override
	public String getText() {
		int mins = (int) Math.floor((double) ticks / 60);
		int secs = ticks % 60;

		NumberFormat fmt = NumberFormat.getFormat("00");
		return fmt.format(mins) + ":" + fmt.format(secs);
	}

	public int getTime() {
		return ticks;
	}
}
