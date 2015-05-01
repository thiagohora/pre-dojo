package br.com.logreader.view;

import static br.com.logreader.properties.PropertiesUtils.*;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.logreader.exceptions.LaunchException;
import br.com.logreader.finddata.SeparatorData;
import br.com.logreader.model.Game;
import br.com.logreader.utils.LogKeys;	

public class MainForm extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton selectFile = new JButton(getPropValues(BTN_TITLE));
	private JTextArea out = new JTextArea(20, 50);
	
	public MainForm() throws HeadlessException, IOException {
		
		super(getPropValues(APP_TITLE));
		
		setLayout( new FlowLayout() );
		
		JScrollPane scrollPane = new JScrollPane(out);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		Font font = new Font(getPropValues(LOG_FONT), Font.BOLD, 12);
	    out.setFont(font);
	    out.setForeground(Color.BLACK);
	    out.setBorder(border);
	    out.setEditable(false);
	    
		selectFile.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 420);
		
		getContentPane().add(this.selectFile);		
		getContentPane().add(scrollPane);
		
		setResizable(false);
	}
	
	
	@SuppressWarnings("resource")
	public void actionPerformed(ActionEvent e)
	{
	  	
		File file;
		
		JFileChooser saveFile = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		
		saveFile.setFileFilter(filter);
		
	    if (saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
	        file = saveFile.getSelectedFile();
	    } else {
	    	return;
	    }
		
		BufferedReader reader = null;
		FileReader 	   fr     = null;
		
		try {
			
			SeparatorData  separatorData = new SeparatorData();
			fr = new FileReader(file);
			reader = new BufferedReader(fr);
			List<Game> games = new ArrayList<Game>();     
			String data = null;
			Game game = null;
			
			while ((data = reader.readLine())!= null) {
				
				separatorData.setData(data);
				
				if(separatorData.checkInitGame()) {
					if(game == null) {
						game = new Game();
						game.setId(separatorData.getId());
					} else {
						throw LaunchException.FormatFileException();
					}
				} else if(data != null && game == null) {
					throw LaunchException.FormatFileException();
				} else {
					
					switch (separatorData.typeLine()) {
						case LogKeys.FINISH_GAME:
							if(game.getId().equals(separatorData.getIdFinish())){
								games.add(game);
								game = null;
							} else {
								throw LaunchException.FormatFileException();
							}
						break;
						
						case LogKeys.MURDER_PLAYER:
							separatorData.addRoundMurder(game);
						break;
						
						case LogKeys.MURDER_WORLD:
							separatorData.addRoundWorld(game);
						break;
	
						default:
							throw LaunchException.FormatFileException();
					}
				}
			}
			
			separatorData.printResult(games, out);
			reader.close();
			
		} catch (FileNotFoundException ex) {
			out.append(LogKeys.FILENOTFOUND+"\n");
		} catch (IOException ex) {
			out.append(LogKeys.ERRORREADEFILE+"\n");
		} catch (RuntimeException ex) {
			out.append(ex.getMessage());
		} catch (Exception ex) {
			out.append(LogKeys.INVALIDFORMAT+"\n");
		}
	}
}