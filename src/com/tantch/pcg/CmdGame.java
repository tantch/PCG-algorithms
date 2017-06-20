package com.tantch.pcg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.tantch.pcg.assets.Item;
import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.assets.Player;
import com.tantch.pcg.mapgeneration.cmd.Draw;
import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.representations.DunRoom;
import com.tantch.pcg.mapgeneration.representations.MpCell;
import com.tantch.pcg.utils.Settings;

public class CmdGame {
	static String musicPath;
	static String basePath;

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {

		if (args.length == 2) {
			musicPath = args[0];
			basePath = args[1];
			System.out.println("Music file : " + musicPath);
			System.out.println("Base folder Path : " + basePath);
			new File(basePath + "Gamefiles/").mkdirs();
			new File(basePath + "MusicResults/").mkdirs();

		} else {
			System.out.println("Wrong use of arguments");

			System.out.println("<program> <musicFilePath> <GameFilesFolderPath>");
			return;
		}

		MyGame game = new MyGame();
		game.loadMusic(musicPath);
		game.generateMap();
		game.generateLevel();
		game.load();
		Draw.drawMap(game.getDMap(), false, true);
		Draw.drawMap(game.getDMap(), false, false);
		createGameFiles(game);
		System.out.println(PrintTools.prettyPrintGeneratedContent(game));

		try (FileWriter file = new FileWriter(
				basePath + "MusicResults/" + game.getMir().getName().split("\\.")[0] + ".txt")) {

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
		map.put("music", musicPath);
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
		JSONArray roomColors = new JSONArray();
		for (int i = 0; i < dmap.getRooms().size(); i++) {
			JSONObject obj = new JSONObject();
			DunRoom room = dmap.getRooms().get(i);
			switch (room.getRoomType()) {
			case BossRoom:
				obj.put("r", new Float(1f));
				obj.put("g", new Float(0.7f));
				obj.put("b", new Float(0.7f));

				break;
			case ExitRoom:
				obj.put("r", new Float(0.7f));
				obj.put("g", new Float(1f));
				obj.put("b", new Float(0.7f));

				break;
			case TreasureRoom:
				obj.put("r", new Float(1f));
				obj.put("g", new Float(1f));
				obj.put("b", new Float(0.7f));

				break;
			case ZooRoom:
				obj.put("r", new Float(1f));
				obj.put("g", new Float(0.7f));
				obj.put("b", new Float(1f));

				break;
			case TempleRoom:
				obj.put("r", new Float(0.7f));
				obj.put("g", new Float(1f));
				obj.put("b", new Float(1f));

				break;

			case BombRoom:
				obj.put("r", new Float(1f));
				obj.put("g", new Float(0.8f));
				obj.put("b", new Float(0.7f));

				break;
			case TrapRoom:
				obj.put("r", new Float(0.7f));
				obj.put("g", new Float(0.8f));
				obj.put("b", new Float(1f));

				break;
			case ShopRoom:
				obj.put("r", new Float(0.9f));
				obj.put("g", new Float(1f));
				obj.put("b", new Float(0.9f));
				break;
			case SimpleRoom:
				obj.put("r", new Float(0.8f));
				obj.put("g", new Float(1f));
				obj.put("b", new Float(0.7f));
				break;

			default:
				obj.put("r", new Float(0.82f));
				obj.put("g", new Float(0.85f));
				obj.put("b", new Float(0.82f));
				break;
			}
			roomColors.add(obj);
		}

		map.put("colors", roomColors);

		JSONArray monsters = new JSONArray();
		for (int i = 0; i < dmap.getMoster().size(); i++) {
			JSONObject temp = new JSONObject();
			Monster ms = dmap.getMoster().get(i);
			int[] pos = ms.getPosition();
			temp.put("x", new Integer(pos[0]));
			temp.put("y", new Integer(pos[1]));
			temp.put("life", new Integer(ms.getMaxHealth()));
			temp.put("attack", new Integer(ms.getAttack()));
			temp.put("blastSpeed", new Integer(ms.getBlastSpeed()));
			temp.put("atkspeed", new Integer(ms.getAttackSpeed()));
			temp.put("speed", new Integer(ms.getSpeed()));
			temp.put("size", new Integer(ms.getSize()));
			temp.put("snared", new Integer(ms.getSnared()));
			temp.put("innactive", new Integer(ms.getInnactive()));

			monsters.add(temp);
		}

		map.put("monsters", monsters);

		JSONArray items = new JSONArray();
		for (int i = 0; i < dmap.getItems().size(); i++) {
			JSONObject ts = new JSONObject();
			Item it = dmap.getItems().get(i);
			ts.put("x", new Integer(it.getX()));
			ts.put("y", new Integer(it.getY()));
			ts.put("desc", it.getDescription());
			ts.put("trueItem", new Integer(it.getTrueItem()));
			ts.put("type", it.getType().toString());
			ts.put("effect", new Integer(it.getEffect()));
			ts.put("value", new Integer(it.getValue()));
			items.add(ts);
		}
		map.put("items", items);

		try (FileWriter file = new FileWriter(basePath + "Gamefiles/map.json")) {

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
		pl.put("atkSpeed", player.getAtkSpeed());

		JSONObject rgb = new JSONObject();

		rgb.put("r", Settings.RGB[0]);
		rgb.put("g", Settings.RGB[1]);
		rgb.put("b", Settings.RGB[2]);

		pl.put("blastColor", rgb);

		try (FileWriter file = new FileWriter(basePath + "Gamefiles/player.json")) {

			file.write(pl.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(pl);

	}

}
