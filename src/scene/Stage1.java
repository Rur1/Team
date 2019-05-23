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

import enemy.Enemy1;
import enemy.State;
import enemyattack.CircleAttack;
import enemyattack.SwordPushing;
import enemyattack.TackleAttack;

public class Stage1 implements IScene {

	AGameCanvas agc;
	EScene next;
	boolean isEnd;
	ImageManager imageManager;
	Sound sound;

	Timer timer;

	CharaManager charaManager;
	Enemy1 enemy1;

	Random rnd;
	List<CircleAttack> circles;
	List<Object> delList;
	TackleAttack tackleAttack;
	SwordPushing swordPushing;

	int frameTimer;

	public static boolean clearFlg;

	public Stage1(AGameCanvas agc, ImageManager imageManager, Sound sound, Timer timer, CharaManager charaManager) {
		this.agc = agc;
		this.imageManager = imageManager;
		this.sound = sound;
		this.timer = timer;
		this.charaManager = charaManager;

		enemy1 = new Enemy1(agc, imageManager);

		rnd = new Random();
		circles = new ArrayList<CircleAttack>();
		delList = new ArrayList<Object>();
		tackleAttack = new TackleAttack(agc, imageManager, sound);
		swordPushing = new SwordPushing(agc, imageManager, sound);
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("gamePlay1", R.drawable.gameplaybg1);

		timer.initialize();

		charaManager.initialize();
		enemy1.initialize();
		charaManager.getEnemyHP().initialize(10, 70.0f);
		charaManager.getLaidoSword().initialize();

		circles.clear();
		tackleAttack.initialize();
		swordPushing.initialize();

		sound.play("drum", false, true);

		frameTimer = 10;

		clearFlg = false;
	}

	public void update() {
		timer.update();
		charaManager.update();

		if (frameTimer < 10) {
			frameTimer++;
		}

		if (!charaManager.getLaidoSword().getIsEnd()) {
			laido();
			return;
		}
		sound.play("gameplay", true, true);

		enemy1.update();

		transition();

		if (charaManager.getEnemyHP().count()) {
			enemy1.setDamage(true);
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
		g.drawImage(imageManager.getImage("gamePlay1"), 0, 0);

		enemy1.draw(g);

		for (CircleAttack circleAttack : circles) {
			circleAttack.draw(g);
		}

		if (enemy1.getState() == State.TACKLE) {
			tackleAttack.draw(g);
		}
		charaManager.draw(g);

		if (frameTimer < 10) {
			g.drawImage(imageManager.getImage("effect"), charaManager.getX() - 75, charaManager.getY() - 75);
		}

		if (enemy1.getState() == State.SWORDPUSH) {
			swordPushing.draw(g);
		}
		charaManager.getLaidoSword().draw(g);
	}

	public void finish() {
		imageManager.finish("gamePlay1");
		// charaManager.finish();
		enemy1.finish();
		// tackleAttack.finish();
		swordPushing.finish();
		sound.pause("drum");
		sound.pause("gameplay");
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
			enemy1.setDamage(true);
		}
		if (charaManager.getLaidoSword().missLaido()) {
			charaManager.getPlayer().addHp(-4);
			enemy1.setAttack(true);
		}
	}

	private void addCircleAttack() {
		if (rnd.nextInt(15) == 0) {
			circles.add(new CircleAttack(agc, imageManager));
		}
		for (CircleAttack circleAttack : circles) {
			circleAttack.update();
			if (circleAttack.push(charaManager)) {
				frameTimer = 0;

				charaManager.getEnemyHP().addDamageCount(1);

				int guard = rnd.nextInt(3);
				if (guard == 0) {
					sound.play("guard1", false, true);
				}
				if (guard == 1) {
					sound.play("guard2", false, true);
				}
				if (guard == 2) {
					sound.play("guard3", false, true);
				}

				delList.add(circleAttack);
			}
			if (circleAttack.getTimer() == 0) {
				charaManager.getPlayer().addHp(-1);
				enemy1.setAttack(true);
				delList.add(circleAttack);
			}
			if (charaManager.getEnemyHP().getDamageCount() >= 5) {
				delList.add(circleAttack);
			}
		}
		circles.removeAll(delList);
	}

	private void tackleAttack() {
		tackleAttack.update();
		tackleAttack.zoomCollision(charaManager.getSlash());
		tackleAttack.defence();
		if (enemy1.attack()) {
			charaManager.getPlayer().addHp(-3);
		}
		if (tackleAttack.getNext()) {
			enemy1.setState(State.SWORDPUSH);
		}
	}

	private void swordPush() {
		swordPushing.update();
		if (swordPushing.count()) {
			charaManager.getEnemyHP().addDamageCount(5);
		}
		if (swordPushing.damage()) {
			charaManager.getPlayer().addHp(-3);
			enemy1.setAttack(true);
		}
		if (swordPushing.getIsEnd()) {
			enemy1.setState(State.WAIT);
		}
	}

	private void enemyAttackInit() {
		circles.clear();
		tackleAttack.initialize();
		swordPushing.initialize();
		charaManager.getEnemyHP().damageCountInit();
	}

	private void transition() {
		if (enemy1.getState() == State.WAIT) {
			addCircleAttack();
		}
		if (enemy1.getState() == State.JUMP) {
			enemyAttackInit();
		}
		if (enemy1.getState() == State.TACKLE) {
			tackleAttack();
		}
		if (enemy1.getState() == State.SWORDPUSH) {
			swordPush();
		}
	}

}
