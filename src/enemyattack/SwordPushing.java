package enemyattack;

import lib.ImageManager;
import lib.Sound;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

import com.example.team.R;

public class SwordPushing {

	AGameCanvas agc;
	ImageManager imageManager;
	Sound sound;
	int count, timer;
	boolean isEnd, damage;

	public SwordPushing(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		this.agc = agc;
		this.imageManager = imageManager;
		this.sound = sound;
	}

	public void initialize() {
		imageManager.load("renda", R.drawable.renda);
		count = 20;
		timer = 120;
		isEnd = damage = false;
	}

	public void update() {
		limit();
		rush();
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("renda"), 290, 290);
	}

	public void finish() {
		imageManager.finish("renda");
	}

	private void limit() {
		timer--;
		if (timer <= 0) {
			timer = 120;
			isEnd = true;
			damage = true;
		}
	}

	private void rush() {
		if (agc.gcGetTouchTrigger()) {
			count--;
			sound.play("renda", false, true);
		}
	}

	public boolean count() {
		if (count <= 0) {
			isEnd = true;
			count = 20;
			return true;
		}
		return false;
	}

	public boolean damage() {
		if (damage) {
			damage = false;
			return true;
		}
		return false;
	}

	public boolean getIsEnd() {
		return isEnd;
	}

	public boolean getDamage() {
		return damage;
	}

}
