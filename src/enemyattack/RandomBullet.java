package enemyattack;

import java.util.Random;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

import com.example.team.CharaManager;

public class RandomBullet extends Bullet {

	Random rnd;
	int i;

	public RandomBullet(AGameCanvas agc, ImageManager imageManager) {
		super(agc, imageManager);
		rnd = new Random();
		timer = 180;
		i = rnd.nextInt(9) + 1;
	}

	public void initialize() {

	}

	public void update() {
		timer--;
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("bullet"), x[i], y[i]);
	}

	public void finish() {

	}

	public boolean pushSet(CharaManager charaManager) {
		if (push(charaManager, i)) {
			timer = 180;
			return true;
		}
		return false;
	}

	public boolean reset() {
		if (getTimer()) {
			timer = 180;
			i = rnd.nextInt(9) + 1;
			return true;
		}
		return false;
	}

}
