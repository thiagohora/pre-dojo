package br.com.logreader.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Game {

	private String id;
	private Set<Player> players = new TreeSet<Player>();
	private List<Murder> murders = new ArrayList<Murder>();
	private List<KillSequence> killSequence = new ArrayList<KillSequence>();
	private Player currentKiller;
	private int score;
	
	public List<KillSequence> getKillSequence() {
		return killSequence;
	}

	public void setKillSequence(List<KillSequence> killSequence) {
		this.killSequence = killSequence;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	public List<Murder> getMurders() {
		return murders;
	}

	public void setMurders(List<Murder> murders) {
		this.murders = murders;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Player getCurrentKiller() {
		return currentKiller;
	}

	public void setCurrentKiller(Player currentKiller) {
		this.currentKiller = currentKiller;
	}

}
