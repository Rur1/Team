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

public class Story2 extends Story implements IScene {

	public Story2(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		super(agc, imageManager, sound);
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("skip", R.drawable.skip);
		imageManager.load("gamePlay2", R.drawable.gameplaybg2);
		charaLoad();
		agc.setOnTouchListener(this);
		currentPage = 0;

		scenarioData = new String[] {
				// 2�b
				"9,��2�b",

				// ����
				"1,�����E�E�����Ă������H",
				"0,�ǂ�����",
				"1,�����̂��Z������Ăǂ�Ȑl�Ȃ́H",
				"0,�̂͂ƂĂ��D�����ēw�͂�ӂ�Ȃ����h�Ȑl������",
				"1,�̂́H���͈Ⴄ�́H",
				"0,�����̘b�����悤���E�E�E",
				"9,(�����̉ߋ�)",
				"9,���͐́A�傫�Ȃ����~�̗p�S�_���Z�M�ƁE�E�E�Z��ł��Ă�����",
				"9,�������Z��͉Ƃ̎҂��P�����Ƃ���ґS�Ă��a��A�����ȗp�S�_������",
				"9,�����A�����A�ˑR�Z�M���Ƃ̐l�Ԃ�S���E���A�ǂ����֎p��������",

				// ����
				"0,�Ȃ�ŁA�Z�M������Ȃ��Ƃ��������킩��Ȃ����ǁA���̈�Ƃɂ͐F�X���b�ɂȂ���",
				"0,����Ȃ݂�Ȃ��E�����Z�M�����͋������Ⴂ���Ȃ��Ǝv��",
				"1,�����E�E���߂�ˁA�������Ⴂ���Ȃ��������",
				"0,�C�ɂ����",

				// ���̎R�E�����
				"0,��������",
				"0,�S�̏����͂������H����",
				"1,�������",
				"3,����A�Ƃ��Ƃ����ǂ蒅���Ă��܂����́H",

				// (�s�m�Γo��)
				"0,���O�́I",
				"3,���͕s�m��",
				"3,�S�_�l�̖��ɂ��M�l�̖�������ɗ���",
				"3,��H�Ȃ񂾁A�M�l�A���̒j�̌����҂��H",
				"0,�����ҁH�܂����I����ς�Z�M�͂����ɂ���̂��I�H",
				"3,�����x��������",
				"3,�����͂����l�Ƃ��Ď��񂾂��H",
				"0,�Ă߂�",

				// �������s�m�΂Ɏa�肩����
				"1,�����҂��āA㩂��I",

				// �s�m�΁E�����E���ۂ̎�����������
				"3,�����A����ŋM�l�͏o���Ȃ��A�����Ղ�ȂԂ�E���Ă��",

				// (�퓬�J�n)

				"9,�����I�I",

				// ����̉���������
				"3,�O�b�E�E�E�n�@�n�@�E�E�N�\�E�E�l�ԕ��",
				"3,�M�l�͂��������n�������邱�ƂɂȂ邼",
				"3,�E�E�E���̐��ő҂��Ă邩��ȁE�E�E",

				// �s�m�Ώ���
				"0,�Z�M�͂�͂�d�ɁE�E�E",
				"1,�����E�E�E",
				"0,�����E�E�E�܂�����",

				// (�s�m�΂̉ߋ��̋L��)
				"9,(���~�E��)",

				"5,���҂��M�l�̂悤�Ȏ҂����������Ă����ꏊ�ł͂Ȃ���",
				"3,����A�|���ł�����Ȃ��ƌ����āA���Ȃ��Ȃɂ��ɋ���������Ă�����", "5,�Ȃ������m���Ă���I�H",
				"3,�b���Ă݂Ȃ�����A�����Ă������H", "5,����E�E���́E�E�E",
				"3,���߂炤�K�v�Ȃ�ĂȂ���A�l�ԒN�ł��Y�݂����傢����ł���̂�", "3,���Ȃ��̔Y�݂͂ǂ�Ȃ��́H",
				"5,���͌Z��̒��j���E�E�E�������͒��������Ă���",
				"5,�̂͌��̋Z�p�����݂����������Ă����A�������ł́A�����ƈꏏ�ɂ���̂��|���Ȃ�񂾁B",
				"5,�����͎����ł��C�Â��Ă��Ȃ����A�����̌��p�͂����������̗̈�ɒB���悤�Ƃ��Ă���",
				"5,���̂܂܂ł͌ق���Ɏ̂Ă��邩������Ȃ��A���ݗ��R�������Ȃ��Ă��܂��̂ł͂Ȃ�����",
				"3,�����A���Ȃ�����ςȎv�������Ă���̂�",
				"3,��ƍ������Ă��܂��̂��A������̂��A�̂Ă���̂��A���ݗ��R�������Ȃ�̂��|��",
				"3,����Ȃ��Ȃ��ɂƂ��Ă������������@���Ă����", "5,�Ȃ񂾁E�E����́H",
				"3,���t�A���̌ق��傺�[��ԊF�E���ɂ�������āA�L���C�����ς�V���������ɐ��܂�ς��΂����̂�", "5,�I�H",
				"3,�ȒP�Ȃ��Ƃ�A��݂�Ȃ��Q�Â܂��Ă��鎞�A�T�N�b�� ��", "3,��������Ίy�ɂȂ���",
				"5,�S���E�E�E�y�ɁH",
				"3,�������A���Ȃ����]�ޒ�ւ̗򓙊��A�ق��傩��̊��҂�S���̂ċ��邱�Ƃ��ł����",
				"3,��邩���Ȃ����͂��Ȃ����悾����",

				// (����)
				"0,�Z�M�A�S�R�C�Â��Ȃ�����",
				"0,����Ȃɋꂵ��ł��Ȃ��",

				// �n�ʂ��h���
				"0,�Ȃ񂾁A�����", "1,�����������Ȃ��ƂȂɂ��}�Y�������N���肻������", "0,�E�E�E",
				"1,�����A���v�H �h��������H", "0,���܂� ���v���A�Z�M�ɉ���Ă����͖��킸�A�a���", "0,�����A�}����",
				"1,�E�E�E����",

				"9, [2�b END]" };

		voiceName = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
				"30", "41", "42", "43", "44", "45", "46", "47", "48", "49",
				"50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
				"60", "61", "62", "63", "64", "65", "66", "67", "68" };

