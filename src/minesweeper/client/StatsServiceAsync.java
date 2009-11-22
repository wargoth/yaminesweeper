package minesweeper.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StatsServiceAsync {
	void saveStats(int level, int time, AsyncCallback<Void> callback);
}
