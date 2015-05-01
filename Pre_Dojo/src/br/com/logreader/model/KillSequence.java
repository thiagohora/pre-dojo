package br.com.logreader.model;

import java.util.Comparator;

public class KillSequence  {
	
	private Player player;
	private int score;
	
	public static class ComparatorKillSequence implements Comparator<KillSequence> {
		@Override
		public int compare(KillSequence o1, KillSequence o2) {
			return o2.getScore() - o1.getScore();
		}
	}
	
	public KillSequence() { }
	
	public KillSequence(Player player, int score) {
		super();
		this.player = player;
		this.score = score;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
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
		KillSequence other = (KillSequence) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}

}