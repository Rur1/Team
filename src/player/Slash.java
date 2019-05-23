package player;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import net.pddin.agc.geom.Line2D;
import net.pddin.agc.geom.Point2D;
import android.view.MotionEvent;

public class Slash {

	AGameCanvas agc;
	ImageManager imageManager;
	Point2D p1, p2;
	Line2D l;
	int timer;

	float fx, fy, dx, dy;
	double distance, radian, degree;

	public Slash(AGameCanvas agc, ImageManager imageManager) {
		this.agc = agc;
		this.imageManager = imageManager;
	}

	public void initialize() {
		p1 = new Point2D(-30, 0);
		p2 = new Point2D(-30, 0);
		timer = 20;

		fx = fy = dx = dy = 0;
		distance = radian = degree = 0.0;
	}

	public void update() {
		timer--;
		if (timer <= 0) {
			initialize();
		}
	}

	public void draw(Graphics g) {
		if (distance >= 700) {
			g.drawImage(imageManager.getImage("slash1"), p1.getX(), p1.getY(), (float) degree);
		}
		if (distance >= 400 && distance < 700) {
			g.drawImage(imageManager.getImage("slash2"), p1.getX(), p1.getY(), (float) degree);
		}
		if (distance < 400) {
			g.drawImage(imageManager.getImage("slash3"), p1.getX(), p1.getY(), (float) degree);
		}
	}

	public void finish() {

	}

	public void setLocation(MotionEvent e1, MotionEvent e2) {
		p1.setLocation(e1.getX() / agc.getRateW(), e1.getY() / agc.getRateH());
		p2.setLocation(e2.getX() / agc.getRateW(), e2.getY() / agc.getRateH());
	}

	public void calcDistance(MotionEvent e1, MotionEvent e2) {
		fx = e2.getX() - e1.getX();
		fy = e2.getY() - e1.getY();
		radian = Math.atan2(fx, fy);
		if (radian < 0) {
			radian += Math.PI * 2;
		}
		degree = radian * 180 / Math.PI;

		dx = (e2.getX() - e1.getX()) / agc.getRateW();
		dy = (-(e2.getY() - e1.getY()) / agc.getRateH());
		distance = Math.sqrt(dx * dx + dy * dy);
	}

	public Line2D getLine() {
		return l = new Line2D(p1, p2);
	}

}
