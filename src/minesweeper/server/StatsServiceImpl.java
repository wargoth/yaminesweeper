package minesweeper.server;

import java.util.Date;

import javax.jdo.PersistenceManager;

import minesweeper.client.StatsService;



import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class StatsServiceImpl extends RemoteServiceServlet implements
		StatsService {
	@Override
	public void saveStats(int level, int time) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user != null) {
			Date created = new Date();
			Stat stat = new Stat(user, time, level, created);

			PersistenceManager pm = PMF.get().getPersistenceManager();

			try {
				pm.makePersistent(stat);
			} finally {
				pm.close();
			}
		}
	}
}
