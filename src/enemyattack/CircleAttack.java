package enemyattack;

import java.util.Random;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

import com.example.team.CharaManager;

public class CircleAttack {

	AGameCanvas agc;
	float x, y, sx;
	ImageManager imageManager;

	Random rnd;
	int timer, frameTimer;

	public CircleAttack(AGameCanvas agc, ImageManager imageManager) {
		this.agc = agc;
		this.imageManager = imageManager;

		rnd = new Random();

		x = rnd.nextInt(agc.getWidthA() - 200) + 50;
		y = rnd.nextInt(640) + 280;

		timer = 3;
	}

	public void update() {
		frameTimer++;
		if (frameTimer % 40 == 0) {
			timer--;
		}

		sx += 150;
		sx %= 1500;
	}

	public void draw(Graphics g) {
		g.drawScaledImage(imageManager.getImage("circle"), x, y, 150, 150, sx,
				0, 150, 150);
	}

	public void finish() {

	}

	public boolean push(CharaManager charaManager) {
		boolean touchX = charaManager.getX() > x
				&& charaManager.getX() < x + 150;
		boolean touchY = charaManager.getY() > y
				&& charaManager.getY() < y + 150;
		if (touchX && touchY) {
			return true;
		}
		return false;
	}

	public int getTimer() {
		return timer;
	}

}
