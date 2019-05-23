package enemyattack;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import net.pddin.agc.geom.Line2D;

import com.example.team.R;

public class HeightAttack extends EnemyAttack {

	float x, y;

	public HeightAttack(AGameCanvas agc, ImageManager imageManager) {
		super(agc, imageManager);
		imageManager.load("height", R.drawable.height);
		x = rnd.nextInt(agc.getWidthA() - 200) + 50;
		y = rnd.nextInt(680) + 280;
		l1 = new Line2D(x, y, x + 80, y);
		l2 = new Line2D(x, y + 100, x + 80, y + 100);
		timer = 5;
	}

	public void update() {
		frameTimer++;
		if (frameTimer % 30 == 0) {
			timer--;
		}
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("height"), l1.getX1() - 10, l1.getY1());
	}

	public void finish() {

	}

}
