package player;

import java.util.Random;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import android.graphics.Color;

import com.example.team.R;

public class LaidoSword {

	AGameCanvas agc;
	ImageManager imageManager;
	Random rnd;
	int startTimer, timer;
	boolean isEnd, flg;

	public LaidoSword(AGameCanvas agc, ImageManager imageManager) {
		this.agc = agc;
		this.imageManager = imageManager;
		rnd = new Random();
	}

	public void initialize() {
		imageManager.load("gameStart", R.drawable.gamestart);
		startTimer = 15;
		timer = rnd.nextInt(40) + 30;
		isEnd = flg = false;
	}

	public void update() {
		startTimer--;
		if (startTimer <= 0) {
			startTimer = 0;
			timer--;
		}
		endLaido();
	}

	public void draw(Graphics g) {
		if (isEnd) {
			return;
		}

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 1205, 150);

		if (timer <= 20) {
			g.setColor(Color.RED);
			g.setFontSize(200);
			g.drawString("!!", 400, 400);
		}

		if (startTimer > 0) {
			g.drawImage(imageManager.getImage("gameStart"), 350, 400);
		}
	}

	public void finish() {
		imageManager.finish("gameStart");
	}

	private void endLaido() {
		if (timer <= 0) {
			timer = 0;
			isEnd = true;
		}
	}

	public void fling() {
		if (isEnd) {
			return;
		}
		if (startTimer <= 0) {
			flg = true;
		}
	}

	public boolean successAttack() {
		if (!flg) {
			return false;
		}

		if (timer < 20) {
			isEnd = true;
			return true;
		}
		return false;
	}

	public boolean missLaido() {
		if (!flg) {
			return false;
		}

		if (startTimer <= 0 && timer >= 21) {
			isEnd = true;
			return true;
		}
		return false;
	}

	public int getStartTimer() {
		return startTimer;
	}

	public boolean getIsEnd() {
		return isEnd;
	}

	public boolean getFlg() {
		return flg;
	}

}
