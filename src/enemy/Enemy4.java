package enemy;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

import com.example.team.R;

public class Enemy4 extends Enemy {

	boolean change;

	public Enemy4(AGameCanvas agc, ImageManager imageManager) {
		super(agc, imageManager);
	}

	public void initialize() {
		imageManager.load("tenguIdle", R.drawable.tengu_idle);
		imageManager.load("tenguAttack", R.drawable.tengu_attack);
		x = 0;
		y = 260;
		dx = dy = 800;
		scale = 10;
		moveTimer = frameTimer = flash = 0;
		attack = damage = false;

		jump = true;
		state = State.JUMP;
		change = false;
	}

	public void update() {
		enemyUpdate();

		// waiting();
		jump();
		if (!change)
			return;
		tackle();
	}

	public void draw(Graphics g) {
		if (attack) {
			g.drawScaledImage(imageManager.getImage("tenguAttack"), x, y, dx, dy, sx,
					sy, 800, 800);
			return;
		}
		if (damage) {
			if (flash % 2 == 0)
				g.drawImage(imageManager.getImage("tenguIdle"), 0, 260, sx, 0,
						800, 800);
			return;
		}
		g.drawScaledImage(imageManager.getImage("tenguIdle"), x, y, dx, dy, sx,
				sy, 800, 800);
	}

	public void finish() {
		imageManager.finish("tenguIdle");
		imageManager.finish("tenguAttack");
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public boolean attack() {
		if (dx >= 760) {
			attack = true;
			return true;
		}
		return false;
	}

	public void positionInit() {
		x = 0;
		y = 260;
		dx = dy = 800;
		scale = 10;
		moveTimer = 0;
		attack = damage = false;

		jump = true;
		state = State.WAIT;
		change = false;
	}

}
