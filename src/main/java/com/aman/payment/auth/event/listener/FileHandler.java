package com.aman.payment.auth.event.listener;

import java.io.File;

import org.apache.log4j.Logger;

public class FileHandler {

	final static Logger logger = Logger.getLogger(FileHandler.class);

	public File handleFile(File input) throws Exception {
		
		System.out.println("FileHandler = "+input.getName());

		return input;
	}

}
