package com.example.team;

import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import android.graphics.Color;

public class Fade implements IScene {

	AGameCanvas agc;
	boolean isEnd;
	final int countMax;
	int count, y, timer;

	public Fade(AGameCanvas agc, int fcount) {
		this.agc = agc;
		countMax = fcount;
	}

	public void initialize() {
		isEnd = false;
		count = countMax;
		y = timer = 0;
	}

	public void update() {
		count--;
		if (count <= 0) {
			count = 0;
			isEnd = true;
		}
	}

	public void shutterUpdate() {
		y += 40;
		if (timer < 255) {
			timer += 8;
		}
		if (y > agc.getHeightA()) {
			y = 0;
			isEnd = true;
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		int alpha = (int) ((float) (countMax - count) / (float) countMax * 255);
		g.fillRect(0, 0, agc.getWidthA(), agc.getHeightA(), alpha);
	}

	public void shutterDraw(Graphics g) {
		g.setColor(Color.BLACK);
		int alpha = timer;
		g.fillRect(0, 0, agc.getWidthA(), y, alpha);
	}

	public void finish() {

	}

	public boolean isEnd() {
		return isEnd;
	}

	public EScene next() {
		return null;
	}

}
