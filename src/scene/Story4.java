package scene;

import lib.ImageManager;
import lib.Sound;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import android.view.MotionEvent;
import android.view.View;

import com.example.team.EScene;
import com.example.team.IScene;
import com.example.team.R;
import com.example.team.Story;

public class Story4 extends Story implements IScene {

	public Story4(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		super(agc, imageManager, sound);
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("skip", R.drawable.skip);
		imageManager.load("gamePlay4", R.drawable.story4bg);
		charaLoad();
		agc.setOnTouchListener(this);
		currentPage = 0;

		scenarioData = new String[] {
				// 4�b
				"9,��4�b",

				// (�R�̒���)
				"1,�������R�̒���E�E�E",
				"0,�Ȃ񂾁A����́H",
				"1,���̒����狭���d�C��������",
				"1,�ԈႢ�Ȃ��A���̒��ɋS�_������",

				// �ڂ̑O�ɍ����e�������~�肨��
				"1,�I�H", "5,�v���Ԃ肾�ȁA���", "0,�����������A�Z�M",
				"5,�E�E�E�����Ȃ��񂾂ȁA����Ȏp�ɂȂ����������Ă�",
				"0,�����A�Z�M���d���ɂȂ��ċS�_�̎艺�ɂȂ����̂͂������m��������", "5,�s�m�΂��E�E�E������ȓz��",
				"5,�����A�܂����X�̋S�_�̊�����|���A�����܂ł����̂����O�������Ƃ́A���͂��ꂵ����",
				"5,�܂����A����Ȍ`�Œ���E�߂邱�Ƃ��ł���̂������", "1,�������E�C���A�����I�����I�I", "0,�E�E�E",
				"5,�ǂ������H�Ȃɂ�������������", "1,�����H", "0,���̂����ŁA����Ȏp�ɂ����Ă��܂��āA���܂Ȃ�����",
				"5,�I�H", "0,�S�������񂾁A�Z�M���ꂵ��ł����ƁA���ɑ΂��鑞���݂�",
				"5,���܂�I���O�ɉ����킩��I�킩�����悤�Ȍ��������ȁI", "0,�����A���͌Z�M�̐l�Ԃ̕������E���Ă��܂���",
				"0,�����č��A���̃P�W�������邽�߂ɌZ�M���A����E�E�E�錎���a��I",
				"5,���̉����a��H���オ��Ȃ�l�ԁI�E���Ă��I", "1,�����E�E�E�{���ɂ����̂����H",
				"0,�����A����őS�͂Ŏa���",
				"0,�E�E�E�s�����I�錎�I",
				"5,�E�I�H�H�H�H",

				// (�퓬�J�n)

				"9,�����I�I",
				"5,�~�C�B�B�c�D�D�L�C�B�B�B�B�I�I",
				"0,����Ȃ�E�E�E�Z�M�E�E",

				// SE
				"5,�K�n�b",

				// SE
				"0,�E�E�E", "1,�悩�����́H�����", "0,�E�E�E�悩������", "1,�͂́E�E�E�z�����Ȃ��Ă���",
				"0,�Z�M���܂Ȃ������A���ꂪ���ɂł��鐸��t�̏�����",

				// �ꔏ�Ԃ�����
				"0,�Ō�̐킢��", "1,����A����Ŕ߂����o�����͍Ō�ɂ��Ȃ�����",
				"0,�������ȁA���Ⴀ�Ō�̎d�������ɍs������", "1,����",

				// SE
				"9, [4�b END]" };

		voiceName = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
				"40", "41" };

		voiceID = new int[] {
				// 4�b
				R.raw.rant1, R.raw.mt1, R.raw.rant2, R.raw.rant3, R.raw.rant4,
				R.raw.y1, R.raw.mt2, R.raw.y2, R.raw.mt3, R.raw.y3,
				R.raw.y4, R.raw.y5, R.raw.rant5, R.raw.mt4, R.raw.y6,
				R.raw.rant6, R.raw.mt5, R.raw.y7, R.raw.mt6, R.raw.y8,
				R.raw.mt7,  R.raw.mt8, R.raw.y9, R.raw.rant7, R.raw.mt9,
				R.raw.mt10, R.raw.y10, R.raw.muon, R.raw.y11, R.raw.mt11,
				R.raw.y12, R.raw.mt12, R.raw.rant8, R.raw.mt13, R.raw.mt14,
				R.raw.mt15, R.raw.mt16, R.raw.rant9, R.raw.mt17, R.raw.rant10,
				R.raw.muon

				// �i���[�V����
				/*R.raw.n, R.raw.ed,*/ };

		result = 26;
		count = -1;
		num = 0;

		if (Stage4.clearFlg) {
			result = 40;
			currentPage = 28;
			count = 27;
			num = 28;
		}

		tx = ty = 0;
	}

	public void update() {
		nextLine();
		soundStop();
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("gamePlay4"), 0, 0);
		allDraw(g);
	}

	public void finish() {
		imageManager.finish("skip");
		imageManager.finish("gamePlay4");
		charaFinish();
	}

	public boolean isEnd() {
		return isEnd;
	}

	public EScene next() {
		return next;
	}

	public boolean onTouch(View v, MotionEvent event) {
		tx = event.getX() / agc.getRateW();
		ty = event.getY() / agc.getRateH();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			push(26, 40, EScene.STAGE4);
			allChange(26, 40, EScene.STAGE4);
			break;
		}

		return false;
	}

}
