package net.pddin.agc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.KeyEvent;

/**
 * AgcActivity AGameCanvas�p�Q�[���I���_�C�A���O�tActivity
 *
 * �p�����AGameCanvas�C���X�^���X�𐶐����āi�Z�b�g�j���Ďg�p���܂��B �Q�[���I���_�C�A���O�ɂ��ẮA�^�C�g���Ȃǂ��ݒ�\�ł��B
 * AGameCanvas����AlertDialog���Ăяo�����߂�postRunnable���������Ă���܂��B
 *
 * @author HARADA Toshinobu
 * @version 0.1 (H26/01/11)
 */
public class AgcActivity extends Activity {
	protected AGameCanvas agc = null;

	/**
	 * setAGC ����AgcActivity�œ��삷��AGameCanvas���x�[�X�Ƃ���C���X�^���X���Z�b�g���܂��B
	 *
	 * @param agc
	 */
	public void setAGC(AGameCanvas agc) {
		this.agc = agc;
	}

	// AGameCanvas����AlertDialog���Ăяo�����߂ɗ��p���邽�߂̂��́B
	private Handler handler = new Handler();

	public void postRunnable(final Runnable run) {
		handler.post(run);
	}

	private String finishDialogTitle = "�I�����܂����H";

	/**
	 * setFinishDialogTitle �Q�[���I���_�C�A���O�̃^�C�g����ݒ肵�܂�
	 */
	public void setFinishDialogTitle(String finishDialogTitle) {
		this.finishDialogTitle = finishDialogTitle;
	}

	private String finishDialogPosMes = "�͂�";

	/**
	 * setFinishDialogPosMes �Q�[���I���_�C�A���O�́u�͂��v��ύX���܂��B
	 *
	 * @param finishDialogPosMes
	 */
	public void setFinishDialogPosMes(String finishDialogPosMes) {
		this.finishDialogPosMes = finishDialogPosMes;
	}

	private String finishDialogNegMes = "������";

	/**
	 * setFinishDialogNegMes �Q�[���I���_�C�A���O�́u�������v��ύX���܂��B
	 *
	 * @param finishDialogPosMes
	 */
	public void setFinishDialogNegMes(String finishDialogNegMes) {
		this.finishDialogNegMes = finishDialogNegMes;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (agc == null) {
			return super.dispatchKeyEvent(event); // agc��null�Ȃ�΂���ȉ��̏��������s���Ȃ��B
		}
		// �L�[�C�x���g�Ƃ��āA�����������ꂽ��A�A�A
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			// �o�b�N�L�[�Ȃ̂��H�o�b�N�L�[��������A�A�A
			if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				agc.onPause(); // �Q�[���L�����o�X���|�[�Y�i�ꎞ��~��ԂɁj

				// �_�C�A���O���o���āA�I���m�F�Ȃǂ�����B
				// �_�C�A���O�����uAlertDialog.Builder�v�����
				AlertDialog.Builder adb = new AlertDialog.Builder(this);

				if (finishDialogTitle == null) {
					finishDialogTitle = "Finish the Game";
				}
				adb.setTitle(finishDialogTitle); // �^�C�g���̐ݒ�
				// adb.setMessage("���邯�ǂ����́H"); //���b�Z�[�W�̐ݒ�

				// �͂��������ꂽ�Ƃ��̐ݒ�
				if (finishDialogPosMes == null) {
					finishDialogPosMes = "YES";
				}
				adb.setPositiveButton(finishDialogPosMes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								AgcActivity.this.finish();
							}
						});
				// �������������ꂽ�Ƃ��̐ݒ�
				if (finishDialogNegMes == null) {
					finishDialogNegMes = "NO";
				}
				adb.setNegativeButton(finishDialogNegMes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								agc.offPause();
							}
						});
				adb.setCancelable(false); // �A���[�g�_�C�A���O����o�b�N�L�[�̏�����r��
				AlertDialog ad = adb.create(); // �_�C�A���O�𐶐�
				ad.show(); // �_�C�A���O�̕\��
			}
		}
		return super.dispatchKeyEvent(event);
	}

}
