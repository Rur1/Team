package scene;

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
import com.example.team.R;

public class StageSelect implements IScene, OnTouchListener, OnGestureListener {

	AGameCanvas agc;
	EScene next;
	boolean isEnd;
	ImageManager imageManager;
	Sound sound;
	float tx, ty;
	float[] x, y;
	float[] cx;
	float cy;
	boolean rightFlg, leftFlg;

	GestureDetector gd;
	Point2D p1, p2;

	Line2D top, btm, right, left;
	Line2D[] lines;
	int count;

	Line2D returnTop, returnBtm, returnRight, returnLeft;
	Line2D[] returns;
	int returnCount;

	int rightMove, leftMove;
	int sceneCount;

	static final int[] type = {
		0, -30, -60, -90, -120
	};
	static final EScene[] param = {
		EScene.STORY1, EScene.STORY2,
		EScene.STORY3, EScene.STORY4,
		EScene.STORY5
	};

	float fx, fy, dx, dy;
	double distance, radian, degree;

	int flash;

	public StageSelect(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		this.agc = agc;
		this.imageManager = imageManager;
		this.sound = sound;
	}

	public void initialize() {
		isEnd = false;

		imageManager.load("selectBg", R.drawable.selectbg);
		imageManager.load("select1", R.drawable.select1);
		imageManager.load("select2", R.drawable.select2);
		imageManager.load("select3", R.drawable.select3);
		imageManager.load("select4", R.drawable.select4);
		imageManager.load("select5", R.drawable.select5);
		sound.play("titleBGM", true, true);

		agc.setOnTouchListener(this);

		tx = ty = 0;
		x = new float[] { 230, 830, 1430, 2030, 2630 };
		y = new float[] { 280, 280, 280, 280, 280 };
		cx = new float[] {615, 45};
		cy = 535;
		rightFlg = leftFlg = false;
		rightMove = leftMove = 0;
		sceneCount = 0;

		gd = new GestureDetector(agc.getContext(), this, agc.getHandler());
		p1 = new Point2D(-30, 0);
		p2 = new Point2D(-30, 0);

		top = new Line2D(230, 280, 580, 280);
		btm = new Line2D(230, 880, 580, 880);
		right = new Line2D(230, 280, 230, 880);
		left = new Line2D(580, 280, 580, 880);
		count = 0;

		returnTop = new Line2D(450, 1030, 720, 1030);
		returnBtm = new Line2D(450, 1170, 720, 1170);
		returnRight = new Line2D(450, 1030, 450, 1170);
		returnLeft = new Line2D(720, 1030, 720, 1170);
		returnCount = 0;

		fx = fy = dx = dy = 0;
		distance = radian = degree = 0.0;

		flash = 0;
	}

	public void update() {
		right();
		left();
		if (returnCount == 2) {
			next = EScene.TITLE;
			isEnd = true;
		}
		returnCount = 0;

		if (count != 2) {
			count = 0;
			return;
		}
		flash++;
		for (int i = 0; i < 5; i++) {
			if (flash < 20) {
				return;
			}
			if (sceneCount == type[i]) {
				agc.setOnTouchListener(null);
				next = param[i];
				isEnd = true;
			}
		}
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("selectBg"), 0, 0);
		if (flash % 2 == 0) {
			g.drawImage(imageManager.getImage("select1"), x[0], y[0]);
			g.drawImage(imageManager.getImage("select2"), x[1], y[1]);
			g.drawImage(imageManager.getImage("select3"), x[2], y[2]);
			g.drawImage(imageManager.getImage("select4"), x[3], y[3]);
			g.drawImage(imageManager.getImage("select5"), x[4], y[4]);
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
		imageManager.finish("selectBg");
		imageManager.finish("select1");
		imageManager.finish("select2");
		imageManager.finish("select3");
		imageManager.finish("select4");
		imageManager.finish("select5");
		sound.pause("titleBGM");
	}

	public boolean isEnd() {
		return isEnd;
	}

	public EScene next() {
		return next;
	}

	private int push() {
		for (int i = 0; i < cx.length; i++) {
			boolean touchX = tx > cx[i] && tx < cx[i] + 145;
			boolean touchY = ty > cy && ty < cy + 85;
			if (touchX && touchY) {
				return i;
			}
		}
		return -1;
	}

	private void right() {
		if (!rightFlg) return;
		for (int i = 0; i < 5; i++) {
			x[i] -= 20;
		}
		rightMove++;
		sceneCount--;
		if (rightMove == 30) {
			rightMove = 0;
			rightFlg = false;
		}
	}

	private void left() {
		if (!leftFlg) return;
		for (int i = 0; i < 5; i++) {
			x[i] += 20;
		}
		leftMove++;
		sceneCount++;
		if (leftMove == 30) {
			leftMove = 0;
			leftFlg = false;
		}
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1 == null) {
			return false;
		}

		p1.setLocation(e1.getX() / agc.getRateW(), e1.getY() / agc.getRateH());
		p2.setLocation(e2.getX() / agc.getRateW(), e2.getY() / agc.getRateH());

		Line2D l = new Line2D(p1, p2);

		lines = new Line2D[] { top, btm, right, left };
		for (Line2D line2d : lines) {
			if (line2d.intersectsLine(l)) {
				count++;
			}
		}

		returns = new Line2D[] { returnTop, returnBtm, returnRight, returnLeft };
		for (Line2D line2d : returns) {
			if (line2d.intersectsLine(l)) {
				returnCount++;
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

		tx = event.getX() / agc.getRateW();
		ty = event.getY() / agc.getRateH();

		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			if (push() == 0) {
				if (sceneCount <= -120) return false;
				rightFlg = true;
			}
			if (push() == 1) {
				if (sceneCount >= 0) return false;
				leftFlg = true;
			}
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
