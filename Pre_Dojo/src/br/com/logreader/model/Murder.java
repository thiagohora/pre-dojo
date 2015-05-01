package br.com.logreader.model;

import java.util.Date;

public class Murder {

	private Date dateTime;
	private Player killer;
	private Player killed;
	private String killedWith;
	
	public Murder() { }
	
	public Murder(Date dateTime, Player killer, Player killed, String killedWith) {
		super();
		this.dateTime = dateTime;
		this.killer = killer;
		this.killed = killed;
		this.killedWith = killedWith;
	}

	public Date getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Player getKiller() {
		return killer;
	}

	public void setKiller(Player killer) {
		this.killer = killer;
	}

	public Player getKilled() {
		return killed;
	}

	public void setKilled(Player killed) {
		this.killed = killed;
	}

	public String getKilledWith() {
		return killedWith;
	}

	public void setKilledWith(String killedWith) {
		this.killedWith = killedWith;
	}
}
