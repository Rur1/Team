package enemy;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

import com.example.team.R;

public class Enemy1 extends Enemy {

	public Enemy1(AGameCanvas agc, ImageManager imageManager) {
		super(agc, imageManager);
	}

	public void initialize() {
		imageManager.load("raiIdle", R.drawable.raiju_idle);
		imageManager.load("raiAttack", R.drawable.raiju_attack);
		x = 0;
		y = 260;
		dx = dy = 800;
		sx = sy = 0;
		scale = 10;
		moveTimer = frameTimer = flash = 0;
		attack = damage = false;
		jump = true;
		state = State.WAIT;
	}

	public void update() {
		enemyUpdate();

		swordPush();

		jump();
		tackle();
		if (rnd.nextInt(200) != 0) {
			return;
		}
		waiting();
	}

	public void draw(Graphics g) {
		if (attack) {
			g.drawImage(imageManager.getImage("raiAttack"), 0, 260, sx, sy,
					800, 800);
			return;
		}
		if (damage) {
			if (flash % 2 == 0)
				g.drawImage(imageManager.getImage("raiIdle"), 0, 260, sx, 0,
						800, 800);
			return;
		}
		g.drawScaledImage(imageManager.getImage("raiIdle"), x, y, dx, dy, sx,
				sy, 800, 800);
	}

	public void finish() {
		imageManager.finish("raiIdle");
		imageManager.finish("raiAttack");
	}

	public boolean attack() {
		if (dx >= 760) {
			attack = true;
			return true;
		}
		return false;
	}

}