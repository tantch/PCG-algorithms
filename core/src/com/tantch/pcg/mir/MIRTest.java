package com.tantch.pcg.mir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class MIRTest {

	public static void main(String[] args) throws IOException, InterruptedException {

		MIRTools mir = new MIRTools();
		mir.loadDescriptors("PianoBlack.mp3");
		
		System.out.println(mir);
		
	}

	/*private static float getBPM() throws IOException, InterruptedException {
		String cmdPath = "/home/pim/Downloads/essentia-2.1_beta3/build/src/examples/";
		String cmdName = "essentia_streaming_rhythmextractor_multifeature";

		String musicFilePath = "/home/pim/Music/";
		String musicName = "Rain.mp3";

		String outputFile = "output.yaml";

		ProcessBuilder pb = new ProcessBuilder(cmdPath + cmdName, musicFilePath + musicName);

		pb.directory(new File("/home/pim/Desktop"));

		File log = new File("/home/pim/Desktop/log.yaml");
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(log));
		Process p = pb.start();
		p.waitFor();

		try (BufferedReader br = new BufferedReader(new FileReader(log))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.split(":")[0].equals("bpm")) {
					return Float.parseFloat(line.split(":")[1]);
				}
			}
		}

		return -1;

	}*/
	
	

}
