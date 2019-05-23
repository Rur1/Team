package enemyattack;

import java.util.Random;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import net.pddin.agc.geom.Line2D;
import player.Slash;

public abstract class EnemyAttack {

	AGameCanvas agc;
	ImageManager imageManager;
	Line2D l1, l2, l3, l4;
	Line2D[] ls;
	int count, timer, frameTimer;
	Random rnd;

	public EnemyAttack(AGameCanvas agc, ImageManager imageManager) {
		this.agc = agc;
		this.imageManager = imageManager;
		rnd = new Random();
	}

	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract void finish();

	/*public void zoomCollision(Line2D l) {
		ls = new Line2D[] { l1, l2, l3, l4 };
		for (Line2D line2d : ls) {
			if (line2d.intersectsLine(l)) {
				count++;
			}
		}
	}*/

	public void zoomCollision(Slash slash) {
		ls = new Line2D[] { l1, l2, l3, l4 };
		for (Line2D line2d : ls) {
			if (line2d.intersectsLine(slash.getLine())) {
				count++;
			}
		}
	}

	public void collision(Line2D l) {
		ls = new Line2D[] { l1, l2 };
		for (Line2D line2d : ls) {
			if (line2d.intersectsLine(l)) {
				count++;
			}
		}
	}

	public boolean guard() {
		if (count == 2) {
			count = 0;
			return true;
		}
		count = 0;
		return false;
	}

	public int getTimer() {
		return timer;
	}

}
