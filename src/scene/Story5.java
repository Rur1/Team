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

public class Story5 extends Story implements IScene {

	public Story5(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		super(agc, imageManager, sound);
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("skip", R.drawable.skip);
		imageManager.load("gamePlay4", R.drawable.gameplaybg4);
		imageManager.load("gamePlay5", R.drawable.gameplaybg5);
		charaLoad();
		agc.setOnTouchListener(this);
		currentPage = 0;

		scenarioData = new String[] {
				// 5�b
				"9,��5�b",

				// (�����̋���)
				"0,�E�E�E", "6,�҂��Ă������A���������ė���", "0,��������o���͖������H",
				"6,�����Ƃ����Ō��Ă�������ȁA���̏ꏊ�͐��E�����n����ꏊ�Ȃ�",
				"0,�Ȃ�قǁA�Ď�����Ă��̂�",
				"1,�S�_�I�Ȃ�ł݂�Ȃ��E�����́H����Ȃɑ�������D���Ȃ�",
				"6,�������A��͐�����߁A���̐���헐�̉Q�Ɋ������݁A�����ς���A���̐�̎���Ɏ�҂͂����",
				"6,���񂾂͎̂��͂��������������̘b��",
				"6,�����M�l��Ⴄ�A��̔z����S�ē|���A���������܂ł��ǂ蒅���Ă݂���",
				"6,������A�d�ɓ]����ɍ~��A���E�̔���������Ă�낤",
				"0,�f��A���������ɗ������������Ă邾��",
				"0,���O���a��ɗ����񂾁A�������ۂ�",
				"6,�Ȃ�Ύd������܂�",
				"6,�M�l��Ƃ̐킢�ɋ�����Ƃ��悤",
				"0,���ہA���ꂪ�Ō�̐킢���A�s�����I",

				// (�퓬�J�n)

				"9,�����I�I",
				"6,�����A���ہE�E�E��͂�M�l��͑f���炵�����݂�",
				"6,���̍��g���I�A��������܂ōV�点���̂͋M�l�炪���߂Ă���",
				"6,�Ō�ɋM�l��ƋY�ꂽ���Ɗ������E�E�v���E�E���E�E�E���E�E��",

				// �S�_����
				"0,�I������E�E�E�́E�E�E��",
				"1,�������I�l�珟������",

				// SE
				"1,���A�ȂɁH�A�����n�܂�́H",
				"0,�����炭�S�_�����ł������A���̋�Ԃ�����悤�Ƃ��Ă���",
				"0,�����E�o���邼�I",

				// ��ʈÓ]

				// (�R�̒���)

				// SE(�t�B�[�h�A�E�g)
				"0,�Ȃ�Ƃ��Ԃɍ�������", "1,�E�E�E", "1,���܂Ŋ����Ă������d���̋C�z���S���Ȃ��Ȃ��Ă�",
				"1,�{���ɏI������񂾂�", "0,����", "1,�����A���肪�Ƃ�",
				"0,�������ۂ̂������Ŏ����̖ړI���ʂ������A��������͉̂������A���肪�Ƃ�",
				"9,�������Ė��������̒����ɓn��킢���I�����", "9,���ۂ͎����̗̓y�ŐV���Ȓ��Ԃ����A�Ђ�����ƕ�炵",
				"9,�����͐V���Ȍق���������Ăїp�S�_�֖߂����B",
				"9,�l�X�͋S�_���|���ꂽ���Ƃ�傢�Ɋ�񂾁B",
				"9,�����|�����҂̖���m��҂͒N�����Ȃ������B",
				"9,�����Č�ɁA�S�_��|���������m��ʉp�Y�̂��Ƃ��a�H�S�ƌĂь㐢�Ɍ��p�����Ƃ����E�E�E",

				// ED �I���
				"9, [5�b END �a�H�S ����]" };

		voiceName = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "32", "33", "34", "35", "36", "37", "38" };

		voiceID = new int[] {
				// 5�b
				R.raw.mf1, R.raw.k1, R.raw.mf2, R.raw.k2, R.raw.mf3,
				R.raw.ranf1, R.raw.k3, R.raw.k4, R.raw.k5, R.raw.k6,
				R.raw.mf4, R.raw.mf5, R.raw.k7, R.raw.k8, R.raw.mf6,
				R.raw.muon, R.raw.k9, R.raw.k10, R.raw.k11, R.raw.mf7,
				R.raw.ranf2, R.raw.ranf3, R.raw.mf8, R.raw.mf9, R.raw.mf10,
				R.raw.ranf4, R.raw.ranf5, R.raw.ranf6, R.raw.mf11, R.raw.ranf7,
				R.raw.mf12, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.muon

				// �i���[�V����
				// R.raw.n,R.raw.ed
				};

		result = 14;
		count = -1;
		num = 0;

		if (Stage5.clearFlg) {
			result = 37;
			currentPage = 16;
			count = 15;
			num = 16;
		}

		tx = ty = 0;
	}

	public void update() {
		nextLine();
		soundStop();
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("gamePlay5"), 0, 0);
		if (currentPage > 24) {
			g.drawImage(imageManager.getImage("gamePlay4"), 0, 0);
		}
		allDraw(g);
	}

	public void finish() {
		imageManager.finish("skip");
		imageManager.finish("gamePlay4");
		imageManager.finish("gamePlay5");
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
			push(14, 37, EScene.STAGE5);
			allChange(14, 37, EScene.STAGE5);
			break;
		}

		return false;
	}

}
