package enemy;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

import com.example.team.R;

public class Enemy2 extends Enemy {

	public Enemy2(AGameCanvas agc, ImageManager imageManager) {
		super(agc, imageManager);
	}

	public void initialize() {
		imageManager.load("shiraIdle", R.drawable.shiranui_idle);
		imageManager.load("shiraAttack", R.drawable.shiranui_attack);
		x = 0;
		y = 260;
		dx = dy = 800;
		scale = 10;
		moveTimer = frameTimer = flash = 0;
		attack = damage = false;
		jump = true;
		state = State.WAIT;
	}

	public void update() {
		enemyUpdate();

		jump();
		if (state == State.JUMP) {
			stateTimer = rnd.nextInt(200) + 300;
		}
		stateTimer--;
		if (stateTimer <= 0) {
			tackle();
			stateTimer = 0;
		}
		if (rnd.nextInt(200) != 0) {
			return;
		}
		waiting();
	}

	public void draw(Graphics g) {
		if (attack) {
			g.drawScaledImage(imageManager.getImage("shiraAttack"), x, y, dx, dy, sx,
					sy, 800, 800);
			return;
		}
		if (damage) {
			if (flash % 2 == 0)
				g.drawScaledImage(imageManager.getImage("shiraIdle"), x, y, dx, dy, sx,
						sy, 800, 800);
			return;
		}
		g.drawScaledImage(imageManager.getImage("shiraIdle"), x, y, dx, dy, sx,
				sy, 800, 800);
	}

	public void finish() {
		imageManager.finish("shiraIdle");
		imageManager.finish("shiraAttack");
	}

}
