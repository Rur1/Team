package enemyattack;

import lib.ImageManager;
import lib.Sound;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import net.pddin.agc.geom.Line2D;

public class TackleAttack extends EnemyAttack {

	Sound sound;

	float x, y, dx, dy, sx, scale;
	boolean next;

	public TackleAttack(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		super(agc, imageManager);
		this.sound = sound;
	}

	public void initialize() {
		x = 325;
		y = 540;
		dx = dy = 150;
		scale = 40;
		next = false;
	}

	public void update() {
		sound.play("tackle", false, true);

		l1 = new Line2D(x, y, x + dx, y);
		l2 = new Line2D(x, y + dy, x + dx, y + dy);
		l3 = new Line2D(x, y, x, y + dy);
		l4 = new Line2D(x + dx, y, x + dx, y + dy);

		sx += 150;
		sx = sx % 1500;

		if (dx < 600) {
			x -= scale / 2;
			dx += scale;
		}
		if (dy < 600) {
			y -= scale / 2;
			dy += scale;
		}
	}

	public void draw(Graphics g) {
		g.drawScaledImage(imageManager.getImage("blueCircle"), x, y, dx, dy, sx, 0,
				150, 150);
	}

	public void finish() {

	}

	public void defence() {
		if (guard()) {
			next = true;
		}
	}

	public boolean getNext() {
		return next;
	}

}
