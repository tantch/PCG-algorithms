package com.tantch.pcg.mir;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class MIRTools {

	private int beatCount;
	private float bpmG;
	private float bpmMF;
	private float avgLoudness;
	private float length;

	// highlevel

	private float danceable;
	private float male;
	private String genreDor, genreEle,genreRos,genreTza,rhythmDance;
	private float genreDorPb, genreElePb,genreRosPb,genreTzaPb,rhythmDancePb;
	private float acoustic;
	private float aggressive;
	private float electronic;
	private float happy;
	private float party;
	private float relaxed;
	private float sad;
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

	private float dark;
	private float tonal;
	private float instrumental;

	public boolean loadDescriptors(String musicPath) throws IOException, InterruptedException {

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

		JsonReader json = new JsonReader();
		JsonValue base = json.parse(new FileHandle("/home/pim/Desktop/output.json"));

		JsonValue rhythm = base.get("rhythm");
		this.beatCount = rhythm.getInt("beats_count");
		this.bpmG = rhythm.getInt("bpm");
		this.avgLoudness = base.get("lowlevel").getFloat("average_loudness");
		JsonValue meta = base.get("metadata");
		this.length = meta.get("audio_properties").getFloat("length");

		cmdName = "essentia_streaming_extractor_music_svm";

		pb = new ProcessBuilder(cmdPath + cmdName, "../" + outputFile, "../output2.json");
		pb.directory(new File("/home/pim/Desktop/essentia"));
		File log2 = new File("/home/pim/Desktop/log2.yaml");
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(log2));
		p = pb.start();
		p.waitFor();
		base = json.parse(new FileHandle("/home/pim/Desktop/output2.json"));

		JsonValue highL = base.get("highlevel");
		this.danceable = highL.get("danceability").getFloat("probability");

		if(!highL.get("danceability").getString("value").equals("danceable")){
			this.danceable= 1-danceable;
		}
		this.male = highL.get("gender").getFloat("probability");
		if(!highL.get("gender").getString("value").equals("male")){
			this.male = 1 - male;
		}
		this.genreDor = highL.get("genre_dortmund").getString("value");
		this.genreDorPb = highL.get("genre_dortmund").getFloat("probability");
		this.genreEle = highL.get("genre_electronic").getString("value");
		this.genreElePb = highL.get("genre_electronic").getFloat("probability");
		this.genreRos = highL.get("genre_rosamerica").getString("value");
		this.genreRosPb = highL.get("genre_rosamerica").getFloat("probability");
		this.genreTza = highL.get("genre_tzanetakis").getString("value");
		this.genreTzaPb= highL.get("genre_tzanetakis").getFloat("probability");
		this.rhythmDance = highL.get("ismir04_rhythm").getString("value");
		this.rhythmDancePb = highL.get("ismir04_rhythm").getFloat("probability");

		this.acoustic = highL.get("mood_acoustic").getFloat("probability");
		if(!highL.get("mood_acoustic").getString("value").equals("acoustic")){
			this.acoustic = 1- acoustic;
		}
		this.aggressive = highL.get("mood_aggressive").getFloat("probability");
		if(!highL.get("mood_aggressive").getString("value").equals("aggressive")){
			this.aggressive = 1- aggressive;
		}
		this.electronic = highL.get("mood_electronic").getFloat("probability");
		if(!highL.get("mood_electronic").getString("value").equals("electronic")){
			this.electronic= 1- electronic;
		}
		this.happy = highL.get("mood_happy").getFloat("probability");
		if(!highL.get("mood_happy").getString("value").equals("happy")){
			this.happy = 1 - happy;
		}
		this.party = highL.get("mood_party").getFloat("probability");
		if(!highL.get("mood_party").getString("value").equals("party")){
			this.party = 1 - party;
		}
		this.relaxed = highL.get("mood_relaxed").getFloat("probability");
		if(!highL.get("mood_relaxed").getString("value").equals("relaxed")){
			this.relaxed = 1- relaxed;
		}
		this.sad = highL.get("mood_sad").getFloat("probability");
		if(!highL.get("mood_sad").getString("value").equals("sad")){
			this.sad = 1-sad;
		}
		this.dark = highL.get("timbre").getFloat("probability");
		if(!highL.get("timbre").getString("value").equals("dark")){
			this.dark = 1-dark;
		}
		this.tonal = highL.get("tonal_atonal").getFloat("probability");
		if(!highL.get("tonal_atonal").getString("value").equals("tonal")){
			this.tonal = 1- tonal;
		}
		this.instrumental = highL.get("voice_instrumental").getFloat("probability");
		if(!highL.get("voice_instrumental").getString("value").equals("instrumental")){
			this.instrumental = 1- instrumental;
		}
		
		return true;


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

}
