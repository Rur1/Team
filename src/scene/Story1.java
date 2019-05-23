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

public class Story1 extends Story implements IScene {

	public Story1(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		super(agc, imageManager, sound);
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("skip", R.drawable.skip);
		imageManager.load("gamePlay1", R.drawable.gameplaybg1);
		charaLoad();
		agc.setOnTouchListener(this);
		currentPage = 0;

		scenarioData = new String[] {
				// 1�b
				"9,��1�b",

				"6,���̐��Ɏカ�҂͂����I�����҂����̐��E�ɑ��݂������A�����đ����̎����z���グ��̂�",
				"7,�E���[�[�[�[�[�[�b�I",
				"8,�����E�E�E���ɁE�E�����ȁE�E�E���E�E�E",
				"1,�݂�ȁE�E�E����ȁE�E�����E�E�E��߂Ă�I�I",

				// ��ʈÓ]
				"1,��߂�[�[�[�[�[�[�[�[�I",
				"1,�܂��E�E���̖����E�E",
				"0,���Ȃ肤�Ȃ���Ă��������v���H",
				"1,�����E�E�E����A���C����",
				"0,���낻��o�����悤",
				"1,����A�S�_�E�E�E�҂��Ă��",

				// (����)
				"0,�{���ɂ��̐�𓌂ɍs���ƋS�_�̍���Ȃ̂��H",
				"1,����A���̐�ɔ����ɋS�_�̗d�C��������",
				"1,�E�E�E",
				"0,�ǂ������H",
				"1,����A�Ȃ�Ŗ����͖l�ɋ��͂��Ă��ꂽ�̂��Ȃ���",
				"0,���͗������Ȃ��玸�H�����Z�M��T���Ă����",
				"0,���ۂƈꏏ�ɗ����Ă���Ȃɂ���|���肭�炢�͒͂߂������ȂƎv���Ă�",
				"1,�����������񂾁E�E�E������Ƃ����ˁI",
				"0,����",

				// �����e���|�����Ď��̃e�L�X�g�\��
				"1,�I�H",
				"1,���̎��̗d�C�Ɏ��Ă�E�E�E",
				"1,���̐�ɋS�_�̎艺�����邩������Ȃ�",
				"0,�����͒m���Ă��Ȃ̂��H",
				"1,�l�̌̋��Ŗ\�ꂽ�d�̗d�C�Ɏ��Ă�񂾁I",
				"0,�������̗d�������璚�x��������ˁ[��",
				"1,�����H",
				"0,�ꏏ�ɋw����낤���I",
				"1,�����E�E�E����I",

				// (�����E����)
				"2,�Ȃ񂾁E�E�E�M�l",
				"1,�����A����ς肠�̎���",
				"0,���O�����ۂ̌̋����r�炵�����{�l���I",
				"2,�Ȃ�̗p���H",
				"0,�S�_�̎���l��ɗ���",
				"2,�S�_�l�E�E���E�E",
				"2,���O�E�E�|��",
				"0,�����E�E����",
				"1,����",

				// ���ۂ̐g�̂����̌`��ɕω����Ă���
				"2,�d���E�E����ɁH",
				"1,���܂������̗d�͐l�Ԃ���������ɕϐg�ł����",
				"2,��H�����E�E�`�����ŁA�������e�Ղł͂Ȃ��ȁH",
				"0,���̌`�ɂȂ��������ŏ\�����A���܂������Č�����I",
				"0,�����E�E�������I",

				// �퓬�J�n

				"9,�����I�I", "2,�O�n�@�[�[�[�[�[�[", "1,������́I�H", "0,�炵����", "1,�݂�ȋw�͎������",
				"0,��Ԃ̂͂܂�������",
				"0,�܂��S�_���c���Ă�",
				"1,����",
				"1,���̗d�̗͂��z�������",
				"0,����Ȃ��Ƃ��ł���̂�",
				"1,����A������Ƒ҂��Ă�",
				"0,�����E�E�����E�E",

				// �ߋ�
				"9,	(���b�̉ߋ�)", "6,�悤�����A���b��", "6,�������炱�����M�l�̋��ꏊ��",
				"6,�䂪��������Ԑl�����Ă��炤", "2,���E�E�S�_�́E�E�Ԑl�H", "6,�������A���l�Ԃł���M�l����̉��œ�����",
				"6,���ꂵ�����낤�H",
				"2,���E�E�l�ԁH",
				"6,�o���Ă��Ȃ��̂��H���O�͂����d����",
				"2,�E�b�E�E�E�b�E�E�I�I�I�I",

				// ����
				"1,�����E�E���v�H", "0,�N�b�E�E�����E�E���v���A�����荡�̂́H",
				"1,���Ԃ�A�͂��z���������ɂ��̗d�̋L���̈ꕔ�������ɗ���Ă����񂾂�",
				"0,�������A�Ƃ������Ƃ͂��̗d�͌��X�l�Ԃ������̂��A������������E�E�E", "1,�����H",
				"0,����A�Ȃ�ł��Ȃ� ����}����", "1,��������",

				"9, [1�b END]" };

		voiceName = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
				"40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
				"50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
				"60", "61", "62", "63", "64", "65", "66", "67", "68", "69",
				"70", "71", "72" };

		voiceID = new int[] {
				// 1�b
				R.raw.k, R.raw.muon, R.raw.muon, R.raw.ran1, R.raw.ran2,
				R.raw.ran3, R.raw.m1, R.raw.ran4, R.raw.m2, R.raw.ran5,
				R.raw.m3, R.raw.ran6, R.raw.ran7, R.raw.m4, R.raw.ran8,
				R.raw.m5, R.raw.m6, R.raw.ran9, R.raw.m7, R.raw.ran10,
				R.raw.ran11, R.raw.ran12, R.raw.m8, R.raw.ran13, R.raw.m9,
				R.raw.ran14, R.raw.m10, R.raw.ran15, R.raw.rai1, R.raw.ran16,
				R.raw.m11, R.raw.rai2, R.raw.m12, R.raw.rai3, R.raw.rai4,
				R.raw.m13, R.raw.ran17, R.raw.rai5, R.raw.ran18, R.raw.rai6,
				R.raw.m14, R.raw.m15,

				// �퓬�I���
				R.raw.rai7, R.raw.ran19, R.raw.m16, R.raw.ran20, R.raw.m17,
				R.raw.m18, R.raw.ran21, R.raw.ran22, R.raw.m19, R.raw.ran23,
				R.raw.m20, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.ran24, R.raw.m21, R.raw.ran25, R.raw.m22,
				R.raw.ran26, R.raw.m23, R.raw.ran27, R.raw.muon,

				// �i���[�V����
				R.raw.n, R.raw.ed };

		result = 41;
		count = -1;
		num = 0;

		if (Stage1.clearFlg) {
			result = 70;
			currentPage = 43;
			count = 41;
			num = 42;
		}

		tx = ty = 0;
	}

	public void update() {
		if (currentPage >= 55 && currentPage <= 64) {
			sound.play("sad", true, true);
		}
		if (currentPage == 65) {
			sound.pause("sad");
		}
		nextLine();
		soundStop();
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("gamePlay1"), 0, 0);
		allDraw(g);
	}

	public void finish() {
		imageManager.finish("skip");
		imageManager.finish("gamePlay1");
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
			push(41, 70, EScene.STAGE1);
			allChange(41, 70, EScene.STAGE1);
			break;
		}

		return false;
	}

}