package br.com.logreader.finddata;

import static br.com.logreader.properties.PropertiesUtils.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JTextArea;

import br.com.logreader.model.Game;
import br.com.logreader.model.KillGunScore;
import br.com.logreader.model.KillSequence;
import br.com.logreader.model.Murder;
import br.com.logreader.model.Player;
import br.com.logreader.utils.Utils;
import br.com.logreader.utils.LogKeys;

public class SeparatorData {

	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public boolean checkInitGame() throws IOException {
		return (!Utils.isEmpty(data) && data.matches(getPropValues(INIT_GAME)));
	}
	
	public String getId() throws IOException {
		return data.replaceAll(getPropValues(ID_GAME), "$2");
	}
	
	public String getIdFinish() throws IOException {
		return data.replaceAll(getPropValues(FINISH_GAME), "$2");
	}
	
	public Date getDateTime() throws ParseException, IOException {
		DateFormat format = new SimpleDateFormat(getPropValues(FMT_DATE));
		return  format.parse(data.replaceAll(getPropValues(TIME), "$1"));
	}
	
	public String getKiller() throws IOException {
		return data.replaceAll(getPropValues(MURDER_PLAYER), "$2");
	}
	
	public String getKilled(int type) throws IOException {
		if(LogKeys.MURDER_PLAYER == type){
			return data.replaceAll(getPropValues(MURDER_PLAYER), "$3");
		} else if(LogKeys.MURDER_WORLD == type){
			return data.replaceAll(getPropValues(MURDER_WORLD), "$3");
		}
		return null; 
	}
	
	public String getGun() throws IOException {
		return data.replaceAll(getPropValues(MURDER_PLAYER), "$4");
	}
	
	public Player getPlayer(String name, Game game) {
		Player player = Utils.getPlayerForName(name, game.getPlayers());
		if(player==null) {
			player = new Player();
			player.setName(name);
		}
		return player;
	}
	
	public KillGunScore getKillGun(String name, Player player) {
		KillGunScore killGunScore = Utils.getGunName(name, player.getKilledWith());
		if(killGunScore==null) {
			killGunScore = new KillGunScore();
			killGunScore.setKilledWith(name);
		}
		return killGunScore;
	}
	
	
	public int typeLine() throws IOException  {
		if(data.matches(getPropValues(FINISH_GAME))){
			return LogKeys.FINISH_GAME;
		} else if(data.matches(getPropValues(MURDER_PLAYER))){
			return LogKeys.MURDER_PLAYER;
		} else if(data.matches(getPropValues(MURDER_WORLD))){
			return LogKeys.MURDER_WORLD;
		} else {
			return 0;
		}
	}
	
	public void addRoundMurder(Game game) throws IOException, ParseException  {
		
		String killerName = getKiller();
		String killedName = getKilled(LogKeys.MURDER_PLAYER);
		String gun = getGun();

		Player killer = getPlayer(killerName, game);
		Player killed = getPlayer(killedName, game);
		
		game.getPlayers().add(killed);
		game.getPlayers().add(killer);
		
		game.getMurders().add(new Murder(getDateTime(), killer, killed, gun));
		
		KillGunScore killGunScore = getKillGun(gun,killer);
		killGunScore.setScore(killGunScore.getScore()+1);
		
		killer.getKilledWith().add(killGunScore);
		killer.setMurders(killer.getMurders()+1);
		
		killed.setDeaths(killed.getDeaths()+1);
		
		if(killer.equals(game.getCurrentKiller())) {
			game.setScore(game.getScore()+1);
		} else {
			if(game.getScore() >= 2){
				game.getKillSequence().add(new KillSequence(game.getCurrentKiller(), game.getScore()));
			}
			game.setCurrentKiller(killer);
			game.setScore(1);
		}
	}
	
	public void addRoundWorld(Game game) throws IOException, ParseException  {
		
		String killedName = getKilled(LogKeys.MURDER_WORLD);
		
		Player killed = getPlayer(killedName, game);
		
		game.getPlayers().add(killed);
				
		killed.setDeaths(killed.getDeaths()+1);
		
		game.setCurrentKiller(null);
	}
	
	
	public void printResult(List<Game> games, JTextArea out) {
		
		out.setText(LogKeys.EMPTY);
		
		for (Game game : games) {
			
			out.append("-------------------- Inicio Game: "+game.getId()+" --------------------"+LogKeys.NEWLINE);
			
			if(game.getScore() >= 2){
				game.getKillSequence().add(new KillSequence(game.getCurrentKiller(), game.getScore()));
			}
			
			List<Player> playersOrder = new ArrayList<Player>(game.getPlayers());
			Collections.sort(playersOrder, new Player.ComparatorPlayer());
			
			int numMurders = playersOrder.get(0).getMurders();
			int numDeaths = playersOrder.get(0).getDeaths();
			
			
			
			for(Player player : playersOrder) {
				out.append("Jogador: "+player.getName()+" matou: "+player.getMurders()+" morreu "+player.getDeaths()+"."+LogKeys.NEWLINE);
			}
			
			out.append(LogKeys.NEWLINE);
			
			for(Player player : playersOrder) {
				if(numMurders == player.getMurders() && numDeaths == player.getDeaths()){
					
					if(player.getDeaths() == 0){
						out.append("O jogador: " +player.getName()+" ganhou award por vencer sem morrer."+LogKeys.NEWLINE);
					}
					
					List<KillGunScore> killGunScores = new ArrayList<KillGunScore>(player.getKilledWith());
					Collections.sort(killGunScores, new KillGunScore.ComparatorKillGunScore());
					
					out.append("A armar preferida de "+player.getName()+" é: "+ killGunScores.get(0).getKilledWith()+", que matou: "+killGunScores.get(0).getScore()+" vezes."+LogKeys.NEWLINE);
					out.append(LogKeys.NEWLINE);
					
				} else {
					break;
				}
			}
			
			List<KillSequence> killSequences = Utils.getKillScore(game.getKillSequence());
			
			if(killSequences.size()>0) {
				out.append("Maior sequência de assassinatos:"+LogKeys.NEWLINE);
				for (KillSequence killSequence : killSequences) {
					out.append("Jogador: "+killSequence.getPlayer().getName()+", numero assassinatos em sequência: "+killSequence.getScore()+LogKeys.NEWLINE);
				} 
			}
			
			out.append("-------------------- Fim Game: "+game.getId()+" ----------------------"+LogKeys.NEWLINE);
		}
	}
}
