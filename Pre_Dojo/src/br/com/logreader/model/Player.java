package br.com.logreader.model;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Player implements Comparable<Player> {

	private String name;
	private Integer deaths;
	private Integer murders;
	private Set<KillGunScore> killedWith = new TreeSet<KillGunScore>();
	private int KillSequence;
	
	public static class ComparatorPlayer implements Comparator<Player> {
		@Override
		public int compare(Player o1, Player o2) {
			return (o2.getMurders() - o2.getDeaths()) - (o1.getMurders() - o1.getDeaths());
		}
	}
	
	public Player(String name) {
		this.deaths = 0;
		this.KillSequence = 0;
		this.murders = 0;
		this.name = name;
	}
	
	public Player() {
		this.deaths = 0;
		this.KillSequence = 0;
		this.murders = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public int getMurders() {
		return murders;
	}
	
	public void setMurders(int murders) {
		this.murders = murders;
	}
	
	public Set<KillGunScore> getKilledWith() {
		return killedWith;
	}
	
	public int getKillSequence() {
		return KillSequence;
	}

	public void setKillSequence(int killSequence) {
		KillSequence = killSequence;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Player o) {
		return this.name.compareTo(o.getName());		
	}

}