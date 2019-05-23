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

public class Story3 extends Story implements IScene {

	public Story3(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		super(agc, imageManager, sound);
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("skip", R.drawable.skip);
		imageManager.load("gamePlay3", R.drawable.gameplaybg3);
		charaLoad();
		agc.setOnTouchListener(this);
		currentPage = 0;

		scenarioData = new String[] {
				// 3�b
				"9,��3�b",

				// (�R�̒���)
				"0,���������R�̒��ォ�E�E�E", "1,���̐킢�����������I����",
				"1,�I",
				"1,�����I�G���I������",
				"0,�Ȃ��I",

				// �����͂��낤���œG�̍U���������
				"4,���͋S�_�l�̕����̈�l�A�ʑ��O", "4,���������͍s�����Ȃ����", "0,�ז����I�ǂ��I",
				"1,�ʑ��O�E�E�E�����o��������A�S�_�̉E�r�ƌĂ΂�Ă���z��", "1,���܂œ|�����Q�̂Ƃ͊i���Ⴄ�A�C��t���āI",
				"0,������",

				// �퓬�J�n

				"9,�����I�I", "4,�\���󂠂�܂���E�E�E�S�_�l", "0,�N�b�E�E�n�@�n�@", "1,���Ȃ��J���Ă邯�Ǒ��v�H",
				"0,���Ȃ��A�����̗͂��z�����Ă���", "1,����",

				"9,(�ʑ��O�̉ߋ�)",

				// �S�_�̍���
				"6,�ʑ��O��A���b�ƕs�m�΂𑒂����҂�����", "4,���", "6,��͂����ł܂��͂�~���A�S�Ă̗̓y��D���ɍs��",

				"9,(����E�R�̒���)",

				"0,�}���Ȃ��ƁA�ǂ����{���Ƀ}�Y���悤����",

				// �n�ʂ��h���
				"1,�E�E�E�I�H�܂��n�k�I�H", "0,��������ɍs����",

				"9, [3�b END]", };

		voiceName = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26" };

		voiceID = new int[] {
				// 3�b
				R.raw.ma1, R.raw.rana1, R.raw.rana2, R.raw.rana3, R.raw.ma2,
				R.raw.t1, R.raw.t2, R.raw.ma3, R.raw.rana4, R.raw.rana5,
				R.raw.ma4, R.raw.muon, R.raw.t3, R.raw.ma5, R.raw.rana6,
				R.raw.ma6, R.raw.rana7, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.ma7, R.raw.rana8, R.raw.ma8,
				R.raw.muon

				// �i���[�V����
				/*R.raw.n, R.raw.ed*/ };

		result = 10;
		count = -1;
		num = 0;

		if (Stage3.clearFlg) {
			result = 25;
			currentPage = 12;
			count = 11;
			num = 12;
		}

		tx = ty = 0;
	}

	public void update() {
		if (currentPage >= 18 && currentPage <= 21) {
			sound.play("sad", true, true);
		}
		if (currentPage == 22) {
			sound.pause("sad");
		}
		nextLine();
		soundStop();
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("gamePlay3"), 0, 0);
		allDraw(g);
	}

	public void finish() {
		imageManager.finish("skip");
		imageManager.finish("gamePlay3");
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
			push(10, 25, EScene.STAGE3);
			allChange(10, 25, EScene.STAGE3);
			break;
		}

		return false;
	}

}
