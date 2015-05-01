package br.com.logreader.reader;

import java.awt.HeadlessException;
import java.io.IOException;

import br.com.logreader.view.MainForm;

public class LogFileReader {
	public static void main(String[] args) throws HeadlessException, IOException {
		MainForm screen = new MainForm();
		screen.setVisible(true); 
	}	
}