package enemyattack;

import java.util.Random;

import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import player.Player;
import android.graphics.Color;

public class LaneAttack {

	AGameCanvas agc;
	Random rnd;
	int alpha, nowNum, count, timer;
	float[] x, y, w, h;

	Player player;

	public LaneAttack(AGameCanvas agc, Player player) {
		this.agc = agc;
		rnd = new Random();
		this.player = player;
	}

	public void initialize() {
		alpha = count = 0;
		timer = 150;
		nowNum = rnd.nextInt(2);
		x = new float[] { 0, agc.getWidthA() / 2 };
		y = new float[] { 0, 0 };
		w = new float[] { agc.getWidthA() / 2, agc.getWidthA() / 2 };
		h = new float[] { agc.getHeightA(), agc.getHeightA() };
	}

	public void update() {
		if (count < 3) {
			alpha += 5;
			move();
			return;
		}
		count = 3;
		timer--;
		if (timer <= 0) {
			count = 0;
			timer = 150;
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x[nowNum], y[nowNum], w[nowNum], h[nowNum], alpha);
	}

	public void finish() {

	}

	public void positionCheck() {
		if (alpha < 150) {
			return;
		}
		nowNum = rnd.nextInt(2);
		count++;
		alpha = 0;
	}

	private void moveRight() {
		if (player.getRoll() < 40) {
			return;
		}
		if (nowNum == 0) {
			positionCheck();
		}
	}

	private void moveLeft() {
		if (player.getRoll() > -40) {
			return;
		}
		if (nowNum == 1) {
			positionCheck();
		}
	}

	private void move() {
		moveRight();
		moveLeft();
	}

	public boolean getAlpha() {
		if (alpha >= 255) {
			alpha = 0;
			return true;
		}
		return false;
	}

	public int getCount() {
		return count;
	}

}