		voiceID = new int[] {
				// 2�b
				R.raw.rand1, R.raw.md1, R.raw.rand2, R.raw.md2, R.raw.rand3,
				R.raw.md3, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.md4, R.raw.md5, R.raw.rand4, R.raw.md6, R.raw.md7,
				R.raw.md8, R.raw.rand5, R.raw.s1, R.raw.md9, R.raw.s2,
				R.raw.s3, R.raw.s4, R.raw.md10, R.raw.s5, R.raw.s6,
				R.raw.md11, R.raw.rand6, R.raw.s7, R.raw.muon, R.raw.s8,
				R.raw.s9,R.raw.s10, R.raw.md12, R.raw.rand7, R.raw.md13,
				R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.muon, R.raw.md14, R.raw.md15,
				R.raw.md16, R.raw.rand8, R.raw.md17, R.raw.rand9, R.raw.md18,
				R.raw.md19, R.raw.rand10, R.raw.muon,

				// �i���[�V����
				R.raw.n, R.raw.ed };

		result = 27;
		count = -1;
		num = 0;

		if (Stage2.clearFlg) {
			result = 67;
			currentPage = 29;
			count = 28;
			num = 29;
		}

		tx = ty = 0;
	}

	public void update() {
		boolean bgmFlg1 = currentPage >= 7 && currentPage <= 10;
		boolean bgmFlg2 = currentPage >= 36 && currentPage <= 58;
		if (bgmFlg1 || bgmFlg2) {
			sound.play("sad", true, true);
		}
		if (currentPage == 11 || currentPage == 59) {
			sound.pause("sad");
		}
		nextLine();
		soundStop();
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("gamePlay2"), 0, 0);
		allDraw(g);
	}

	public void finish() {
		imageManager.finish("skip");
		imageManager.finish("gamePlay2");
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
			push(27, 67, EScene.STAGE2);
			allChange(27, 67, EScene.STAGE2);
			break;
		}

		return false;
	}

}
