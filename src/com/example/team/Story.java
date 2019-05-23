package com.example.team;

import lib.ImageManager;
import lib.Sound;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import scene.Stage1;
import scene.Stage2;
import scene.Stage3;
import scene.Stage4;
import scene.Stage5;
import android.graphics.Color;
import android.view.View.OnTouchListener;

public abstract class Story implements OnTouchListener {

	protected AGameCanvas agc;
	protected EScene next;
	protected boolean isEnd;
	protected ImageManager imageManager;
	protected Sound sound;

	int currentChara;
	protected int currentPage;
	String currentCharaName;
	int stringLength;
	String firstLine;
	String secondLine;
	String thirdLine;

	int fontSize;
	protected int result, count, num;

	float nameX, nameY;
	protected float x, y, tx, ty;

	protected String[] scenarioData, voiceName;
	protected int[] voiceID;

	String[] charaNames;
	float charaY;

	protected Story(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		this.agc = agc;
		this.imageManager = imageManager;
		this.sound = sound;

		nameX = 50;
		nameY = 980;
		fontSize = 20;
		count = -1;
		num = 0;

		x = 570;
		y = 795;

		charaNames = new String[] { "mituki", "ranmaru", "raiju", "shiranui",
				"tamamo", "tengu1", "tengu2", "kishin" };

		charaY = 150;
	}

	protected void charaLoad() {
		imageManager.load(charaNames[0], R.drawable.mituki);
		imageManager.load(charaNames[1], R.drawable.ranmaru);
		imageManager.load(charaNames[2], R.drawable.raiju);
		imageManager.load(charaNames[3], R.drawable.shiranui);
		imageManager.load(charaNames[4], R.drawable.tamamo);
		imageManager.load(charaNames[5], R.drawable.tengu1);
		imageManager.load(charaNames[6], R.drawable.tengu2);
		imageManager.load(charaNames[7], R.drawable.kishin);
	}

	protected void charaFinish() {
		for (int i = 0; i < charaNames.length; i++) {
			imageManager.finish(charaNames[i]);
		}
	}

	protected void nextLine() {
		currentChara = Integer.valueOf(scenarioData[currentPage]
				.substring(0, 1));
		stringLength = scenarioData[currentPage].length() - 2;
		if (stringLength >= 25) {
			firstLine = scenarioData[currentPage].substring(2, 22);
			secondLine = scenarioData[currentPage].substring(22);
		} else {
			firstLine = scenarioData[currentPage].substring(2);
			secondLine = null;
			thirdLine = null;
		}
	}

	protected void allDraw(Graphics g) {
		switch (currentChara) {
		case 0:
			currentCharaName = "–£ŒŽ";
			g.drawImage(imageManager.getImage(charaNames[0]), 0, charaY);
			break;
		case 1:
			currentCharaName = "—’ŠÛ";
			g.drawImage(imageManager.getImage(charaNames[1]), 0, charaY);
			break;
		case 2:
			currentCharaName = "—‹b";
			g.drawImage(imageManager.getImage(charaNames[2]), 0, charaY);
			break;
		case 3:
			currentCharaName = "•s’m‰Î";
			g.drawImage(imageManager.getImage(charaNames[3]), 0, charaY);
			break;
		case 4:
			currentCharaName = "‹Ê‘”‘O";
			g.drawImage(imageManager.getImage(charaNames[4]), 0, charaY);
			break;
		case 5:
			currentCharaName = "–éŒŽ";
			g.drawImage(imageManager.getImage(charaNames[6]), 0, charaY);
			break;
		case 6:
			currentCharaName = "‹S_";
			g.drawImage(imageManager.getImage(charaNames[7]), 0, charaY);
			break;
		case 7:
			currentCharaName = "—d‰öA";
			break;
		case 8:
			currentCharaName = "—d‰öB";
			break;
		case 9:
			currentCharaName = "";
			break;
		}

		g.setColor(30, 30, 30);
		g.fillRect(0, 900, agc.getWidthA(), 310);

		g.setColor(Color.WHITE);
		g.setFontSize(fontSize);

		g.drawString(currentCharaName, nameX, nameY);

		if (firstLine != null) {
			g.drawString(firstLine, 50, 1050);
		}
		if (secondLine != null) {
			g.drawString(secondLine, 50, 1090);
		}
		if (thirdLine != null) {
			g.drawString(thirdLine, 50, 1130);
		}

		g.drawImage(imageManager.getImage("skip"), x, y);
	}

	protected void voice() {
		count++;
		sound.load(voiceName[count], voiceID[count]);
		sound.play(voiceName[count], false, true);
		if (count > num) {
			sound.stop(voiceName[count - 1]);
			sound.finish(voiceName[count - 1]);
		}
	}

	protected void push(int num1, int num2, EScene next) {
		boolean touchX = tx > x && tx < x + 200;
		boolean touchY = ty > y && ty < y + 80;
		if (touchX && touchY) {
			if (result == num1) {
				this.next = next;
			}
			if (result == num2) {
				Stage1.clearFlg = false;
				Stage2.clearFlg = false;
				Stage3.clearFlg = false;
				Stage4.clearFlg = false;
				Stage5.clearFlg = false;
				this.next = EScene.STAGESELECT;
			}
			isEnd = true;
		}
	}

	protected void allChange(int num1, int num2, EScene next) {
		if (count < result) {
			currentPage++;
			voice();
			return;
		}
		if (result == num1) {
			this.next = next;
		}
		if (result == num2) {
			Stage1.clearFlg = false;
			Stage2.clearFlg = false;
			Stage3.clearFlg = false;
			Stage4.clearFlg = false;
			Stage5.clearFlg = false;
			this.next = EScene.STAGESELECT;
		}
		isEnd = true;
	}

	protected void soundStop() {
		if (isEnd) {
			sound.stop(voiceName[count]);
			sound.finish(voiceName[count]);
		}
	}

}
