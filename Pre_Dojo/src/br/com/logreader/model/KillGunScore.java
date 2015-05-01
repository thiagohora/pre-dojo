package br.com.logreader.model;

import java.util.Comparator;

public class KillGunScore implements Comparable<KillGunScore> {

	private String killedWith;
	private Integer score;
	
	public static class ComparatorKillGunScore implements Comparator<KillGunScore> {
		@Override
		public int compare(KillGunScore o1, KillGunScore o2) {
			return o2.getScore() - o1.getScore();
		}
	}
	
	public KillGunScore() {
		this.score = 0;
	}
	
	public String getKilledWith() {
		return killedWith;
	}
	public void setKilledWith(String killedWith) {
		this.killedWith = killedWith;
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
		result = prime * result
				+ ((killedWith == null) ? 0 : killedWith.hashCode());
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
		KillGunScore other = (KillGunScore) obj;
		if (killedWith == null) {
			if (other.killedWith != null)
				return false;
		} else if (!killedWith.equals(other.killedWith))
			return false;
		return true;
	}

	@Override
	public int compareTo(KillGunScore o) {
		return this.killedWith.compareTo(o.getKilledWith());
	}
}
