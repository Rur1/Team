package net.pddin.agc;

import android.graphics.Bitmap;
import android.graphics.Paint;

/**
 * Image�N���X�iAndroidGameCanvas�p�j DoJa(i-mode
 * Java)��Image�N���X���C�N��Bitmap&Paint���b�p�[�N���X
 *
 * @author HARADA Toshinobu
 * @version 1.0 (H23/06/10)
 */
/*
 * �ȉ��A���������\�b�h
 *
 * static Image createImage(int width, int height) �C���[�W��V���ɐ������܂��B
 *
 * static Image createImage(int width, int height, int[] data, int off)
 * RGB�z����w�肵�ăC���[�W�I�u�W�F�N�g�𐶐����܂��B
 *
 * Graphics getGraphics() �C���[�W�ɕ`�悷�邽�߂̃O���t�B�b�N�X�I�u�W�F�N�g���擾���܂��B
 *
 * int getTransparentColor() setTransparentColor(int) �ɂĎw�肳��Ă��铧�ߐF���擾���܂��B
 *
 * void setTransparentColor(int color) ���ߐF���w�肵�܂��B
 *
 * void setTransparentEnabled(boolean enabled) ���ߐF�w��̗L���E������ݒ肵�܂��B
 */
public class Image {
	private Bitmap bmp;
	private Paint paint;

	/**
	 * Image�R���X�g���N�^ �����Ŏw�肵��Bitmap�I�u�W�F�N�g�����Ƃ�Image�I�u�W�F�N�g�𐶐����܂�
	 *
	 * @param bmp
	 */
	public Image(Bitmap bmp) {
		this.bmp = bmp;
		this.paint = new Paint(); // Image�I�u�W�F�N�g�ɂ͌ŗL��paint�I�u�W�F�N�g���������Ă����B���ꂼ��Ƀ��l�Ȃǂ�ݒ肷�邽�߁B
	}

	/**
	 * getHeight �C���[�W�I�u�W�F�N�g�̏c�̃T�C�Y�iHeight�j��Ԃ��܂� �����Bitmap�I�u�W�F�N�g�̏c�̃T�C�Y�����̂܂ܕԂ��܂��B
	 *
	 * @return height�i�c�̃T�C�Y�j
	 */
	public int getHeight() {
		return this.bmp.getHeight();
	}

	/**
	 * getWidth �C���[�W�I�u�W�F�N�g�̉��̃T�C�Y�iwidth�j��Ԃ��܂� �����Bitmap�I�u�W�F�N�g�̉��̃T�C�Y�����̂܂ܕԂ��܂��B
	 *
	 * @return width�i���̃T�C�Y�j
	 */
	public int getWidth() {
		return this.bmp.getWidth();
	}

	public Bitmap getBitmap() {
		return this.bmp;
	}

	/**
	 * setAlpha �C���[�W�I�u�W�F�N�g�Ƀ��l��0�`255�͈̔͂Őݒ肵�܂��B
	 *
	 * @param alpha
	 */
	public void setAlpha(int alpha) {
		this.paint.setAlpha(alpha);
	}

	/**
	 * getAlpha ���ݐݒ肳��Ă��郿�l��Ԃ��܂��B
	 *
	 * @return
	 */
	public int getAlpha() {
		return this.paint.getAlpha();
	}

	public Paint getPaint() {
		return this.paint;
	}

	// �ꉞ �f�[�^�j���p�̃��\�b�h������Ă����B
	/**
	 * dispose �s�v�ɂȂ���Image�I�u�W�F�N�g����������Ƃ��ɌĂяo���܂��B
	 */
	public void dispose() {
		this.bmp.recycle(); // �g�p���Ă���bmp�����
		this.paint = null; // �ꉞ�y�C���g������i�������null�N���A�ŗǂ��Ƃ���B�j
	}
}
