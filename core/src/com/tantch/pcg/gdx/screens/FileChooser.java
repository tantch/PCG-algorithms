package com.tantch.pcg.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class FileChooser extends Dialog {
	FileHandle folder;

	public FileChooser(String title, Skin skin) {
		super(title, skin);

	}

	@Override
	public Dialog show(Stage stage) {

		final Label lbl1 = new Label("Chooseee", super.getSkin());
		getContentTable().add(lbl1).row();
		
		String path = "/home/pim/Music";
		folder = Gdx.files.absolute(path);
		
		FileHandle[] childs =folder.list();
		for (FileHandle fileHandle : childs) {
			System.out.println(fileHandle.name());
			final Label lbl2 = new Label(fileHandle.name(),getSkin());
			getContentTable().add(lbl2).row();

		}

		padTop(50).padBottom(50);
		getButtonTable().padTop(50);
		TextButton dbutton = new TextButton("Yes", getSkin());
		button(dbutton, true);
		return super.show(stage);
	}

}
