package com.tantch.pcg.gdx.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tantch.pcg.gdx.MyGdxGame;
import com.tantch.pcg.utils.Settings;

public class SettingsMenuScreen implements Screen {

	protected Skin skin;
	protected Stage stage;
	protected final TextField lbl1;
	protected MyGdxGame game;
	private String selectedSong;

	public SettingsMenuScreen(MyGdxGame game) {
		this.game = game;

		stage = new Stage(new FitViewport(800, 600));
		Gdx.input.setInputProcessor(stage);

		skin = new Skin();
		Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		BitmapFont bfont = new BitmapFont();

		skin.add("default", bfont);

		WindowStyle windowS = new WindowStyle();
		windowS.background = skin.newDrawable("white", Color.DARK_GRAY);
		windowS.titleFont = skin.getFont("default");

		skin.add("default", windowS);
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("default");
		labelStyle.background = skin.newDrawable("white", Color.BLUE);
		labelStyle.fontColor = Color.WHITE;

		skin.add("default", labelStyle);

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

		textButtonStyle.font = skin.getFont("default");

		skin.add("default", textButtonStyle);

		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = skin.getFont("default");
		textFieldStyle.background = skin.newDrawable("white", Color.BLUE);
		textFieldStyle.fontColor = Color.WHITE;

		skin.add("default", textFieldStyle);

		final TextButton textButton = new TextButton("Load Music File", textButtonStyle);
		textButton.setPosition(400, 400);
		textButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				FileChooser fc = new FileChooser("Pick track", skin, SettingsMenuScreen.this);
				fc.show(stage);

			}
		});
		stage.addActor(textButton);

		final TextButton startButton = new TextButton("Start", textButtonStyle);
		startButton.setPosition(300, 200);
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				startGame();

			}
		});
		stage.addActor(startButton);

		final Label mapSizeLbl = new Label("Map Size:" + Settings.MAPSIZE, skin);
		mapSizeLbl.setBounds(0, 550, 300, 50);

		final Label mapDivisionLbl = new Label("Map Division: \n  Partition Sizes : " + Settings.MINPARTITIONSIZE + "/"
				+ Settings.MAXPARTITIONSIZE + " \n  Minimum Room Size: " + Settings.MINROOMSIZE, skin);
		mapDivisionLbl.setBounds(0, 450, 300, 100);
		String AgentOptions = "Agent Settings: \n" + "Agent Turning Probability: " + Settings.AGENT_TURNPROB + "\n"
				+ "Simple room connections : " + Settings.ROOMSIMPLECONNECTIONS + "\n"
				+ "Connecting only to middle Room: " + Settings.CONNECT_ONLY_TO_MIDDLE_ROOM;
		final Label agentsLbl = new Label(AgentOptions, skin);
		agentsLbl.setBounds(0, 350, 300, 100);
		stage.addActor(mapSizeLbl);
		stage.addActor(mapDivisionLbl);
		stage.addActor(agentsLbl);

		lbl1 = new TextField("", skin);
		lbl1.setBounds(400, 500, 400, 100);

		stage.addActor(lbl1);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();

	}

	public void setSongPath(String string) {
		this.lbl1.setText(string);
		this.selectedSong = string;

	}

	public void startGame() {

		try {
			game.startGame(selectedSong);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
