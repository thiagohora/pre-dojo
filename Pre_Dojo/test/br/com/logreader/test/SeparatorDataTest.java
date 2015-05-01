package br.com.logreader.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.logreader.finddata.SeparatorData;
import br.com.logreader.model.Game;
import br.com.logreader.model.Player;
import br.com.logreader.utils.LogKeys;

public class SeparatorDataTest {

	@Test
	public void testCase1() throws IOException, ParseException {
		
		String dataTest1 = "23/04/2013 15:34:22 - New match 11348965 has started";
		
		SeparatorData separatorData = new SeparatorData();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		separatorData.setData(dataTest1);
		Assert.assertEquals("11348965", separatorData.getId());
		Assert.assertEquals(format.parse("23/04/2013 15:34:22"), separatorData.getDateTime());
		Assert.assertTrue(separatorData.checkInitGame());
	}
	
	@Test
	public void testCase2() throws IOException, ParseException {
		
		String dataTest2 = "23/04/2013 15:36:04 - John killed Roman using M16";
		SeparatorData separatorData = new SeparatorData();
		
		separatorData.setData(dataTest2);
		
		Assert.assertTrue(separatorData.typeLine() == LogKeys.MURDER_PLAYER);
		Assert.assertEquals("Roman", separatorData.getKilled(LogKeys.MURDER_PLAYER));
		Assert.assertEquals("John", separatorData.getKiller());
		Assert.assertEquals("M16", separatorData.getGun());
	}	
		
	@Test
	public void testCase3() throws IOException, ParseException {
		
		String dataTest3 = "23/04/2013 15:36:33 - <WORLD> killed Mike by DROWN";
		SeparatorData separatorData = new SeparatorData();	
		
		separatorData.setData(dataTest3);
		Assert.assertTrue(separatorData.typeLine() == LogKeys.MURDER_WORLD);
		Assert.assertEquals("Mike", separatorData.getKilled(LogKeys.MURDER_WORLD));
		
	}	
	
	@Test
	public void testCase4() throws IOException, ParseException {
		
		String dataTest4 = "23/04/2013 15:39:22 - Match 11348965 has ended";
		SeparatorData separatorData = new SeparatorData();
		
		separatorData.setData(dataTest4);
		Assert.assertEquals("11348965", separatorData.getIdFinish());
		Assert.assertTrue(separatorData.typeLine() == LogKeys.FINISH_GAME);
	}
	
	@Test
	public void testGame() throws IOException, ParseException {
	
		List<Game> games = new ArrayList<Game>();
		Game game = null;
		SeparatorData separatorData = new SeparatorData();
		
		String [] lines = {
			"23/04/2013 15:34:22 - New match 11348965 has started",
			"23/04/2013 15:36:04 - Roman killed Nick using M16",
			"23/04/2013 15:36:04 - John killed Roman using M16",
			"23/04/2013 15:36:33 - <WORLD> killed John by DROWN",
			"23/04/2013 15:36:33 - <WORLD> killed Mike by DROWN",
			"23/04/2013 15:36:04 - Mark killed Teste using M16",
			"23/04/2013 15:36:04 - Mark killed Teste1 using M16",
			"23/04/2013 15:36:04 - Mark killed Teste2 using M16",
			"23/04/2013 15:39:22 - Match 11348965 has ended"			
		};
		
		for (String data : lines) {
			
			separatorData.setData(data);
			
			if(separatorData.checkInitGame()) {
				if(game == null) {
					game = new Game();
					game.setId(separatorData.getId());
				} else {
					fail(LogKeys.INVALIDFORMAT);
				}
			} else if(data != null && game == null) {
				fail(LogKeys.INVALIDFORMAT);
			} else {
				
				switch (separatorData.typeLine()) {
					case LogKeys.FINISH_GAME:
						if(game.getId().equals(separatorData.getIdFinish())){
							games.add(game);
							game = null;
						} else {
							fail(LogKeys.INVALIDFORMAT);
						}
					break;
					
					case LogKeys.MURDER_PLAYER:
						separatorData.addRoundMurder(game);
					break;
					
					case LogKeys.MURDER_WORLD:
						separatorData.addRoundWorld(game);
					break;
	
					default:
						fail(LogKeys.INVALIDFORMAT);
				}
			}
		}
		
		
		Assert.assertEquals(1, games.size());
		
		game = games.get(0);
		
		Assert.assertTrue(game.getPlayers().contains(new Player("Roman")));
		Assert.assertTrue(game.getPlayers().contains(new Player("Nick")));
		Assert.assertTrue(game.getPlayers().contains(new Player("John")));
		Assert.assertTrue(game.getPlayers().contains(new Player("Mark")));
		Assert.assertTrue(game.getPlayers().contains(new Player("Mike")));
		Assert.assertTrue(game.getPlayers().contains(new Player("Teste")));
		Assert.assertTrue(game.getPlayers().contains(new Player("Teste1")));
		Assert.assertTrue(game.getPlayers().contains(new Player("Teste2")));
		
		List<Player> players = new ArrayList<Player>(game.getPlayers());
		Collections.sort(players, new Player.ComparatorPlayer());
		
		// Primeiro colocado
		Assert.assertTrue(players.get(0).equals(new Player("Mark")));
		Assert.assertEquals(3, players.get(0).getMurders());
		Assert.assertEquals(0, players.get(0).getDeaths());
		
		
	}
}