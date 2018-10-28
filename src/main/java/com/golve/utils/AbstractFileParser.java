package com.golve.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

/**
 * parse a file of a specific extension
 * subclasses should define the returned value's type (T) and implement the actual parse logic ({@link #parse(String)})
 * @author pazb
 *
 * @param <T> the returned value type
 */
public abstract class AbstractFileParser<T> {
	private final String extension;

	public AbstractFileParser(String extension) {
		this.extension = extension;
	}
	
	public T getRulesData(String filePath) throws Exception{
		String ext = filePath.substring(filePath.lastIndexOf(".")+1);
		if (!ext.equals(extension)) {
			throw new Exception("file extension (" + ext + ") is not supported by this parser (try a file with ." + extension + " extension).");
		}
		return parse(filePath);
	}
	
	protected abstract T parse(String path) throws FileNotFoundException, IOException, java.text.ParseException, ParseException;
}
