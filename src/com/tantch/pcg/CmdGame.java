package com.tantch.pcg;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.assets.Player;
import com.tantch.pcg.mapgeneration.cmd.Draw;
import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.representations.MpCell;

public class CmdGame {
	static String musicPath = "/home/pim/Music/PianoBlack.ogg";

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
		
		MyGame game = new MyGame();
		game.loadMusic(musicPath);
		game.generateMap();
		game.load();
		game.generateLevel();
		Draw.drawMap(game.getDMap(), false, true);
		Draw.drawMap(game.getDMap(), false, false);
		createGameFiles(game);
		System.out.println(PrintTools.prettyPrintGeneratedContent(game));

		try (FileWriter file = new FileWriter("/home/pim/Desktop/MusicResults/" + game.getMir().getName().split("\\.")[0] + ".txt")) {

			file.write(PrintTools.prettyPrintGeneratedContent(game));
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private static void createGameFiles(MyGame game) {

		DunMap dmap = game.getDMap();
		

		
		
		JSONObject map = new JSONObject();
		map.put("music",musicPath);
		map.put("size", dmap.getSize());

		JSONArray board = new JSONArray();
		MpCell[][] cells = dmap.getMap();
		for (int i = 0; i < cells.length; i++) {
			JSONArray list = new JSONArray();

			for (int j = 0; j < cells.length; j++) {
				list.add(cells[i][j].getRoomId());
			}
			board.add(list);

		}

		map.put("board", board);
		
		JSONArray monsters = new JSONArray();
		for(int i =0;i<dmap.getMoster().size();i++){
			JSONObject temp = new JSONObject();
			Monster ms = dmap.getMoster().get(i);
			int[] pos = ms.getPosition();
			temp.put("x",new Integer(pos[0]));
			temp.put("y",new Integer(pos[1]));
			temp.put("life", new Integer(ms.getMaxHealth()));
			temp.put("armor", new Integer(ms.getArmor()));

			temp.put("atkspeed", new Integer(ms.getAttackSpeed()));
			monsters.add(temp);
		}
		
		map.put("monsters",monsters);
		
		
		JSONArray treasures = new JSONArray();
		JSONObject ts = new JSONObject();
		ts.put("x", new Integer(dmap.getTreasure().getX()));
		ts.put("y", new Integer(dmap.getTreasure().getY()));
		treasures.add(ts);

		map.put("treasures", treasures);

		try (FileWriter file = new FileWriter("/home/pim/Desktop/Gamefiles/map.json")) {

			file.write(map.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(map);

		// player

		Player player = dmap.getPlayer();

		JSONObject pl = new JSONObject();

		pl.put("px", new Integer(player.getPosition()[0]));
		pl.put("py", new Integer(player.getPosition()[1]));

		pl.put("maxLife", player.getMaxLife());
		pl.put("curLife", player.getCurLife());
		pl.put("attack", player.getAttack());
		pl.put("armor", player.getArmor());
		pl.put("speed", player.getSpeed());
		pl.put("luck", player.getLuck());
		pl.put("atkSpeed", player.getAttack());

		try (FileWriter file = new FileWriter("/home/pim/Desktop/Gamefiles/player.json")) {

			file.write(pl.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(pl);

	}

}
