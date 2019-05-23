package scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lib.ImageManager;
import lib.Sound;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import net.pddin.agc.geom.Line2D;
import net.pddin.agc.geom.Point2D;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.team.EScene;
import com.example.team.IScene;
import com.example.team.Particle;
import com.example.team.R;

public class Title implements IScene, OnTouchListener, OnGestureListener {

	AGameCanvas agc;
	EScene next;
	boolean isEnd;
	ImageManager imageManager;
	Sound sound;
	float x;
	float[] y;

	float topX, topY, btmX, btmY;

	GestureDetector gd;
	Point2D p1, p2;

	Line2D selectTop, selectBtm, selectRight, selectLeft;
	Line2D[] selects;
	int select;

	Line2D demoTop, demoBtm, demoRight, demoLeft;
	Line2D[] demos;
	int demo;

	Line2D staffTop, staffBtm, staffRight, staffLeft;
	Line2D[] staffs;
	int staff;

	boolean flg;

	List<Particle> pl;
	static final int PARTICLE_MAX_SIZE = 500;
	Random rnd;

	float fx, fy, dx, dy;
	double distance, radian, degree;

	public Title(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		this.agc = agc;
		this.imageManager = imageManager;
		this.sound = sound;
		rnd = new Random();
	}

	public void initialize() {
		isEnd = false;

		imageManager.load("title", R.drawable.titlebg);
		imageManager.load("titleTop", R.drawable.ttop);
		imageManager.load("titleBtm", R.drawable.tbtm);
		sound.play("titleBGM", true, true);

		agc.setOnTouchListener(this);

		x = 230;
		y = new float[] { 520, 750, 980 };

		topX = btmX = 30;
		topY = btmY = 200;

		gd = new GestureDetector(agc.getContext(), this, agc.getHandler());
		p1 = new Point2D(-30, 0);
		p2 = new Point2D(-30, 0);

		selectTop = new Line2D(270, 570, 540, 570);
		selectBtm = new Line2D(270, 710, 540, 710);
		selectRight = new Line2D(540, 570, 540, 710);
		selectLeft = new Line2D(270, 570, 270, 710);

		demoTop = new Line2D(270, 800, 540, 800);
		demoBtm = new Line2D(270, 940, 540, 940);
		demoRight = new Line2D(540, 800, 540, 940);
		demoLeft = new Line2D(270, 800, 270, 940);

		staffTop = new Line2D(270, 1020, 540, 1020);
		staffBtm = new Line2D(270, 1160, 540, 1160);
		staffRight = new Line2D(540, 1020, 540, 1160);
		staffLeft = new Line2D(270, 1020, 270, 1160);

		select = demo = staff = 0;
		flg = false;

		pl = new ArrayList<Particle>();

		fx = fy = dx = dy = 0;
		distance = radian = degree = 0.0;
	}

	public void update() {
		particle();

		if (select == 2) {
			next = EScene.STAGESELECT;
			flg = true;
		}
		select = 0;

		if (demo == 2) {
			next = EScene.DEMO;
			flg = true;
		}
		demo = 0;

		if (staff == 2) {
			next = EScene.STAFF;
			flg = true;
		}
		staff = 0;

		if (flg) {
			topX -= 8;
			topY -= 8;
			btmX += 8;
			btmY += 8;
		}
		if (topY <= 0) {
			isEnd = true;
		}
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("title"), 0, 0);
		g.drawImage(imageManager.getImage("titleTop"), topX, topY);
		g.drawImage(imageManager.getImage("titleBtm"), btmX, btmY);

		for (Particle p : pl) {
			p.draw(g);
		}

		if (distance >= 700) {
			g.drawImage(imageManager.getImage("slash1"), p1.getX(), p1.getY(), (float) degree);
		}
		if (distance >= 400 && distance < 700) {
			g.drawImage(imageManager.getImage("slash2"), p1.getX(), p1.getY(), (float) degree);
		}
		if (distance < 400) {
			g.drawImage(imageManager.getImage("slash3"), p1.getX(), p1.getY(), (float) degree);
		}
	}

	public void finish() {
		imageManager.finish("title");
		imageManager.finish("titleTop");
		imageManager.finish("titleBtm");
		sound.pause("titleBGM");
	}

	public boolean isEnd() {
		return isEnd;
	}

	public EScene next() {
		return next;
	}

	private void particle() {
		if (pl.size() > PARTICLE_MAX_SIZE) {
			return;
		}
		if (rnd.nextInt(20) == 0) {
			pl.add(new Particle(agc, imageManager));
		}

		List<Object> delList = new ArrayList<Object>();
		for (Particle p : pl) {
			p.update();
			if (p.getY() > agc.getHeightA()) {
				delList.add(p);
			}
		}
		pl.removeAll(delList);
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1 == null) {
			return false;
		}

		p1.setLocation(e1.getX() / agc.getRateW(), e1.getY() / agc.getRateH());
		p2.setLocation(e2.getX() / agc.getRateW(), e2.getY() / agc.getRateH());

		Line2D l = new Line2D(p1, p2);

		selects = new Line2D[] { selectTop, selectBtm, selectRight, selectLeft };
		for (Line2D line2d : selects) {
			if (line2d.intersectsLine(l)) {
				select++;
			}
		}

		demos = new Line2D[] { demoTop, demoBtm, demoRight, demoLeft };
		for (Line2D line2d : demos) {
			if (line2d.intersectsLine(l)) {
				demo++;
			}
		}

		staffs = new Line2D[] { staffTop, staffBtm, staffRight, staffLeft };
		for (Line2D line2d : staffs) {
			if (line2d.intersectsLine(l)) {
				staff++;
			}
		}

		fx = e2.getX() - e1.getX();
		fy = e2.getY() - e1.getY();
		radian = Math.atan2(fx, fy);
		if (radian < 0) {
			radian += Math.PI * 2;
		}
		degree = radian * 180 / Math.PI;

		dx = (e2.getX() - e1.getX()) / agc.getRateW();
		dy = (-(e2.getY() - e1.getY()) / agc.getRateH());
		distance = Math.sqrt(dx * dx + dy * dy);

		sound.play("slash", false, true);

		return false;
	}

	public boolean onTouch(View v, MotionEvent event) {
		gd.onTouchEvent(event);
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
