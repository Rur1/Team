package enemyattack;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import net.pddin.agc.geom.Line2D;

import com.example.team.R;

public class WidthAttack extends EnemyAttack {

	float x, y;

	public WidthAttack(AGameCanvas agc, ImageManager imageManager) {
		super(agc, imageManager);
		imageManager.load("width", R.drawable.width);
		x = rnd.nextInt(agc.getWidthA() - 200) + 50;
		y = rnd.nextInt(680) + 280;
		l1 = new Line2D(x, y, x, y + 80);
		l2 = new Line2D(x + 100, y, x + 100, y + 80);
		timer = 2;
	}

	public void update() {
		frameTimer++;
		if (frameTimer % 30 == 0) {
			timer--;
		}
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("width"), l1.getX1(), l1.getY1() - 10);
	}

	public void finish() {

	}

}
