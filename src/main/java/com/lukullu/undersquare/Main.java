package com.lukullu.undersquare;

import com.kilix.p2p.client.KilixP2PClient;
import com.kilix.processing.ProcessingSketch;
import com.lukullu.undersquare.common.IO;

import java.io.*;

public class Main {
	
	public static void main(String[] args) {
		try {
			initTrust();
		} catch (IOException e) {
			System.err.println("Unable to validate TrustStore. Exiting");
			System.exit(-1);
		}
		KilixP2PClient.setTrustStore("data/trust.jks","changeit");

		ProcessingSketch<UnderSquare> gameSketch = ProcessingSketch
				.builder(UnderSquare.class)
				.fullScreen()
				.build();

	}

	public static void initTrust() throws IOException {
		File trustStoreFile = new File("data/", "trust.jks");
		if (trustStoreFile.isFile()) return; // "normal" state

		if (trustStoreFile.exists()); // fail + return

		if (! trustStoreFile.getParentFile().exists())  // if folder does not exist
			trustStoreFile.getParentFile().mkdirs(); 	// create folder
		trustStoreFile.createNewFile();

		// 1: get inputstream of trust-store inside jar
		InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("trust.jks");
		// 2: get outputStream to external trust-store file
		FileOutputStream fileOutputStream = new FileOutputStream(trustStoreFile);
		// 3: copy from in to out
		inputStream.transferTo(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();
		inputStream.close();
	}
}
