package scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lib.ImageManager;
import lib.Sound;
import lib.Timer;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

import com.example.team.CharaManager;
import com.example.team.EScene;
import com.example.team.IScene;
import com.example.team.R;

import enemy.Enemy3;
import enemy.State;
import enemyattack.HeightAttack;
import enemyattack.LaneAttack;
import enemyattack.ThunderBall;
import enemyattack.WidthAttack;

public class Stage3 implements IScene {

	AGameCanvas agc;
	EScene next;
	boolean isEnd;
	ImageManager imageManager;
	Sound sound;

	Timer timer;

	CharaManager charaManager;
	Enemy3 enemy3;

	Random rnd;
	List<WidthAttack> widths;
	List<HeightAttack> heights;
	List<ThunderBall> thunderBalls;
	List<Object> delList;

	LaneAttack laneAttack;

	int hp;
	float x, y, length;

	public static boolean clearFlg;

	public Stage3(AGameCanvas agc, ImageManager imageManager, Sound sound, Timer timer,
			CharaManager charaManager) {
		this.agc = agc;
		this.imageManager = imageManager;
		this.sound = sound;
		this.timer = timer;
		this.charaManager = charaManager;

		enemy3 = new Enemy3(agc, imageManager);

		rnd = new Random();
		widths = new ArrayList<WidthAttack>();
		heights = new ArrayList<HeightAttack>();
		thunderBalls = new ArrayList<ThunderBall>();
		delList = new ArrayList<Object>();

		laneAttack = new LaneAttack(agc, charaManager.getPlayer());
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("gamePlay3", R.drawable.gameplaybg3);

		hp = 25;
		length = 28.0f;

		timer.initialize();

		charaManager.initialize();
		enemy3.initialize();
		charaManager.getEnemyHP().initialize(hp, length);
		charaManager.getLaidoSword().initialize();

		widths.clear();
		heights.clear();
		thunderBalls.clear();

		laneAttack.initialize();

		sound.play("drum", false, true);

		x = -570;
		y = 240;

		clearFlg = false;
	}

	public void update() {
		timer.update();
		charaManager.update();

		if (!charaManager.getLaidoSword().getIsEnd()) {
			laido();
			return;
		}
		sound.play("gameplay", true, true);

		enemy3.update();

		transition();

		if (charaManager.getEnemyHP().count()) {
			enemy3.setDamage(true);
		}

		if (charaManager.getEnemyHP().getBar() <= 0) {
			clearFlg = true;
			next = EScene.RESULT;
			isEnd = true;
		}

		if (charaManager.getPlayer().getHp() <= 0) {
			next = EScene.RESULT;
			isEnd = true;
		}
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("gamePlay3"), 0, 0);

		enemy3.draw(g);

		for (WidthAttack widthAttack : widths) {
			widthAttack.draw(g);
		}

		for (HeightAttack heightAttack : heights) {
			heightAttack.draw(g);
		}

		for (ThunderBall thunderBall : thunderBalls) {
			thunderBall.draw(g);
		}

		charaManager.draw(g);
		laneAttack.draw(g);
		charaManager.getLaidoSword().draw(g);
	}

	public void finish() {
		imageManager.finish("gamePlay3");
		// charaManager.finish();
		enemy3.finish();
		sound.pause("gameplay");
		sound.pause("drum");
	}

	public boolean isEnd() {
		return isEnd;
	}

	public EScene next() {
		return next;
	}

	private void laido() {
		charaManager.getLaidoSword().update();

		if (charaManager.getLaidoSword().successAttack()) {
		    charaManager.getEnemyHP().addHp(-4);
			enemy3.setDamage(true);
		}
		if (charaManager.getLaidoSword().missLaido()) {
			charaManager.getPlayer().addHp(-4);
			enemy3.setAttack(true);
		}
	}

	private void addWidthAttack() {
		if (rnd.nextInt(60) == 0) {
			widths.add(new WidthAttack(agc, imageManager));
		}
		for (WidthAttack widthAttack : widths) {
			widthAttack.update();
			widthAttack.collision(charaManager.getSlash().getLine());
			if (widthAttack.guard()) {
				charaManager.getEnemyHP().addDamageCount(1);
				delList.add(widthAttack);
			}
			if (widthAttack.getTimer() == 0) {
				charaManager.getPlayer().addHp(-1);
				enemy3.setAttack(true);
				delList.add(widthAttack);
			}
			if (charaManager.getEnemyHP().getDamageCount() >= 5) {
				delList.add(widthAttack);
			}
		}
		widths.removeAll(delList);
	}

	private void addHeightAttack() {
		if (rnd.nextInt(60) == 0) {
			heights.add(new HeightAttack(agc, imageManager));
		}
		for (HeightAttack heightAttack : heights) {
			heightAttack.update();
			heightAttack.collision(charaManager.getSlash().getLine());
			if (heightAttack.guard()) {
				charaManager.getEnemyHP().addDamageCount(1);
				delList.add(heightAttack);
			}
			if (heightAttack.getTimer() == 0) {
				charaManager.getPlayer().addHp(-1);
				enemy3.setAttack(true);
				delList.add(heightAttack);
			}
			if (charaManager.getEnemyHP().getDamageCount() >= 5) {
				delList.add(heightAttack);
			}
		}
		heights.removeAll(delList);
	}

	private void addThunderBall() {
		if (rnd.nextInt(60) == 0) {
			thunderBalls.add(new ThunderBall(agc, imageManager));
		}
		for (ThunderBall thunderBall : thunderBalls) {
			thunderBall.update();
			thunderBall.permitCollision(charaManager.getSlash());
			thunderBall.setState();
			if (thunderBall.getDx() <= 130) {
				charaManager.getEnemyHP().addHp(-1);
				enemy3.setDamage(true);
				delList.add(thunderBall);
			}
			if (thunderBall.getDx() >= 400) {
				charaManager.getPlayer().addHp(-1);
				enemy3.setAttack(true);
				delList.add(thunderBall);
			}
		}
		thunderBalls.removeAll(delList);
	}

	private void limitPosition() {
		if (x < -700) {
			x = -700;
		}
		if (x > 0) {
			x = 0;
		}
	}

	private void move() {
		if (charaManager.getPlayer().getRoll() > 30) {
			x -= 20;
			limitPosition();
		}
		if (charaManager.getPlayer().getRoll() < -30) {
			x += 20;
			limitPosition();
		}
	}

	private void laneAttack() {
		laneAttack.update();

		if (laneAttack.getAlpha()) {
			charaManager.getPlayer().addHp(-3);
			enemy3.setAttack(true);
		}
	}

	private void transition() {
		if (enemy3.getState() == State.WAIT) {
			if (laneAttack.getCount() >= 3) {
				addHeightAttack();
				addWidthAttack();
			}
			if (laneAttack.getCount() < 3) {
				move();
			}

			laneAttack();

			if (laneAttack.getCount() <= 0) {
				heights.clear();
				widths.clear();
			}
		}

		if (enemy3.getState() == State.JUMP) {
			laneAttack.initialize();
			heights.clear();
			widths.clear();
		}

		if (enemy3.getStateTimer() <= 0) {
			thunderBalls.clear();
			return;
		}

		if (enemy3.getState() == State.TACKLE) {
			heights.clear();
			widths.clear();
			addThunderBall();
		}
	}

}
