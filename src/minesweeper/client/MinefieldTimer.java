package minesweeper.client;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;

public class MinefieldTimer extends Timer {
	private int ticks = 0;
	private Label widget = new Label();
	private boolean is_started = false;

	public void init() {
		stop();
		ticks = 0;
		widget.setText("00:00");
	}

	public void start() {
		if (!is_started) {
			is_started = true;
			scheduleRepeating(1000);
		}
	}

	public void stop() {
		is_started = false;
		cancel();
	}

	public Label getWidget() {
		return widget;
	}

	@Override
	public void run() {
		ticks++;
		int mins = (int) Math.floor((double) ticks / 60);
		int secs = ticks % 60;

		NumberFormat fmt = NumberFormat.getFormat("00");
		widget.setText(fmt.format(mins) + ":" + fmt.format(secs));
	}

	public int getTime() {
		return ticks;
	}
}
