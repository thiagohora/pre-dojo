	package br.com.logreader.properties;

import java.io.IOException;
import java.util.PropertyResourceBundle;

public class PropertiesUtils {

	private static final String LITERAL = "literal.properties";
	
	public  static final String INIT_GAME = "game.init";
	public  static final String ID_GAME = "game.id";
	public  static final String FMT_DATE = "format.date";
	public  static final String MURDER_WORLD = "game.murder.world";
	public  static final String MURDER_PLAYER = "game.murder.player";
	public  static final String TIME = "game.time.play";
	public  static final String FINISH_GAME = "game.finish";
	public  static final String APP_TITLE = "app.title";
	public  static final String LOG_FONT = "app.font";
	public  static final String BTN_TITLE = "app.btn.title";
	
	public static String getPropValues(String propertie) throws IOException {
		PropertyResourceBundle prop = new PropertyResourceBundle(PropertiesUtils.class.getResourceAsStream(LITERAL));		
		return prop.getString(propertie);
	}
	
}