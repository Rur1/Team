package com.example.team;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

public class Particle {

	AGameCanvas agc;
	float x, y, r, dx, dy, sp, agl, gv, sx, sy;
	float timer = 0;
	ImageManager imageManager;

	public Particle(AGameCanvas agc, ImageManager imageManager) {
		this.agc = agc;
		this.imageManager = imageManager;
		imageManager.load("leaf", R.drawable.leaf);
		x = 700;
		y = 20;
		r = 5.0f;
		sp = (float) (Math.random() * 1.0) + 1.4f;
		agl = (float) (Math.random() * 90.0) + 90.0f;
		dx = sp * (float) (Math.cos(Math.toRadians(agl)));
		dy = sp * -(float) (Math.sin(Math.toRadians(agl)));
		gv = 0.005f;
		sx = sy = 0;
	}

	public void update() {
		move();

		timer++;
		if (timer % 4 == 0) sx += 100;
		if (sx >= 1000 && sy == 0) {
			sx = 0;
			sy = 100;
		}
		if (sx >= 1000 && sy == 100) {
			sx = 0;
			sy = 0;
		}
	}

	public void draw(Graphics g) {
		g.drawScaledImage(imageManager.getImage("leaf") , x, y, 100, 100, sx, sy, 100, 100);
	}

	public void finish() {
		
	}

	public void move() {
		x += dx;
		dy -= gv;
		y -= dy;
	}

	public float getY() {
		return y;
	}

	public float getSx() {
		return sx;
	}

	public float getSy() {
		return sy;
	}

}