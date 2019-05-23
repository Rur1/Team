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

import enemy.Enemy4;
import enemy.State;
import enemyattack.LaneAttack;
import enemyattack.LaneBullet;
import enemyattack.RandomBullet;
import enemyattack.TackleAttack;

public class Stage4 implements IScene {

	AGameCanvas agc;
	EScene next;
	boolean isEnd;
	ImageManager imageManager;
	Sound sound;

	Timer timer;

	CharaManager charaManager;
	Enemy4 enemy4;

	LaneBullet laneBullet;

	TackleAttack tackleAttack;
	LaneAttack laneAttack;

	List<RandomBullet> bullets;
	List<Object> delList;

	int addAttack, attackCount;

	public static boolean clearFlg;

	public Stage4(AGameCanvas agc, ImageManager imageManager, Sound sound,
			Timer timer, CharaManager charaManager) {
		this.agc = agc;
		this.imageManager = imageManager;
		this.sound = sound;
		this.charaManager = charaManager;
		this.timer = timer;

		enemy4 = new Enemy4(agc, imageManager);

		bullets = new ArrayList<RandomBullet>();
		delList = new ArrayList<Object>();
		laneBullet = new LaneBullet(agc, imageManager, charaManager);

		tackleAttack = new TackleAttack(agc, imageManager, sound);
		laneAttack = new LaneAttack(agc, charaManager.getPlayer());
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("gamePlay4", R.drawable.gameplaybg4);

		timer.initialize();

		charaManager.initialize();
		enemy4.initialize();
		charaManager.getEnemyHP().initialize(40, 17.5f);
		charaManager.getLaidoSword().initialize();

		laneBullet.initialize();

		tackleAttack.initialize();
		laneAttack.initialize();

		bullets.clear();

		sound.play("drum", false, true);

		addAttack = 20;
		attackCount = 0;

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

		enemy4.update();

		transition();

		if (charaManager.getEnemyHP().count()) {
			enemy4.setDamage(true);
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
		g.drawImage(imageManager.getImage("gamePlay4"), 0, 0);

		enemy4.draw(g);

		if (enemy4.getState() == State.WAIT) {
			for (RandomBullet randomBullet : bullets) {
				randomBullet.draw(g);
			}
			laneAttack.draw(g);
		}

		if (enemy4.getState() == State.TACKLE) {
			if (laneBullet.getNum() >= 3) {
				tackleAttack.draw(g);
			}

			if (attackCount < 15) {
				for (RandomBullet randomBullet : bullets) {
					randomBullet.draw(g);
				}
			} else {
				laneBullet.draw(g);
			}
		}

		charaManager.draw(g);
		charaManager.getLaidoSword().draw(g);
	}

	public void finish() {
		imageManager.finish("gamePlay4");
		enemy4.finish();
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
			enemy4.setDamage(true);
		}
		if (charaManager.getLaidoSword().missLaido()) {
			charaManager.getPlayer().addHp(-4);
			enemy4.setAttack(true);
		}
	}

	private void tackleAttack() {
		tackleAttack.update();
		tackleAttack.zoomCollision(charaManager.getSlash());
		tackleAttack.defence();
		if (enemy4.attack()) {
			charaManager.getPlayer().addHp(-3);
		}
		if (tackleAttack.getNext()) {
			enemy4.positionInit();

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
				enemy4.setAttack(true);
				delList.add(randomBullet);
			}
		}
		bullets.removeAll(delList);
	}

	private void transition() {
		if (enemy4.getState() == State.WAIT) {
			addBulletAttack();
			laneAttack.update();
			if (laneAttack.getAlpha()) {
				charaManager.getPlayer().addHp(-3);
				enemy4.setAttack(true);
			}
			if (attackCount >= 5) {
				charaManager.getEnemyHP().addDamageCount(5);
			}
			if (attackCount >= 9) {
				enemy4.setChange(false);
				enemy4.waiting();
				charaManager.getEnemyHP().damageCountInit();
				tackleAttack.initialize();
				laneBullet.setNum(0);
				bullets.removeAll(delList);
				attackCount = 0;
			}
		}

		if (enemy4.getState() == State.TACKLE) {
			if (laneBullet.getNum() >= 3) {
				attackCount = 0;
				enemy4.setChange(true);
				tackleAttack();
			}

			if (attackCount < 15) {
				addBulletAttack();
				return;
			}
			laneBullet.update();
			laneBullet.collision(charaManager.getSlash().getLine());
			if (laneBullet.reset()) {
				charaManager.getPlayer().addHp(-1);
			}
		}
	}

}
