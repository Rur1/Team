package scene;

import java.util.ArrayList;
import java.util.List;

import lib.ImageManager;
import lib.Sound;
import lib.Timer;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

import com.example.team.CharaManager;
import com.example.team.EScene;
import com.example.team.IScene;
import com.example.team.R;

import enemy.Enemy5;
import enemy.State;
import enemyattack.LaneAttack;
import enemyattack.LaneBullet;
import enemyattack.NineBullet;
import enemyattack.RandomBullet;
import enemyattack.TackleAttack;

public class Stage5 implements IScene {

	AGameCanvas agc;
	EScene next;
	boolean isEnd;
	ImageManager imageManager;
	Sound sound;

	Timer timer;

	CharaManager charaManager;
	Enemy5 enemy5;

	LaneBullet laneBullet;

	TackleAttack tackleAttack;
	LaneAttack laneAttack;

	List<RandomBullet> bullets;
	List<NineBullet> nineBullet;
	List<Object> delList;

	int addAttack, attackCount;

	boolean startAttack;

	public static boolean clearFlg;

	public Stage5(AGameCanvas agc, ImageManager imageManager, Sound sound, Timer timer,
			CharaManager charaManager) {
		this.agc = agc;
		this.imageManager = imageManager;
		this.sound = sound;
		this.timer = timer;
		this.charaManager = charaManager;

		enemy5 = new Enemy5(agc, imageManager);

		laneBullet = new LaneBullet(agc, imageManager, charaManager);

		tackleAttack = new TackleAttack(agc, imageManager, sound);
		laneAttack = new LaneAttack(agc, charaManager.getPlayer());

		bullets = new ArrayList<RandomBullet>();
		nineBullet = new ArrayList<NineBullet>();
		delList = new ArrayList<Object>();
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("gamePlay5", R.drawable.gameplaybg5);

		timer.initialize();

		charaManager.initialize();
		enemy5.initialize();
		charaManager.getEnemyHP().initialize(40, 17.5f);
		charaManager.getLaidoSword().initialize();

		laneBullet.initialize();

		tackleAttack.initialize();
		laneAttack.initialize();

		bullets.clear();
		nineBullet.clear();
		delList.clear();

		sound.play("drum", false, true);

		addAttack = 20;
		attackCount = 0;
		startAttack = true;

		clearFlg = false;
	}

	public void update() {
		timer.update();
		charaManager.update();

		if (!charaManager.getLaidoSword().getIsEnd()) {
			laido();
			return;
		}
		sound.play("gameplay2", true, true);

		enemy5.update();

		transition();

		if (charaManager.getEnemyHP().count()) {
			enemy5.setDamage(true);
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
		g.drawImage(imageManager.getImage("gamePlay5"), 0, 0);

		enemy5.draw(g);

		if (charaManager.getLaidoSword().getIsEnd()) {
			for (NineBullet nineBullet : nineBullet) {
				nineBullet.draw(g);
			}
		}

		for (RandomBullet randomBullet : bullets) {
			randomBullet.draw(g);
		}

		if (enemy5.getState() == State.TACKLE) {
			laneBullet.draw(g);
			laneAttack.draw(g);
			if (laneBullet.getNum() >= 5) {
				tackleAttack.draw(g);
			}
		}

		charaManager.draw(g);
		charaManager.getLaidoSword().draw(g);
	}

	public void finish() {
		imageManager.finish("gamePlay5");
		enemy5.finish();
		sound.pause("gameplay2");
		sound.pause("drum");
	}

	public boolean isEnd() {
		return isEnd;
	}

	public EScene next() {
		return next;
	}

	private void addNineBullet() {
		charaManager.setX(0);
		charaManager.setY(0);
		for (int i = 1; i <= 9; i++) {
			nineBullet.add(new NineBullet(agc, imageManager, i));
		}
	}

	private void laido() {
		charaManager.getLaidoSword().update();

		if (charaManager.getLaidoSword().successAttack()) {
			charaManager.getEnemyHP().addHp(-4);
			enemy5.setDamage(true);
			addNineBullet();
		}
		if (charaManager.getLaidoSword().missLaido()) {
			charaManager.getPlayer().addHp(-4);
			enemy5.setAttack(true);
			addNineBullet();
		}
	}

	private void tackleAttack() {
		tackleAttack.update();
		tackleAttack.zoomCollision(charaManager.getSlash());
		tackleAttack.defence();
		if (enemy5.attack()) {
			charaManager.getPlayer().addHp(-3);
		}
		if (tackleAttack.getNext()) {
			enemy5.positionInit();
		}
	}

	private void addBulletAttack() {
		addAttack--;
		if (addAttack <= 0) {
			bullets.add(new RandomBullet(agc, imageManager));
			addAttack = 20;
		}
		for (RandomBullet randomBullet : bullets) {
			randomBullet.update();
			if (randomBullet.pushSet(charaManager)) {
				attackCount++;
				delList.add(randomBullet);
			}

			if (randomBullet.reset()) {
				charaManager.getPlayer().addHp(-1);
				enemy5.setAttack(true);
				delList.add(randomBullet);
			}
		}
		bullets.removeAll(delList);
	}

	private void allAttack() {
		for (NineBullet nineBullet : nineBullet) {
			nineBullet.update();
			if (nineBullet.pushSet(charaManager)) {
				delList.add(nineBullet);
			}

			if (nineBullet.reset()) {
				charaManager.getPlayer().addHp(-1);
				enemy5.setAttack(true);
				delList.add(nineBullet);
			}
			if (delList.size() == 9) {
				startAttack = false;
			}
		}
		nineBullet.removeAll(delList);
	}

	private void transition() {
		if (enemy5.getState() == State.WAIT) {
			if (startAttack == true) {
				allAttack();
				return;
			}
			addBulletAttack();
			if (attackCount >= 5) {
				charaManager.getEnemyHP().addDamageCount(5);
			}
			if (attackCount >= 15) {
				enemy5.setChange(false);
				enemy5.waiting();
				charaManager.getEnemyHP().damageCountInit();
				tackleAttack.initialize();
				laneBullet.setNum(0);
				bullets.removeAll(delList);
				attackCount = 0;
			}
		}

		if (enemy5.getState() == State.TACKLE) {
			if (laneBullet.getNum() >= 5) {
				enemy5.setChange(true);
				tackleAttack();
				return;
			}
			laneBullet.update();
			laneBullet.collision(charaManager.getSlash().getLine());
			if (laneBullet.reset()) {
				charaManager.getPlayer().addHp(-1);
			}
			laneAttack.update();
			if (laneAttack.getAlpha()) {
				charaManager.getPlayer().addHp(-3);
				enemy5.setAttack(true);
			}
		}
	}

}
