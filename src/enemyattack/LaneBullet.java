package enemyattack;

import java.util.Random;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import net.pddin.agc.geom.Line2D;

import com.example.team.CharaManager;

public class LaneBullet extends Bullet {

	CharaManager charaManager;

	Random rnd;
	int i, bulletRnd, deleteNum, count;
	int[][] pattern;

	Line2D l1, l2;
	Line2D[] ls;

	int[][] line1, line2;

	public LaneBullet(AGameCanvas agc, ImageManager imageManager,
			CharaManager charaManager) {
		super(agc, imageManager);
		this.charaManager = charaManager;
		rnd = new Random();
	}

	public void initialize() {
		timer = 180;
		bulletRnd = rnd.nextInt(8);
		pattern = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 },
				{ 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 }, { 1, 5, 9 }, { 3, 5, 7 } };

		line1 = new int[][] { { 150, 360, 150, 460 }, { 150, 610, 150, 710 },
				{ 150, 860, 150, 960 }, { 100, 410, 200, 410 },
				{ 350, 410, 450, 410 }, { 600, 410, 700, 410 },
				{ 200, 360, 100, 460 }, { 600, 360, 700, 460 }, };
		line2 = new int[][] { { 650, 360, 650, 460 }, { 650, 610, 650, 710 },
				{ 650, 860, 650, 960 }, { 100, 910, 200, 910 },
				{ 350, 910, 450, 910 }, { 600, 910, 700, 910 },
				{ 700, 860, 600, 960 }, { 100, 860, 200, 960 }, };

		for (i = 0; i < pattern[bulletRnd].length; i++) {
			l1 = new Line2D(line1[bulletRnd][0], line1[bulletRnd][1],
					line1[bulletRnd][2], line1[bulletRnd][3]);
			l2 = new Line2D(line2[bulletRnd][0], line2[bulletRnd][1],
					line2[bulletRnd][2], line2[bulletRnd][3]);
		}

		count = 0;
	}

	public void update() {
		timer--;
		if (count == 2) {
			initialize();
			deleteNum++;
			charaManager.setX(0);
			charaManager.setY(0);
		}
		count = 0;
	}

	public void draw(Graphics g) {
		for (i = 0; i < pattern[bulletRnd].length; i++) {
			g.drawImage(imageManager.getImage("bullet"),
					x[pattern[bulletRnd][i]], y[pattern[bulletRnd][i]]);
		}
	}

	public void finish() {

	}

	public boolean reset() {
		if (timer <= 0) {
			initialize();
			return true;
		}
		return false;
	}

	public int getNum() {
		return deleteNum;
	}

	public void setNum(int deleteNum) {
		this.deleteNum = deleteNum;
	}

	public void collision(Line2D l) {
		ls = new Line2D[] { l1, l2 };

		for (Line2D line2d : ls) {
			if (line2d.intersectsLine(l)) {
				count++;
			}
		}
	}

}
