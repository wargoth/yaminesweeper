package minesweeper.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("stats")
public interface StatsService extends RemoteService {
	void saveStats(int level, int time);
}