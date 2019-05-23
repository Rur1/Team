package enemyattack;

import java.util.Random;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

import com.example.team.CharaManager;

public class NineBullet extends Bullet {

	Random rnd;
	int i;

	public NineBullet(AGameCanvas agc, ImageManager imageManager, int i) {
		super(agc, imageManager);
		timer = 100;
		this.i = i;
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
			return true;
		}
		return false;
	}

	public boolean reset() {
		if (getTimer()) {
			return true;
		}
		return false;
	}

}