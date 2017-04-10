package com.tantch.pcg;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.tantch.pcg.assets.Player;
import com.tantch.pcg.mapgeneration.cmd.Draw;
import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.representations.MpCell;

public class CmdGame {

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {

		MyGame game = new MyGame();
		game.loadMusic("/home/pim/Music/01 - Brianstorm.mp3");
		game.generateMap();
		game.load();
		game.generateLevel();
		Draw.drawMap(game.getDMap(), false, true);
		Draw.drawMap(game.getDMap(), false, false);
		createGameFiles(game);

	}

	@SuppressWarnings("unchecked")
	private static void createGameFiles(MyGame game) {

		DunMap dmap = game.getDMap();
		JSONObject map = new JSONObject();
		map.put("size", dmap.getSize());

		JSONArray board = new JSONArray();
		MpCell[][] cells = dmap.getMap();
		for (int i=0;i<cells.length;i++) {
			JSONArray list = new JSONArray();

			for(int j=0;j<cells.length;j++){	
				list.add(cells[i][j].getRoomId());
			}
			board.add(list);

		}

		map.put("board", board);
		
		JSONArray treasures =  new JSONArray();
		JSONObject ts = new JSONObject();
		ts.put("x",new Integer( dmap.getTreasure().getX()));
		ts.put("y",new Integer(dmap.getTreasure().getY()));
		treasures.add(ts);

		
		map.put("treasures", treasures);

		try (FileWriter file = new FileWriter("/home/pim/Desktop/Gamefiles/map.json")) {

			file.write(map.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(map);
		
		
		//player
		
		Player player = dmap.getPlayer();
		
		JSONObject pl = new JSONObject();
		
		pl.put("px",new Integer(player.getPosition()[0]));
		pl.put("py",new Integer(player.getPosition()[1]));
		
		pl.put("maxLife",player.getMaxLife());
		pl.put("curLife",player.getCurLife());
		pl.put("attack",player.getAttack());
		pl.put("armor", player.getArmor());
		pl.put("speed",player.getSpeed());
		pl.put("luck",player.getLuck());
		pl.put("atkSpeed",player.getAttack());
		
		
		
		try (FileWriter file = new FileWriter("/home/pim/Desktop/Gamefiles/player.json")) {

			file.write(pl.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(pl);
		
		
		
		

	}

}
