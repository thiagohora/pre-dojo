package br.com.logreader.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import br.com.logreader.model.KillGunScore;
import br.com.logreader.model.KillSequence;
import br.com.logreader.model.Player;

public class Utils {

	public static boolean isEmpty(String data){
		return (data == null || data == LogKeys.EMPTY);
	}
	
	public static Player getPlayerForName(String name, Set<Player> players) {
		Iterator<Player> iterator = players.iterator();
		while(iterator.hasNext()){
			Player player = iterator.next(); 
			if(player.getName().equals(name)){
				return player;
			}
		}
		return null;
	}
	
	public static KillGunScore getGunName(String name, Set<KillGunScore> killGunScores) {
		Iterator<KillGunScore> iterator = killGunScores.iterator();
		while(iterator.hasNext()){
			KillGunScore killGunScore = iterator.next(); 
			if(killGunScore.getKilledWith().equals(name)){
				return killGunScore;
			}
		}
		return null;
	}
	
	public static List<KillSequence> getKillScore(List<KillSequence> killSequences) {
		
		Collections.sort(killSequences, new KillSequence.ComparatorKillSequence());
		List<KillSequence> killScores = new ArrayList<KillSequence>();
		
		int score = killSequences.get(0).getScore();
		
		for (KillSequence killSequence : killSequences) {
			if(killSequence.getPlayer().getDeaths() == 0 && killSequence.getScore() == score){
				killScores.add(killSequence);
			}
		}
		
		return killScores;
	}
}
