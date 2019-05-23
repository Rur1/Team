package enemyattack;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import net.pddin.agc.geom.Line2D;
import player.Slash;

import com.example.team.R;

public class DistanceAttack extends EnemyAttack {

	float x, y, dx, dy, sx, scale;
	Zoom zoom;

	public DistanceAttack(AGameCanvas agc, ImageManager imageManager) {
		super(agc, imageManager);
		imageManager.load("distance", R.drawable.distance);
		x = 320;
		y = 400;
		dx = dy = 150;
		scale = 10;

		zoom = Zoom.IN;
	}

	public void update() {
		sx += 150;
		sx %= 1200;

		l1 = new Line2D(x, y, x + dx, y);
		l2 = new Line2D(x, y + dy, x + dx, y + dy);
		l3 = new Line2D(x, y, x, y + dy);
		l4 = new Line2D(x + dx, y, x + dx, y + dy);

		ZoomIn();
		ZoomOut();
	}

	public void draw(Graphics g) {
		g.drawScaledImage(imageManager.getImage("distance"), x, y, dx, dy, sx, 0,
				150, 150);
	}

	public void finish() {

	}

	private void ZoomIn() {
		if (zoom == Zoom.IN) {
			if (dx < 400) {
				x -= scale / 2;
				dx += scale;
			}
			if (dy < 400) {
				y -= scale / 2;
				dy += scale;
				y += 15;
			}
		}
	}

	private void ZoomOut() {
		if (zoom == Zoom.OUT) {
			if (dx > 130) {
				x += scale / 2;
				dx -= scale;
			}
			if (dy > 130) {
				y += scale / 2;
				dy -= scale;
				y -= 15;
			}
		}
	}

	public void permitCollision(Slash slash) {
		if (dx >= 350) {
			zoomCollision(slash);
		}
	}

	public void setState(){
		if (guard()) {
			zoom = Zoom.OUT;
		}
	}

	public float getDx() {
		return dx;
	}

}
