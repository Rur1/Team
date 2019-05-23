package com.example.team;

import lib.ImageManager;
import lib.Sound;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import player.LaidoSword;
import player.Player;
import player.Slash;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import enemy.EnemyHP;

public class CharaManager implements OnTouchListener, OnGestureListener {

	AGameCanvas agc;
	ImageManager imageManager;
	Sound sound;

	GestureDetector gd;

	Player player;
	EnemyHP enemyHP;
	Slash slash;
	LaidoSword laidoSword;

	float x, y;

	public CharaManager(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		this.agc = agc;
		this.imageManager = imageManager;
		this.sound = sound;

		gd = new GestureDetector(agc.getContext(), this, agc.getHandler());

		player = new Player(agc, imageManager, sound);
		enemyHP = new EnemyHP(agc, imageManager);
		slash = new Slash(agc, imageManager);
		laidoSword = new LaidoSword(agc, imageManager);
	}

	public void initialize() {
		agc.setOnTouchListener(this);
		player.initialize();
		slash.initialize();
	}

	public void update() {
		player.update();
		enemyHP.update();
		slash.update();
	}

	public void draw(Graphics g) {
		player.draw(g);
		enemyHP.draw(g);
		slash.draw(g);
	}

	public void finish() {
		player.finish();
		enemyHP.finish();
		slash.finish();
		laidoSword.finish();
	}

	public Player getPlayer() {
		return player;
	}

	public EnemyHP getEnemyHP() {
		return enemyHP;
	}

	public Slash getSlash() {
		return slash;
	}

	public LaidoSword getLaidoSword() {
		return laidoSword;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1 == null) {
			return false;
		}

		if (laidoSword.getStartTimer() <= 0) {
			slash.setLocation(e1, e2);
			slash.calcDistance(e1, e2);
		}
		enemyHP.receiveDamage(slash);
		laidoSword.fling();

		sound.play("slash", false, true);

		return false;
	}

	public boolean onTouch(View v, MotionEvent event) {
		gd.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			x = event.getX() / agc.getRateW();
			y = event.getY() / agc.getRateH();
			break;
		}
		return false;
	}

	public boolean onDown(MotionEvent e) {
		return false;
	}

	public void onShowPress(MotionEvent e) {

	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	public void onLongPress(MotionEvent e) {

	}

}
