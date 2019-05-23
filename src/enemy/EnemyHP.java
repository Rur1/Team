package enemy;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import net.pddin.agc.Graphics.Font;
import net.pddin.agc.geom.Line2D;
import player.Slash;
import android.graphics.Color;

import com.example.team.R;

public class EnemyHP {

	AGameCanvas agc;
	ImageManager imageManager;
	Line2D eTop, eBottom, eLeft, eRight;
	Line2D[] el;
	int hp, count, timer;
	float bar, length;

	int damageCount, damageTimer;
	boolean receiveDamage;

	public EnemyHP(AGameCanvas agc, ImageManager imageManager) {
		this.agc = agc;
		this.imageManager = imageManager;
	}

	public void initialize(int hp, float length) {
		this.hp = hp;
		this.length = length;
		imageManager.load("enemyBack", R.drawable.enemyhpback);
		imageManager.load("enemyHP", R.drawable.enemyhp);
		imageManager.load("cut", R.drawable.cut);
		eTop = new Line2D(95, 265, 670, 265);
		eBottom = new Line2D(95, 1060, 670, 1060);
		eLeft = new Line2D(95, 265, 95, 1060);
		eRight = new Line2D(670, 265, 670, 1060);
		count = timer = 0;

		damageCount = damageTimer = 0;
		receiveDamage = false;
	}

	public void update() {
		bar = hp * length;

		damage();
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFontSize(Font.SIZE_LARGE);
		g.drawString("EnemyHP:" + hp, 10, 150);

		g.drawScaledImage(imageManager.getImage("enemyBack"), 50 - 10,
				180 - 10, 720, 70, 0, 0, 20, 110);
		g.drawScaledImage(imageManager.getImage("enemyHP"), 50, 180, bar, 50,
				0, 0, 10, 100);
		
		if (receiveDamage) {
			g.drawImage(imageManager.getImage("cut"), 270, 290);
		}
	}

	public void finish() {
		imageManager.finish("enemyBack");
		imageManager.finish("enemyHP");
	}

	public void collision(Line2D l) {
		el = new Line2D[] { eTop, eBottom, eLeft, eRight };

		for (Line2D line2d : el) {
			if (line2d.intersectsLine(l)) {
				count++;
			}
		}
	}

	public boolean count() {
		if (count == 2) {
			hp--;
			count = 0;
			return true;
		}
		count = 0;
		return false;
	}

	public void addHp(int hp) {
		this.hp += hp;
	}

	public float getBar() {
		return bar;
	}

	private void damage() {
		if (damageCount >= 5) {
			receiveDamage = true;
			damageTimer++;
		}
		if (damageTimer > 50) {
			receiveDamage = false;
			damageCount = 0;
			damageTimer = 0;
		}
	}

	public int getDamageCount() {
		return damageCount;
	}

	public void addDamageCount(int num) {
		damageCount += num;
	}

	public void damageCountInit() {
		damageCount = 0;
		receiveDamage = false;
	}

	public void receiveDamage(Slash slash) {
		if (receiveDamage) {
			collision(slash.getLine());
		}
	}

}
