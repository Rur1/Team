package enemy;

import java.util.Random;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

public abstract class Enemy {

	AGameCanvas agc;
	ImageManager imageManager;

	State state;
	Random rnd;

	float x, y, dx, dy, sx, sy, scale;
	int moveTimer, frameTimer, stateTimer, flash;
 	boolean attack, damage, jump;

	public Enemy(AGameCanvas agc, ImageManager imageManager) {
		this.agc = agc;
		this.imageManager = imageManager;

		rnd = new Random();
	}

	public abstract void initialize();
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract void finish();

	public void enemyUpdate() {
		frameTimer++;
		if (frameTimer % 2 == 0) {
			sx += 800;
			frameTimer = 0;
		}
		if (sx >= 8000 && sy == 0) {
			sx = 0;
			sy = 800;
		}
		if (sx >= 8000 && sy == 800) {
			sx = 0;
			sy = 0;
		}

		if (attack || damage) {
			moveTimer++;
		}

		if (moveTimer >= 15) {
			attack = false;
			damage = false;
			moveTimer = 0;
		}

		if (damage)
			flash++;
	}

	public void setAttack(boolean attack) {
		this.attack = attack;
	}

	public void setDamage(boolean damage) {
		this.damage = damage;
	}

	public void up() {
		if (y <= 140) {
			jump = false;
			return;
		}
		if (!jump) return;
		y -= 11;
	}

	public void down() {
		if (y >= 460)
			return;
		if (jump)
			return;
		y += 11;
	}

	public void waiting() {
		if (state == State.WAIT) {
			jump = true;
			state = State.JUMP;
		}
	}

	public void jump() {
		if (state == State.JUMP) {
			scale = 10;
			up();
			down();

			x += scale / 2;
			y += scale / 2;
			dx -= scale;
			dy -= scale;
		}
		if (y == 460)
			state = State.TACKLE;
	}

	public void tackle() {
		if (state != State.TACKLE) {
			return;
		}

		scale = 40;
		x -= scale / 2;
		y -= scale / 2;
		dx += scale;
		dy += scale;

		if (dx >= 800) {
			state = State.WAIT;
		}
	}

	public void swordPush() {
		if (state == State.SWORDPUSH) {
			x = 0;
			y = 260;
			dx = dy = 800;
		}
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public int getStateTimer() {
		return stateTimer;
	}

}
