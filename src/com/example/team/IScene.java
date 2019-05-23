package com.example.team;

import net.pddin.agc.Graphics;


public interface IScene {
	void initialize();
	void update();
	void draw(Graphics g);
	void finish();
	boolean isEnd();
	EScene next();
}
