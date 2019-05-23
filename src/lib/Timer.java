package lib;

import net.pddin.agc.Graphics;

public class Timer {

	int timer, frameTimer;

	public Timer() {

	}

	public void initialize() {
		frameTimer = 0;
		timer = 0;
	}

	public void update() {
		frameTimer++;
		if (frameTimer % 30 == 0) {
			timer++;
		}
		/*if (timer <= 0) {
			timer = 0;
			initialize();
		}*/
	}

	public void draw(Graphics g) {

	}

	public void finish() {

	}

	public int getTimer() {
		return timer;
	}

}
