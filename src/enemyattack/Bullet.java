package enemyattack;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

import com.example.team.CharaManager;

public abstract class Bullet {

	AGameCanvas agc;
	ImageManager imageManager;
	float[] x, y;
	int timer;
	boolean touchX, touchY;

	public Bullet(AGameCanvas agc, ImageManager imageManager) {
		this.agc = agc;
		this.imageManager = imageManager;
		x = new float[] { -150, 50, 300, 550, 50, 300, 550, 50, 300, 550 };
		y = new float[] { -150, 310, 310, 310, 560, 560, 560, 810, 810, 810};
	}

	public abstract void initialize();
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract void finish();

	public boolean push(CharaManager charaManager, int i) {
		touchX = charaManager.getX() > x[i] && charaManager.getX() < x[i] + 206;
		touchY = charaManager.getY() > y[i] && charaManager.getY() < y[i] + 206;
		if (touchX && touchY) {
			return true;
		}
		return false;
	}

	public boolean getTimer() {
		if (timer <= 0) {
			return true;
		}
		return false;
	}

}
