package com.tantch.pcg.mir;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MIRTools {

	private int beatCount;
	private float bpmG;
	private float bpmMF;
	private float avgLoudness;
	private float length;

	// highlevel

	private float danceable;
	private float male;
	private String genreDor, genreEle, genreRos, genreTza, rhythmDance;
	private float genreDorPb, genreElePb, genreRosPb, genreTzaPb, rhythmDancePb;
	private float acoustic;
	private float aggressive;
	private float electronic;
	private float happy;
	private float party;
	private float relaxed;
	private float sad;

	private float dark;
	private float tonal;
	private float instrumental;
	private String name;
	private float pitch;
	private String keyScale;
	private String keyKey;

	public boolean loadDescriptors(String musicPath) throws IOException, InterruptedException, ParseException {

		String cmdPath = "/home/pim/Desktop/essentia/";
		String cmdName = "essentia_streaming_extractor_music";

		String outputFile = "output.json";

		ProcessBuilder pb = new ProcessBuilder(cmdPath + cmdName, musicPath, outputFile);

		pb.directory(new File("/home/pim/Desktop"));

		File log = new File("/home/pim/Desktop/log.yaml");
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(log));
		Process p = pb.start();
		p.waitFor();

		JSONParser json = new JSONParser();
		JSONObject base = (JSONObject) json.parse(new FileReader("/home/pim/Desktop/output.json"));

		extractLowLevelDescriptors(base);

		cmdName = "essentia_streaming_extractor_music_svm";

		pb = new ProcessBuilder(cmdPath + cmdName, "../" + outputFile, "../output2.json");
		pb.directory(new File("/home/pim/Desktop/essentia"));
		File log2 = new File("/home/pim/Desktop/log2.yaml");
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(log2));
		p = pb.start();
		p.waitFor();

		base = (JSONObject) json.parse(new FileReader("/home/pim/Desktop/output2.json"));

		JSONObject highL = (JSONObject) base.get("highlevel");
		parseHighLevelDescriptors(highL);

		return true;

	}

	private void extractLowLevelDescriptors(JSONObject base) {
		JSONObject rhythm = (JSONObject) base.get("rhythm");
		this.beatCount = objectToInt(rhythm.get("beats_count"));
		this.bpmG = objectToFloat(rhythm.get("bpm"));
		this.avgLoudness = objectToFloat(((JSONObject) base.get("lowlevel")).get("average_loudness"));
		this.pitch = objectToFloat(
				((JSONObject) ((JSONObject) base.get("lowlevel")).get("pitch_salience")).get("mean"));
		JSONObject meta = (JSONObject) base.get("metadata");
		this.length = objectToFloat(((JSONObject) meta.get("audio_properties")).get("length"));
		this.name = (String) ((JSONObject) meta.get("tags")).get("file_name");
		this.keyScale = (String) ((JSONObject) base.get("tonal")).get("key_scale");
		this.keyKey = (String) ((JSONObject) base.get("tonal")).get("key_key");
	}

	private void parseHighLevelDescriptors(JSONObject highL) {
		this.danceable = objectToFloat(((JSONObject) highL.get("danceability")).get("probability"));

		if (!((String) ((JSONObject) highL.get("danceability")).get("value")).equals("danceable")) {
			this.danceable = 1 - danceable;
		}
		this.male = objectToFloat(((JSONObject) highL.get("gender")).get("probability"));
		if (!((String) ((JSONObject) highL.get("gender")).get("value")).equals("male")) {
			this.male = 1 - male;
		}
		this.genreDor = (String) ((JSONObject) highL.get("genre_dortmund")).get("value");
		this.genreDorPb = objectToFloat(((JSONObject) highL.get("genre_dortmund")).get("probability"));
		this.genreEle = (String) ((JSONObject) highL.get("genre_electronic")).get("value");
		this.genreElePb = objectToFloat(((JSONObject) highL.get("genre_electronic")).get("probability"));
		this.genreRos = (String) ((JSONObject) highL.get("genre_rosamerica")).get("value");
		this.genreRosPb = objectToFloat(((JSONObject) highL.get("genre_rosamerica")).get("probability"));
		this.genreTza = (String) ((JSONObject) highL.get("genre_tzanetakis")).get("value");
		this.genreTzaPb = objectToFloat(((JSONObject) highL.get("genre_tzanetakis")).get("probability"));
		this.rhythmDance = (String) ((JSONObject) highL.get("ismir04_rhythm")).get("value");
		this.rhythmDancePb = objectToFloat(((JSONObject) highL.get("ismir04_rhythm")).get("probability"));
		this.acoustic = objectToFloat(((JSONObject) highL.get("mood_acoustic")).get("probability"));
		if (!((String) ((JSONObject) highL.get("mood_acoustic")).get("value")).equals("acoustic")) {
			this.acoustic = 1 - acoustic;
		}
		this.aggressive = objectToFloat(((JSONObject) highL.get("mood_aggressive")).get("probability"));
		if (!((String) ((JSONObject) highL.get("mood_aggressive")).get("value")).equals("aggressive")) {
			this.aggressive = 1 - aggressive;
		}
		this.electronic = objectToFloat(((JSONObject) highL.get("mood_electronic")).get("probability"));
		if (!((String) ((JSONObject) highL.get("mood_electronic")).get("value")).equals("electronic")) {
			this.electronic = 1 - electronic;
		}
		this.happy = objectToFloat(((JSONObject) highL.get("mood_happy")).get("probability"));
		if (!((String) ((JSONObject) highL.get("mood_happy")).get("value")).equals("happy")) {
			this.happy = 1 - happy;
		}
		this.party = objectToFloat(((JSONObject) highL.get("mood_party")).get("probability"));
		if (!((String) ((JSONObject) highL.get("mood_party")).get("value")).equals("party")) {
			this.party = 1 - party;
		}
		this.relaxed = objectToFloat(((JSONObject) highL.get("mood_relaxed")).get("probability"));
		if (!((String) ((JSONObject) highL.get("mood_relaxed")).get("value")).equals("relaxed")) {
			this.relaxed = 1 - relaxed;
		}
		this.sad = objectToFloat(((JSONObject) highL.get("mood_sad")).get("probability"));
		if (!((String) ((JSONObject) highL.get("mood_sad")).get("value")).equals("sad")) {
			this.sad = 1 - sad;
		}
		this.dark = objectToFloat(((JSONObject) highL.get("timbre")).get("probability"));
		if (!((String) ((JSONObject) highL.get("timbre")).get("value")).equals("dark")) {
			this.dark = 1 - dark;
		}
		this.tonal = objectToFloat(((JSONObject) highL.get("tonal_atonal")).get("probability"));
		if (!((String) ((JSONObject) highL.get("tonal_atonal")).get("value")).equals("tonal")) {
			this.tonal = 1 - tonal;
		}
		this.instrumental = objectToFloat(((JSONObject) highL.get("voice_instrumental")).get("probability"));
		if (!((String) ((JSONObject) highL.get("voice_instrumental")).get("value")).equals("instrumental")) {
			this.instrumental = 1 - instrumental;
		}
	}

	@Override
	public String toString() {
		String ret = "";
		ret += "Beat Count: " + this.beatCount + "\n";
		ret += "BPM: " + this.bpmG + "\n";
		ret += "Loudness: " + this.avgLoudness + "\n";
		ret += "Length: " + this.length + "\n";
		ret += "Danceable: " + this.danceable + "\n";
		ret += "Male: " + this.male + "\n";
		ret += "genreDor: " + this.genreDor + "\n";
		ret += "genreDorPb: " + this.genreDorPb + "\n";
		ret += "genreEle: " + this.genreEle + "\n";
		ret += "genreElePb: " + this.genreElePb + "\n";
		ret += "genreRos: " + this.genreRos + "\n";
		ret += "genreRosPb: " + this.genreRosPb + "\n";
		ret += "genreTza: " + this.genreTza + "\n";
		ret += "genreTzaPb: " + this.genreTzaPb + "\n";
		ret += "rhyhtm: " + this.rhythmDance + "\n";
		ret += "rhyhtmPb: " + this.rhythmDancePb + "\n";
		ret += "acoustic: " + this.acoustic + "\n";
		ret += "aggressive: " + this.aggressive + "\n";
		ret += "electronic: " + this.electronic + "\n";
		ret += "happy: " + this.happy + "\n";
		ret += "party: " + this.party + "\n";
		ret += "relaxed: " + this.relaxed + "\n";
		ret += "sad: " + this.sad + "\n";
		ret += "dark: " + this.dark + "\n";
		ret += "tonal: " + this.tonal + "\n";
		ret += "instrumental: " + this.instrumental + "\n";

		return ret;
	}

	public int getBeatCount() {
		return beatCount;
	}

	public float getBpmG() {
		return bpmG;
	}

	public float getBpmMF() {
		return bpmMF;
	}

	public float getAvgLoudness() {
		return avgLoudness;
	}

	public float getLength() {
		return length;
	}

	public float getDanceable() {
		return danceable;
	}

	public float getMale() {
		return male;
	}

	public String getGenreDor() {
		return genreDor;
	}

	public String getGenreEle() {
		return genreEle;
	}

	public String getGenreRos() {
		return genreRos;
	}

	public String getGenreTza() {
		return genreTza;
	}

	public String getRhythmDance() {
		return rhythmDance;
	}

	public float getGenreDorPb() {
		return genreDorPb;
	}

	public float getGenreElePb() {
		return genreElePb;
	}

	public float getGenreRosPb() {
		return genreRosPb;
	}

	public float getGenreTzaPb() {
		return genreTzaPb;
	}

	public float getRhythmDancePb() {
		return rhythmDancePb;
	}

	public float getAcoustic() {
		return acoustic;
	}

	public float getAggressive() {
		return aggressive;
	}

	public float getElectronic() {
		return electronic;
	}

	public float getHappy() {
		return happy;
	}

	public float getParty() {
		return party;
	}

	public float getRelaxed() {
		return relaxed;
	}

	public float getSad() {
		return sad;
	}

	public float getDark() {
		return dark;
	}

	public float getTonal() {
		return tonal;
	}

	public float getInstrumental() {
		return instrumental;
	}

	public String getName() {
		return name;
	}

	public float getPitch() {
		return pitch;
	}

	public String getKeyScale() {
		return keyScale;
	}

	public String getKeyKey() {
		return keyKey;
	}

	public float objectToFloat(Object ob) {
		if (ob instanceof Double) {
			return (float)(double) ob;
		}
		return (float)(long) ob;
	}
	
	public int objectToInt(Object ob){
		if (ob instanceof Double) {
			return (int)(double) ob;
		}
		return (int)(long) ob;
	}

}
