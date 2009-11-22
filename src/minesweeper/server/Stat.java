package minesweeper.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Stat {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private User user;

	@Persistent
	private int time;

	@Persistent
	private int level;

	@Persistent
	private Date created;

	public Stat(User user, int time, int level, Date created) {
		this.user = user;
		this.time = time;
		this.level = level;
		this.created = created;
	}

	public Key getKey() {
		return key;
	}

	public User getAuthor() {
		return user;
	}

	public int getTime() {
		return time;
	}

	public int getLevel() {
		return level;
	}

	public Date getCreated() {
		return created;
	}

	public void setAuthor(User user) {
		this.user = user;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
