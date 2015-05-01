package br.com.logreader.exceptions;

import br.com.logreader.utils.LogKeys;

public class LaunchException {

	public static RuntimeException FormatFileException(){
		return new RuntimeException(LogKeys.INVALIDFORMAT);
	}
	
}
