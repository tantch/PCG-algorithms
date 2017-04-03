package com.tantch.pcg.gdx.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tantch.pcg.gdx.MyGdxGame;

public class GdxPlayer {

	private Texture player;
	private int frame = 0;
	private final float fps = 0.5f;
	private float cntr = 0;
	private boolean moving = false;

	public GdxPlayer() {
		player = new Texture("Player.png");
	}

	public void update(float delta) {

		cntr += delta;
		if (cntr > fps) {
			cntr = 0;
			incFrame();
		}
		

	}

	public void drawPlayer(MyGdxGame game, int[] pos) {
			game.batch.draw(new TextureRegion(player, frameX(), frameY(), 64, 64), pos[0] * 2, pos[1] * 2 + 1, 2, 2);
		
	}

	private void incFrame() {
		frame++;
		if (frame > 2) {
			frame = 0;
		}
		if(moving && frame == 0 ){
			frame =1;
		}else if(!moving){
			frame=0;
		}
	}

	private int frameX() {
		if (frame % 2 == 0) {
			return 0;
		} else {
			return 64;
		}
	}

	private int frameY() {
		
		return frame / 2 * 64;
	}
	public void moving(){
		moving=true;
	}
	public void idle(){
		moving =false;
	}

	public void attack() {
			
		
		
	}
}
